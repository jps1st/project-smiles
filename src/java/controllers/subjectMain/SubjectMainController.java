/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.subjectMain;

import controllers.utils.JsfUtil;
import entities.BedSubject;
import entities.services.BedSubjectService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author John Philip
 */
@ManagedBean
@SessionScoped
public class SubjectMainController implements Serializable {

    private List<BedSubject> data = new ArrayList();
    private BedSubject currentSubject;
    private String keyword;

    //bed_subject_remove
    private String removeMessage;
    private boolean allowRemove;

    public void delete() {
        String usages = getUsageInCurriculum();
        if (!usages.equals("NONE")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Allowed.", "Deleting this entry is not allowed. Usages: " + usages + ".");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        //TODO: should delete here.
    }

    public void search() {
        data = BedSubjectService.getInstance().search(keyword);
        currentSubject = null;
        keyword = "";
    }

    @PostConstruct
    public void refresh() {
        search();

    }

    /**
     * @return the selecteSubject
     */
    public BedSubject getCurrentSubject() {

        return currentSubject;
    }

    /**
     * @param selecteSubject the selecteSubject to set
     */
    public void setCurrentSubject(BedSubject selecteSubject) {

        this.currentSubject = selecteSubject;
    }

    /**
     * @return the data
     */
    public List<BedSubject> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<BedSubject> data) {
        this.data = data;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String createNewSubject() {
        currentSubject = new BedSubject();
        return "bed_subject_edit_view.xhtml?faces-redirect=true";
    }

    public String editViewSubject(BedSubject s) {
        currentSubject = s;
        return "bed_subject_edit_view.xhtml?faces-redirect=true";
    }

    //
    //
    //
    //bed_subject_edit_view specifics
    //
    //
    //
    public String bed_subject_edit_view_SaveChanges() {

        try {
            
            boolean taken = BedSubjectService.getInstance().subjectTaken(currentSubject);
            if (taken) {
                JsfUtil.addWarningMessage("Subject code taken",
                        "Please provide another subject code.");
                return null;
            }

            currentSubject = BedSubjectService.getInstance().update(currentSubject);

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error upon saving.", "An error occured while saving.");
            return null;
        }

        keyword = "";
        search();

        return "subjectMain.xhtml?faces-redirect=true";
    }

    public String getUsageInCurriculum() {

        return BedSubjectService.getInstance().getUsageInCurri(currentSubject);
    }

    /**
     * @return the removeMessage
     */
    public String getRemoveMessage() {
        return removeMessage;
    }

    /**
     * @param removeMessage the removeMessage to set
     */
    public void setRemoveMessage(String removeMessage) {
        this.removeMessage = removeMessage;
    }

    //
    //
    //
    //bed_subject_remove specifics
    //
    //
    //
    public String removeSubject(BedSubject sub) {
        currentSubject = sub;

        String usageInCurriculum = getUsageInCurriculum();
        boolean noUsage = usageInCurriculum.equals("NONE");

        allowRemove = noUsage;

        if (noUsage) {
            removeMessage = "Removing/deleting record: " + currentSubject.getSubjcode() + " = " + currentSubject.getDisplayDesc();
        } else {
            removeMessage = "Remove/delete is NOT ALLOWED. Subject used in: " + usageInCurriculum;
        }
        return "bed_subject_remove.xhtml?faces-redirect=true";
    }

    public String removeSubjectNow() {

        BedSubjectService.getInstance().remove(currentSubject);
        currentSubject = null;
        keyword = "";
        search();
        return "subjectMain.xhtml?faces-redirect=true";

    }

    /**
     * @return the allowRemove
     */
    public boolean isAllowRemove() {
        return allowRemove;
    }

    /**
     * @param allowRemove the allowRemove to set
     */
    public void setAllowRemove(boolean allowRemove) {
        this.allowRemove = allowRemove;
    }
}
