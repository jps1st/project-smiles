/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacherMain;

import controllers.utils.JsfUtil;
import entities.BedCurriculumDetail;
import entities.BedSections;
import entities.services.ReportingService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class TeacherLoadConsoGradeSheetDiagController implements Serializable {

    private BedSections section;
    private BedCurriculumDetail aggregateSubject;
    private int gradingPeriod;

    public void setup(BedSections s, BedCurriculumDetail d) {
        this.section = s;
        this.aggregateSubject = d;
    }

    public BedCurriculumDetail getAggregateSubject() {
        return aggregateSubject;
    }

    public void setAggregateSubject(BedCurriculumDetail aggregateSubject) {
        this.aggregateSubject = aggregateSubject;
    }

    public int getGradingPeriod() {
        return gradingPeriod;
    }

    public void setGradingPeriod(int gradingPeriod) {
        this.gradingPeriod = gradingPeriod;
    }

    public String print() throws JRException {

        if (!aggregateSubject.isAggregate()) {
            JsfUtil.addWarningMessage("Aborted", "This subject is not an aggregate subject.");
            return null;
        }

        return ReportingService.getInstance().generateF1011(section, aggregateSubject, gradingPeriod);
    }

    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }

}
