/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.parents;

import controllers.utils.JsfUtil;
import entities.BedParent;
import entities.services.BedParentService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@SessionScoped
public class ParentMainController implements Serializable {

    private List<BedParent> parentList;
    private BedParent currentParent;
    private String keyword;

    private String parent_remove_RemoveMessage;
    private boolean parent_remove_AllowRemove;

    public void randomizeCurrentParentID() {
        currentParent.setParentId(null); // getParentId does the job when parentid is null set getParentId on BedParent
    }

    public String removeParentNow() {
        if (currentParent != null) {
            BedParentService.getInstance().remove(currentParent);
            currentParent = null;
            JsfUtil.addInfoMessage("Saved", "Your changes are saved.");
        }
        return "parentMain.xhtml?faces-redirect=true";
    }

    public String saveChanges() {

        BedParent parent
                = BedParentService.getInstance()
                .findByParentId(currentParent.getId());

        if (parent != null) {

            boolean newItem = currentParent.getId() == null;
            boolean sameUser = parent.getId().equals(currentParent.getId());

            if (!sameUser | newItem) {
                JsfUtil.addWarningMessage("Parent ID taken", "Parent id "
                        + currentParent.getParentId()
                        + " is already taken. Please specify another.");
                return null;
            }

        }

        boolean exists
                = BedParentService.getInstance()
                .parentRecordAlreadyExists(currentParent);

        if (exists) {
            JsfUtil.addWarningMessage("Parent record exists.",
                    "This parent has an existing record. "
                    + "Match found according to first, middle and last name.");
            return null;
        }

        currentParent = BedParentService.getInstance().update(currentParent);

        JsfUtil.addInfoMessage("Saved", "Your changes are saved.");
        search();
        return "parentMain.xhtml?faces-redirect=true";
    }

    public String removeParent(BedParent parent) {
        this.currentParent = parent;

        boolean hasDependents
                = BedParentService.getInstance().hasDependents(currentParent.getId());

        if (hasDependents) {
            parent_remove_RemoveMessage
                    = "Removing/deleting parent with ID: "
                    + currentParent.getId() + " or "
                    + currentParent.getFullName();
            parent_remove_AllowRemove = true;
        } else {
            parent_remove_RemoveMessage
                    = "This parent (ID: " + currentParent.getId()
                    + " or " + currentParent.getFullName()
                    + ") record has dependent(s). Remove not allowed.";
            parent_remove_AllowRemove = false;
        }

        search();
        return "parentMain.xhtml?faces-redirect=true";
    }

    public String editParent(BedParent parent) throws Exception {
        if (parent == null) {
            JsfUtil.addWarningMessage("Edit failed.", "No parent was selected for edit. Creating a new record instead.");
            return createNewParent();
        }
        this.currentParent = parent;
        return "parentEdit.xhtml?faces-redirect=true";
    }

    public String createNewParent() {
        currentParent = new BedParent();
        currentParent.setLastName("");
        currentParent.setFirstName("");
        currentParent.setMiddleName("");
        currentParent.setContactNum("");
        return "parentEdit.xhtml?faces-redirect=true";
    }

    @PostConstruct
    public void search() {
        parentList =  BedParentService.getInstance().search(keyword);
    }

    /**
     * @return the parentList
     */
    public List<BedParent> getParentList() {
        return parentList;
    }

    /**
     * @param parentList the parentList to set
     */
    public void setParentList(List<BedParent> parentList) {
        this.parentList = parentList;
    }

    /**
     * @return the currentParent
     */
    public BedParent getCurrentParent() {
        return currentParent;
    }

    /**
     * @param currentParent the currentParent to set
     */
    public void setCurrentParent(BedParent currentParent) {
        this.currentParent = currentParent;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the parent_remove_RemoveMessage
     */
    public String getParent_remove_RemoveMessage() {
        return parent_remove_RemoveMessage;
    }

    /**
     * @param parent_remove_RemoveMessage the parent_remove_RemoveMessage to set
     */
    public void setParent_remove_RemoveMessage(String parent_remove_RemoveMessage) {
        this.parent_remove_RemoveMessage = parent_remove_RemoveMessage;
    }

    /**
     * @return the parent_remove_AllowRemove
     */
    public boolean isParent_remove_AllowRemove() {
        return parent_remove_AllowRemove;
    }

    /**
     * @param parent_remove_AllowRemove the parent_remove_AllowRemove to set
     */
    public void setParent_remove_AllowRemove(boolean parent_remove_AllowRemove) {
        this.parent_remove_AllowRemove = parent_remove_AllowRemove;
    }

}
