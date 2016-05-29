package entities.services;

import entities.BedSettings;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedSettingsService extends NewAbstractEntityService<BedSettings> {

    private static final long serialVersionUID = 1L;

    private BedSettingsService() {
    }

    public String getCompleteSY() {
        int schoolYear = getActiveSchoolYear();
        return schoolYear + "-" + (schoolYear + 1);
    }

    public int getActiveSchoolYear() {
        return getActiveBedSettings().getId();
    }

    public BedSettings getActiveBedSettings() {
        EntityManager m = getNewEntityManager();
        List<BedSettings> rs = m.createQuery("SELECT c FROM BedSettings c WHERE c.activeYear = 1", BedSettings.class).getResultList();
        m.close();

        if (!rs.isEmpty()) {
            return rs.get(0);
        }

        int schoolYear = Calendar.getInstance().get(Calendar.YEAR);
        BedSettings s = new BedSettings();
        s.setId(schoolYear);
        s.setSchoolPrincipalName("n/a");
        s.setSchoolsDivisionSuperintendent("n/a");
        s.setDivisionRepresentative("n/a");
        s.setSchoolId("n/a");
        s.setSchoolName("n/a");
        s.setDivision("n/a");
        s.setDistrict("n/a");
        return BedSettingsService.getInstance().insert(s);

    }

    public void disableAllSettings() {
        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();
        List<BedSettings> rs = m.createQuery("SELECT c FROM BedSettings c WHERE c.activeYear = TRUE", BedSettings.class).getResultList();
        for (BedSettings s : rs) {
            s.setActiveYear(false);
        }
        m.getTransaction().commit();
        m.close();

    }

    @Override
    public Class getEntityClass() {
        return BedSettings.class;
    }

    private static BedSettingsService instance;

    public static BedSettingsService getInstance() {
        if (instance == null) {
            instance = new BedSettingsService();
        }
        return instance;
    }

}
