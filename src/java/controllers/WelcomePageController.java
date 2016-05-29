/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author John Philip
 */
@ManagedBean
@ViewScoped
public class WelcomePageController implements Serializable {

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            images.add("gallery" + i + ".jpg");
        }
        Collections.shuffle(images);
    }

    public void preRenderEvent() {

        String userType = userManager.getLoggedUserType();
        JsfUtil.addInfoMessage("Welcome.", "You are logged in as " + userType + ".");
    }

    public List<String> getImages() {
        return images;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * @param userManager the userManager to set
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
