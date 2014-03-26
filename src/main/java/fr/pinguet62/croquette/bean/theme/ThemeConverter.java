package fr.pinguet62.croquette.bean.theme;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * The {@link Theme} converter.<br />
 * Uses the <code>key</code> as an identifier.
 */
@FacesConverter("themeConverter")
public final class ThemeConverter implements Converter {

    /**
     * Convert {@link String} to {@link Theme}.
     *
     * @param context
     *            The context.
     * @param component
     *            The component.
     * @param value
     *            The string value.
     * @return The theme value, <code>null</code> if not find.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	return Theme.fromKey(value);
    }

    /**
     * Convert {@link Theme} to {@link String}.
     *
     * @param context
     *            The context.
     * @param component
     *            The component.
     * @param value
     *            The object value.
     * @return The string value.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	Theme theme = (Theme) value;
	return theme.getKey();
    }

}
