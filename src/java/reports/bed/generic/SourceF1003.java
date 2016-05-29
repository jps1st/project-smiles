package reports.bed.generic;

import controllers.utils.JsfUtil;
import entities.BedEnrollmentCharacterTraits;
import entities.BedEnrollmentdetails;
import entities.BedSettings;
import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import entities.BedCurriculumDetail;
import entities.BedSections;
import entities.services.BedEnrollmentService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Report source for Report Cards
 *
 * @author John Philip
 */
public class SourceF1003 implements JRDataSource {

    private BedEnrollment enrollment;

    BedEnrollmentAttendanceRecord attendanceRecords;
    List<BedEnrollmentCharacterTraits> traits;
    List<BedEnrollmentdetails> grades;

    private int seq;
    private String initialName;
    private BedSettings settings;
    private final int gradingPeriod;
    private final int monthStart;

    BedSections section;
    private final int monthEnd;

    String seq1 = "";
    String seq2 = "";
    String seq3 = "";
    String seq4 = "";

    private final Iterator<BedEnrollment> iterator;

    public SourceF1003(BedSections section, List<BedEnrollment> enrollments, int gradingPeriod, int monthStart, int monthEnd) {
        this.iterator = enrollments.iterator();
        this.gradingPeriod = gradingPeriod;
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
        this.section = section;
    }

    @Override
    public boolean next() throws JRException {

        if (!this.iterator.hasNext()) {
            return false;
        }

        this.enrollment = this.iterator.next();

        if (this.settings == null) {
            this.settings
                    = BedEnrollmentService.getInstance()
                    .getEnrollmentBedSettings(enrollment.getId());
        }

        this.seq = enrollment.getPrintSeq();
        this.initialName
                = enrollment.getStudent().getFirstname()
                .substring(0, 1)
                .toUpperCase()
                + ". " + enrollment.getStudent().getLastname();

        seq1 = "";
        seq2 = "";
        seq3 = "";
        seq4 = "";

        switch (gradingPeriod) {
            case 0: //no labels
            case 1:
                seq1 = seq + " - " + initialName;
                break;
            case 2:
                seq2 = seq + " - " + initialName;
                break;
            case 3:
                seq3 = seq + " - " + initialName;
                break;
            case 4:
                seq4 = seq + " - " + initialName;
                break;
        }

        attendanceRecords
                = BedEnrollmentService.getInstance()
                .getAttendanceRecord(enrollment.getId());
        traits
                = BedEnrollmentService.getInstance()
                .getCharacterRecords(enrollment.getId());
        grades
                = BedEnrollmentService.getInstance()
                .getDetails(enrollment.getId());

        List<BedEnrollmentdetails> temp = new ArrayList();

        boolean parent;
        double lastWhole = -1;
        for (BedEnrollmentdetails enrollmentDetail : grades) {

            double displayOrder
                    = enrollmentDetail.getCurriDetail().getDisplayOrder();

            parent = displayOrder % 1 == 0;

            if (parent) {
                lastWhole = displayOrder;
            } else {

                double floor = Math.floor(displayOrder);

                if (floor != lastWhole) {
                    //additional detail here
                    BedEnrollmentdetails m = getMockDetailSubject(enrollmentDetail, floor);
                    if (m != null) {
                        temp.add(m);
                    } else {
                        JsfUtil.addWarningMessage("cant find floor", null);
                    }
                    lastWhole = floor;
                }
            }
            temp.add(enrollmentDetail);
        }

        grades = temp;

        return true;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        switch (n) {
            case "tbl1Datasource": {
                SourceF1003Tbl1 s = new SourceF1003Tbl1(section, grades, gradingPeriod);
                return s;
            }
            case "tbl2Datasource": {
                SourceF1003Tbl2 s = new SourceF1003Tbl2(attendanceRecords, settings, monthStart, monthEnd);
                return s;
            }
            case "tbl3Datasource": {
                JRDataSource s = new SourceF1003Tbl3(traits, gradingPeriod);
                return s;
            }
            case "tbl4Datasource": {
                JRDataSource s = new SourceF1003Tbl3(traits, gradingPeriod);
                return s;
            }
            case "adviser":
                try {
                    String adviser = enrollment.getSection().getAdviser().getFullName();
                    return returnAppropriate(adviser);
                } catch (Exception e) {
                    return "";
                }
            case "principal":
                String schoolPrincipalName = settings.getSchoolPrincipalName();
                return returnAppropriate(schoolPrincipalName);
            case "lacking_credit":
                return returnAppropriate(enrollment.getMissingUnitsIn());
            case "eligible_to":
                if (enrollment.getEligibleForTransferTo() == null) {
                    return "";
                }
                return returnAppropriate(enrollment.getEligibleForTransferTo());
            case "star":
                //ImageIcon i = new ImageIcon("reports/bed/generic/icon_star.png");
                URL resource = getClass().getResource("/reports/bed/generic_icon.png");
                return resource;
            case "seq_1":
                return seq1;
            case "seq_2":
                return seq2;
            case "seq_3":
                return seq3;
            case "seq_4":
                return seq4;
            default:
                return "";

        }
    }

    private String returnAppropriate(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.equals("null") || s.trim().contains("null")) {
            return "";
        }
        return s;
    }

    public String getProperCase(String s) {
        return toTitleCase(s.toLowerCase());

    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    private BedEnrollmentdetails getMockDetailSubject(BedEnrollmentdetails d, double floor) {
        BedCurriculumDetail sample = d.getCurriDetail();

        EntityManager em = BedEnrollmentService.getInstance().getNewEntityManager();
        List<BedCurriculumDetail> rs = em.createQuery("SELECT c FROM BedCurriculumDetail c "
                + "WHERE c.currcode.curricode = ?1 "
                + "AND c.yrLevel =?2 "
                + "AND c.displayOrder = ?3", BedCurriculumDetail.class)
                .setParameter(1, sample.getCurrcode().getCurricode())
                .setParameter(2, d.getBedEnrollment().getSection().getYear()).setParameter(3, floor).getResultList();
        em.close();

        if (rs.isEmpty()) {
            return null;
        }

        BedEnrollmentdetails newDetail = new BedEnrollmentdetails();
        newDetail.setCurriDetail(rs.get(0));
        return newDetail;
    }
}
