/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedEnrollment;
import entities.BedSections;
import entities.JdStudent;
import entities.services.BedEnrollmentDetailService;
import entities.services.BedSectionService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import static reports.bed.generic.SourceF1003Tbl1.FORMAT_2;
import static reports.bed.generic.SourceF1003Tbl1.FORMAT_3;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1008_1 implements JRDataSource {

    private final Iterator iterator;
    private Object current;
    private String currentGender = "";
    private int currentCount = 0;
    
  

    public SourceF1008_1(BedSections section) {

        List list = new ArrayList<>();
        List<BedEnrollment> male = BedSectionService.getInstance().getEnrolled(section.getId(), "M");
        list.addAll(male);
        list.add("Total Male: " + male.size());
        List<BedEnrollment> fmale = BedSectionService.getInstance().getEnrolled(section.getId(), "F");
        list.addAll(fmale);
        list.add("Total Female: " + fmale.size());
        int combined = (fmale.size() + male.size());
        list.add("Combined: " + combined);
        this.iterator = list.iterator();
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            current = iterator.next();

            if (current instanceof BedEnrollment) {
                BedEnrollment e = (BedEnrollment) current;
                if (!currentGender.matches(e.getStudent().getSex())) {
                    currentGender = e.getStudent().getSex();
                    currentCount = 0;
                }
                currentCount++;
            }

            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        if (current instanceof BedEnrollment) {
            BedEnrollment en = (BedEnrollment) current;
            switch (jrf.getName()) {
                case "lrn":
                    return en.getStudent().getLearnerIdNo();
                case "learnersName":
                    JdStudent s = en.getStudent();
                    String name = s.getLastname() + ", " + s.getFirstname() + ", " + s.getMiddlename();
                    return " " + currentCount + ". " + name;
                case "generaAve":
                    Double genAv = en.updateGeneralAverage();
                    return en.isHonorLearner()
                            ? FORMAT_3.format(genAv)
                            : FORMAT_2.format(genAv);
                case "generaAve_desc":
                    return BedEnrollmentDetailService.getProficiencyLevel(en.getGeneralAverage());
                case "action":
                    String at = en.getActionTaken();
                    return at.toLowerCase().matches("n/a") ? "" : at;
                case "completedInc":
                    return ""; //TODO: impl this
                case "inc":
                    return en.getIncompleteThisYear();
            }
        }

        switch (jrf.getName()) {
            case "learnersName":
                return current.toString();
        }
        return "";
    }

}
