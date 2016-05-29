package reports.bed.generic;

import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedSectionHiddenSubject;
import entities.BedSections;
import entities.services.BedEnrollmentDetailService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import entities.services.BedSectionHiddenSubjectService;
import java.math.RoundingMode;
import java.text.FieldPosition;

/**
 *
 * @author John Philip
 */
public class SourceF1003Tbl1 implements JRDataSource {

    public static final DecimalFormat FORMAT_1 = new DecimalFormat("#.0") {
        @Override
        public StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }

    };
    public static final DecimalFormat FORMAT_2 = new DecimalFormat("##") {
        @Override
        public StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }
    };
    public static final DecimalFormat FORMAT_3 = new DecimalFormat("##.000") {
        @Override
        public StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
            if (number == 0) {
                return new StringBuffer("");
            }
            return super.format(number, result, fieldPosition); //To change body of generated methods, choose Tools | Templates.
        }
    };

    private final int gradingPeriod;
    private final Iterator<BedEnrollmentdetails> iterator;

    private BedEnrollmentdetails current;

    static {
        FORMAT_1.setRoundingMode(RoundingMode.HALF_UP);
        FORMAT_2.setRoundingMode(RoundingMode.HALF_UP);
        FORMAT_3.setRoundingMode(RoundingMode.HALF_UP);
    }

    public SourceF1003Tbl1(BedSections section, List<BedEnrollmentdetails> details, int gradingPeriod) {

        this.gradingPeriod = gradingPeriod;

        List<BedEnrollmentdetails> toRemove = new ArrayList();

        List<BedSectionHiddenSubject> hiddenSubjects = BedSectionHiddenSubjectService.getInstance().getHiddenSubjects(section);

        for (BedEnrollmentdetails det : details) {

            boolean eq = false;
            for (BedSectionHiddenSubject h : hiddenSubjects) {
                eq = det.getCurriDetail().getSubjcode().getSubjcode().equalsIgnoreCase(h.getSubjectCode());
                if (eq) {
                    break;
                }
            }

            if (eq || det.getCurriDetail().getDisplayOrder() == -1) {
                toRemove.add(det);
            }
        }

        details.removeAll(toRemove);
        this.iterator = details.iterator();

    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            current = iterator.next();
            current.updateFinalAve();
            BedEnrollmentDetailService.getInstance().update(current);
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        try {

            String field = jrf.getName();

            if (gradingPeriod == 0 || gradingPeriod == 1) {

                if (field.equals("learningArea")) {
                    //<editor-fold defaultstate="collapsed" desc="learning area text">
                    double dispOrder = current.getCurriDetail().getDisplayOrder();
                    dispOrder = dispOrder % 1;

                    String subDesc = current.getCurriDetail().getSubjcode().getSubjdesc();
                    boolean secondary = dispOrder > 0;
                    if (secondary) {
                        subDesc = "    " + subDesc;
                    }
                    return subDesc;
                    //</editor-fold>
                }

                if (field.equals("p1value")) {
                    return FORMAT_2.format(current.getP1());
                }

            }

            if (gradingPeriod == 0 || gradingPeriod == 2) {
                //<editor-fold defaultstate="collapsed" desc="gp2">
                if (field.equals("p2value")) {
                    return FORMAT_2.format(current.getP2());
                }
                //</editor-fold>
            }
            if (gradingPeriod == 0 || gradingPeriod == 3) {
                //<editor-fold defaultstate="collapsed" desc="gp3">
                if (field.equals("p3value")) {
                    return FORMAT_2.format(current.getP3());
                }
                //</editor-fold>
            }

            if (gradingPeriod == 0 || gradingPeriod == 4) {
                //<editor-fold defaultstate="collapsed" desc="gp4">
                if (field.equals("p4value")) {
                    return FORMAT_2.format(current.getP4());
                }
                //</editor-fold>
            }

            if (current.getP4() > 0) {
                switch (field) {
                    case "final":
                        if (!current.getBedEnrollment().isHonorLearner()) {
                            return FORMAT_2.format(current.getFinals());
                        }
                        break;
                    case "final_hform":
                        if (current.getBedEnrollment().isHonorLearner()) {
                            return FORMAT_3.format(current.getFinals());
                        }
                        break;
                    case "final_r": {
                        Double finals = current.getFinals();
                        if (finals == null || finals == 0) {
                            return "";
                        }
                        if (finals < 75) {
                            return "Failed";
                        } else {
                            return "Passed";
                        }
                    }
                    case "general":
                        if (!current.getBedEnrollment().isHonorLearner()) {
                            BedEnrollment e = current.getBedEnrollment();
                            e.updateGeneralAverage();
                            return FORMAT_2.format(e.getGeneralAverage());
                        }
                        break;
                    case "general_hform":
                        if (current.getBedEnrollment().isHonorLearner()) {
                            BedEnrollment e = current.getBedEnrollment();
                            e.updateGeneralAverage();
                            return FORMAT_3.format(e.getGeneralAverage());
                        }
                        break;
                    case "general_r": {
                        BedEnrollment e = current.getBedEnrollment();
                        Double finalG = e.updateGeneralAverage();
                        if (finalG == 0) {
                            return "";
                        }
                        if (finalG < 75) {
                            return "Failed";
                        } else {
                            return "Passed";
                        }
                    }

                    default:
                        break;
                }

            }

            return "";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //</editor-fold>
}
