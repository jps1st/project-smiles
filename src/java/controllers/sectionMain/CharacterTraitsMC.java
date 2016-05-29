package controllers.sectionMain;

import controllers.utils.JsfUtil;
import entities.BedCharacterTraits;
import entities.BedEnrollmentCharacterTraits;
import entities.BedSections;
import entities.services.BedCharacterService;
import entities.services.BedEnrollmentCharacterServices;
import entities.services.BedSectionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;


/**
 * Main controller for bed_sections_character_traits.xhtml
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class CharacterTraitsMC implements Serializable {



//<editor-fold defaultstate="collapsed" desc="variables">
    private int viewOptions = 1;
    private final Integer sectionId;

    private BedSections section;
    private BedCharacterTraits currentCharacterTrait;
    private BedCharacterTraits editingTrait;

    private List<BedCharacterTraits> traitList;
    private List<BedEnrollmentCharacterTraits> studentCharacterRecordsM;
    private List<BedEnrollmentCharacterTraits> studentCharacterRecordsF;
//</editor-fold>

    public CharacterTraitsMC() {
        sectionId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));//TODO Change or verify
    }

    @PostConstruct
    public void init() {
        this.section = BedSectionService.getInstance().find(sectionId);
        updateTraitList();
    }

    public void characterTraitsPerformSave(BedEnrollmentCharacterTraits trait) {
        if (trait == null) {
            return;
        }
        if (currentCharacterTrait == null) {
            return;
        }

        BedEnrollmentCharacterServices.getInstance().update(trait);
        JsfUtil.addInfoMessage("Saved", "Your changes were saved.");
    }

    public void saveSectionTraitGroup() {
        currentCharacterTrait = null;
        BedSectionService.getInstance().update(section);
        updateTraitList();
        JsfUtil.addInfoMessage("Trait group saved.", "Section set with new character trait group.");
    }

    public void saveEditingTrait() {
        BedCharacterService.getInstance().update(editingTrait);
        JsfUtil.addInfoMessage("Saved", "Your changes were saved.");
    }

    public void updateCharacterRecordsList() {

        if (currentCharacterTrait == null) {
            studentCharacterRecordsF = new ArrayList<>();
            studentCharacterRecordsM = new ArrayList<>();
            return;
        }
        studentCharacterRecordsF = BedEnrollmentCharacterServices.getInstance().getCharacterTraits("F", sectionId, currentCharacterTrait.getCtId());
        studentCharacterRecordsM = BedEnrollmentCharacterServices.getInstance().getCharacterTraits("M", sectionId, currentCharacterTrait.getCtId());

    }

    public void updateTraitList() {
        traitList = BedEnrollmentCharacterServices.getInstance().getTraitList(section.getCharTraitGroup());
    }

//<editor-fold defaultstate="collapsed" desc="getters/setters">
    public List<BedCharacterTraits> getTraitList() {
        return traitList;
    }

    public void setTraitList(List<BedCharacterTraits> traitList) {
        this.traitList = traitList;
    }

    public List<BedEnrollmentCharacterTraits> getStudentCharacterRecordsM() {
        return studentCharacterRecordsM;
    }

    public void setStudentCharacterRecordsM(List<BedEnrollmentCharacterTraits> studentCharacterRecordsM) {
        this.studentCharacterRecordsM = studentCharacterRecordsM;
    }

    public List<BedEnrollmentCharacterTraits> getStudentCharacterRecordsF() {
        return studentCharacterRecordsF;
    }

    public void setStudentCharacterRecordsF(List<BedEnrollmentCharacterTraits> studentCharacterRecordsF) {
        this.studentCharacterRecordsF = studentCharacterRecordsF;
    }

    public BedCharacterTraits getCurrentCharacterTrait() {
        return currentCharacterTrait;
    }

    public void setCurrentCharacterTrait(BedCharacterTraits currentCharacterTrait) {
        this.currentCharacterTrait = currentCharacterTrait;
    }

    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }

    public BedCharacterTraits getEditingTrait() {
        return editingTrait;
    }

    public void setEditingTrait(BedCharacterTraits editingTrait) {
        this.editingTrait = editingTrait;
    }

    public int getViewOptions() {
        return viewOptions;
    }

    public void setViewOptions(int viewOptions) {
        this.viewOptions = viewOptions;
    }
//</editor-fold>
    
}
