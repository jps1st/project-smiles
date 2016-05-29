package controllers.studentMain;

import controllers.utils.JsfUtil;
import entities.BedParent;
import entities.BedSections;
import entities.JdStudent;
import entities.services.BedParentService;
import entities.services.BedSectionService;
import entities.services.BedStudentService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class StudentMainController implements Serializable {

    public void processNewParent(BedParent parent) {
        if (editingParent == 2) {
            this.currentStudent.setParentFather(parent);
        }

        if (editingParent == 3) {
            this.currentStudent.setParentMother(parent);
        }

        if (editingParent == 1) {
            this.currentStudent.setParentGuardian(parent);
        }

    }

    private BedSections selectedSectionToEnroll;

    private int editingParent = 0;

    public void editGuardian() {
        editingParent = 1;
    }

    public void editFather() {
        editingParent = 2;
    }

    public void editMother() {
        editingParent = 3;
    }

    private List<JdStudent> studentList;
    private JdStudent currentStudent;
    private String keyword;

    //student_remove
    private String student_remove_RemoveMessage;
    private boolean student_remove_AllowRemove;

    public List<BedParent> completeParent(String key) {
        return BedParentService.getInstance().completeParent(key);
    }

    public void enrollStudent() {
        BedSectionService.getInstance().enrollStudentToSection(currentStudent.getStdntid(), selectedSectionToEnroll.getId());
    }

    public void transferStudent() {
        BedSectionService.getInstance().transferStudent(currentStudent.getStdntid(), selectedSectionToEnroll.getId());
    }

    public void unenrollStudent() {
        BedSectionService.getInstance().unenrollStudent(currentStudent.getStdntid(), selectedSectionToEnroll.getId());
    }

    public List<BedSections> completeSections(String key) {
        return BedSectionService.getInstance().completeSections(key);
    }

    @PostConstruct
    public void search() {
        studentList = BedStudentService.getInstance().search(keyword);
        for (JdStudent s : studentList) {
            String e = BedStudentService.getInstance().getCurrentEnrolledSectionName(s.getStdntid());
            s.setSectionEnrolled(e);
        }
    }

    /**
     * @return the studentList
     */
    public List<JdStudent> getStudentList() {
        if (studentList == null) {
            search();
        }
        return studentList;
    }

    /**
     * @param studentList the studentList to set
     */
    public void setStudentList(List<JdStudent> studentList) {
        this.studentList = studentList;

    }

    /**
     * @return the currentStudent
     */
    public JdStudent getCurrentStudent() {
        return currentStudent;
    }

    /**
     * @param currentStudent the currentStudent to set
     */
    public void setCurrentStudent(JdStudent currentStudent) {
        this.currentStudent = currentStudent;
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

    public String createNewStudent() {
        currentStudent = new JdStudent();
        return "student_edit_view?faces-redirect=true";

    }

    //
    //
    //
    //  student_edit_view Specifics
    //
    //
    //
    public String editViewStudent(JdStudent sub) {
        currentStudent = sub;
        return "/students/student_edit_view?faces-redirect=true";
    }

    public void student_edit_view_SaveChanges() {
        try {
            currentStudent = BedStudentService.getInstance().update(currentStudent);
            search();
            JsfUtil.addInfoMessage("Saved.", "Your changes were saved.");
            // return "studentMain?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error saving.", "An error occured during saving.");
        }
        //return null;
    }

    /**
     * @return the student_remove_RemoveMessage
     */
    public String getStudent_remove_RemoveMessage() {
        return student_remove_RemoveMessage;
    }

    /**
     * @param student_remove_RemoveMessage the student_remove_RemoveMessage to
     * set
     */
    public void setStudent_remove_RemoveMessage(String student_remove_RemoveMessage) {
        this.student_remove_RemoveMessage = student_remove_RemoveMessage;
    }

    /**
     * @return the student_remove_AllowRemove
     */
    public boolean isStudent_remove_AllowRemove() {
        return student_remove_AllowRemove;
    }

    /**
     * @param student_remove_AllowRemove the student_remove_AllowRemove to set
     */
    public void setStudent_remove_AllowRemove(boolean student_remove_AllowRemove) {
        this.student_remove_AllowRemove = student_remove_AllowRemove;
    }

    public String removeStudent(JdStudent sub) {
        currentStudent = sub;

        long enrollmentRecords
                = BedStudentService.getInstance()
                .countEnrollmentRecords(currentStudent.getStdntid());

        student_remove_AllowRemove = (enrollmentRecords == 0);

        if (student_remove_AllowRemove) {
            student_remove_RemoveMessage
                    = "Removing/deleting student: ID "
                    + currentStudent.getLearnerIdNo()
                    + " or "
                    + currentStudent.getFullName();
        } else {
            student_remove_RemoveMessage
                    = "Removing/deleting NOT ALLOWED. This student has "
                    + enrollmentRecords
                    + " enrollment/grade records."
                    + "You need to unenroll this student first from its current section.";
        }

        return "student_remove?faces-redirect=true";
    }

    public String removeStudentNow() {
        BedStudentService.getInstance().remove(currentStudent);
        currentStudent = null;
        keyword = "";
        search();
        return "studentMain?faces-redirect=true";
    }

    /**
     * @return the selectedSectionToEnroll
     */
    public BedSections getSelectedSectionToEnroll() {
        return selectedSectionToEnroll;
    }

    /**
     * @param selectedSectionToEnroll the selectedSectionToEnroll to set
     */
    public void setSelectedSectionToEnroll(BedSections selectedSectionToEnroll) {
        this.selectedSectionToEnroll = selectedSectionToEnroll;
    }

    /**
     * @return the editingParent
     */
    public int getEditingParent() {
        return editingParent;
    }

    /**
     * @param editingParent the editingParent to set
     */
    public void setEditingParent(int editingParent) {
        this.editingParent = editingParent;
    }

    public BedSectionService getBedSectionSV() {
        return BedSectionService.getInstance();
    }

}
