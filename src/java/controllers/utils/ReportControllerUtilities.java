package controllers.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

/**
 *
 * @author John Philip
 */
public class ReportControllerUtilities {

    public static final String TEMP_FOLDER = "reports_temp";

    public static PdfFile exportPdfToTempFolder(String name, JasperPrint fillReport) throws JRException {
        String tempF = getTempFolderRealPath();
        System.out.println("info: tempF=" + tempF);
        String fileName = "/" + name + ".pdf";
        String newFileName = tempF + fileName;

        JasperExportManager.exportReportToPdfFile(fillReport, newFileName);

        String canonicalFilePath = "";
        File f = new File(newFileName);
        if (f.exists()) {
            try {
                canonicalFilePath = f.getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(ReportControllerUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }

        String webUrl = "/media.xhtml?fl=" + name + "&faces-redirect=true";
        System.out.println("webUrl=" + webUrl);
        PdfFile pdfFile = new PdfFile(webUrl, canonicalFilePath);
        return pdfFile;
    }

    public static String getTempFolderRealPath() {
        String tempF
                = JsfUtil.getContextRealPath()
                + File.separator
                + TEMP_FOLDER;
        File tempFolder = new File(tempF);
        boolean exists = tempFolder.exists();
        if (!exists) {
            tempFolder.mkdirs();
        }
        return tempF;
    }

    public static JasperPrint createJasperPrint(String reportFileName, JRDataSource dataSource) throws JRException {

        JRSwapFileVirtualizer virtualizer
                = new JRSwapFileVirtualizer(3,
                        new JRSwapFile(getTempFolderRealPath(), 2048, 1024),
                        false);

        Map param = new HashMap();
        param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        InputStream stream
                = ReportControllerUtilities.class
                .getResourceAsStream(reportFileName);

        JasperPrint print
                = JasperFillManager.fillReport(stream, param, dataSource);

        return print;
    }

    /**
     * Combines p1 and p2. Pages on p2 will be added to p1. Note that p1 will be
     * altered. This will return p1.
     *
     * @param p1
     * @param p2
     * @return p1 - the combined pages
     */
    public static JasperPrint combineJasperPrints(JasperPrint p1, JasperPrint p2) {

        List<JRPrintPage> pages = p2.getPages();
        for (JRPrintPage jRPrintPage : pages) {
            p1.addPage(jRPrintPage);
        }
        return p1;
    }

    public static String createPDFLink(JasperPrint mainReport, String pdfFileName) {
        
        if (mainReport == null) {
            return null;
        }

        PdfFile pdf = new PdfFile("", "");

        try {
            pdf = exportPdfToTempFolder(pdfFileName, mainReport);
        } catch (Exception ex) {
            Logger.getLogger(ReportControllerUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        String str = pdf.getWebUrl();

        return str;
    }
}
