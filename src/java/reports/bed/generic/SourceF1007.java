package reports.bed.generic;

import entities.BedEnrollment;
import entities.BedSections;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class SourceF1007 implements JRDataSource {

    private final String sectionName;
    private BedEnrollment current;
    private Iterator<BedEnrollment> iterator;
    private String sy;
    private String gender;

    public SourceF1007(BedSections section, List<BedEnrollment> enrolled, String gender) {
        this.gender = gender;
        this.sectionName = section.getName() + " Grade: " + section.getYear();
        this.sy = section.getSy() + "-" + (section.getSy()+1);
        this.iterator = enrolled.iterator();
    }

    private int count;

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
    DecimalFormat f = new DecimalFormat("##.###");

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();
        if (n.equals("no")) {
            return count + "";
        }

        if (n.equals("name")) {
            return current.getStudent().getFullName();
        }

        if (n.equals("section")) {
            return sectionName;
        }

        if (n.equals("lrn")) {
            return current.getStudent().getLearnerIdNo();
        }

        if (n.equals("sy")) {
            return sy;
        }
        
        if(n.equals("gender")){
            return gender;
        }

        return "%error%";
    }

}
