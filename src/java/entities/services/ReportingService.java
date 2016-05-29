package entities.services;

import controllers.utils.JsfUtil;
import controllers.utils.ReportControllerUtilities;
import static controllers.utils.ReportControllerUtilities.createPDFLink;
import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedLoading;
import entities.BedSections;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import reports.bed.generic.F12_src;
import reports.bed.generic.F13_src;
import reports.bed.generic.F14_src;
import reports.bed.generic.F15_src;
import reports.bed.generic.SourceF1001;
import reports.bed.generic.SourceF1002;
import reports.bed.generic.SourceF1003;
import reports.bed.generic.SourceF1004;
import reports.bed.generic.SourceF1005;
import reports.bed.generic.SourceF1006;
import reports.bed.generic.SourceF1007;
import reports.bed.generic.SourceF1008;
import reports.bed.generic.SourceF1011;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class ReportingService {

    private ReportingService() {
    }

//<editor-fold defaultstate="collapsed" desc="statics">
    private static ReportingService instance;

    public static ReportingService getInstance() {
        if (instance == null) {
            instance = new ReportingService();
        }
        return instance;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Report Files">
    private static final String F1001 = "/reports/bed/generic/F1001.jasper";
    private static final String F1002 = "/reports/bed/generic/F12.jasper";
    private static final String F1002_1 = "/reports/bed/generic/F12_1.jasper";
    private static final String F13 = "/reports/bed/generic/F13.jasper";
    private static final String F13_1 = "/reports/bed/generic/F13_1.jasper";
    private static final String F14 = "/reports/bed/generic/F14.jasper";
    private static final String F1005 = "/reports/bed/generic/F1005.jasper";
    private static final String F16 = "/reports/bed/generic/F16.jasper";
    private static final String F1007 = "/reports/bed/generic/F1007.jasper";
    private static final String F1008 = "/reports/bed/generic/F1008.jasper";
    private static final String F1011 = "/reports/bed/generic/F1011.jasper";
    private static final String F15 = "/reports/bed/generic/F15.jasper";
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Generic reports generator">
    public String generateF1001() throws JRException {
        //prepare teacher passwords
        BedTeacherService.getInstance().assignTeacherAccsDefaultPass();
        JasperPrint p
                = ReportControllerUtilities.createJasperPrint(
                        F1001,
                        new SourceF1001(
                                BedTeacherService.getInstance()
                                .getActiveTeachers()
                        )
                );
        return createPDFLink(p, "F1001"); //TODO pdf file names filter, and should include school id
    }

    public String generateF1002(BedSections s, String sex, int gradingPeriod, int ms, int me) throws JRException {

        List<BedEnrollment> enrollments = BedSectionService.getInstance().getEnrolledInPrintSequence(s.getId(), sex);

        int seq = 0;
        
        for (BedEnrollment e : enrollments) {

            seq++;

            boolean hasPrintSeq = e.getPrintSeq() != null ? e.getPrintSeq() == 0 : false;

            if (!hasPrintSeq) {
                e.setPrintSeq(seq);
                e = BedEnrollmentService.getInstance().update(e);
            }

        }

        JRDataSource source = new F12_src(enrollments, gradingPeriod, ms, me);

        String reportFile = gradingPeriod > 1 ? F1002_1 : F1002;

        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(reportFile, source);
        
        String fileName = "F12" + s.getId() + "" + sex;

        return createPDFLink(mainReport, fileName);

    }

    public String generateF1003(BedSections s, String sex, int gradingPeriod) throws JRException {

        List<BedEnrollment> enrollments
                = BedSectionService.getInstance()
                .getEnrolledInPrintSequence(s.getId(), sex);

        int seq = 0;
        for (BedEnrollment enrollment : enrollments) {
            seq++;

            boolean hasPrintSeq = enrollment.getPrintSeq() != null ? enrollment.getPrintSeq() == 0 : false;
            if (!hasPrintSeq) {
                enrollment.setPrintSeq(seq);
                enrollment = BedEnrollmentService.getInstance().update(enrollment);
            }

        }

        JRDataSource source = new F13_src(s, enrollments, gradingPeriod);

        String reportFile = gradingPeriod <= 1 ? F13 : F13_1;
        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(reportFile, source);

        return createPDFLink(mainReport, "F13." + s.getId() + "." + sex);
    }

    public String generateF15(BedSections s, String sex, int gradingPeriod, int ms, int me) throws JRException {

        List<BedEnrollment> enrollments
                = BedSectionService.getInstance()
                .getEnrolledInPrintSequence(s.getId(), sex);

        int seq = 0;
        for (BedEnrollment enrollment : enrollments) {
            seq++;

            boolean hasPrintSeq = enrollment.getPrintSeq() != null ? enrollment.getPrintSeq() == 0 : false;
            if (!hasPrintSeq) {
                enrollment.setPrintSeq(seq);
                enrollment = BedEnrollmentService.getInstance().update(enrollment);
            }

        }

        JRDataSource source = new F15_src(s, enrollments, gradingPeriod, ms, me);

        String reportFile = F15;

        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(reportFile, source);

        return createPDFLink(mainReport, "F15." + s.getId() + "." + sex);
    }

    public String genF14(BedSections s, String gender) throws JRException {
        List<BedLoading> loadings = BedLoadingService.getInstance().getLoadings(s);

        if (loadings.isEmpty()) {
            JsfUtil.addWarningMessage("No teacher loads", "Grading sheets cannot be generated.");
            return null;
        }

        int printSequence = 0;
        JasperPrint main = null;
        for (BedLoading load : loadings) {
            printSequence++;
            JRDataSource src = new F14_src(printSequence, gender, load);
            JasperPrint p = ReportControllerUtilities.createJasperPrint(F14, src);
            if (main == null) {
                main = p;
            } else {
                main = ReportControllerUtilities.combineJasperPrints(main, p);
            }
        }

        return createPDFLink(main, "F14." + s.getId() + "." + gender);
    }

    public String generateF1004PerLoad(BedLoading load, String gender) throws JRException {

        JRDataSource src = new SourceF1004(-1, gender, load);
        JasperPrint p = ReportControllerUtilities.createJasperPrint(F14, src);
        //JasperPrint p = ReportControllerUtilities.createJasperPrint(F1004_1, src); //for cjc
        return createPDFLink(p, "F1004L." + load.getId() + "." + gender);
    }

    public String generateF1005(BedSections section, String sex, int gp, boolean byRank) throws JRException {
        JRDataSource source = new SourceF1005(section, sex, gp, byRank);
        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(F1005, source);
        String disp = byRank ? "BYRANK" : "BYNAME";
        return createPDFLink(mainReport, "F1005." + section.getId() + "." + gp + "." + disp + "." + sex);
    }

    public String genF16(BedSections s, int gp) throws JRException {
        JRDataSource source = new SourceF1006(s, gp);
        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(F16, source);
        return createPDFLink(mainReport, "F1006" + "." + s.getId() + "." + gp);
    }

    public String generateF1007(BedSections section, int option) throws JRException {
        List<BedEnrollment> list = null;
        String gender = "";
        switch (option) {
            case 1: //all
                list = BedSectionService.getInstance().getEnrolledByName(section.getId());
                break;

            case 2:
                list = BedSectionService.getInstance().getEnrolledM(section.getId());
                JRDataSource source = new SourceF1007(section, list, "Boys");
                JasperPrint jp1 = ReportControllerUtilities.createJasperPrint(F1007, source);

                list = BedSectionService.getInstance().getEnrolledF(section.getId());
                source = new SourceF1007(section, list, "Girls");
                JasperPrint jp2 = ReportControllerUtilities.createJasperPrint(F1007, source);

                ReportControllerUtilities.combineJasperPrints(jp1, jp2);
                return createPDFLink(jp1, "F1007." + section.getId() + "." + "mixed");

            case 3://male
                gender = "Boys";
                list = BedSectionService.getInstance().getEnrolledM(section.getId());
                break;

            case 4://female
                gender = "Girls";
                list = BedSectionService.getInstance().getEnrolledF(section.getId());
                break;
        }

        JRDataSource source = new SourceF1007(section, list, gender);
        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(F1007, source);
        return createPDFLink(mainReport, "F1007" + "." + section.getId() + "." + gender);

    }

    public String generateF1008(BedSections section) throws JRException {
        JasperPrint print
                = ReportControllerUtilities.createJasperPrint(F1008,
                        new SourceF1008(section));
        return createPDFLink(print, "F1008" + "." + section.getId());
    }

    public String generateF1011(BedSections s, BedCurriculumDetail aggregate, int gp) throws JRException {
        JRDataSource source = new SourceF1011(s, aggregate, gp);
        JasperPrint mainReport = ReportControllerUtilities.createJasperPrint(F1011, source);
        return createPDFLink(mainReport, "F1011" + "." + s.getId() + "." + aggregate.getId() + "." + gp);
    }
//</editor-fold>

}
