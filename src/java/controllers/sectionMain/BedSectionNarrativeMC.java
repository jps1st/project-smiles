package controllers.sectionMain;

import controllers.utils.JsfUtil;
import entities.BedEnrollment;
import entities.BedEnrollmentNarrativeReport;
import entities.BedSections;
import entities.services.NarrativeReportService;
import entities.services.BedSectionService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * Main controller for bed_sections_character_traits.xhtml
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class BedSectionNarrativeMC implements Serializable {


    private int viewOption = 1;
    
    private BedSections section;
    private List<BedEnrollment> maleEnrollments;
    private List<BedEnrollment> femaleEnrollments;

    @PostConstruct
    public void init() {

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
                NarrativeReportService.getInstance().initNarrativeReportRecords(sectionId);
            }
            
        } catch (Exception ex) {
             Logger.getLogger(BedSectionProfileMC.class.getName()).log(Level.SEVERE, null, ex);
        }

        maleEnrollments = BedSectionService.getInstance().getEnrolledM(sectionId);
        femaleEnrollments = BedSectionService.getInstance().getEnrolledF(sectionId);
    }

    public void saveNarrativeReport(BedEnrollmentNarrativeReport narr) {
        NarrativeReportService.getInstance().update(narr);
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

     public int getViewOption() {
        return viewOption;
    }

    public void setViewOption(int viewOption) {
        this.viewOption = viewOption;
    }
    //</editor-fold>
}
