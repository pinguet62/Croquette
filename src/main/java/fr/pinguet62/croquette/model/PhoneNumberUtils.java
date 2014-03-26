package fr.pinguet62.croquette.model;

public class PhoneNumberUtils {

    private static class CountryCallingCodeAndNewIndex {
	public final int countryCallingCode;
	public final int newIndex;

	public CountryCallingCodeAndNewIndex(int countryCode, int newIndex) {
	    countryCallingCode = countryCode;
	    this.newIndex = newIndex;
	}
    }

    /*
     * If true, the number is country calling code.
     */
    private static final boolean COUNTRY_CALLING_CALL[] = { true, true, false,
	false, false, false, false, true, false, false, false, false,
	false, false, false, false, false, false, false, false, true,
	false, false, false, false, false, false, true, true, false, true,
	true, true, true, true, false, true, false, false, true, true,
	false, false, true, true, true, true, true, true, true, false,
	true, true, true, true, true, true, true, true, false, true, true,
	true, true, true, true, true, false, false, false, false, false,
	false, false, false, false, false, false, false, false, false,
	true, true, true, true, false, true, false, false, true, true,
	true, true, true, true, true, false, false, true, false, };

    private static final int CCC_LENGTH = COUNTRY_CALLING_CALL.length;
    
    // Three and four digit phone numbers for either special services,
    // or 3-6 digit addresses from the network (eg carrier-originated SMS
    // messages) should
    // not match.
    //
    // This constant used to be 5, but SMS short codes has increased in length
    // and
    // can be easily 6 digits now days. Most countries have SMS short code
    // length between
    // 3 to 6 digits. The exceptions are
    //
    // Australia: Short codes are six or eight digits in length, starting with
    // the prefix "19"
    // followed by an additional four or six digits and two.
    // Czech Republic: Codes are seven digits in length for MO and five (not
    // billed) or
    // eight (billed) for MT direction
    //
    // see http://en.wikipedia.org/wiki/Short_code#Regional_differences for
    // reference
    //
    // However, in order to loose match 650-555-1212 and 555-1212, we need to
    // set the min match
    // to 7.
    static final int MIN_MATCH = 7;

    /*
     * Special characters
     *
     * (See "What is a phone number?" doc) 'p' --- GSM pause character, same as
     * comma 'n' --- GSM wild character 'w' --- GSM wait character
     */
    public static final char PAUSE = ',';

    public static final char WAIT = ';';

    public static final char WILD = 'N';

    /**
     * Return true if the prefix of "str" is "ignorable". Here, "ignorable"
     * means that "str" has only one digit and separator characters. The one
     * digit is assumed to be trunk prefix.
     */
    private static boolean checkPrefixIsIgnorable(final String str,
	    int forwardIndex, int backwardIndex) {
	boolean trunk_prefix_was_read = false;
	while (backwardIndex >= forwardIndex) {
	    if (tryGetISODigit(str.charAt(backwardIndex)) >= 0) {
		if (trunk_prefix_was_read)
		    // More than one digit appeared, meaning that "a" and "b"
		    // is different.
		    return false;
		else
		    // Ignore just one digit, assuming it is trunk prefix.
		    trunk_prefix_was_read = true;
	    } else if (isDialable(str.charAt(backwardIndex)))
		// Trunk prefix is a digit, not "*", "#"...
		return false;
	    backwardIndex--;
	}

	return true;
    }

    /**
     * Compare phone numbers a and b, return true if they're identical enough
     * for caller ID purposes.
     */
    public static boolean compare(String a, String b) {
	// We've used loose comparation at least Eclair, which may change in the
	// future.

	return compare(a, b, false);
    }

    /**
     * @hide only for testing.
     */
    public static boolean compare(String a, String b,
	    boolean useStrictComparation) {
	return (useStrictComparation ? compareStrictly(a, b) : compareLoosely(
		a, b));
    }

    /**
     * Compare phone numbers a and b, return true if they're identical enough
     * for caller ID purposes.
     *
     * - Compares from right to left - requires MIN_MATCH (7) characters to
     * match - handles common trunk prefixes and international prefixes
     * (basically, everything except the Russian trunk prefix)
     *
     * Note that this method does not return false even when the two phone
     * numbers are not exactly same; rather; we can call this method
     * "similar()", not "equals()".
     *
     * @hide
     */
    public static boolean compareLoosely(String a, String b) {
	int ia, ib;
	int matched;
	int numNonDialableCharsInA = 0;
	int numNonDialableCharsInB = 0;

	if ((a == null) || (b == null))
	    return a == b;

	if ((a.length() == 0) || (b.length() == 0))
	    return false;

	ia = indexOfLastNetworkChar(a);
	ib = indexOfLastNetworkChar(b);
	matched = 0;

	while ((ia >= 0) && (ib >= 0)) {
	    char ca, cb;
	    boolean skipCmp = false;

	    ca = a.charAt(ia);

	    if (!isDialable(ca)) {
		ia--;
		skipCmp = true;
		numNonDialableCharsInA++;
	    }

	    cb = b.charAt(ib);

	    if (!isDialable(cb)) {
		ib--;
		skipCmp = true;
		numNonDialableCharsInB++;
	    }

	    if (!skipCmp) {
		if ((cb != ca) && (ca != WILD) && (cb != WILD))
		    break;
		ia--;
		ib--;
		matched++;
	    }
	}

	if (matched < MIN_MATCH) {
	    int effectiveALen = a.length() - numNonDialableCharsInA;
	    int effectiveBLen = b.length() - numNonDialableCharsInB;

	    // if the number of dialable chars in a and b match, but the matched
	    // chars < MIN_MATCH,
	    // treat them as equal (i.e. 404-04 and 40404)
	    if ((effectiveALen == effectiveBLen) && (effectiveALen == matched))
		return true;

	    return false;
	}

	// At least one string has matched completely;
	if ((matched >= MIN_MATCH) && ((ia < 0) || (ib < 0)))
	    return true;

	/*
	 * Now, what remains must be one of the following for a match:
	 *
	 * - a '+' on one and a '00' or a '011' on the other - a '0' on one and
	 * a (+,00)<country code> on the other (for this, a '0' and a '00'
	 * prefix would have succeeded above)
	 */

	if (matchIntlPrefix(a, ia + 1) && matchIntlPrefix(b, ib + 1))
	    return true;

	if (matchTrunkPrefix(a, ia + 1) && matchIntlPrefixAndCC(b, ib + 1))
	    return true;

	if (matchTrunkPrefix(b, ib + 1) && matchIntlPrefixAndCC(a, ia + 1))
	    return true;

	return false;
    }

    /**
     * @hide
     */
    public static boolean compareStrictly(String a, String b) {
	return compareStrictly(a, b, true);
    }

    /**
     * @hide
     */
    public static boolean compareStrictly(String a, String b,
	    boolean acceptInvalidCCCPrefix) {
	if ((a == null) || (b == null))
	    return a == b;
	else if ((a.length() == 0) && (b.length() == 0))
	    return false;

	int forwardIndexA = 0;
	int forwardIndexB = 0;

	CountryCallingCodeAndNewIndex cccA = tryGetCountryCallingCodeAndNewIndex(
		a, acceptInvalidCCCPrefix);
	CountryCallingCodeAndNewIndex cccB = tryGetCountryCallingCodeAndNewIndex(
		b, acceptInvalidCCCPrefix);
	boolean bothHasCountryCallingCode = false;
	boolean okToIgnorePrefix = true;
	boolean trunkPrefixIsOmittedA = false;
	boolean trunkPrefixIsOmittedB = false;
	if ((cccA != null) && (cccB != null)) {
	    if (cccA.countryCallingCode != cccB.countryCallingCode)
		// Different Country Calling Code. Must be different phone
		// number.
		return false;
	    // When both have ccc, do not ignore trunk prefix. Without this,
	    // "+81123123" becomes same as "+810123123" (+81 == Japan)
	    okToIgnorePrefix = false;
	    bothHasCountryCallingCode = true;
	    forwardIndexA = cccA.newIndex;
	    forwardIndexB = cccB.newIndex;
	} else if ((cccA == null) && (cccB == null))
	    // When both do not have ccc, do not ignore trunk prefix. Without
	    // this,
	    // "123123" becomes same as "0123123"
	    okToIgnorePrefix = false;
	else {
	    if (cccA != null)
		forwardIndexA = cccA.newIndex;
	    else {
		int tmp = tryGetTrunkPrefixOmittedIndex(b, 0);
		if (tmp >= 0) {
		    forwardIndexA = tmp;
		    trunkPrefixIsOmittedA = true;
		}
	    }
	    if (cccB != null)
		forwardIndexB = cccB.newIndex;
	    else {
		int tmp = tryGetTrunkPrefixOmittedIndex(b, 0);
		if (tmp >= 0) {
		    forwardIndexB = tmp;
		    trunkPrefixIsOmittedB = true;
		}
	    }
	}

	int backwardIndexA = a.length() - 1;
	int backwardIndexB = b.length() - 1;
	while ((backwardIndexA >= forwardIndexA)
		&& (backwardIndexB >= forwardIndexB)) {
	    boolean skip_compare = false;
	    final char chA = a.charAt(backwardIndexA);
	    final char chB = b.charAt(backwardIndexB);
	    if (isSeparator(chA)) {
		backwardIndexA--;
		skip_compare = true;
	    }
	    if (isSeparator(chB)) {
		backwardIndexB--;
		skip_compare = true;
	    }

	    if (!skip_compare) {
		if (chA != chB)
		    return false;
		backwardIndexA--;
		backwardIndexB--;
	    }
	}

	if (okToIgnorePrefix) {
	    if ((trunkPrefixIsOmittedA && (forwardIndexA <= backwardIndexA))
		    || !checkPrefixIsIgnorable(a, forwardIndexA, backwardIndexA))
		if (acceptInvalidCCCPrefix)
		    // Maybe the code handling the special case for Thailand
		    // makes the
		    // result garbled, so disable the code and try again.
		    // e.g. "16610001234" must equal to "6610001234", but with
		    // Thailand-case handling code, they become equal to each
		    // other.
		    //
		    // Note: we select simplicity rather than adding some
		    // complicated
		    // logic here for performance(like "checking whether
		    // remaining
		    // numbers are just 66 or not"), assuming inputs are small
		    // enough.
		    return compare(a, b, false);
		else
		    return false;
	    if ((trunkPrefixIsOmittedB && (forwardIndexB <= backwardIndexB))
		    || !checkPrefixIsIgnorable(b, forwardIndexA, backwardIndexB))
		if (acceptInvalidCCCPrefix)
		    return compare(a, b, false);
		else
		    return false;
	} else {
	    // In the US, 1-650-555-1234 must be equal to 650-555-1234,
	    // while 090-1234-1234 must not be equal to 90-1234-1234 in Japan.
	    // This request exists just in US (with 1 trunk (NDD) prefix).
	    // In addition, "011 11 7005554141" must not equal to
	    // "+17005554141",
	    // while "011 1 7005554141" must equal to "+17005554141"
	    //
	    // In this comparison, we ignore the prefix '1' just once, when
	    // - at least either does not have CCC, or
	    // - the remaining non-separator number is 1
	    boolean maybeNamp = !bothHasCountryCallingCode;
	    while (backwardIndexA >= forwardIndexA) {
		final char chA = a.charAt(backwardIndexA);
		if (isDialable(chA))
		    if (maybeNamp && (tryGetISODigit(chA) == 1))
			maybeNamp = false;
		    else
			return false;
		backwardIndexA--;
	    }
	    while (backwardIndexB >= forwardIndexB) {
		final char chB = b.charAt(backwardIndexB);
		if (isDialable(chB))
		    if (maybeNamp && (tryGetISODigit(chB) == 1))
			maybeNamp = false;
		    else
			return false;
		backwardIndexB--;
	    }
	}

	return true;
    }

    /**
     * index of the last character of the network portion (eg anything after is
     * a post-dial string)
     */
    static private int indexOfLastNetworkChar(String a) {
	int pIndex, wIndex;
	int origLength;
	int trimIndex;

	origLength = a.length();

	pIndex = a.indexOf(PAUSE);
	wIndex = a.indexOf(WAIT);

	trimIndex = minPositive(pIndex, wIndex);

	if (trimIndex < 0)
	    return origLength - 1;
	else
	    return trimIndex - 1;
    }

    /**
     * @return true when input is valid Country Calling Code.
     */
    private static boolean isCountryCallingCode(int countryCallingCodeCandidate) {
	return (countryCallingCodeCandidate > 0)
		&& (countryCallingCodeCandidate < CCC_LENGTH)
		&& COUNTRY_CALLING_CALL[countryCallingCodeCandidate];
    }

    /** True if c is ISO-LATIN characters 0-9, *, # , +, WILD */
    public final static boolean isDialable(char c) {
	return ((c >= '0') && (c <= '9')) || (c == '*') || (c == '#')
		|| (c == '+') || (c == WILD);
    }

    /** True if c is ISO-LATIN characters 0-9 */
    public static boolean isISODigit(char c) {
	return (c >= '0') && (c <= '9');
    }

    /** True if c is ISO-LATIN characters 0-9, *, # , +, WILD, WAIT, PAUSE */
    public final static boolean isNonSeparator(char c) {
	return ((c >= '0') && (c <= '9')) || (c == '*') || (c == '#')
		|| (c == '+') || (c == WILD) || (c == WAIT) || (c == PAUSE);
    }

    /** Returns true if ch is not dialable or alpha char */
    private static boolean isSeparator(char ch) {
	return !isDialable(ch)
		&& !((('a' <= ch) && (ch <= 'z')) || (('A' <= ch) && (ch <= 'Z')));
    }

    /**
     * Phone numbers are stored in "lookup" form in the database as reversed
     * strings to allow for caller ID lookup
     *
     * This method takes a phone number and makes a valid SQL "LIKE" string that
     * will match the lookup form
     *
     */
    /**
     * all of a up to len must be an international prefix or
     * separators/non-dialing digits
     */
    private static boolean matchIntlPrefix(String a, int len) {
	/* '([^0-9*#+pwn]\+[^0-9*#+pwn] | [^0-9*#+pwn]0(0|11)[^0-9*#+pwn] )$' */
	/* 0 1 2 3 45 */

	int state = 0;
	for (int i = 0; i < len; i++) {
	    char c = a.charAt(i);

	    switch (state) {
	    case 0:
		if (c == '+')
		    state = 1;
		else if (c == '0')
		    state = 2;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 2:
		if (c == '0')
		    state = 3;
		else if (c == '1')
		    state = 4;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 4:
		if (c == '1')
		    state = 5;
		else if (isNonSeparator(c))
		    return false;
		break;

	    default:
		if (isNonSeparator(c))
		    return false;
		break;

	    }
	}

	return (state == 1) || (state == 3) || (state == 5);
    }

    /**
     * all of 'a' up to len must be a (+|00|011)country code) We're fast and
     * loose with the country code. Any \d{1,3} matches
     */
    private static boolean matchIntlPrefixAndCC(String a, int len) {
	/* [^0-9*#+pwn]*(\+|0(0|11)\d\d?\d? [^0-9*#+pwn] $ */
	/* 0 1 2 3 45 6 7 8 */

	int state = 0;
	for (int i = 0; i < len; i++) {
	    char c = a.charAt(i);

	    switch (state) {
	    case 0:
		if (c == '+')
		    state = 1;
		else if (c == '0')
		    state = 2;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 2:
		if (c == '0')
		    state = 3;
		else if (c == '1')
		    state = 4;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 4:
		if (c == '1')
		    state = 5;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 1:
	    case 3:
	    case 5:
		if (isISODigit(c))
		    state = 6;
		else if (isNonSeparator(c))
		    return false;
		break;

	    case 6:
	    case 7:
		if (isISODigit(c))
		    state++;
		else if (isNonSeparator(c))
		    return false;
		break;

	    default:
		if (isNonSeparator(c))
		    return false;
	    }
	}

	return (state == 6) || (state == 7) || (state == 8);
    }

    /** all of 'a' up to len must match non-US trunk prefix ('0') */
    private static boolean matchTrunkPrefix(String a, int len) {
	boolean found;

	found = false;

	for (int i = 0; i < len; i++) {
	    char c = a.charAt(i);

	    if ((c == '0') && !found)
		found = true;
	    else if (isNonSeparator(c))
		return false;
	}

	return found;
    }

    /** or -1 if both are negative */
    static private int minPositive(int a, int b) {
	if ((a >= 0) && (b >= 0))
	    return (a < b) ? a : b;
	else if (a >= 0)
	    return a;
	else if (b >= 0)
	    return b;
	else
	    return -1;
    }

    /*
     * Note that this function does not strictly care the country calling code
     * with 3 length (like Morocco: +212), assuming it is enough to use the
     * first two digit to compare two phone numbers.
     */
    private static CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex(
	    String str, boolean acceptThailandCase) {
	// Rough regexp:
	// ^[^0-9*#+]*((\+|0(0|11)\d\d?|166) [^0-9*#+] $
	// 0 1 2 3 45 6 7 89
	//
	// In all the states, this function ignores separator characters.
	// "166" is the special case for the call from Thailand to the US. Uguu!
	int state = 0;
	int ccc = 0;
	final int length = str.length();
	for (int i = 0; i < length; i++) {
	    char ch = str.charAt(i);
	    switch (state) {
	    case 0:
		if (ch == '+')
		    state = 1;
		else if (ch == '0')
		    state = 2;
		else if (ch == '1') {
		    if (acceptThailandCase)
			state = 8;
		    else
			return null;
		} else if (isDialable(ch))
		    return null;
		break;

	    case 2:
		if (ch == '0')
		    state = 3;
		else if (ch == '1')
		    state = 4;
		else if (isDialable(ch))
		    return null;
		break;

	    case 4:
		if (ch == '1')
		    state = 5;
		else if (isDialable(ch))
		    return null;
		break;

	    case 1:
	    case 3:
	    case 5:
	    case 6:
	    case 7: {
		int ret = tryGetISODigit(ch);
		if (ret > 0) {
		    ccc = (ccc * 10) + ret;
		    if ((ccc >= 100) || isCountryCallingCode(ccc))
			return new CountryCallingCodeAndNewIndex(ccc, i + 1);
		    if ((state == 1) || (state == 3) || (state == 5))
			state = 6;
		    else
			state++;
		} else if (isDialable(ch))
		    return null;
	    }
	    break;
	    case 8:
		if (ch == '6')
		    state = 9;
		else if (isDialable(ch))
		    return null;
		break;
	    case 9:
		if (ch == '6')
		    return new CountryCallingCodeAndNewIndex(66, i + 1);
		else
		    return null;
	    default:
		return null;
	    }
	}

	return null;
    }

    /**
     * Returns integer corresponding to the input if input "ch" is ISO-LATIN
     * characters 0-9. Returns -1 otherwise
     */
    private static int tryGetISODigit(char ch) {
	if (('0' <= ch) && (ch <= '9'))
	    return ch - '0';
	else
	    return -1;
    }

    /**
     * Currently this function simply ignore the first digit assuming it is
     * trunk prefix. Actually trunk prefix is different in each country.
     *
     * e.g. "+79161234567" equals "89161234567" (Russian trunk digit is 8)
     * "+33123456789" equals "0123456789" (French trunk digit is 0)
     *
     */
    private static int tryGetTrunkPrefixOmittedIndex(String str,
	    int currentIndex) {
	int length = str.length();
	for (int i = currentIndex; i < length; i++) {
	    final char ch = str.charAt(i);
	    if (tryGetISODigit(ch) >= 0)
		return i + 1;
	    else if (isDialable(ch))
		return -1;
	}
	return -1;
    }

}
