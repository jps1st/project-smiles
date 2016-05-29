/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.BedCurriculum;
import entities.services.BedCurriculumService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author John Philip
 */
@FacesConverter(forClass = entities.BedCurriculum.class)
public class BedCurriculumConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            BedCurriculum f = BedCurriculumService.getInstance().find(Integer.parseInt(value));
            return f;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        BedCurriculum c = (BedCurriculum) value;
        return c.getCurricode() + "";

    }
}
