package reports.bed.generic;

import controllers.utils.Utils;
import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import entities.BedSettings;
import entities.services.BedEnrollmentService;
import entities.services.BedSettingsService;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class F12_src implements JRDataSource {

    //<editor-fold defaultstate="collapsed" desc="variables">
    private String name;
    private String sy;
    private String grade;
    private String gender;
    private String age;
    private String sec;
    private String seq1 = "";
    private String seq2 = "";
    private String seq3 = "";
    private String seq4 = "";

    BedEnrollment current;
    //</editor-fold>

    private Iterator<BedEnrollment> iterator;
    private final int gradingPeriod;
    private final int monthStart;
    private final int monthEnd;
    private final BedSettings settings;
    private F12_t1_src tblds;

    public F12_src(List<BedEnrollment> enrollments, int gradingPeriod, int monthStart, int monthEnd) {
        this.iterator = enrollments.iterator();
        this.gradingPeriod = gradingPeriod;
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
        this.settings = BedSettingsService.getInstance().getActiveBedSettings();
    }

    DecimalFormat ftr = new DecimalFormat("##");

    @Override
    public boolean next() throws JRException {
        boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            return false;
        }

        current = iterator.next();

        //seq
        String seq = current.getPrintSeq() + " - " + current.getStudent().getFirstname().substring(0, 1) + ". " + current.getStudent().getLastname();

        seq1 = "";
        seq2 = "";
        seq3 = "";
        seq4 = "";

        switch (gradingPeriod) {
            case 1:
                seq1 = seq;
                break;
            case 2:
                seq2 = seq;
                break;
            case 3:
                seq3 = seq;
                break;
            case 4:
                seq4 = seq;
                break;
        }

        //name
        this.name = current.getStudent().getFullName();
        //age
        this.age = "" + ftr.format(Utils.getAge(current.getStudent().getBirthdate(), new java.util.Date()));
        //sy
        Integer sy = current.getSection().getSy();
        this.sy = sy + "-" + (sy + 1);
        //grade
        this.grade = "" + current.getSection().getYear();
        //gen
        String s = current.getStudent().getSex();
        if (s != null && s.equals("M")) {
            this.gender = "MALE";
        } else {
            this.gender = "FEMALE";
        }
        //sec
        this.sec = current.getSection().getName();
        //tblds
        BedEnrollmentAttendanceRecord attendanceRecords = BedEnrollmentService.getInstance().getAttendanceRecord(current.getId());
        tblds = new F12_t1_src(attendanceRecords, settings, monthStart, monthEnd);

        return true;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        if (n.equals("name")) {
            return name;
        }

        if (n.equals("seq_1")) {
            return seq1;
        }

        if (n.equals("seq_2")) {
            return seq2;
        }

        if (n.equals("seq_3")) {
            return seq3;
        }

        if (n.equals("seq_4")) {
            return seq4;
        }

        if (n.equals("sec")) {
            return sec;
        }

        if (n.equals("sy")) {
            return sy;
        }

        if (n.equals("grade")) {
            return grade;
        }

        if (n.equals("gender")) {
            return gender;
        }

        if (n.equals("age")) {
            return age;
        }

        if (n.equals("tblds")) {
            return tblds;
        }

        if (n.equals("principal")) {
            return settings.getSchoolPrincipalName();
        }

        if (n.equals("teacher")) {
            return getAdviser(current);
        }

        if (n.equals("seq_1")) {
            return seq1;
        }

        if (n.equals("region")) {
            return settings.getRegion();
        }

        if (n.equals("division")) {
            return settings.getDivision();

        }
        if (n.equals("district")) {
            return settings.getDistrict();

        }
        if (n.equals("school")) {
            return settings.getSchoolName();
        }

        return null;
    }

    private String adviser;

    private Object getAdviser(BedEnrollment current) {
        if (adviser == null) {
            adviser = current.getSection().getAdviser().getFullName();
        }
        return adviser;
    }

}
