package fr.pinguet62.croquette.android.oauth;

import java.io.Serializable;

import org.apache.harmony.javax.security.auth.callback.Callback;

public class TextInputCallback implements Callback, Serializable {

    private static final long serialVersionUID = 1;

    private String defaultText;

    private String inputText;

    private String prompt;

    public TextInputCallback(String prompt) {
        setPrompt(prompt);
    }

    public TextInputCallback(String prompt, String defaultText) {
        setPrompt(prompt);
        setDefaultText(defaultText);
    }

    public String getDefaultText() {
        return defaultText;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getText() {
        return inputText;
    }

    private void setDefaultText(String defaultText) {
        if ((defaultText == null) || defaultText.isEmpty())
            throw new IllegalArgumentException("auth.15"); //$NON-NLS-1$
        this.defaultText = defaultText;
    }

    private void setPrompt(String prompt) {
        if ((prompt == null) || prompt.isEmpty())
            throw new IllegalArgumentException("auth.14"); //$NON-NLS-1$
        this.prompt = prompt;
    }

    public void setText(String text) {
        inputText = text;
    }
}