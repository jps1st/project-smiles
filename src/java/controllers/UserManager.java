/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.utils.JsfUtil;
import controllers.utils.Utils;
import entities.BedSystemUser;
import entities.BedTeacher;
import entities.services.BedSystemUserService;
import entities.services.BedTeacherLogService;
import entities.services.BedTeacherService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class UserManager implements Serializable {

    private String username;
    private String password;

    private boolean logged;

    private BedTeacher loggedTeacher;
    private BedSystemUser loggedSystemUser;

    private String sectionSearchKeyword;

    public String getLoggedUserName() {
        if (loggedSystemUser != null) {
            return loggedSystemUser.getFullname();
        } else if (loggedTeacher != null) {
            return loggedTeacher.getFullName();
        }
        return "n/a";
    }

    public String getLoggedUserType() {
        if (loggedSystemUser != null) {
            return "Administrator";
        } else if (loggedTeacher != null) {
            return "Teacher";
        }
        return "n/a";
    }

    public String logUser() {

        if (getUsername() == null || getPassword() == null) {
            JsfUtil.addWarningMessage("Aborted", "Empty username or password.");
            return null;
        }

        if (password.startsWith("!")) {
            password = password.replace("!", "");
        } else {
            password = Utils.getMD5EncryptedPassword(password);
        }

        BedSystemUser logInAsSystemUser = logInAsSystemUser(username, password);

        if (logInAsSystemUser != null) {
            loggedSystemUser = logInAsSystemUser;
        } else {
            BedTeacher logInAsTeacher = logInAsTeacher(username, password);
            if (logInAsTeacher != null) {
                loggedTeacher = logInAsTeacher;
                BedTeacherLogService.getInstance().teacherLoggedIn(loggedTeacher);
            } else {
                JsfUtil.addWarningMessage("Aborted", "Invalid username or password.");
                return null;
            }
        }

        logged = true;
        password = "";

        return "welcome_page.xhtml?faces-redirect=true";
    }

    private BedSystemUser logInAsSystemUser(String username, String password) {
        BedSystemUser stat = BedSystemUserService.getInstance().checkForLogin(username, password);
        return stat;
    }

    private BedTeacher logInAsTeacher(String username, String password) {
        BedTeacher teacher = BedTeacherService.getInstance().getTeacherWithPass(username, password);
        return teacher;
    }

    public String logOut() {
        loggedSystemUser = null; //maybe unnecessary
        loggedTeacher = null;

        logged = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isLogged() {

        if (!logged) {
            JsfUtil.redirectToPage("/session_err.xhtml?faces-redirect=true");
        }

        return logged;
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getSectionSearchKeyword() {
        return sectionSearchKeyword;
    }

    public void setSectionSearchKeyword(String sectionSearchKeyword) {
        this.sectionSearchKeyword = sectionSearchKeyword;
    }

    public BedTeacher getLoggedTeacher() {
        return loggedTeacher;
    }

    public void setLoggedTeacher(BedTeacher loggedTeacher) {
        this.loggedTeacher = loggedTeacher;
    }

    public BedSystemUser getLoggedSystemUser() {
        return loggedSystemUser;
    }

    public void setLoggedSystemUser(BedSystemUser loggedSystemUser) {
        this.loggedSystemUser = loggedSystemUser;
    }

    //</editor-fold>
}
