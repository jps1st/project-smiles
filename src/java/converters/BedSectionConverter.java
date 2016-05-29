/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.BedSections;
import entities.services.BedSectionService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author John Philip
 */
@FacesConverter(forClass = entities.BedSections.class)
public class BedSectionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            BedSections f = BedSectionService.getInstance().find(Integer.parseInt(value));
            return f;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "n/a";
        }
        BedSections s = (BedSections) value;
        return s.getId() + "";
    }
}
