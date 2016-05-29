package reports.bed.generic;

import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedSections;
import entities.services.BedCurriculumDetailService;
import entities.services.BedEnrollmentDetailService;
import static entities.services.BedEnrollmentDetailService.getProficiencyLevel;
import entities.services.BedSectionService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1011 implements JRDataSource {

    private final DecimalFormat decimalFormatter = new DecimalFormat("##.##");

    private final int gradingPeriod;
    private final BedSections section;

    private final List<DataUnit> dums = new ArrayList();
    private final Iterator<DataUnit> iterator;

    private DataUnit current;
    private final BedCurriculumDetail aggregateSubject;

    public SourceF1011(BedSections section, BedCurriculumDetail aggregateSubject, int gradingPeriod) {

        this.section = section;
        this.gradingPeriod = gradingPeriod;
        this.aggregateSubject = aggregateSubject;

        List<BedCurriculumDetail> componentSubjects = BedCurriculumDetailService.getInstance().getComponents(aggregateSubject);

        // dums.add(new Dum("BOYS", "Final Rating", "", ""));
        List<BedEnrollment> enrolledM = BedSectionService.getInstance().getEnrolledM(section.getId());
        populate("BOYS", enrolledM, componentSubjects, gradingPeriod);

        //dums.add(new Dum("GIRLS", "Final Rating", "", ""));
        List<BedEnrollment> enrolledF = BedSectionService.getInstance().getEnrolledF(section.getId());
        populate("GIRLS", enrolledF, componentSubjects, gradingPeriod);
        this.iterator = dums.iterator();
    }

    private void populateHead(String categoryName, List<BedCurriculumDetail> subjects) {
        for (BedCurriculumDetail d : subjects) {
            dums.add(new DataUnit(categoryName, d.getSubjcode().getSubjcode(), "", ""));
        }
        dums.add(new DataUnit(categoryName, "Final Rating", "", ""));
    }

    /**
     * What will be reflected here is the actual average of the component
     * subjects. The parent subject's grade will be ignored.
     *
     * @param cat
     * @param enrolledM
     * @param aggregateSubject
     * @param gp
     */
    private void populate(String cat, List<BedEnrollment> enrolledM, List<BedCurriculumDetail> componentSubjects, int gp) {

        int studentCount = 0;
        for (BedEnrollment studentEnrollment : enrolledM) {

            if (studentCount == 0) {
                populateHead(cat, componentSubjects);
            }
            studentCount++;
            String stdName = studentCount + ". " + studentEnrollment.getStudent().getFullName();

            double finalAverage = 0;

            for (BedCurriculumDetail subject : componentSubjects) {
                BedEnrollmentdetails d = BedEnrollmentDetailService.getInstance().getEnrollmentDetail(subject, studentEnrollment);
                //<editor-fold defaultstate="collapsed" desc="counter check and get grade">
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
                
                finalAverage += studentGrade;

                String grade = decimalFormatter.format(studentGrade);
                String desc = getProficiencyLevel(studentGrade);

                //</editor-fold>
                dums.add(new DataUnit(stdName, subject.getSubjcode().getSubjcode(), grade, desc));
            }

            finalAverage = finalAverage / componentSubjects.size();
            dums.add(new DataUnit(stdName, "Final Rating", decimalFormatter.format(finalAverage), getProficiencyLevel(finalAverage)));
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
            return "Grading Period: " + gradingPeriod;
        }

        if (n.equals("subjectName")) {
            return aggregateSubject.getSubjcode().getSubjcode();
        }

        return "%errors% ";
    }

    private final class DataUnit {

        String row;
        String col;
        String v1;
        String v2;

        public DataUnit(String row, String col, String v1, String v2) {
            this.row = row;
            this.col = col;
            this.v1 = v1;
            this.v2 = v2;
        }

    }

}
