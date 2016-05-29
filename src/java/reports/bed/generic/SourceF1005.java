/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedSections;
import entities.services.BedCurriculumService;
import entities.services.BedEnrollmentService;
import entities.services.BedSectionService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class SourceF1005 implements JRDataSource {

    private Iterator<Datum> iterator;
    private Datum current;
    String section;
    int count;
    Double[] rankList;
    private int gradingPeriod;

    private class Datum {

        int origIndex;
        int numofsubjects;
        String name;
        double average = -1;

        public Datum(int numofsubjects, String name, double average) {
            this.numofsubjects = numofsubjects;
            this.name = name;
            this.average = average;
        }

    }

    public SourceF1005(BedSections section, String sex, int gp, boolean orderByRank) {

        this.gradingPeriod = gp;
        this.section = new StringBuilder(section.getName()).append(" - Grade ").append(section.getYear()).toString();

        List<BedEnrollment> enrolled;
        if (sex == null || sex.equals("A")) {
            enrolled = BedSectionService.getInstance().getEnrolledByName(section.getId());
        } else if (sex.equals("M")) {
            enrolled = BedSectionService.getInstance().getEnrolledM(section.getId());
        } else {
            enrolled = BedSectionService.getInstance().getEnrolledF(section.getId());
        }

        List<Datum> d = new ArrayList();
        SortedSet<Double> rl = new TreeSet();

        for (BedEnrollment e : enrolled) {
            Datum dt = getAverageOnGP(e, this.gradingPeriod);
            d.add(dt);
            rl.add(dt.average);
        }

        rankList = new Double[rl.size()];
        rankList = rl.toArray(rankList);
        Arrays.sort(rankList);

        if (orderByRank) {
            Collections.sort(d, new Comparator<Datum>() {

                @Override
                public int compare(Datum o1, Datum o2) {
                    return Double.compare(o1.average, o2.average);
                }
            });
        }

        this.iterator = d.iterator();

    }

    private Datum getAverageOnGP(BedEnrollment enrollment, int gp) {

        Integer curricode = enrollment.getSection().getCurriculum().getCurricode();
        Integer year = enrollment.getSection().getYear();
        List<BedCurriculumDetail> subjects
                = BedCurriculumService.getInstance()
                .getBedCurriculumDetails(curricode, year);

        List<BedEnrollmentdetails> studentGrades
                = BedEnrollmentService.getInstance()
                .getDetailsWithValues(enrollment.getId());

        List<Double> grades = new ArrayList();

        for (BedCurriculumDetail c : subjects) {
            for (BedEnrollmentdetails d : studentGrades) {
                boolean match = Objects.equals(d.getCurriDetail().getId(), c.getId());
                boolean minorSubject = d.getCurriDetail().getDisplayOrder() % 1 > 0;
                if (match) {
                    Double studentGrade = 0d;
                    switch (gp) {
                        case 1:
                            studentGrade = d.getP1();
                            break;
                        case 2:
                            studentGrade = d.getP2();
                            break;
                        case 3:
                            studentGrade = d.getP3();
                            break;
                        case 4:
                            studentGrade = d.getP4();
                            break;
                        case 5:
                            d.updateFinalAve();
                            studentGrade = d.getFinals();
                    }

                    if (studentGrade == null || studentGrade == 0) {
                    } else {
                        if (!minorSubject) {
                            grades.add(studentGrade);
                        }
                    }

                    //  studentGrades.remove(d);
                }
            }
        }

        double average = 0d;
        for (Double double1 : grades) {
            average += double1;
        }
        average = average / grades.size();

        return new Datum(grades.size(), enrollment.getStudent().getFullName(), average);
    }

    @Override
    public boolean next() throws JRException {
        boolean g = iterator.hasNext();
        if (g) {
            count++;
            current = iterator.next();
        }
        return g;
    }

    //rank, name, grade, section
    private static final DecimalFormat decForm = new DecimalFormat("##.###");

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();
        if (n.equals("no")) {
            return count + "";
        }
        if (n.equals("gradingPeriod")) {
            if(gradingPeriod == 5){
                return "Finals";
            }
            return gradingPeriod + "";
        }
        if (n.equals("rank")) {
            return ((rankList.length - Arrays.binarySearch(rankList, current.average))) + "";
        }
        if (n.equals("name")) {
            return current.name;
        }
        if (n.equals("grade")) {

            try {
                return decForm.format(current.average);
            } catch (Exception e) {
            }

            return "n/a";
        }
        if (n.equals("section")) {
            return section;
        }
        if (n.equals("numofsubj")) {
            return current.numofsubjects + "";
        }
        return "%error%";
    }

}
