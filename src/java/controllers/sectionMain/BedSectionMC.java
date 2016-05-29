package controllers.sectionMain;

import controllers.UserManager;
import entities.BedSections;
import entities.services.BedSectionService;
import entities.services.BedSettingsService;
import entities.services.ReportingService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.ViewScoped;

/**
 *
 * @author John Philip Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class BedSectionMC implements Serializable {

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    private String keyword;
    private List<BedSections> sectionList;
    private BedSections selectedBedSection;

    private int intOption1;
    private int intOption2;
    private int intOption3;
    private int intOption4;

    public String getCompleteSY() {
        return BedSettingsService.getInstance().getCompleteSY();
    }

    public String editCharacterRecords(int sectionId) {
        return "bed_sections_character_traits.xhtml?faces-redirect=true&id=" + sectionId;
    }

    public String createNew() {
        return "bed_sections_profile.xhtml?faces-redirect=true&id=-1";
    }

    public String editSection(int sectionId) {
        return "bed_sections_profile.xhtml?faces-redirect=true&id=" + sectionId;
    }

    public String editEnrollment(int sectionId) {
        return "bed_sections_enrollments.xhtml?faces-redirect=true&id=" + sectionId;
    }

    public String editCharacter(int sectionId) {
        return "bed_sections_character_traits.xhtml?faces-redirect=true&id=" + sectionId;
    }

    public String editAttandance(int sectionId) {
        return "bed_sections_attendance.xhtml?faces-redirect=true&id=" + sectionId;
    }

    public String editNarrative(int sectionId) {
        return "bed_sections_narrative.xhtml?faces-redirect=true&id=" + sectionId;
    }

    @PostConstruct
    public void search() {
        if (userManager.getLoggedTeacher() != null) {
            sectionList = BedSectionService.getInstance().getAdvisory(userManager.getLoggedTeacher().getId());
            return;
        }
        sectionList = BedSectionService.getInstance().search(userManager.getSectionSearchKeyword());
    }

    public void select(BedSections section) {
        setSelectedBedSection(section);
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public List<BedSections> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<BedSections> sectionList) {
        this.sectionList = sectionList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BedSections getSelectedBedSection() {
        return selectedBedSection;
    }

    public void setSelectedBedSection(BedSections selectedBedSection) {
        this.selectedBedSection = selectedBedSection;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public int getIntOption1() {
        return intOption1;
    }

    public void setIntOption1(int intOption1) {
        this.intOption1 = intOption1;
    }

    public ReportingService getReportingSV() {
        return ReportingService.getInstance();
    }

    public int getIntOption2() {
        return intOption2;
    }

    public void setIntOption2(int intOption2) {
        this.intOption2 = intOption2;
    }

    public int getIntOption3() {
        return intOption3;
    }

    public void setIntOption3(int intOption3) {
        this.intOption3 = intOption3;
    }

    public int getIntOption4() {
        return intOption4;
    }

    public void setIntOption4(int intOption4) {
        this.intOption4 = intOption4;
    }

//</editor-fold>
}
