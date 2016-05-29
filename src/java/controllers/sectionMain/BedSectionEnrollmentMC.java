package controllers.sectionMain;

import controllers.utils.JsfUtil;
import entities.BedEnrollment;
import entities.BedSections;
import entities.JdStudent;
import entities.services.BedEnrollmentService;
import entities.services.BedSectionService;
import entities.services.BedStudentService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BedSectionEnrollmentMC implements Serializable {

    private int viewOptions = 1;

    private BedSections section;
    private JdStudent studentToEnroll;
    private List<BedEnrollment> maleEnrollments;

    @PostConstruct
    public void init() {

        //TODO: if this works, then move others to post construct
        String val = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        Integer sectionId;
        if (val != null) {
            sectionId = Integer.valueOf(val);
        } else {
            sectionId = null;
        }

        try {
            if (sectionId == -1 || sectionId == null) {//new section
                throw new Exception("Section not found."); //TODO: redirect to a 'section not found' page.
            } else {
                section = BedSectionService.getInstance().find(sectionId);
                if (section == null) {
                    throw new Exception("Section not found."); //TODO: redirect to a 'section not found' page.
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BedSectionProfileMC.class.getName()).log(Level.SEVERE, null, ex);
        }

        section = BedSectionService.getInstance().find(sectionId);
        refreshEnrollmentList();
    }

    public void saveEnrollment(BedEnrollment enr) {
        BedEnrollmentService.getInstance().update(enr);
        JsfUtil.addInfoMessage("Saved.", "Your changes were saved.");
    }

    public List<JdStudent> completeStudent(String key) {
        return BedStudentService.getInstance().completeStudent(key);
    }

    public void addStudent() {
        if (studentToEnroll == null) {
            JsfUtil.addWarningMessage("Aborted", "Please select a student.");
            return;
        }
        
        BedEnrollment en
                = BedSectionService.getInstance()
                .enrollStudentToSection(
                        studentToEnroll.getStdntid(),
                        section.getId()
                );
        
        if (en != null) {
            maleEnrollments.add(0, en);
        }
    }

    public void transferStudent() {
        
        if (studentToEnroll == null) {
            JsfUtil.addWarningMessage("Aborted", "Please select a student.");
            return;
        }

        BedEnrollment en
                = BedSectionService.getInstance()
                .transferStudent(
                        studentToEnroll.getStdntid(),
                        section.getId()
                );

        if (en != null) {
            maleEnrollments.add(0, en);
        }
    }

    public void unenrollStudent() {
        if (studentToEnroll == null) {
            JsfUtil.addWarningMessage("Aborted", "Please select a student.");
            return;
        }

        BedSectionService.getInstance()
                .unenrollStudent(
                        studentToEnroll.getStdntid(),
                        section.getId()
                );
    }

    public void refreshEnrollmentList() {
        switch (viewOptions) {
            case 1:
                maleEnrollments = BedSectionService.getInstance().getEnrolledM(section.getId());
                break;
            case 2:
                maleEnrollments = BedSectionService.getInstance().getEnrolledF(section.getId());
                break;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }

    public List<BedEnrollment> getMaleEnrollments() {
        return maleEnrollments;
    }

    public void setMaleEnrollments(List<BedEnrollment> maleEnrollments) {
        this.maleEnrollments = maleEnrollments;
    }

    public JdStudent getStudentToEnroll() {
        return studentToEnroll;
    }

    public void setStudentToEnroll(JdStudent studentToEnroll) {
        this.studentToEnroll = studentToEnroll;
    }

    public int getViewOptions() {
        return viewOptions;
    }

    public void setViewOptions(int viewOptions) {
        this.viewOptions = viewOptions;
    }
    //</editor-fold>
}
