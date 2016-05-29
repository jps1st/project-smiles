/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.curriculumMain;

import controllers.utils.JsfUtil;
import entities.BedCurriculum;
import entities.BedCurriculumDetail;
import entities.BedSubject;
import entities.services.BedCurriculumDetailService;
import entities.services.BedCurriculumService;
import entities.services.BedEnrollmentDetailService;
import entities.services.BedLoadingService;
import entities.services.BedSubjectService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BedCurriculumController implements Serializable {

    private List<BedCurriculum> items;
    private BedCurriculum current;
    //curri_subjects specifics
    private List<BedCurriculumDetail> subjectsInCurrent;
    private int subjectsInCurrentPage_NavigatingGrade;
    //curri_subject_view_edit specifics
    private BedCurriculumDetail currentBedCurriDetail;

    //curri_remove
    private String curri_remove_RemoveMessage;
    private boolean curri_remove_AllowRemove;

    //curri_remove
    private String curri_subject_remove_RemoveMessage;
    private boolean curri_subject_remove_AllowRemove;

    /**
     * @return the subjectsInCurrentPage_NavigatingGrade
     */
    public int getSubjectsInCurrentPage_NavigatingGrade() {
        return subjectsInCurrentPage_NavigatingGrade;
    }

    /**
     * @param subjectsInCurrentPage_NavigatingGrade the
     * subjectsInCurrentPage_NavigatingGrade to set
     */
    public void setSubjectsInCurrentPage_NavigatingGrade(int subjectsInCurrentPage_NavigatingGrade) {
        this.subjectsInCurrentPage_NavigatingGrade = subjectsInCurrentPage_NavigatingGrade;
    }

    /**
     * @return the items
     */
    public List<BedCurriculum> getItems() {
        if (items == null) {
            retrieveItems("");
        }
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<BedCurriculum> items) {
        this.items = items;
    }

    /**
     * @return the current
     */
    public BedCurriculum getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(BedCurriculum current) {
        this.current = current;
    }

    /**
     * @return the subjectsInCurrent
     */
    public List<BedCurriculumDetail> getSubjectsInCurrent() {
        return subjectsInCurrent;
    }

    /**
     * @param subjectsInCurrent the subjectsInCurrent to set
     */
    public void setSubjectsInCurrent(List<BedCurriculumDetail> subjectsInCurrent) {
        this.subjectsInCurrent = subjectsInCurrent;
    }

    //
    //
    //
    //curriculumsMain functions
    //
    //
    //
    private void retrieveItems(String key) {
        items = BedCurriculumService.getInstance().searchCurriculum(key);
    }

    public void prepareSubjectsInCurrent() {
        prepareSubjectsInCurrent(1);
    }

    public void prepareSubjectsInCurrent(int grade) {
        subjectsInCurrentPage_NavigatingGrade = grade;
        subjectsInCurrent = BedCurriculumDetailService.getInstance()
                .getCurriDetails(current.getCurricode(), grade);
    }

    public String showSubjects(BedCurriculum c) {
        current = c;
        prepareSubjectsInCurrent();
        return "curri_subjects.xhtml?faces-redirect=true";
    }

    public String editViewCurriculum(BedCurriculum c) {
        current = c;
        return "curri_view_edit.xhtml?faces-redirect=true";
    }

    public String createNew() {
        current = new BedCurriculum();
        return "curri_view_edit.xhtml?faces-redirect=true";

    }

    //
    //
    //
    //curriculumsMain functions
    //
    //
    //
    public String createNewBedCurriDetail() {
        currentBedCurriDetail = new BedCurriculumDetail();
        currentBedCurriDetail.setCrUnits(0d);
        currentBedCurriDetail.setCurrcode(current);
        currentBedCurriDetail.setDisplayOrder(0);
        currentBedCurriDetail.setYrLevel(subjectsInCurrentPage_NavigatingGrade);
        return "curri_subject_view_edit.xhtml?faces-redirect=true";
    }

    public String editViewBedCurriDetail(BedCurriculumDetail d) {
        currentBedCurriDetail = d;
        return "curri_subject_view_edit.xhtml?faces-redirect=true";
    }

    //
    //
    //
    //curri_view_edit functions
    //
    //
    //
    public String showSubjectsInGrade(int c) {
        prepareSubjectsInCurrent(c);
        return "curri_subjects.xhtml?faces-redirect=true";
    }

    public List<BedSubject> completeSubjects(String key) {
        return BedSubjectService.getInstance().search(key);
    }

    public String curri_view_edit_SaveChanges() {

        try {

            current = BedCurriculumService.getInstance().update(current);
            JsfUtil.addInfoMessage("Changes Saves",
                    "Your changes were successful.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error saving",
                    "A problem occured when saving.");
        }

        retrieveItems("");
        return "curriculumsMain.xhtml?faces-redirect=true";
    }

    public String curri_subject_view_edit_SaveChanges() {

        try {
            currentBedCurriDetail = BedCurriculumDetailService
                    .getInstance().update(currentBedCurriDetail);

            JsfUtil.addInfoMessage("Changes Saves",
                    "Your changes were successful.");

        } catch (Exception e) {
            
            JsfUtil.addErrorMessage("Error saving",
                    "A problem occured when saving.");
        }

        prepareSubjectsInCurrent(subjectsInCurrentPage_NavigatingGrade);

        return "curri_subjects.xhtml?faces-redirect=true";
    }

    /**
     * @return the currentBedCurriDetail
     */
    public BedCurriculumDetail getCurrentBedCurriDetail() {
        return currentBedCurriDetail;
    }

    /**
     * @param currentBedCurriDetail the currentBedCurriDetail to set
     */
    public void setCurrentBedCurriDetail(BedCurriculumDetail currentBedCurriDetail) {
        this.currentBedCurriDetail = currentBedCurriDetail;
    }

    /**
     * @return the curri_remove_RemoveMessage
     */
    public String getCurri_remove_RemoveMessage() {
        return curri_remove_RemoveMessage;
    }

    /**
     * @param curri_remove_RemoveMessage the curri_remove_RemoveMessage to set
     */
    public void setCurri_remove_RemoveMessage(String curri_remove_RemoveMessage) {
        this.curri_remove_RemoveMessage = curri_remove_RemoveMessage;
    }

    /**
     * @return the curri_remove_AllowRemove
     */
    public boolean isCurri_remove_AllowRemove() {
        return curri_remove_AllowRemove;
    }

    /**
     * @param curri_remove_AllowRemove the curri_remove_AllowRemove to set
     */
    public void setCurri_remove_AllowRemove(boolean curri_remove_AllowRemove) {
        this.curri_remove_AllowRemove = curri_remove_AllowRemove;
    }

    //
    //
    //
    //curri_remove specifics
    //
    //
    //
    public String removeCurri(BedCurriculum c) {
        current = c;

        long subjectsUnder = BedCurriculumService.getInstance()
                .countDetails(c.getCurricode());

        curri_remove_AllowRemove = subjectsUnder == 0;

        if (curri_remove_AllowRemove) {
            curri_remove_RemoveMessage
                    = "Removing/deleting curriculum record: ID: "
                    + c.getCurricode()
                    + " or "
                    + current.getStringRepresentation();
        } else {
            curri_remove_RemoveMessage
                    = "Removing/deleting NOT ALLOWED. "
                    + "Curriculum still contains "
                    + subjectsUnder
                    + " subjects.";
        }
        return "curri_remove.xhtml?faces-redirect=true";
    }

    public String removeCurriNow() {
        BedCurriculumService.getInstance().remove(current);
        current = null;
        retrieveItems("");
        return "curriculumsMain.xhtml?faces-redirect=true";
    }

    //
    //
    //
    //curri_remove specifics
    //
    //
    //
    /**
     * @return the curri_subject_remove_RemoveMessage
     */
    public String getCurri_subject_remove_RemoveMessage() {
        return curri_subject_remove_RemoveMessage;
    }

    /**
     * @param curri_subject_remove_RemoveMessage the
     * curri_subject_remove_RemoveMessage to set
     */
    public void setCurri_subject_remove_RemoveMessage(String curri_subject_remove_RemoveMessage) {
        this.curri_subject_remove_RemoveMessage = curri_subject_remove_RemoveMessage;
    }

    /**
     * @return the curri_subject_remove_AllowRemove
     */
    public boolean isCurri_subject_remove_AllowRemove() {
        return curri_subject_remove_AllowRemove;
    }

    /**
     * @param curri_subject_remove_AllowRemove the
     * curri_subject_remove_AllowRemove to set
     */
    public void setCurri_subject_remove_AllowRemove(boolean curri_subject_remove_AllowRemove) {
        this.curri_subject_remove_AllowRemove = curri_subject_remove_AllowRemove;
    }

    public String removeCurriSubject(BedCurriculumDetail sub) {
        currentBedCurriDetail = sub;

        long gradeRecordsI = BedEnrollmentDetailService.getInstance().countUseInEnrollment(currentBedCurriDetail.getId());
        long teacherLoadsI = BedLoadingService.getInstance().countUseInLoading(currentBedCurriDetail.getId());

        curri_subject_remove_AllowRemove = gradeRecordsI == 0 && teacherLoadsI == 0;
        if (curri_subject_remove_AllowRemove) {
            curri_subject_remove_RemoveMessage = "Removing/deleting record: ID: " + sub.getId() + " or " + sub.getSubjcode().getSubjcode() + " in " + current.getShortName();
        } else {
            curri_subject_remove_RemoveMessage = "Removing/deleting NOT ALLOWED. Record is still in use in " + gradeRecordsI + " grade records and " + teacherLoadsI + " teacher loads.";
        }

        return "curri_subject_remove.xhtml?faces-redirect=true";
    }

    public String removeCurriSubjectNow() {
        BedCurriculumDetailService.getInstance().remove(currentBedCurriDetail);
        currentBedCurriDetail = null;
        prepareSubjectsInCurrent(subjectsInCurrentPage_NavigatingGrade);
        return "curri_subjects.xhtml?faces-redirect=true";
    }

}
