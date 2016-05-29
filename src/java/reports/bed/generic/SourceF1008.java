/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedSections;
import entities.BedSettings;
import entities.services.BedSectionService;
import entities.services.BedSettingsService;
import java.io.InputStream;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1008 implements JRDataSource {

    private final BedSections section;
    private final BedSettings abs;

    private boolean hasNext = true;

    long promotedM;
    long promotedF;

    long irregM;
    long irregF;

    long retainedM;
    long retainedF;

    long beginM;
    long beginF;

    long devM;
    long devF;

    long appEM;
    long appEF;

    long profM;
    long profF;

    long advM;
    long advF;

    private final String schoolYear;
    private JRRenderable logo = null;

    public SourceF1008(BedSections section) {
        this.section = section;

        int sy = this.section.getSy();
        this.schoolYear = sy + "-" + (sy + 1);

        InputStream logo3Stream = this.getClass().getResourceAsStream("/reports/bed/generic/logos/kagawaran_ng_edukasyon.png");

        try {
            this.logo = JRImageRenderer.getInstance(logo3Stream, OnErrorTypeEnum.ERROR);
        } catch (JRException jRException) {
        }

        abs = BedSettingsService.getInstance().getActiveBedSettings();

    }

    @Override
    public boolean next() throws JRException {
        if (hasNext) {
            hasNext = false;
            return true;
        }
        return false;
    }

    /**
     * TODO: Allow support for incomplete subjects listing. TODO: region, school
     * year settings
     *
     * @param jrf
     * @return
     * @throws JRException
     */
    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        switch (jrf.getName()) {
            case "logo":
                return logo;
            case "region":
                //TODO: region
                return "n/a";
            case "schoolId":
                return abs.getSchoolId() + "";
            case "schoolName":
                return abs.getSchoolName();
            case "division":
                return abs.getDivision();
            case "schoolYear":
                return schoolYear; //TODO: school yar
            case "district":
                return abs.getDistrict();
            case "curriculum":
                return section.getCurriculum().getStringRepresentation();
            case "gradeLvl":
                return section.getYear() + "";
            case "section":
                return section.getName();

            case "promotedM":
                promotedM = BedSectionService.getInstance().countPromoted(section.getId(), "M");
                return promotedM + "";
            case "promotedF":
                promotedF = BedSectionService.getInstance().countPromoted(section.getId(), "F");
                return promotedF + "";
            case "promotedT":
                return (promotedF + promotedM) + "";
            case "irregM":
                irregM = BedSectionService.getInstance().countIrregulars(section.getId(), "M");
                return irregM + "";
            case "irregF":
                irregF = BedSectionService.getInstance().countIrregulars(section.getId(), "F");
                return irregF + "";
            case "irregT":
                return (irregM + irregF) + "";
            case "retainedM":
                retainedM = BedSectionService.getInstance().countRetained(section.getId(), "M");
                return retainedM + "";
            case "retainedF":
                retainedF = BedSectionService.getInstance().countRetained(section.getId(), "F");
                return retainedF + "";
            case "retainedT":
                return (retainedM + retainedF) + "";

            case "beginningM":
                beginM = BedSectionService.getInstance().countBeginning(section.getId(), "M");
                return beginM + "";
            case "beginningF":
                beginF = BedSectionService.getInstance().countBeginning(section.getId(), "F");
                return beginF + "";
            case "beginningT":
                return (beginM + beginF) + "";

            case "developingM":
                devM = BedSectionService.getInstance().countDeveloping(section.getId(), "M");
                return "" + devM;
            case "developingF":
                devF = BedSectionService.getInstance().countDeveloping(section.getId(), "F");
                return "" + devF;
            case "developingT":
                return "" + (devM + devF);

            case "appEffM":
                appEM = BedSectionService.getInstance().countAproachingEff(section.getId(), "M");
                return "" + appEM;
            case "appEffF":
                appEF = BedSectionService.getInstance().countAproachingEff(section.getId(), "F");
                return "" + appEF;
            case "appEffT":
                return "" + (appEM + appEF);

            case "profM":
                profM = BedSectionService.getInstance().countProf(section.getId(), "M");
                return "" + profM;
            case "profF":
                profF = BedSectionService.getInstance().countProf(section.getId(), "F");
                return "" + profF;
            case "profT":
                return "" + (profM + profF);

            case "advancedM":
                advM = BedSectionService.getInstance().countAdvanced(section.getId(), "M");
                return "" + advM;
            case "advancedF":
                advF = BedSectionService.getInstance().countAdvanced(section.getId(), "F");
                return "" + advF;
            case "advancedT":
                return "" + (advM + advF);

            case "adviser":
                return section.getAdviser().getFullName();
            case "schoolHead":
                return abs.getSchoolPrincipalName();
            case "supervisor":
                return abs.getSchoolsDivisionSuperintendent();
            case "dset1Source":
                return new SourceF1008_1(section);
        }
        return "n/a";
    }

}
