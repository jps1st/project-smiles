/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.BedSubject;
import entities.services.BedSubjectService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter( forClass = entities.BedSubject.class)
public class BedSubjectConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            BedSubject f = BedSubjectService.getInstance().find(Integer.parseInt(value));
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
        try {
            BedSubject s = (BedSubject) value;
            return s.getId() + "";
        } catch (Exception e) {
        }
        return "";
    }
}
