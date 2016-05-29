/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedEnrollmentCharacterTraits;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip
 */
public class SourceF1003Tbl3 implements JRDataSource {

    private final Iterator<BedEnrollmentCharacterTraits> iterator;
    BedEnrollmentCharacterTraits current;
    private final int gradingPeriod;

    public SourceF1003Tbl3(List<BedEnrollmentCharacterTraits> enrollTraits, int gradingPeriod) {

        iterator = enrollTraits.iterator();
        this.gradingPeriod = gradingPeriod;
    }

    @Override
    public boolean next() throws JRException {
        boolean n = iterator.hasNext();
        if (n) {
            current = iterator.next();
        }
        return n;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String name = jrf.getName();
        if (name.equals("character_name")) {
            return current.getBedCharacterTrait().getTraitName();
        } else if (name.equals("p1")) {
            return current.getP1Value();
        } else if (name.equals("p2")) {
            return current.getP2Value();
        } else if (name.equals("p3")) {
            return current.getP3Value();
        } else if (name.equals("p4")) {
            return current.getP4Value();
        } else if (name.equals("p1_ast") && (gradingPeriod == 0 || gradingPeriod == 1) ) {
            return getAst(current.getP1Value() );
        } else if (name.equals("p2_ast") && (gradingPeriod == 0 || gradingPeriod == 2)) {
            return getAst(current.getP2Value());
        } else if (name.equals("p3_ast") && (gradingPeriod == 0 || gradingPeriod == 3)) {
            return getAst(current.getP3Value());
        } else if (name.equals("p4_ast") && (gradingPeriod == 0 || gradingPeriod == 4)) {
            return getAst(current.getP4Value());
        }
        return "";
    }

    private String getAst(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        value = value.trim();
        if (value.contains("*")) {
            return value;
        }
        try {
            double d = Double.parseDouble(value);
            if (d >= 75 && d < 83) {
                return "*";
            } else if (d >= 83 && d < 92) {
                return "**";
            } else if (d >= 92 && d <= 100) {
                return "***";
            }
        } catch (NumberFormatException numberFormatException) {
            return value;
        }

        return "";

    }
}
