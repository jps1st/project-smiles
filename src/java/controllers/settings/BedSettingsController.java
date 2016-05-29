/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.settings;

import controllers.utils.JsfUtil;
import entities.BedSettings;
import entities.services.BedSettingsService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author John Philip
 */
@ManagedBean
@ViewScoped
public class BedSettingsController implements Serializable {

    private int schoolYear = -1;
    private BedSettings currentSettings;

    public void refresh() {
        currentSettings = BedSettingsService.getInstance().find(this.schoolYear);

        if (currentSettings == null) {
            BedSettings s = new BedSettings();
            s.setId(schoolYear);
            s.setSchoolPrincipalName("n/a");
            s.setSchoolsDivisionSuperintendent("n/a");
            s.setDivisionRepresentative("n/a");
            s.setSchoolId("n/a");
            s.setSchoolName("n/a");
            s.setDivision("n/a");
            s.setDistrict("n/a");
            currentSettings = BedSettingsService.getInstance().insert(s);
        } else {
            currentSettings = BedSettingsService.getInstance().refresh(currentSettings);
        }

    }

    public void saveChanges() {
        currentSettings = BedSettingsService.getInstance().update(currentSettings);
        if (currentSettings.isActiveYear()) {
            BedSettingsService.getInstance().disableAllSettings();
            currentSettings.setActiveYear(true);
        }
        BedSettingsService.getInstance().update(currentSettings);
        controllers.utils.JsfUtil.addInfoMessage("Settings Saved.", "Your settings were saved.");
    }

    @PostConstruct
    public void init() {
        BedSettings activeBedSettings = BedSettingsService.getInstance().getActiveBedSettings();
        if (activeBedSettings == null) {
            JsfUtil.addWarningMessage("Active year not set.", "Please set up global settings properly first. Assign an active year.");
        } else {
            currentSettings = activeBedSettings;
        }
    }

    /**
     * @return the schoolYear
     */
    public int getSchoolYear() {
        if (schoolYear == -1) {
            schoolYear = getCompleteSYI();
        }
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * @return the currentSettings
     */
    public BedSettings getCurrentSettings() {
        return currentSettings;
    }

    /**
     * @param currentSettings the currentSettings to set
     */
    public void setCurrentSettings(BedSettings currentSettings) {
        this.currentSettings = currentSettings;
    }

    public int getCompleteSYI() {
        return BedSettingsService.getInstance().getActiveSchoolYear();
    }

}
