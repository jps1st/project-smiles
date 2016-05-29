package entities.services;

import entities.BedEnrollment;
import entities.JdStudent;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedStudentService extends NewAbstractEntityService<JdStudent> {

    private static final long serialVersionUID = 1L;

    private BedStudentService() {
    }
    private static BedStudentService instance;

    public static BedStudentService getInstance() {
        if (instance == null) {
            instance = new BedStudentService();
        }
        return instance;
    }

    public List<JdStudent> completeStudent(String key) {
        EntityManager m = getNewEntityManager();
        List<JdStudent> rs;
        if (key.contains(",")) {
            rs = m.createQuery("SELECT c FROM JdStudent c WHERE CONCAT(c.lastname, ', ' , c.firstname ) LIKE ?1", JdStudent.class).setParameter(1, "%" + key + "%").getResultList();
        } else {
            rs = m.createQuery("SELECT c FROM JdStudent c WHERE c.lastname LIKE ?1 OR c.stdntid LIKE ?1 OR c.firstname LIKE ?1 OR c.middlename LIKE ?1 ORDER BY c.lastname, c.firstname", JdStudent.class).setMaxResults(20).setParameter(1, "%" + key + "%").getResultList();
        }
        m.close();
        return rs;
    }

    public BedEnrollment getEnrollmentRecord(String studentId, int sectionId) {
        try {
            EntityManager m = getNewEntityManager();
            BedEnrollment enrollment = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 AND c.student.stdntid = ?2", BedEnrollment.class).setParameter(1, sectionId).setParameter(2, studentId).getSingleResult();
            m.close();
            return enrollment;
        } catch (Exception e) {
        }
        return null;
    }

    public String getCurrentEnrolledSectionName(String studentId) {
        int activeSchoolYear = BedSettingsService.getInstance().getActiveSchoolYear();

        EntityManager m = getNewEntityManager();
        List rs = m.createQuery("SELECT CONCAT(, c.section.year, ' - ' ,c.section.name) FROM BedEnrollment c "
                + "WHERE c.student.stdntid = ?1 and c.section.sy =  ?2").setParameter(1, studentId).setParameter(2, activeSchoolYear).getResultList();
        m.close();
        if (rs.isEmpty()) {
            return "n/a";
        }
        return rs.get(0).toString();
    }

    public boolean existing(JdStudent std) {
        boolean b = false;
        try {
            JdStudent f = find(std.getStdntid());
            b = f != null;
        } catch (Exception e) {

        }
        return b;
    }

//<editor-fold defaultstate="collapsed" desc="getters/setters">
    @Override
    public Class getEntityClass() {
        return JdStudent.class;
    }

//</editor-fold>
    public List<JdStudent> search(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        EntityManager m = getNewEntityManager();
        List<JdStudent> rs
                = m.createQuery("SELECT s FROM JdStudent s "
                        + "WHERE s.lastname like ?1 "
                        + "OR s.firstname like ?1 "
                        + "OR s.learnerIdNo LIKE ?1 "
                        + "OR s.middlename LIKE ?1 "
                        + "ORDER BY s.lastname", JdStudent.class)
                .setParameter(1, "%" + keyword + "%")
                .setMaxResults(50)
                .getResultList();
        m.close();
        return rs;
    }

    public long countEnrollmentRecords(String stdntid) {
        EntityManager m = getNewEntityManager();
        long records
                = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                        + "WHERE c.student.stdntid LIKE ?1", Long.class)
                .setParameter(1, stdntid)
                .getSingleResult();
        m.close();
        return records;
    }

}
