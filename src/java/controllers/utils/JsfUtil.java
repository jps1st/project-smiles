package controllers.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;

public class JsfUtil {

    public static String getContextRealPath() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String p = servletContext.getRealPath("");
        return p;
    }
    
    public static String getContextPath(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String p = servletContext.getContextPath();
        return p;
    }

    public static void redirectToPage(String page) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
                    NavigationHandler nav = facesContext.getApplication().getNavigationHandler();
            nav.handleNavigation(facesContext, null, page);
   //     if (!facesContext.getResponseComplete()) {
          //  NavigationHandler nav = facesContext.getApplication().getNavigationHandler();
         //   nav.handleNavigation(facesContext, null, page);
    //    }
    }

    public static void execute(String s) {
        RequestContext.getCurrentInstance().execute(s);
    }

    public static void update(String s) {
        RequestContext.getCurrentInstance().update(s);
    }

    public static void addErrorMessage(String msg, String msgDescription) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msgDescription);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addWarningMessage(String msg, String msgDescription) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msgDescription);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addFatalMessage(String msg, String msgDescription) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msgDescription);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addInfoMessage(String msg, String msgDescription) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msgDescription);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }
}