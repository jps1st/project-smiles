/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.BedCurriculumDetail;
import entities.services.BedCurriculumDetailService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = entities.BedCurriculumDetail.class)
public class BedCurriDetailConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value);
            BedCurriculumDetail find = BedCurriculumDetailService.getInstance().find(id);
            return find;
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
        
        BedCurriculumDetail detail = (BedCurriculumDetail) value;
        
        return detail.getId() + "";
    }
}
