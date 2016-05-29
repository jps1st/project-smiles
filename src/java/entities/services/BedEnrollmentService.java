package entities.services;

import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import entities.BedEnrollmentCharacterTraits;
import entities.BedEnrollmentNarrativeReport;
import entities.BedEnrollmentdetails;
import entities.BedSettings;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedEnrollmentService extends NewAbstractEntityService<BedEnrollment> {

    private static final long serialVersionUID = 1L;

    private BedEnrollmentService() {
    }
    
    private static BedEnrollmentService instance;

    public static BedEnrollmentService getInstance() {
        if (instance == null) {
            instance = new BedEnrollmentService();
        }
        return instance;
    }

    public BedEnrollment getEnrollmentRecord(String studentId, int sy) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> rs = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.sy = ?1 AND c.student.stdntid = ?2", BedEnrollment.class).setParameter(1, sy).setParameter(2, studentId).getResultList();
        m.close();
        if (rs.isEmpty()) {
            return null;
        }
        return rs.get(0);
    }

    /**
     * This method follows the rule/assumption that there should only be one(1)
     * BedEnrollmentdetails instance for every combination of BedEnrollment,
     * BedSubject (to span grade retrieved across curriculums) and school year.
     *
     * @param bedEnrollmentId
     * @param subjectId The BedSubject id.
     * @param sy
     * @return BedEnrollmentdetails
     */
    public BedEnrollmentdetails getEnrollmentDetails(int bedEnrollmentId, int subjectId, int sy) {
        try {
            EntityManager m = getNewEntityManager();
            BedEnrollmentdetails studentDetail = m.createQuery("SELECT c FROM BedEnrollmentdetails c "
                    + "WHERE c.bedEnrollment.section.sy = ?1 "
                    + "AND c.bedEnrollment.id = ?2 "
                    + "AND c.curriDetail.subjcode.id = ?3", BedEnrollmentdetails.class)
                    .setParameter(1, sy)
                    .setParameter(2, bedEnrollmentId)
                    .setParameter(3, subjectId)
                    .getSingleResult();
            m.close();
            return studentDetail;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    public List<BedEnrollmentdetails> getDetailsWithValues(int enrollmentId) {
        List<BedEnrollmentdetails> details = getDetails(enrollmentId);
        List<BedEnrollmentdetails> toRemove = new ArrayList();
        for (BedEnrollmentdetails d : details) {
            if (!detailHasValues(d)) {
                toRemove.add(d);
            }
        }
        details.removeAll(toRemove);
        return details;
    }

    public List<BedEnrollmentdetails> getDetails(int enrollmentId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollmentdetails> details
                = m.createQuery("SELECT c FROM BedEnrollmentdetails c WHERE c.bedEnrollment.id=?1 ORDER BY c.curriDetail.displayOrder" , BedEnrollmentdetails.class).setParameter(1, enrollmentId).getResultList();
        m.close();
        return details;
    }

    public List<BedEnrollmentCharacterTraits> getCharacterRecords(int enrollmentId) {
        EntityManager m = getNewEntityManager();
        String traitGroup = m.createQuery("SELECT c.section.charTraitGroup FROM BedEnrollment c WHERE c.id = ?1", String.class).setParameter(1, enrollmentId).getSingleResult();
        List<BedEnrollmentCharacterTraits> results
                = m.createQuery("SELECT c FROM BedEnrollmentCharacterTraits c WHERE c.bedEnrollment.id = ?1 AND c.bedCharacterTrait.charGroup LIKE ?2", BedEnrollmentCharacterTraits.class)
                .setParameter(1, enrollmentId)
                .setParameter(2, new StringBuilder("%").append(traitGroup).append("%").toString()).getResultList();
        m.close();
        return results;
    }

    public BedEnrollmentNarrativeReport getNarrativeRecord(int enrollmentId) {
        EntityManager m = getNewEntityManager();
        BedEnrollment e = m.find(BedEnrollment.class, enrollmentId);

        m.refresh(e);
        BedEnrollmentNarrativeReport record = e.getBedEnrollmentNarrativeReport();
        if (record == null) {
            m.getTransaction().begin();

            record = new BedEnrollmentNarrativeReport();
            record.setId(e.getId());
            record.setFirstGrading("");
            record.setFourthGrading("");
            record.setSecondGrading("");
            record.setThirdGrading("");
            record.setBedEnrollment(e);

            record = m.merge(record);
            m.getTransaction().commit();
            m.close();

        }
        return record;
    }

    public BedEnrollmentAttendanceRecord getAttendanceRecord(int enrollmentId) {
        EntityManager m = getNewEntityManager();
        BedEnrollment e = m.find(BedEnrollment.class, enrollmentId);
        m.refresh(e);

        BedEnrollmentAttendanceRecord record = e.getBedEnrollmentAttendanceRecord();
        if (record == null) {
            m.getTransaction().begin();
            record = new BedEnrollmentAttendanceRecord();
            record.setId(e.getId());
            record.setBedEnrollment(e);
            record = m.merge(record);
            m.getTransaction().commit();
            m.close();
        }
        return record;
    }

    public double getTotalAttendance(int enrollmentId) {
        BedEnrollmentAttendanceRecord attendance = getAttendanceRecord(enrollmentId);
        double june = attendance.getJune();
        double july = attendance.getJuly();
        double august = attendance.getAugust();
        double september = attendance.getSeptember();
        double october = attendance.getOctober();
        double november = attendance.getNovember();
        double december = attendance.getDecember();
        double january = attendance.getJanuary();
        double february = attendance.getFebruary();
        double march = attendance.getMarch();
        return june + july + august + september + october + november + december + january + february + march;
    }

    public BedSettings getEnrollmentBedSettings(int enrollmentId) {
        EntityManager m = getNewEntityManager();
        Object sy = m.createQuery("SELECT c.section.sy FROM BedEnrollment c WHERE c.id = ?1").setParameter(1, enrollmentId).getSingleResult();
        int settingsId = Integer.parseInt(sy.toString());
        BedSettings settings = m.find(BedSettings.class, settingsId);
        m.close();
        return settings;
    }

    private static boolean detailHasValues(BedEnrollmentdetails detail) {
        return (detail.getP1() != null && detail.getP1() > 0) || (detail.getP2() != null && detail.getP2() > 0) || (detail.getP3() != null && detail.getP3() > 0) || (detail.getP4() != null && detail.getP4() > 0);
    }

    @Override
    public Class getEntityClass() {
        return BedEnrollment.class;
    }

}
