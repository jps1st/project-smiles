/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utils;

import entities.BedSystemUser;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author user
 */
public class Utils {

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String getFullFilePath(String tempFile) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.getRequestContextPath() + tempFile;
    }

    public static String getMD5EncryptedPassword(String pass) {
        String ret = null;
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(pass.getBytes(), 0, pass.length());
            ret = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
        }
        return ret;
    }

    public static void recordLogActivity(EntityManagerFactory factory, String module, BedSystemUser systemUser, String entity, String action, String actionDescription) {
        //TODO: implement
    }

    public static String getClientIP() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static double getAge(Date bday, Date now) {
        if (bday == null) {
            return 0d;
        }
        LocalDate systrt = new LocalDate(now);
        LocalDate bdate = new LocalDate(bday);
        Period p = new Period(bdate, systrt, PeriodType.yearMonthDay());
        double age = p.getYears() + (p.getMonths() / 12d);
        DecimalFormat f = new DecimalFormat("##.##");
        return Double.parseDouble(f.format(age));
    }

}
