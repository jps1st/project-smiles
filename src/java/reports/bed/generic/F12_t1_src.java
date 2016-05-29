/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedEnrollmentAttendanceRecord;
import entities.BedSettings;
import java.text.DecimalFormat;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip
 */
public class F12_t1_src implements JRDataSource {

    private final int MAX_ROWS = 3;
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");
    private final BedSettings s;
    private final BedEnrollmentAttendanceRecord r;

    private int count = 0;
    private final int monthStart;
    private final int monthEnd;

    public F12_t1_src(BedEnrollmentAttendanceRecord attendanceRecord, BedSettings settings, int monthStart, int monthEnd) {
        this.r = attendanceRecord;
        this.s = settings;
        this.monthStart = monthStart;
        this.monthEnd = monthEnd;
    }

    public String format(double d) {
        if (d == 0) {
            return "0";
        }
        return DECIMAL_FORMAT.format(d);
    }

    @Override
    public boolean next() throws JRException {
        return count++ < MAX_ROWS;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();

        //<editor-fold defaultstate="collapsed" desc="No. of School Days">
        if (count == 1) {
            if (n.equals("description")) {
                return "School days";
            }

            if (monthStart > 1) {
                return "";
            }

            if (n.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                return "" + format(s.getJuneDays());
            } else if (n.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                return "" + format(s.getJulyDays());
            } else if (n.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                return "" + format(s.getAugDays());
            } else if (n.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                return "" + format(s.getSeptDays());
            } else if (n.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                return "" + format(s.getOctDays());
            } else if (n.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                return "" + format(s.getNovDays());
            } else if (n.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                return "" + format(s.getDecDays());
            } else if (n.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                return "" + format(s.getJanDays());
            } else if (n.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                return "" + format(s.getFebDays());
            } else if (n.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(s.getMarchDays());
            } else if (n.equals("april") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(s.getAprilDays());
            } else if (n.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {

                double total = s.getJuneDays()
                        + s.getJulyDays()
                        + s.getAugDays()
                        + s.getSeptDays()
                        + s.getOctDays()
                        + s.getNovDays()
                        + s.getDecDays()
                        + s.getJanDays()
                        + s.getFebDays()
                        + s.getMarchDays()
                        + s.getAprilDays();

                return format(total);
            }
        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="No. of Days Present">
        if (count == 2) {
            if (n.equals("description")) {
                return "Days present";
            } else if (n.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                Double val = s.getJuneDays() - r.getJune();
                return format(val);
            } else if (n.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                Double val = s.getJulyDays() - r.getJuly();
                return format(val);
            } else if (n.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                Double val = s.getAugDays() - r.getAugust();
                return format(val);
            } else if (n.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                Double val = s.getSeptDays() - r.getSeptember();
                return format(val);
            } else if (n.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                Double val = s.getOctDays() - r.getOctober();
                return format(val);
            } else if (n.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                Double val = s.getNovDays() - r.getNovember();
                return format(val);
            } else if (n.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                Double val = s.getDecDays() - r.getDecember();
                return format(val);
            } else if (n.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                Double val = s.getJanDays() - r.getJanuary();
                return format(val);
            } else if (n.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                Double val = s.getFebDays() - r.getFebruary();
                return format(val);
            } else if (n.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                Double val = s.getMarchDays() - r.getMarch();
                return format(val);
            } else if (n.equals("april") && (monthStart == 0 || (monthStart <= 11 && monthEnd >= 11))) {
                Double val = s.getAprilDays() - r.getApril();
                return format(val);
            } else if (n.equals("total") && (monthStart == 0 || (monthStart <= 11 && monthEnd >= 11))) {
                Double val = 0d;
                val += s.getJuneDays() - r.getJune();
                val += s.getJulyDays() - r.getJuly();
                val += s.getAugDays() - r.getAugust();
                val += s.getSeptDays() - r.getSeptember();
                val += s.getOctDays() - r.getOctober();
                val += s.getNovDays() - r.getNovember();
                val += s.getDecDays() - r.getDecember();
                val += s.getJanDays() - r.getJanuary();
                val += s.getFebDays() - r.getFebruary();
                val += s.getMarchDays() - r.getMarch();
                val += s.getAprilDays() - r.getApril();
                return format(val);
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="no of days absent">
        if (count == 3) {

            if (n.equals("description")) {
                return "Days absent";
            } else if (n.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                return "" + format(r.getJune());
            } else if (n.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                return "" + format(r.getJuly());
            } else if (n.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                return "" + format(r.getAugust());
            } else if (n.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                return "" + format(r.getSeptember());
            } else if (n.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                return "" + format(r.getOctober());
            } else if (n.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                return "" + format(r.getNovember());
            } else if (n.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                return "" + format(r.getDecember());
            } else if (n.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                return "" + format(r.getJanuary());
            } else if (n.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                return "" + format(r.getFebruary());
            } else if (n.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(r.getMarch());
            } else if (n.equals("april") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(r.getApril());
            } else if (n.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                double total = r.getJune() + r.getJuly() + r.getAugust()
                        + r.getSeptember() + r.getOctober() + r.getNovember()
                        + r.getDecember() + r.getJanuary() + r.getFebruary()
                        + r.getMarch() + r.getApril();

                return format(total);
            }
        }
//</editor-fold>
        return "";
    }
}
