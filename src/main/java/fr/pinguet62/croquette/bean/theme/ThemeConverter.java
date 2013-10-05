package fr.pinguet62.croquette.bean.theme;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * The {@link Theme} converter.<br />
 * Uses the <code>key</code> as an identifier.
 */
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
    public Object getAsObject(final FacesContext context,
	    final UIComponent component, final String value) {
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
    public String getAsString(final FacesContext context,
	    final UIComponent component, final Object value) {
	Theme theme = (Theme) value;
	return theme.getKey();
    }

}
