package reports.bed.generic;

import controllers.utils.Utils;
import entities.BedEnrollment;
import entities.BedEnrollmentNarrativeReport;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1002 implements JRDataSource {

//<editor-fold defaultstate="collapsed" desc="variables">
    private String q4;
    private String q3;
    private String q2;
    private String q1;
    private String name;
    private String bday;
    private String age;
    private String curri;
    private String sy;
    private String grade;
    private String gender;
    private String seq;
    private String lrn;
    private String sec;
    private String seq1 = "";
    private String seq2 = "";
    private String seq3 = "";
    private String seq4 = "";
    private JRRenderable logo3;
    private JRRenderable logo2;
    private JRRenderable logo1;

    BedEnrollment current;
//</editor-fold>
    private Iterator<BedEnrollment> iterator;
    private final int gradingPeriod;

    public SourceF1002(List<BedEnrollment> enrollments, int gradingPeriod) {
        this.iterator = enrollments.iterator();
        this.gradingPeriod = gradingPeriod;

        InputStream logo3Stream
                = this.getClass()
                .getResourceAsStream("/reports/bed/generic"
                        + "/logos/kagawaran_ng_edukasyon.png"
                );

        InputStream logo2Stream
                = this.getClass()
                .getResourceAsStream("/reports/bed/generic"
                        + "/logos/deped_k12.png"
                );

        InputStream logo1Stream = this.getClass()
                .getResourceAsStream("/reports/bed/generic"
                        + "/logos/hcmnhs.png"
                );

        try {

            this.logo3
                    = JRImageRenderer.getInstance(
                            logo3Stream,
                            OnErrorTypeEnum.ERROR
                    );
            this.logo2
                    = JRImageRenderer.getInstance(
                            logo2Stream,
                            OnErrorTypeEnum.ERROR
                    );
            this.logo1
                    = JRImageRenderer.getInstance(
                            logo1Stream,
                            OnErrorTypeEnum.ERROR
                    );

        } catch (JRException jRException) {
            jRException.printStackTrace();
        }

    }

    @Override
    public boolean next() throws JRException {
        boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            return false;
        }
        current = iterator.next();

        BedEnrollmentNarrativeReport narrativeReport = current.getBedEnrollmentNarrativeReport();
        this.q1 = "";
        this.q2 = "";
        this.q3 = "";
        this.q4 = "";

        this.seq
                = current.getPrintSeq()
                + " - "
                + current.getStudent().getFirstname().substring(0, 1)
                + ". "
                + current.getStudent().getLastname();

        //<editor-fold defaultstate="collapsed" desc="narrative reports filter">
        seq1 = "";
        seq2 = "";
        seq3 = "";
        seq4 = "";
        if (narrativeReport != null) {
            switch (gradingPeriod) {
                case 0:
                    this.seq1 = seq;
                    this.q1 = narrativeReport.getFirstGrading();
                    this.q2 = narrativeReport.getSecondGrading();
                    this.q3 = narrativeReport.getThirdGrading();
                    this.q4 = narrativeReport.getFourthGrading();
                    break;
                case 1:
                    this.seq1 = seq;
                    this.q1 = narrativeReport.getFirstGrading();
                    break;
                case 2:
                    this.seq2 = seq;
                    this.q2 = narrativeReport.getSecondGrading();
                    break;
                case 3:
                    this.seq3 = seq;
                    this.q3 = narrativeReport.getThirdGrading();
                    break;
                case 4:
                    this.seq4 = seq;
                    this.q4 = narrativeReport.getFourthGrading();
                    break;
            }
        }
//</editor-fold>

        this.name = current.getStudent().getFullName();
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.bday = f.format(current.getStudent().getBirthdate());
        } catch (Exception ex) {
            this.bday = "";
        }

        DecimalFormat ftr = new DecimalFormat("##");
        this.age = "" + ftr.format(Utils.getAge(current.getStudent().getBirthdate(), new java.util.Date()));
        this.curri = current.getSection().getCurriculum().getShortName();
        Integer id = current.getSection().getSy();
        this.sy = id + "-" + (id + 1);
        this.grade = "" + current.getSection().getYear();

        String s = current.getStudent().getSex();
        if (s != null && s.equals("M")) {
            this.gender = "LALAKI";
        } else {
            this.gender = "BABAE";
        }

        this.lrn = current.getStudent().getLearnerIdNo();
        this.sec = current.getSection().getName();
        return true;
    }

    //q1, q2,q3, q4,name,bday,sy,grade,gender,age,curri,sec,lrn, seq
    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        if (n.equals("q1")) {
            return q1;
        }
        if (n.equals("q2")) {
            return q2;
        }
        if (n.equals("q3")) {
            return q3;
        }
        if (n.equals("q4")) {
            return q4;
        }
        if (n.equals("name")) {
            return name;
        }
        if (n.equals("bday")) {
            return bday;
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
        if (n.equals("lrn")) {
            return lrn;
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
        if (n.equals("curri")) {
            return curri;
        }
        if (n.equals("logo1")) {
            return logo1;
        }
        if (n.equals("logo2")) {
            return logo2;
        }
        if (n.equals("logo3")) {
            return logo3;
        }

        return "%error%";
    }

}
