package controllers.sectionMain;

import controllers.utils.JsfUtil;
import entities.BedCurriculum;
import entities.BedCurriculumDetail;
import entities.BedSections;
import entities.BedTeacher;
import entities.services.BedCurriculumDetailService;
import entities.services.BedCurriculumService;
import entities.services.BedSectionHiddenSubjectService;
import entities.services.BedSectionService;
import entities.services.BedSettingsService;
import entities.services.BedTeacherService;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.primefaces.model.UploadedFile;
import servlets.ImageServlet;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@ManagedBean
@ViewScoped
public class BedSectionProfileMC implements Serializable {

    private Integer sectionId;
    private BedSections section;
    private boolean forYearLevelChange;
    private List<BedCurriculum> curriculumList;
    private List hiddenSubjects;

    private UploadedFile file;

    @PostConstruct
    public void init() {
        String val = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (val != null) {
            sectionId = Integer.valueOf(val);
        } else {
            sectionId = null;
        }

        try {
            if (sectionId == -1 || sectionId == null) {
                //new section
                section = new BedSections();
                section.setSy(BedSettingsService.getInstance().getActiveSchoolYear());
            } else {
                section = BedSectionService.getInstance().find(sectionId);
                hiddenSubjects = BedSectionHiddenSubjectService.getInstance().getHiddenSubjects(section);
            }
        } catch (Exception ex) {
            Logger.getLogger(BedSectionProfileMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveToFile(InputStream is, File dest) {
        try {
            OutputStream os = new FileOutputStream(dest, false);

            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(byte[] data, File dest) {
        try {
            OutputStream os = new FileOutputStream(dest, false);
            os.write(data);
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadNewAvatar() {
        if (file != null) {
            handleFileUpload(file);
            FacesMessage msg = new FacesMessage("Succesful", "File is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            file = null;
        }

    }

    public void handleFileUpload(UploadedFile f) {

        String contentType = f.getContentType();
        contentType = contentType.split("/")[1];

        String folder = ImageServlet.IMAGE_FOLDER;
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }

        String nameNoEx = folder + section.getAvatarFileName();
        String nameWiEx = nameNoEx + "." + contentType;

        try {
            File dest = new File(nameWiEx);
            if (!dest.exists()) {
                dest.createNewFile();
            }

            saveToFile(f.getInputstream(), dest);

            Image image = Toolkit.getDefaultToolkit().createImage(nameWiEx);

            Image main = image.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            Image sub = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

            ImageIcon maini = new ImageIcon(main);
            saveToFile(getImgBytes(maini.getImage()), new File(nameNoEx + ".jpeg"));

            ImageIcon subi = new ImageIcon(sub);
            saveToFile(getImgBytes(subi.getImage()), new File(nameNoEx + "_sub" + ".jpeg"));

        } catch (IOException iOException) {
            iOException.printStackTrace();
        }

    }

    public static byte[] getImgBytes(Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(getBufferedImage(image), "JPEG", baos);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static BufferedImage getBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.createGraphics().drawImage(image, null, null);
        return bi;
    }

    public void markForYearLevelChange() {
        forYearLevelChange = true;
    }

    public List<BedTeacher> completeTeacher(String key) {
        return BedTeacherService.getInstance().completeTeacher(key);
    }

    public List<BedCurriculumDetail> completeCurriDetails(String key) {
        return BedCurriculumDetailService.getInstance().search(section.getCurriculum(), section.getYear(), key);
    }

    public void saveChanges() {
        if (forYearLevelChange) {
            Integer currentYearLevel = BedSectionService.getInstance().getYearLevel(sectionId);
            if (!Objects.equals(currentYearLevel, section.getYear())) {
                BedSectionService.getInstance().removeLoadsAndGradeRecords(sectionId);
            }
        }
        section = BedSectionService.getInstance().update(section);

       // System.out.println("hidden=" + hiddenSubjects.size());

        for (int i = 0; i < hiddenSubjects.size(); i++) {
            Object subj = hiddenSubjects.get(i);
            BedSectionHiddenSubjectService.getInstance().addHiddenSubject(section, subj.toString());
        }

        JsfUtil.addInfoMessage("Saved", "Your changes were saved.");
    }

    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }

    public List<BedCurriculum> getCurriculumList() {
        if (curriculumList == null) {
            curriculumList = BedCurriculumService.getInstance().getAllCurriculumsByEffectiveSY();
        }
        return curriculumList;
    }

    public void setCurriculumList(List<BedCurriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<BedCurriculumDetail> getHiddenSubjects() {
        return hiddenSubjects;
    }

    public void setHiddenSubjects(List<BedCurriculumDetail> hiddenSubjects) {
        this.hiddenSubjects = hiddenSubjects;
    }

}
