/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.controllers;

import controllers.sectionMain.BedSectionMC;
import controllers.utils.JsfUtil;
import entities.BedSections;
import java.io.Serializable;

import net.sf.jasperreports.engine.JRException;
import entities.BedLoading;
import entities.services.ReportingService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@RequestScoped
public class ReportsController implements Serializable {

    @ManagedProperty(value = "#{bedSectionMC}")
    private BedSectionMC bedSectionMainPageController;

    public String f1001() throws JRException {
        return ReportingService.getInstance().generateF1001();
    }

    public String f1002(BedSections s) throws JRException {
        System.out.println("invoked");

        if (bedSectionMainPageController.getIntOption3() != 0 && (bedSectionMainPageController.getIntOption3() > bedSectionMainPageController.getIntOption4())) {
            JsfUtil.addWarningMessage("Printing Aborted", "Starting month is later than ending month." + bedSectionMainPageController.getIntOption3() + " > " + bedSectionMainPageController.getIntOption4());
            return null;
        }

        int gradingPeriod = bedSectionMainPageController.getIntOption2();
        int ms = bedSectionMainPageController.getIntOption3();
        int me = bedSectionMainPageController.getIntOption4();

        switch (bedSectionMainPageController.getIntOption1()) {
            
            case 1:
                return ReportingService.getInstance().generateF1002(s, "M", gradingPeriod, ms, me);
            case 2:
                return ReportingService.getInstance().generateF1002(s, "F", gradingPeriod, ms, me);
                
        }
        
        return null;
    }

    public String f15(BedSections s) throws JRException {

        int gradingPeriod = bedSectionMainPageController.getIntOption2();
        int ms = bedSectionMainPageController.getIntOption3();
        int me = bedSectionMainPageController.getIntOption4();

        switch (bedSectionMainPageController.getIntOption1()) {
            case 1:
                return ReportingService.getInstance().generateF15(s, "M", gradingPeriod, ms, me);
            case 2:
                return ReportingService.getInstance().generateF15(s, "F", gradingPeriod, ms, me);
        }

        return null;

    }

    public String f1003(BedSections s) throws JRException {

        int gradingPeriod = bedSectionMainPageController.getIntOption2();
        int gender = bedSectionMainPageController.getIntOption1();
        
        switch (gender) {
            case 1:
                return ReportingService.getInstance().generateF1003(s, "M", gradingPeriod);
            case 2:
                return ReportingService.getInstance().generateF1003(s, "F", gradingPeriod);
        }
        return null;
    }

    public String f1004(BedSections s) throws JRException {
        switch (bedSectionMainPageController.getIntOption1()) {
            case 1:
                return ReportingService.getInstance().genF14(s, "M");
            case 2:
                return ReportingService.getInstance().genF14(s, "F");
        }
        return null;
    }

    public String f1004PerLoad(BedLoading load, String gender) throws JRException {
        return ReportingService.getInstance().generateF1004PerLoad(load, gender);
    }

    public String f1005(BedSections s) throws JRException {

        String sex;
        switch (bedSectionMainPageController.getIntOption1()) {
            case 1:
                sex = "M";
                break;
            case 2:
                sex = "F";
                break;
            default:
                sex = "A";
        }
        int gp = bedSectionMainPageController.getIntOption3();
        boolean byRank = bedSectionMainPageController.getIntOption2() == 2;
        return ReportingService.getInstance().generateF1005(s, sex, gp, byRank);
    }

    public String f1006(BedSections section) throws JRException {
        return ReportingService.getInstance().genF16(section, bedSectionMainPageController.getIntOption1());
    }

    public String f1007(BedSections section) throws JRException {
        return ReportingService.getInstance().generateF1007(section, bedSectionMainPageController.getIntOption1());
    }

    public String f1008(BedSections section) throws JRException {
        String link = ReportingService.getInstance().generateF1008(section);
        return link;
    }

    public BedSectionMC getBedSectionMainPageController() {
        return bedSectionMainPageController;
    }

    public void setBedSectionMainPageController(BedSectionMC bedSectionMainPageController) {
        this.bedSectionMainPageController = bedSectionMainPageController;
    }

}
