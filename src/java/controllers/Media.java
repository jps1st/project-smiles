/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.utils.JsfUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author admin
 */
@ManagedBean
@ViewScoped
public class Media implements Serializable {

    private String pdfFile;

    @PostConstruct
    public void init() {
        String file
                = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("fl");

        this.pdfFile = JsfUtil.getContextPath() + "/reports_temp/" + file + ".pdf";

    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

}
