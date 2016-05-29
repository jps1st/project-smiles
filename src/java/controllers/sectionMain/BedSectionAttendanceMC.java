/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.sectionMain;

import controllers.utils.JsfUtil;
import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import entities.BedSections;
import entities.services.BedAttendanceRecordService;
import entities.services.BedEnrollmentService;
import entities.services.BedSectionService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class BedSectionAttendanceMC implements Serializable {

    private int viewOptions;

    private BedSections section;
    private List<BedEnrollment> maleEnrollments;
    private List<BedEnrollment> femaleEnrollments;

    @PostConstruct
    public void init() {

        SimpleDateFormat d = new SimpleDateFormat("M");
        int month = Integer.parseInt(d.format(new java.util.Date()));
        if (month == 5) {
            month = 6;
        }

        viewOptions = month;

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
                BedAttendanceRecordService.getInstance().initAttendanceRecords(sectionId);
            }
        } catch (Exception ex) {
            Logger.getLogger(BedSectionProfileMC.class.getName()).log(Level.SEVERE, null, ex);
        }


        //initialize enrollment records
        
        BedSectionService.getInstance().updateEnrollmentAttendanceRecords(sectionId);
        
        
        maleEnrollments = BedSectionService.getInstance().getEnrolledM(sectionId);
        femaleEnrollments = BedSectionService.getInstance().getEnrolledF(sectionId);
        
    }

    public void saveAttendance(BedEnrollmentAttendanceRecord attRec) {
        BedAttendanceRecordService.getInstance().update(attRec);
        JsfUtil.addInfoMessage("Saved", "Your changes were saved.");
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

    public List<BedEnrollment> getFemaleEnrollments() {
        return femaleEnrollments;
    }

    public void setFemaleEnrollments(List<BedEnrollment> femaleEnrollments) {
        this.femaleEnrollments = femaleEnrollments;
    }

    public int getViewOptions() {
        return viewOptions;
    }

    public void setViewOptions(int viewOptions) {
        this.viewOptions = viewOptions;
    }
    //</editor-fold>
}
