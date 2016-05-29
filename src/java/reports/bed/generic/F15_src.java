/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedEnrollment;
import entities.BedSections;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class F15_src implements JRDataSource {

    F12_src f12;
    F13_src f13;

    public F15_src(BedSections section, List<BedEnrollment> enrollments, int gradingPeriod, int monthStart, int monthEnd) {
        f12 = new F12_src(enrollments, gradingPeriod, monthStart, monthEnd);
        f13 = new F13_src(section, enrollments, gradingPeriod);

    }

    @Override
    public boolean next() throws JRException {
        return f12.next() && f13.next();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {

        String n = jrf.getName();

        if (n.equals("logo1")) {
            return this.getClass().getResourceAsStream("/reports/bed/generic/logos/cjcn.png");// = this.getClass().getResourceAsStream("/reports/bed/generic/logos/cjc.png");
        }
        if (n.equals("logo2")) {
            return this.getClass().getResourceAsStream("/reports/bed/generic/logos/deped_k12.png");
        }

        Object o = f12.getFieldValue(jrf);
        if (o != null) {
            return o;
        }
        return f13.getFieldValue(jrf);
    }

}
