/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacherMain;

import controllers.UserManager;
import controllers.utils.JsfUtil;
import entities.BedTeacher;
import entities.services.BedTeacherService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class TeacherMainPageController implements Serializable {

    private String searchKeys;
    private List<BedTeacher> teacherList;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;
    private Integer loggedTeacherId;

    @PostConstruct
    public void init() {

        BedTeacher teacherLogged
                = BedTeacherService.getInstance()
                .findByTeacherId(userManager.getUsername());

        if (teacherLogged != null) {
            loggedTeacherId = teacherLogged.getId();
        }

        search();
    }

    public void search() {
        if (loggedTeacherId == null) {
            teacherList = BedTeacherService.getInstance()
                    .search(searchKeys);
        } else {
            teacherList = new ArrayList<>();
            teacherList
                    .add(BedTeacherService.getInstance()
                            .refresh(
                                    BedTeacherService.getInstance()
                                    .find(loggedTeacherId)
                            )
                    );
        }

    }

    public String createNewTeacher() {
        return "teacher_edit_view.xhtml?faces-redirect=true&id=-1";
    }

    public String editTeacher(Integer id) {
        return "teacher_edit_view.xhtml?faces-redirect=true&id=" + id;
    }

    public String editTeacherLoading(Integer id) {
        return "teacher_loading.xhtml?faces-redirect=true&id=" + id;
    }

    public String resetPassword(Integer id) {
        return "teacher_reset_password.xhtml?faces-redirect=true&id=" + id;
    }

    public String getSearchKeys() {
        return searchKeys;
    }

    public void setSearchKeys(String searchKeys) {
        this.searchKeys = searchKeys;
    }

    public void saveTeacher(BedTeacher teach) {
        BedTeacherService.getInstance().update(teach);
        JsfUtil.addInfoMessage("Saved.", "Your changes were saved.");
    }

    public List<BedTeacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<BedTeacher> teacherList) {
        this.teacherList = teacherList;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

}
