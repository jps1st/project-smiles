/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.JdStudent;
import entities.services.BedStudentService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author John Philip
 */
@FacesConverter(forClass = entities.JdStudent.class)
public class StudentConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            JdStudent f = BedStudentService.getInstance().find(value);
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
        JdStudent s = (JdStudent) value;
        return s.getStdntid();
    }
}
