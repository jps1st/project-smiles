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
public class SourceF1003Tbl2 implements JRDataSource {

    private final int MAX_ROWS = 4;
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");
    private final BedSettings settings;
    private final BedEnrollmentAttendanceRecord attendanceRecord;

    private int count = 0;
    private final int monthStart;
    private final int monthEnd;

//                             <f:selectItem itemLabel="June" itemValue="1" />
//                            <f:selectItem itemLabel="July" itemValue="2" />
//                            <f:selectItem itemLabel="Aug" itemValue="3" />
//                            <f:selectItem itemLabel="Sept" itemValue="4" />
//                            <f:selectItem itemLabel="Oct" itemValue="5" />
//                            <f:selectItem itemLabel="Nov" itemValue="6" />
//                            <f:selectItem itemLabel="Dec" itemValue="7" />
//                            <f:selectItem itemLabel="Jan" itemValue="8" />
//                            <f:selectItem itemLabel="Feb" itemValue="9" />
//                            <f:selectItem itemLabel="March" itemValue="10" />
    public SourceF1003Tbl2(BedEnrollmentAttendanceRecord attendanceRecord, BedSettings settings, int monthStart, int monthEnd) {
        this.attendanceRecord = attendanceRecord;
        this.settings = settings;
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
        String name = jrf.getName();

        //<editor-fold defaultstate="collapsed" desc="Class days for each month">
        if (count == 1) {
            if (name.equals("description")) {
                return "Bilang ng Araw na May Pasok";
            }
            if (monthStart > 1) {
                return "";
            }

            if (name.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                return "" + format(settings.getJuneDays());
            } else if (name.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                return "" + format(settings.getJulyDays());
            } else if (name.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                return "" + format(settings.getAugDays());
            } else if (name.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                return "" + format(settings.getSeptDays());
            } else if (name.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                return "" + format(settings.getOctDays());
            } else if (name.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                return "" + format(settings.getNovDays());
            } else if (name.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                return "" + format(settings.getDecDays());
            } else if (name.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                return "" + format(settings.getJanDays());
            } else if (name.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                return "" + format(settings.getFebDays());
            } else if (name.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(settings.getMarchDays());
            } else if (name.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {

                double total = settings.getJuneDays()
                        + settings.getJulyDays()
                        + settings.getAugDays()
                        + settings.getSeptDays()
                        + settings.getOctDays()
                        + settings.getNovDays()
                        + settings.getDecDays()
                        + settings.getJanDays()
                        + settings.getFebDays()
                        + settings.getMarchDays();
                return format(total);
            }
        }
//</editor-fold>

        if (count == 2) {

            if (name.equals("description")) {
                return "Bilang ng Araw na Pumasok";
            } else if (name.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                Double val = settings.getJuneDays() - attendanceRecord.getJune();
                return format(val);
            } else if (name.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                Double val = settings.getJulyDays() - attendanceRecord.getJuly();
                return format(val);
            } else if (name.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                Double val = settings.getAugDays() - attendanceRecord.getAugust();
                return format(val);
            } else if (name.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                Double val = settings.getSeptDays() - attendanceRecord.getSeptember();
                return format(val);
            } else if (name.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                Double val = settings.getOctDays() - attendanceRecord.getOctober();
                return format(val);
            } else if (name.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                Double val = settings.getNovDays() - attendanceRecord.getNovember();
                return format(val);
            } else if (name.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                Double val = settings.getDecDays() - attendanceRecord.getDecember();
                return format(val);
            } else if (name.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                Double val = settings.getJanDays() - attendanceRecord.getJanuary();
                return format(val);
            } else if (name.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                Double val = settings.getFebDays() - attendanceRecord.getFebruary();
                return format(val);
            } else if (name.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                Double val = settings.getMarchDays() - attendanceRecord.getMarch();
                return format(val);
            } else if (name.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                Double val = 0d;
                    val += settings.getJuneDays() - attendanceRecord.getJune();
                    val += settings.getJulyDays() - attendanceRecord.getJuly();
                    val += settings.getAugDays() - attendanceRecord.getAugust();
                    val += settings.getSeptDays() - attendanceRecord.getSeptember();
                    val += settings.getOctDays() - attendanceRecord.getOctober();
                    val += settings.getNovDays() - attendanceRecord.getNovember();
                    val += settings.getDecDays() - attendanceRecord.getDecember();
                    val += settings.getJanDays() - attendanceRecord.getJanuary();
                    val += settings.getFebDays() - attendanceRecord.getFebruary();
                    val += settings.getMarchDays() - attendanceRecord.getMarch();
                return format(val);
            }
        }

        if (count == 3) {

            if (name.equals("description")) {
                return "Bilang ng Araw na Hindi Pumasok";
            } else if (name.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                return "" + format(attendanceRecord.getJune());
            } else if (name.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                return "" + format(attendanceRecord.getJuly());
            } else if (name.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                return "" + format(attendanceRecord.getAugust());
            } else if (name.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                return "" + format(attendanceRecord.getSeptember());
            } else if (name.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                return "" + format(attendanceRecord.getOctober());
            } else if (name.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                return "" + format(attendanceRecord.getNovember());
            } else if (name.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                return "" + format(attendanceRecord.getDecember());
            } else if (name.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                return "" + format(attendanceRecord.getJanuary());
            } else if (name.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                return "" + format(attendanceRecord.getFebruary());
            } else if (name.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return "" + format(attendanceRecord.getMarch());
            } else if (name.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                double total = attendanceRecord.getJune() + attendanceRecord.getJuly() + attendanceRecord.getAugust()
                        + attendanceRecord.getSeptember() + attendanceRecord.getOctober() + attendanceRecord.getNovember()
                        + attendanceRecord.getDecember() + attendanceRecord.getJanuary() + attendanceRecord.getFebruary()
                        + attendanceRecord.getMarch();

                return format(total);
            }
        }

        if (count == 4) {

            if (name.equals("description")) {
                return "Bilang ng Araw na Pumasok ng Huli";
            } else if (name.equals("june") && (monthStart == 0 || (monthStart <= 1 && monthEnd >= 1))) {
                return format(attendanceRecord.getTardyJune());
            } else if (name.equals("july") && (monthStart == 0 || (monthStart <= 2 && monthEnd >= 2))) {
                return format(attendanceRecord.getTardyJuly());
            } else if (name.equals("august") && (monthStart == 0 || (monthStart <= 3 && monthEnd >= 3))) {
                return format(attendanceRecord.getTardyAug());
            } else if (name.equals("september") && (monthStart == 0 || (monthStart <= 4 && monthEnd >= 4))) {
                return format(attendanceRecord.getTardySept());
            } else if (name.equals("october") && (monthStart == 0 || (monthStart <= 5 && monthEnd >= 5))) {
                return format(attendanceRecord.getTardyOct());
            } else if (name.equals("november") && (monthStart == 0 || (monthStart <= 6 && monthEnd >= 6))) {
                return format(attendanceRecord.getTardyNov());
            } else if (name.equals("december") && (monthStart == 0 || (monthStart <= 7 && monthEnd >= 7))) {
                return format(attendanceRecord.getTardyDec());
            } else if (name.equals("january") && (monthStart == 0 || (monthStart <= 8 && monthEnd >= 8))) {
                return format(attendanceRecord.getTardyJan());
            } else if (name.equals("february") && (monthStart == 0 || (monthStart <= 9 && monthEnd >= 9))) {
                return format(attendanceRecord.getTardyFeb());
            } else if (name.equals("march") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {
                return format(attendanceRecord.getTardyMarch());
            } else if (name.equals("total") && (monthStart == 0 || (monthStart <= 10 && monthEnd >= 10))) {

//                
                double total = (attendanceRecord.getTardyJune()
                        + attendanceRecord.getTardyJuly()
                        + attendanceRecord.getTardyAug()
                        + attendanceRecord.getTardySept()
                        + attendanceRecord.getTardyOct()
                        + attendanceRecord.getTardyNov()
                        + attendanceRecord.getTardyDec()
                        + attendanceRecord.getTardyJan()
                        + attendanceRecord.getTardyFeb()
                        + attendanceRecord.getTardyMarch());
                return format(total);
            }
        }

        return "";
    }
}
