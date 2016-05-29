package reports.bed.generic;

import controllers.utils.JsfUtil;
import entities.BedEnrollmentCharacterTraits;
import entities.BedEnrollmentdetails;
import entities.BedSettings;
import entities.BedEnrollment;
import entities.BedCurriculumDetail;
import entities.BedSections;
import entities.services.BedEnrollmentService;
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
public class F13_src implements JRDataSource {

    private BedEnrollment enrollment;

    List<BedEnrollmentCharacterTraits> traits;
    List<BedEnrollmentdetails> grades;
    
    BedSections section;

    private int seq;
    private String initialName;
    private BedSettings settings;
    private final int gradingPeriod;

    String seq1 = "";
    String seq2 = "";
    String seq3 = "";
    String seq4 = "";

    private final Iterator<BedEnrollment> iterator;

    public F13_src(BedSections section, List<BedEnrollment> enrollments, int gradingPeriod) {
        this.iterator = enrollments.iterator();
        this.gradingPeriod = gradingPeriod;
        this.section = section;
    }

    @Override
    public boolean next() throws JRException {

        if (!this.iterator.hasNext()) {
            return false;
        }

        this.enrollment = this.iterator.next();

        if (this.settings == null) {
            this.settings = BedEnrollmentService.getInstance().getEnrollmentBedSettings(enrollment.getId());
        }

        //<editor-fold defaultstate="collapsed" desc="sequence">
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
        //</editor-fold>

        traits = BedEnrollmentService.getInstance().getCharacterRecords(enrollment.getId());
        grades = BedEnrollmentService.getInstance().getDetails(enrollment.getId());

        //<editor-fold defaultstate="collapsed" desc="arr">
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
        //</editor-fold>

        return true;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        switch (n) {
            //<editor-fold defaultstate="collapsed" desc="headers">
            case "tbl1Datasource": {
                SourceF1003Tbl1 s = new SourceF1003Tbl1(section, grades, gradingPeriod);
                return s;
            }
            case "seq_1":
                return seq1;
            case "seq_2":
                return seq2;
            case "seq_3":
                return seq3;
            case "seq_4":
                return seq4;
//</editor-fold>
        }

        if (gradingPeriod == 0 || gradingPeriod == 1) {
            //<editor-fold defaultstate="collapsed" desc="gp1">
            switch (n) {
                case "f1_1":
                    return findC(1, 1);
                case "f2_1":
                    return findC(2, 1);
                case "f3_1":
                    return findC(3, 1);
                case "f4_1":
                    return findC(4, 1);
                case "f5_1":
                    return findC(5, 1);
                case "f6_1":
                    return findC(6, 1);
                case "f7_1":
                    return findC(7, 1);
            }
//</editor-fold>
        }

        if (gradingPeriod == 0 || gradingPeriod == 2) {
            //<editor-fold defaultstate="collapsed" desc="gp2">
            switch (n) {
                case "f1_2":
                    return findC(1, 2);
                case "f2_2":
                    return findC(2, 2);
                case "f3_2":
                    return findC(3, 2);
                case "f4_2":
                    return findC(4, 2);
                case "f5_2":
                    return findC(5, 2);
                case "f6_2":
                    return findC(6, 2);
                case "f7_2":
                    return findC(7, 2);
            }
            //</editor-fold>
        }

        if (gradingPeriod == 0 || gradingPeriod == 3) {
            //<editor-fold defaultstate="collapsed" desc="gp3">
            switch (n) {
                case "f1_3":
                    return findC(1, 3);
                case "f2_3":
                    return findC(2, 3);
                case "f3_3":
                    return findC(3, 3);
                case "f4_3":
                    return findC(4, 3);
                case "f5_3":
                    return findC(5, 3);
                case "f6_3":
                    return findC(6, 3);
                case "f7_3":
                    return findC(7, 3);

            }
//</editor-fold>
        }

        if (gradingPeriod == 0 || gradingPeriod == 4) {
            //<editor-fold defaultstate="collapsed" desc="gp4">
            switch (n) {
                case "f1_4":
                    return findC(1, 4);
                case "f2_4":
                    return findC(2, 4);
                case "f3_4":
                    return findC(3, 4);
                case "f4_4":
                    return findC(4, 4);
                case "f5_4":
                    return findC(5, 4);
                case "f6_4":
                    return findC(6, 4);
                case "f7_4":
                    return findC(7, 4);

            }
//</editor-fold>
        }

        return "";
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

    private Object findC(double r, int c) {
        for (BedEnrollmentCharacterTraits t : traits) {
            if (t.getBedCharacterTrait().getCtOrder() == r) {
                return find(t, c);
            }
        }
        return "";

    }

    private Object find(BedEnrollmentCharacterTraits t, int c) {
        switch (c) {
            case 1:
                return t.getP1Value();
            case 2:
                return t.getP2Value();
            case 3:
                return t.getP3Value();
            case 4:
                return t.getP4Value();
        }
        return "";
    }
}
