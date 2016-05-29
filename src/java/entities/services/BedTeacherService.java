/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedLoading;
import entities.BedTeacher;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedTeacherService extends NewAbstractEntityService<BedTeacher> {

    private static final long serialVersionUID = 1L;
    
    private BedTeacherService() {
    }

    private static BedTeacherService instance;

    public static BedTeacherService getInstance() {
        if (instance == null) {
            instance = new BedTeacherService();
        }
        return instance;
    }

    public static final int TEACHER_DEFAULT_PASS_LENGTH = 5;

    //Returns only active teachers
    public List<BedTeacher> completeTeacher(String key) {
        if (key == null) {
            key = "";
        }
        EntityManager m = getNewEntityManager();
        List<BedTeacher> rs = m.createQuery("SELECT c FROM BedTeacher c "
                + "WHERE c.active = 1 "
                + "AND (c.firstName LIKE ?1 OR c.lastName LIKE ?1 OR c.middleName LIKE ?1 ) "
                + "ORDER BY c.lastName", BedTeacher.class)
                .setParameter(1, "%" + key + "%").getResultList();
        m.close();
        return rs;
    }

    public int assignTeacherAccsDefaultPass() {

        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();
        List<BedTeacher> rs = m.createQuery("SELECT c FROM BedTeacher c WHERE c.active = TRUE AND c.password IS NULL", BedTeacher.class).getResultList();
        for (BedTeacher t : rs) {
            t.setPassword(createRandomTeacherPassword());
        }
        m.getTransaction().commit();
        m.close();
        return rs.size();
    }

    public List<BedTeacher> getActiveTeachers() {
        EntityManager m = getNewEntityManager();
        List<BedTeacher> activeTeachers = m.createQuery("SELECT c FROM BedTeacher c WHERE c.active = TRUE ORDER BY c.lastName", BedTeacher.class).getResultList();
        m.close();
        return activeTeachers;
    }

    public static String createRandomTeacherPassword() {
        return UUID.randomUUID().toString().substring(0, TEACHER_DEFAULT_PASS_LENGTH);
    }

    @Override
    public Class getEntityClass() {
        return BedTeacher.class;
    }

    public BedTeacher getTeacherWithPass(String username, String password) {
        EntityManager em = getNewEntityManager();
        List<BedTeacher> users = em.createQuery("SELECT s FROM BedTeacher s WHERE s.active = true "
                + "AND s.teacherId LIKE ?1 AND s.password LIKE ?2", BedTeacher.class)
                .setParameter(1, username)
                .setParameter(2, password).getResultList();
        em.close();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public BedTeacher findByTeacherId(String username) {
        EntityManager m = getNewEntityManager();
        List<BedTeacher> rs
                = m.createQuery("SELECT c FROM BedTeacher c "
                        + "WHERE c.teacherId LIKE ?1", BedTeacher.class)
                .setParameter(1, username)
                .getResultList();
        m.close();
        if (!rs.isEmpty()) {
            return rs.get(0);
        }
        return null;
    }

    public List<BedLoading> getLoadRecords(int activeSchoolYear, String teacherId) {
        EntityManager m = getNewEntityManager();
        List<BedLoading> rs = m.createQuery("SELECT c FROM BedLoading c WHERE c.section.sy = ?1 "
                + "AND c.teacher.teacherId = ?2 ORDER BY c.section.year, c.section.name", BedLoading.class)
                .setParameter(1, activeSchoolYear)
                .setParameter(2, teacherId).getResultList();
        m.close();
        return rs;
    }

    public List<BedTeacher> search(String key) {
        if (key == null) {
            key = "";
        }
        EntityManager m = getNewEntityManager();
        List<BedTeacher> rs = m.createQuery("SELECT c FROM BedTeacher c "
                + "WHERE c.lastName LIKE ?1 "
                + "OR c.firstName LIKE ?1 "
                + "OR c.teacherId LIKE ?1 "
                + "ORDER BY c.lastName", BedTeacher.class)
                .setParameter(1, "%" + key + "%")
                .setMaxResults(50)
                .getResultList();
        m.close();
        return rs;
    }

}
