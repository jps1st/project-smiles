/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacherMain;

import controllers.UserManager;
import controllers.utils.JsfUtil;
import controllers.utils.Utils;
import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedLoading;
import entities.BedSections;
import entities.BedTeacher;
import entities.services.BedEnrollmentDetailService;
import entities.services.BedLoadingService;
import entities.services.BedSectionService;
import entities.services.BedSettingsService;
import entities.services.BedTeacherService;
import static entities.services.BedTeacherService.createRandomTeacherPassword;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@SessionScoped
public class TeacherMainController implements Serializable {

    private List<BedTeacher> teacherList;
    private BedTeacher currentTeacher;
    private String currentTeacherOldPass;
    private String currentTeacherNewPass;
    private String currentTeacherConfPass;
    private String teacherMain_SearckKey;
    //grade entry specifics
    private String viewOptions = "0";
    private String gradeEntryTblLength = "900";
    private boolean showFirstQ;
    private boolean showSecondQ;
    private boolean showThirdQ;
    private boolean showFourthQ;
    private boolean showFinals = true;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    @PostConstruct
    public void init() {
        BedTeacher found = BedTeacherService.getInstance().findByTeacherId(userManager.getUsername());
        if (found != null) {
            currentTeacher = found;
            updateTeacherLoads();
        }
        search();
    }

    public void updateGradeEntryView() {
        int p = Integer.parseInt(viewOptions);
        gradeEntryTblLength = "590";
        switch (p) {
            case 0:
                showFirstQ = true;
                showSecondQ = true;
                showThirdQ = true;
                showFourthQ = true;
                showFinals = true;
                gradeEntryTblLength = "900";
                break;
            case 1:
                showFirstQ = true;
                showSecondQ = false;
                showThirdQ = false;
                showFourthQ = false;
                showFinals = false;
                break;
            case 2:
                showFirstQ = false;
                showSecondQ = true;
                showThirdQ = false;
                showFourthQ = false;
                showFinals = false;
                break;

            case 3:
                showFirstQ = false;
                showSecondQ = false;
                showThirdQ = true;
                showFourthQ = false;
                showFinals = false;
                break;

            case 4:
                showFirstQ = false;
                showSecondQ = false;
                showThirdQ = false;
                showFourthQ = true;
                showFinals = false;
                break;
        }
    }

    //remove
    private String teacher_remove_RemoveMessage;
    private boolean teacher_remove_AllowRemove;
    private String newPassword;

    //load
    private List<BedLoading> currentTeacherLoad;
    private BedSections selectedSection;
    private BedCurriculumDetail selectedSubject;

    //grade entry
    private BedLoading currentTeacherLoading;
    private List<BedEnrollmentdetails> currentTeacherLoadingStudentRecords;
    private List<BedEnrollmentdetails> currentTeacherLoadingStudentRecordsF;
    private String maleGradeEntryHashValue;
    private String femaleGradeEntryHashValue;

    DecimalFormat f = new DecimalFormat("##.#");

    public void updateHashValues() { //improve this
        maleGradeEntryHashValue = getHashValue(currentTeacherLoadingStudentRecords);
        femaleGradeEntryHashValue = getHashValue(currentTeacherLoadingStudentRecordsF);
    }

    private String getHashValue(List<BedEnrollmentdetails> list) {
        double gp1 = 0;
        double gp2 = 0;
        double gp3 = 0;
        double gp4 = 0;
        int count = 0;
        for (BedEnrollmentdetails r : list) {
            count++;
            gp1 += count * (r.getP1() != null ? r.getP1() : 0);
            gp2 += count * (r.getP2() != null ? r.getP2() : 0);
            gp3 += count * (r.getP3() != null ? r.getP3() : 0);
            gp4 += count * (r.getP4() != null ? r.getP4() : 0);
        }

        return "1st=" + f.format(gp1) + "     2nd=" + f.format(gp2) + "     3rd=" + f.format(gp3) + " 4th=" + f.format(gp4) + "";
    }

    public void saveTeacher(BedTeacher teach) {
        BedTeacherService.getInstance().update(teach);
        JsfUtil.addInfoMessage("Saved.", "Your changes were saved.");
    }

    public void performSaveGradeRecord(BedEnrollmentdetails detail) {
        BedEnrollmentDetailService.getInstance().update(detail);
        updateHashValues();
        BedEnrollmentDetailService.getInstance().updateParentDetail(detail);
        JsfUtil.addInfoMessage("Saved.", "Saved.");
    }

    public String showGradeEntry(BedLoading datum) {
        viewOptions = "0";
        updateGradeEntryView();

        currentTeacherLoading = datum;

        if (currentTeacherLoading == null) {
            return "teacherMain.xhtml?faces-redirect=true";
        }

        BedSections section = currentTeacherLoading.getSection();
        section = BedSectionService.getInstance().refresh(section);

        List<BedEnrollment> elist
                = BedSectionService.getInstance()
                .getEnrolledM(section.getId());

        currentTeacherLoadingStudentRecords
                = BedLoadingService.getInstance()
                .getEnrollmentDetails(elist,
                        this.currentTeacherLoading.getId());

        elist = BedSectionService.getInstance().getEnrolledF(section.getId());

        currentTeacherLoadingStudentRecordsF
                = BedLoadingService.getInstance()
                .getEnrollmentDetails(elist,
                        this.currentTeacherLoading.getId());

        updateHashValues();

        return "teacher_grade_entry.xhtml?faces-redirect=true";
    }

    public List<BedSections> completeSections(String query) {
        List<BedSections> s = BedSectionService.getInstance().completeSections(query);
        return s;
    }

    public List<BedCurriculumDetail> completeSubjects(String query) {
        if (query == null) {
            query = "";
        }

        if (selectedSection == null) {
            return new ArrayList();
        }

        List<BedCurriculumDetail> subjects
                = BedSectionService.getInstance()
                .getSubjects(selectedSection, query);

        if (subjects.isEmpty()) {
            JsfUtil.addWarningMessage("No subjects.",
                    "No subjects for grade "
                    + selectedSection.getYear());
        }

        return subjects;
    }

    private BedLoading getLoad(BedSections toAdd, BedCurriculumDetail subject) {
        return BedSectionService.getInstance().getSectionSubjectLoadRecord(toAdd, subject);
    }

    public void transferLoad() {

        if (currentTeacher == null) {
            JsfUtil.addWarningMessage("No teacher.", "Please specify a teacher.");
            return;
        }

        if (selectedSection == null) {
            JsfUtil.addWarningMessage("No section.", "Please specify a section");
            return;
        }

        if (selectedSubject == null) {
            JsfUtil.addWarningMessage("No subject.", "Please specifiy a subject");
            return;
        }

        BedLoading l = getLoad(selectedSection, selectedSubject);
        if (l == null) {
            l = new BedLoading();
            l.setSection(selectedSection);
            l.setSubject(selectedSubject);
            l.setTeacher(currentTeacher);

            l = BedLoadingService.getInstance().update(l);
            currentTeacherLoad.add(0, l);

            //renew only subject
            selectedSubject = null;
            JsfUtil.addInfoMessage("Success", "Subject loaded.");
            updateTeacherLoads();
            return;
        }

        boolean loadedAlready = l.getTeacher().equals(currentTeacher);

        if (loadedAlready) {
            JsfUtil.addWarningMessage("Aborted",
                    "Subject already assigned to this teacher.");
        } else {

            l.setTeacher(currentTeacher);
            l = BedLoadingService.getInstance().update(l);
            JsfUtil.addInfoMessage("Success",
                    "Subject assigned to "
                    + l.getTeacher().getFullName()
                    + ".");
            updateTeacherLoads();
        }

    }

    public void addLoad() {

        if (currentTeacher == null) {
            JsfUtil.addWarningMessage("No teacher.",
                    "Please specify a teacher.");
            return;
        }

        if (selectedSection == null) {
            JsfUtil.addWarningMessage("No section.",
                    "Please specify a section");
            return;
        }

        if (selectedSubject == null) {
            JsfUtil.addWarningMessage("No subject.",
                    "Please specifiy a subject");
            return;
        }

        BedLoading l = getLoad(selectedSection, selectedSubject);
        if (l == null) {
            l = new BedLoading();
            l.setSection(selectedSection);
            l.setSubject(selectedSubject);
            l.setTeacher(currentTeacher);
            l = BedLoadingService.getInstance().update(l);
            currentTeacherLoad.add(0, l);

            //renew only subject
            selectedSubject = null;
            JsfUtil.addInfoMessage("Success", "Subject loaded.");
            updateTeacherLoads();
            return;
        }

        if (l.getTeacher() == null) {
            l.setTeacher(currentTeacher);

            l = BedLoadingService.getInstance().update(l);

            currentTeacherLoad.add(0, l);

            //renew only subject
            selectedSubject = null;
            JsfUtil.addInfoMessage("Success", "Subject loaded.");
            updateTeacherLoads();
            return;
        }

        boolean loadedAlready = l.getTeacher().equals(currentTeacher);

        if (loadedAlready) {
            JsfUtil.addWarningMessage("Aborted", "Subject already assigned to this teacher.");
        } else {
            JsfUtil.addWarningMessage("Aborted", "Subject already assigned to " + l.getTeacher().getFullName() + ". Use transfer instead.");
        }

    }

    public void removeLoad() {

        boolean error = false;
        if (currentTeacher == null) {
            JsfUtil.addWarningMessage("No teacher.", "Please specify a teacher.");
            error = true;
        }

        if (selectedSection == null) {
            JsfUtil.addWarningMessage("No section.", "Please specify a section");
            error = true;
        }

        if (selectedSubject == null) {
            JsfUtil.addWarningMessage("No subject.", "Please specifiy a subject");
            error = true;
        }

        BedLoading rLoad = null;
        if (!currentTeacherLoad.isEmpty() && selectedSubject != null) {
            boolean exists = false;
            for (BedLoading e : currentTeacherLoad) {
                if (e.getSubject() == null) {
                    continue;
                }
                exists
                        = e.getSection().getId()
                        .equals(selectedSection.getId())
                        && e.getSubject().getId()
                        .equals(selectedSubject.getId());
                if (exists) {
                    rLoad = e;
                    break;
                }

            }
            if (!exists) {
                JsfUtil.addWarningMessage("Subject not assigned.", "");
                error = true;
            }

        }

        if (error || rLoad == null) {
            return;
        }

        currentTeacherLoad.remove(rLoad);

        rLoad.setTeacher(null);
        rLoad = BedLoadingService.getInstance().update(rLoad);
        selectedSubject = null;
        JsfUtil.addInfoMessage("Successfully removed.", "Remove success.");
    }

    public void search() {

        teacherList = BedTeacherService.getInstance()
                .search(teacherMain_SearckKey);

    }

    public String resetPassword(BedTeacher s) {
        setCurrentTeacher(s);
        setNewPassword(createRandomTeacherPassword());
        return "teacher_reset_password.xhtml?faces-redirect=true";

    }

    public String doResetPassword() {

        currentTeacher.setLastModified(new java.util.Date());
        currentTeacher.setPassword(getNewPassword());
        currentTeacher = BedTeacherService.getInstance().update(currentTeacher);

        return "teacherMain.xhtml?faces-redirect=true";
    }

    public String teacher_edit_view_SaveChanges() {
        if (currentTeacherOldPass != null && !currentTeacherOldPass.isEmpty()) {

            if (currentTeacherOldPass.replaceAll("!", "").equals(currentTeacher.getPassword())) {

                if (currentTeacherNewPass != null && !currentTeacherConfPass.isEmpty()) {
                    if (currentTeacherConfPass != null && !currentTeacherNewPass.equals(currentTeacherConfPass)) {
                        JsfUtil.addWarningMessage("Passwords don't match", "Please enter matching passwords");
                        return null;
                    } else {
                        currentTeacher.setPassword(Utils.getMD5EncryptedPassword(currentTeacherNewPass));
                    }
                }
            } else {
                JsfUtil.addWarningMessage("Old password not valid.", "Please enter old password.");
                return null;
            }
            currentTeacherOldPass = null;
            currentTeacherNewPass = null;
            currentTeacherConfPass = null;
        }

        currentTeacher.setLastModified(new java.util.Date());
        currentTeacher = BedTeacherService.getInstance().update(currentTeacher);
        search();
        if (userManager.getLoggedTeacher() != null) {
            JsfUtil.addInfoMessage("Changes saved.", "Your changes were saved.");
            return null;
        } else {
            return "teacherMain.xhtml?faces-redirect=true";
        }
    }

    public String createNewTeacher() {
        BedTeacher t = new BedTeacher();
        t.setActive(true);
        setCurrentTeacher(t);
        return "teacher_edit_view.xhtml?faces-redirect=true";
    }

    public String editTeacher(BedTeacher t) {
        setCurrentTeacher(t);
        return "teacher_edit_view.xhtml?faces-redirect=true";
    }

    public String removeTeacher(BedTeacher t) {
        setCurrentTeacher(t);
        return "teacher_remove.xhtml?faces-redirect=true";
    }

    public String editTeacherLoading(BedTeacher t) {
        setCurrentTeacher(t);

        if (getCurrentTeacher() == null) {
            setCurrentTeacherLoad(new ArrayList());
            return "teacher_loading.xhtml?faces-redirect=true";
        }

        updateTeacherLoads(); //see metadata event prerender
        updateGradeEntryView();

        return "teacher_loading.xhtml?faces-redirect=true";
    }

    private void updateTeacherLoads() {
        int activeSchoolYear
                = BedSettingsService.getInstance()
                .getActiveSchoolYear();
        String teacherId = this.getCurrentTeacher().getTeacherId();
        currentTeacherLoad = BedTeacherService
                .getInstance().getLoadRecords(activeSchoolYear, teacherId);

    }

    /**
     * @return the teacherList
     */
    public List<BedTeacher> getTeacherList() {
        return teacherList;
    }

    /**
     * @param teacherList the teacherList to set
     */
    public void setTeacherList(List<BedTeacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * @return the currentTeacher
     */
    public BedTeacher getCurrentTeacher() {
        return currentTeacher;
    }

    /**
     * @param currentTeacher the currentTeacher to set
     */
    public void setCurrentTeacher(BedTeacher currentTeacher) {
        this.currentTeacher = currentTeacher;
        currentTeacherOldPass = null;
        currentTeacherNewPass = null;
        currentTeacherConfPass = null;
    }

    /**
     * @return the teacherMain_SearckKey
     */
    public String getTeacherMain_SearckKey() {
        return teacherMain_SearckKey;
    }

    /**
     * @param teacherMain_SearckKey the teacherMain_SearckKey to set
     */
    public void setTeacherMain_SearckKey(String teacherMain_SearckKey) {
        this.teacherMain_SearckKey = teacherMain_SearckKey;
    }

    /**
     * @return the teacher_remove_RemoveMessage
     */
    public String getTeacher_remove_RemoveMessage() {
        return teacher_remove_RemoveMessage;
    }

    /**
     * @param teacher_remove_RemoveMessage the teacher_remove_RemoveMessage to
     * set
     */
    public void setTeacher_remove_RemoveMessage(String teacher_remove_RemoveMessage) {
        this.teacher_remove_RemoveMessage = teacher_remove_RemoveMessage;
    }

    /**
     * @return the teacher_remove_AllowRemove
     */
    public boolean isTeacher_remove_AllowRemove() {
        return teacher_remove_AllowRemove;
    }

    /**
     * @param teacher_remove_AllowRemove the teacher_remove_AllowRemove to set
     */
    public void setTeacher_remove_AllowRemove(boolean teacher_remove_AllowRemove) {
        this.teacher_remove_AllowRemove = teacher_remove_AllowRemove;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the currentTeacherLoad
     */
    public List<BedLoading> getCurrentTeacherLoad() {
        return currentTeacherLoad;
    }

    /**
     * @param currentTeacherLoad the currentTeacherLoad to set
     */
    public void setCurrentTeacherLoad(List<BedLoading> currentTeacherLoad) {
        this.currentTeacherLoad = currentTeacherLoad;
    }

    /**
     * @return the selectedSection
     */
    public BedSections getSelectedSection() {
        return selectedSection;
    }

    /**
     * @param selectedSection the selectedSection to set
     */
    public void setSelectedSection(BedSections selectedSection) {
        this.selectedSection = selectedSection;
    }

    /**
     * @return the selectedSubject
     */
    public BedCurriculumDetail getSelectedSubject() {
        return selectedSubject;
    }

    /**
     * @param selectedSubject the selectedSubject to set
     */
    public void setSelectedSubject(BedCurriculumDetail selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    /**
     * @return the currentTeacherLoading
     */
    public BedLoading getCurrentTeacherLoading() {
        return currentTeacherLoading;
    }

    /**
     * @param currentTeacherLoading the currentTeacherLoading to set
     */
    public void setCurrentTeacherLoading(BedLoading currentTeacherLoading) {
        this.currentTeacherLoading = currentTeacherLoading;
    }

    /**
     * @return the currentTeacherLoadingStudentRecords
     */
    public List<BedEnrollmentdetails> getCurrentTeacherLoadingStudentRecords() {
        return currentTeacherLoadingStudentRecords;
    }

    /**
     * @param currentTeacherLoadingStudentRecords the
     * currentTeacherLoadingStudentRecords to set
     */
    public void setCurrentTeacherLoadingStudentRecords(List<BedEnrollmentdetails> currentTeacherLoadingStudentRecords) {
        this.currentTeacherLoadingStudentRecords = currentTeacherLoadingStudentRecords;
    }

    /**
     * @return the viewOptions
     */
    public String getViewOptions() {
        return viewOptions;
    }

    /**
     * @param viewOptions the viewOptions to set
     */
    public void setViewOptions(String viewOptions) {
        this.viewOptions = viewOptions;
    }

    /**
     * @return the showSecondQ
     */
    public boolean isShowSecondQ() {
        return showSecondQ;
    }

    /**
     * @param showSecondQ the showSecondQ to set
     */
    public void setShowSecondQ(boolean showSecondQ) {
        this.showSecondQ = showSecondQ;
    }

    /**
     * @return the showThirdQ
     */
    public boolean isShowThirdQ() {
        return showThirdQ;
    }

    /**
     * @param showThirdQ the showThirdQ to set
     */
    public void setShowThirdQ(boolean showThirdQ) {
        this.showThirdQ = showThirdQ;
    }

    /**
     * @return the showFourthQ
     */
    public boolean isShowFourthQ() {
        return showFourthQ;
    }

    /**
     * @param showFourthQ the showFourthQ to set
     */
    public void setShowFourthQ(boolean showFourthQ) {
        this.showFourthQ = showFourthQ;
    }

    /**
     * @return the showFirstQ
     */
    public boolean isShowFirstQ() {
        return showFirstQ;
    }

    /**
     * @param showFirstQ the showFirstQ to set
     */
    public void setShowFirstQ(boolean showFirstQ) {
        this.showFirstQ = showFirstQ;
    }

    /**
     * @return the showFinals
     */
    public boolean isShowFinals() {
        return showFinals;
    }

    /**
     * @param showFinals the showFinals to set
     */
    public void setShowFinals(boolean showFinals) {
        this.showFinals = showFinals;
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

    /**
     * @return the gradeEntryTblLength
     */
    public String getGradeEntryTblLength() {

        return gradeEntryTblLength;
    }

    /**
     * @param gradeEntryTblLength the gradeEntryTblLength to set
     */
    public void setGradeEntryTblLength(String gradeEntryTblLength) {
        this.gradeEntryTblLength = gradeEntryTblLength;
    }

    /**
     * @return the currentTeacherNewPass
     */
    public String getCurrentTeacherNewPass() {
        return currentTeacherNewPass;
    }

    /**
     * @param currentTeacherNewPass the currentTeacherNewPass to set
     */
    public void setCurrentTeacherNewPass(String currentTeacherNewPass) {
        this.currentTeacherNewPass = currentTeacherNewPass;
    }

    /**
     * @return the currentTeacherConfPass
     */
    public String getCurrentTeacherConfPass() {
        return currentTeacherConfPass;
    }

    /**
     * @param currentTeacherConfPass the currentTeacherConfPass to set
     */
    public void setCurrentTeacherConfPass(String currentTeacherConfPass) {
        this.currentTeacherConfPass = currentTeacherConfPass;
    }

    /**
     * @return the currentTeacherOldPass
     */
    public String getCurrentTeacherOldPass() {
        return currentTeacherOldPass;
    }

    /**
     * @param currentTeacherOldPass the currentTeacherOldPass to set
     */
    public void setCurrentTeacherOldPass(String currentTeacherOldPass) {
        this.currentTeacherOldPass = currentTeacherOldPass;
    }

    /**
     * @return the currentTeacherLoadingStudentRecordsF
     */
    public List<BedEnrollmentdetails> getCurrentTeacherLoadingStudentRecordsF() {
        return currentTeacherLoadingStudentRecordsF;
    }

    /**
     * @param currentTeacherLoadingStudentRecordsF the
     * currentTeacherLoadingStudentRecordsF to set
     */
    public void setCurrentTeacherLoadingStudentRecordsF(List<BedEnrollmentdetails> currentTeacherLoadingStudentRecordsF) {
        this.currentTeacherLoadingStudentRecordsF = currentTeacherLoadingStudentRecordsF;
    }

    /**
     * @return the maleGradeEntryHashValue
     */
    public String getMaleGradeEntryHashValue() {
        return maleGradeEntryHashValue;
    }

    /**
     * @param maleGradeEntryHashValue the maleGradeEntryHashValue to set
     */
    public void setMaleGradeEntryHashValue(String maleGradeEntryHashValue) {
        this.maleGradeEntryHashValue = maleGradeEntryHashValue;
    }

    /**
     * @return the femaleGradeEntryHashValue
     */
    public String getFemaleGradeEntryHashValue() {
        return femaleGradeEntryHashValue;
    }

    /**
     * @param femaleGradeEntryHashValue the femaleGradeEntryHashValue to set
     */
    public void setFemaleGradeEntryHashValue(String femaleGradeEntryHashValue) {
        this.femaleGradeEntryHashValue = femaleGradeEntryHashValue;
    }

}
