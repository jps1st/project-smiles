/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utils;

/**
 *
 * @author John Philip
 */
public class PdfFile {
    
    private String webUrl;
    private String actualFile;

    public PdfFile(String webUrl, String actualFile) {
        this.webUrl = webUrl;
        this.actualFile = actualFile;
    }
    
    /**
     * @return the webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * @param webUrl the webUrl to set
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * @return the actualFile
     */
    public String getActualFile() {
        return actualFile;
    }

    /**
     * @param actualFile the actualFile to set
     */
    public void setActualFile(String actualFile) {
        this.actualFile = actualFile;
    }
    
}
