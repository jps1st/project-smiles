package reports.bed.generic;

import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedSections;
import entities.services.BedCurriculumService;
import static entities.services.BedEnrollmentDetailService.getProficiencyLevel;
import entities.services.BedEnrollmentService;
import entities.services.BedSectionService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import static reports.bed.generic.SourceF1003Tbl1.FORMAT_2;
import static reports.bed.generic.SourceF1003Tbl1.FORMAT_3;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1006 implements JRDataSource {

    private final int gradingPeriod;
    private final BedSections section;

    private final List<Dum> dums = new ArrayList();
    private final Iterator<Dum> iterator;

    private Dum current;

    public SourceF1006(BedSections section, int gradingPeriod) {

        this.section = section;
        this.gradingPeriod = gradingPeriod;

        // dums.add(new Dum("BOYS", "Final Rating", "", ""));
        List<BedEnrollment> enrolledM = BedSectionService.getInstance().getEnrolledM(section.getId());
        populate("BOYS", enrolledM, gradingPeriod);

        //dums.add(new Dum("GIRLS", "Final Rating", "", ""));
        List<BedEnrollment> enrolledF = BedSectionService.getInstance().getEnrolledF(section.getId());
        populate("GIRLS", enrolledF, gradingPeriod);
        this.iterator = dums.iterator();

    }

    private void populateHeadWithStr(String cat, String[] dets) {
        for (String d : dets) {
            dums.add(new Dum(cat, d, "", ""));
        }
        dums.add(new Dum(cat, "Final Rating", "", ""));
    }

    private void populateHead(String cat, List<BedCurriculumDetail> dets) {
        for (BedCurriculumDetail d : dets) {
            dums.add(new Dum(cat, d.getSubjcode().getSubjcode(), "", ""));
        }
        dums.add(new Dum(cat, "Final Rating", "", ""));
    }

    private double getAverageForGp(BedEnrollment enrolledM, int gp) {
        List<BedCurriculumDetail> subjects = BedCurriculumService.getInstance().getBedCurriculumDetails(section.getCurriculum().getCurricode(), section.getYear());
        List<BedEnrollmentdetails> studentGrades = BedEnrollmentService.getInstance().getDetailsWithValues(enrolledM.getId());

        double finalAverage = 0;
        int subjectsForAverage = 0;
        for (BedCurriculumDetail c : subjects) {

            for (BedEnrollmentdetails d : studentGrades) {
                //<editor-fold defaultstate="collapsed" desc="counter check and get grade">
                boolean match = Objects.equals(d.getCurriDetail().getId(), c.getId());

                if (!match) {
                    continue;
                }

                boolean minorSubject = d.getCurriDetail().getDisplayOrder() % 1 > 0;

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
                }

                if (studentGrade == null || studentGrade == 0) {
                    studentGrade = 0d;
                } else if (!minorSubject) {
                    subjectsForAverage++;
                    finalAverage += studentGrade;
                }

                studentGrades.remove(d);
                break;
                //</editor-fold>
            }

        }

        return finalAverage / subjectsForAverage;
    }

    private void populateFinals(String cat, List<BedEnrollment> enrolledM) {

        String[] strs = new String[]{"First GP", "Second GP", "Third GP", "Fourth GP"};
        populateHeadWithStr(cat, strs);

        int index = 0;
        for (BedEnrollment enrollment : enrolledM) {
            index++;
            String stdName = index + ". " + enrollment.getStudent().getFullName();

            double finalAverage = 0d;
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                double studentGrade = getAverageForGp(enrollment, i + 1);
                finalAverage += studentGrade;
                String grade = enrollment.isHonorLearner() ? FORMAT_3.format(studentGrade) : FORMAT_2.format(studentGrade);
                String desc = getProficiencyLevel(studentGrade);
                dums.add(new Dum(stdName, str, grade, desc));
            }

            finalAverage = finalAverage / strs.length;
            
            String finalAverageStr = enrollment.isHonorLearner() ? FORMAT_3.format(finalAverage) : FORMAT_2.format(finalAverage);
            
            dums.add(new Dum(stdName, "Final Rating", finalAverageStr, getProficiencyLevel(finalAverage)));
        }

    }

    private void populate(String cat, List<BedEnrollment> enrolledM, int gp) {

        if (gp == 6) {
            populateFinals(cat, enrolledM);
            return;
        }

        int studentCount = 0;
        for (BedEnrollment enrollment : enrolledM) {
            studentCount++;
            String stdName = studentCount + ". " + enrollment.getStudent().getFullName();
            List<BedCurriculumDetail> subjects = BedCurriculumService.getInstance().getBedCurriculumDetails(section.getCurriculum().getCurricode(), section.getYear());

            if (studentCount == 1) {
                populateHead(cat, subjects);
            }

            List<BedEnrollmentdetails> studentGrades = BedEnrollmentService.getInstance().getDetailsWithValues(enrollment.getId());

            double finalAverage = 0;
            int subjectsForAverage = 0;
            for (BedCurriculumDetail c : subjects) {

                String grade = "";
                String desc = "";
                for (BedEnrollmentdetails d : studentGrades) {

                    //<editor-fold defaultstate="collapsed" desc="counter check and get grade">
                    boolean match = Objects.equals(d.getCurriDetail().getId(), c.getId());

                    if (!match) {
                        continue;
                    }

                    boolean minorSubject = d.getCurriDetail().getDisplayOrder() % 1 > 0;

                    double studentGrade = 0d;

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
                            studentGrade = (d.getP1() + d.getP2() + d.getP3() + d.getP4()) / 4;
                    }

                    if (!minorSubject) {
                        subjectsForAverage++;
                        finalAverage += studentGrade;
                    }

                    grade = FORMAT_2.format(studentGrade);

                    desc = getProficiencyLevel(studentGrade);

                    studentGrades.remove(d);
                    break;
                    //</editor-fold>
                }

                dums.add(new Dum(stdName, c.getSubjcode().getSubjcode(), grade, desc));

            }

            finalAverage = finalAverage / subjectsForAverage;
            
            String finalAverageStr = enrollment.isHonorLearner() ? FORMAT_3.format(finalAverage) : FORMAT_2.format(finalAverage);
            
            dums.add(new Dum(stdName, "Final Rating", finalAverageStr, getProficiencyLevel(finalAverage)));
        }
    }

    @Override
    public boolean next() throws JRException {
        boolean i = iterator.hasNext();
        if (i) {
            this.current = iterator.next();
        }
        return i;
    }

    //row, row1, val, val1
    @Override
    public Object getFieldValue(JRField jrf) throws JRException {

        String n = jrf.getName();

        if (n.equals("row")) {
            return current.row;
        }

        if (n.equals("col")) {
            return current.col;
        }

        if (n.equals("val")) {
            if (current.v1 == null || current.v1.equals("null")) {
                return "";
            }
            return current.v1;
        }

        if (n.equals("val1")) {
            if (current.v1 == null || current.v1.equals("null")) {
                return "";
            }
            return current.v2;
        }

        if (n.equals("sec")) {
            return "Grade " + section.getYear() + " - " + section.getName();
        }

        if (n.equals("grading")) {
            if (gradingPeriod == 5) {
                return "Grading Period: Final";
            }
            return "Grading Period: " + gradingPeriod;
        }
        return "%errors% ";
    }

    private List<BedCurriculumDetail> getChildren(BedCurriculumDetail mainSubject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final class Dum {

        String row;
        String col;
        String v1;
        String v2;

        public Dum(String row, String col, String v1, String v2) {
            this.row = row;
            this.col = col;
            this.v1 = v1;
            this.v2 = v2;
        }

    }

}
