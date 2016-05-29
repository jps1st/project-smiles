/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacherMain;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class TeacherProfilePageController implements Serializable {

    private Integer teacherId;

    @PostConstruct
    public void init() {
        String val
                = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("id");

        this.teacherId
                = (val == null || val.trim().matches("-1"))
                ? null
                : Integer.parseInt(val.trim());

    }

}
