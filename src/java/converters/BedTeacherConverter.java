/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.BedTeacher;
import entities.services.BedTeacherService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter(forClass = entities.BedTeacher.class)
public class BedTeacherConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value.trim());
            BedTeacher f = BedTeacherService.getInstance().find(id);
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "n/a";
        }
        BedTeacher s = (BedTeacher) value;
        return s.getId() + "";
    }
}
