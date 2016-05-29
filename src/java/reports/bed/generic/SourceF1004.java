/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedLoading;
import entities.BedSections;
import static entities.services.BedEnrollmentDetailService.getProficiencyLevel;
import entities.services.BedEnrollmentService;
import entities.services.BedSectionService;
import entities.services.BedSettingsService;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. SolastudentCount johnphilipsolastudentCount@gmail.com
 */
public class SourceF1004 implements JRDataSource {

    private final String sectionName;

    private final int printingSequenceNum;
    private final String gender;
    private final BedLoading teacherLoad;

    private int studentCount = 0;
    private BedEnrollment currentEnrollment;
    private final Iterator<BedEnrollment> iterator;

    /**
     *
     * @param printingSequenceNum Will not be displayed if < 0.
     * @param gender
     * @param teacherLoad
     * @param BedEnrollmentService.getInstance()
     * @param BedSectionService.getInstance()
     */
    public SourceF1004(int printingSequenceNum, String gender, BedLoading teacherLoad) {
        this.teacherLoad = teacherLoad;
        this.printingSequenceNum = printingSequenceNum;

        BedSections bedSection = teacherLoad.getSection();
        this.sectionName = new StringBuilder("Grade ").append(bedSection.getYear()).append(" - ").append(bedSection.getName()).toString();
        List<BedEnrollment> enrollment = BedSectionService.getInstance().getEnrolled(bedSection.getId(), gender);
        this.gender = gender.equals("M") ? "BOYS" : "GIRLS";
        this.iterator = enrollment.iterator();
    }

    @Override
    public boolean next() throws JRException {
        boolean hasNext = this.iterator.hasNext();
        if (hasNext) {
            this.currentEnrollment = iterator.next();
            studentCount++;
        }
        return hasNext;
    }

    //logo1,logo2,subject,teacher,section,gender,student,n1,d1,n2,d2,n3,d3,n4,d4,n5,d5
    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        if (n.equals("logo1")) {
            return JRDataSource.class.getClass().getResourceAsStream("/reports/bed/generic/logos/hcmnhs.png");// = this.getClass().getResourceAsStream("/reports/bed/generic/logos/cjc.png");
        }
        if (n.equals("logo2")) {
            return JRDataSource.class.getResourceAsStream("/reports/bed/generic/logos/deped_k12.png");
        }
        if (n.equals("subject")) {
            return teacherLoad.getSubject().getSubjcode().getSubjdesc();
        }
        if (n.equals("teacher")) {
            try {
                return teacherLoad.getTeacher().getFullName();
            } catch (Exception e) {
            }
            return "n/a";
        }
        if (n.equals("section")) {
            return sectionName;
        }

        if (n.equals("sy")) {
            return BedSettingsService.getInstance().getCompleteSY();
        }
        if (n.equals("gender")) {
            return gender;
        }
        if (n.equals("student")) {
            return studentCount + ". " + currentEnrollment.getStudent().getFullName();
        }
        if (n.equals("no")) {
            if (printingSequenceNum <= 0) {
                return "";
            }
            return "" + printingSequenceNum;
        }

        //student details here:
        BedEnrollmentdetails studentDetail = BedEnrollmentService.getInstance().getEnrollmentDetails(currentEnrollment.getId(),
                teacherLoad.getSubject().getSubjcode().getId(),
                teacherLoad.getSection().getSy());

        if (studentDetail == null) {
            return "";
        }

        boolean p1ok = studentDetail.getP1() != null && studentDetail.getP1() > 0;
        boolean p2ok = studentDetail.getP2() != null && studentDetail.getP2() > 0;
        boolean p3ok = studentDetail.getP3() != null && studentDetail.getP3() > 0;
        boolean p4ok = studentDetail.getP4() != null && studentDetail.getP4() > 0;

        if (p1ok) {
            if (n.equals("n1")) {
                return "" + studentDetail.getP1();
            }

            if (n.equals("d1")) {
                return "" + getProficiencyLevel(studentDetail.getP1());
            }
        }
        if (p2ok) {

            if (n.equals("n2")) {
                return "" + studentDetail.getP2();
            }

            if (n.equals("d2")) {
                return "" + getProficiencyLevel(studentDetail.getP2());
            }
        }

        if (p3ok) {
            if (n.equals("n3")) {
                return "" + studentDetail.getP3();
            }

            if (n.equals("d3")) {
                return "" + getProficiencyLevel(studentDetail.getP3());
            }
        }

        if (p4ok) {

            if (n.equals("n4")) {
                return "" + studentDetail.getP4();
            }

            if (n.equals("d4")) {
                return "" + getProficiencyLevel(studentDetail.getP4());
            }
        }

        if (p1ok && p2ok && p3ok && p4ok) {
            double n5 = studentDetail.getP1() + studentDetail.getP2() + studentDetail.getP3() + studentDetail.getP4();
            n5 = n5 / 4;
            DecimalFormat f = new DecimalFormat("##.###");
            String formated = f.format(n5);
            if (n.equals("n5")) {
                return "" + formated;
            }

            if (n.equals("d5")) {
                return "" + getProficiencyLevel(n5);
            }
        }

        return "";

    }

}
