/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCurriculum;
import entities.BedSubject;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedSubjectService extends NewAbstractEntityService<BedSubject> {

    private static final long serialVersionUID = 1L;
    
    private BedSubjectService() {
    }

    @Override
    public Class getEntityClass() {
        return BedSubject.class;
    }

    private static BedSubjectService instance;

    public static BedSubjectService getInstance() {
        if (instance == null) {
            instance = new BedSubjectService();
        }
        return instance;
    }

    public List<BedSubject> search(String key) {
        if (key == null) {
            key = "";
        }
        EntityManager m = getNewEntityManager();
        List<BedSubject> rs = m.createQuery("SELECT s FROM BedSubject s WHERE s.subjcode != '' AND (s.subjcode LIKE ?1 OR s.subjdesc LIKE ?1) ORDER BY s.subjcode", BedSubject.class)
                .setParameter(1, "%" + key + "%")
                .setMaxResults(50).getResultList();
        m.close();
        return rs;
    }

    public boolean subjectTaken(BedSubject currentSubject) {
        EntityManager m = getNewEntityManager();
        long rs
                = m.createQuery("SELECT COUNT(c) FROM BedSubject c "
                        + "WHERE c.subjcode = ?1 "
                        + "AND c != ?2", Long.class)
                .setParameter(1, currentSubject.getSubjcode())
                .setParameter(2, currentSubject)
                .getSingleResult();
        m.close();
        return rs > 0;
    }

    public String getUsageInCurri(BedSubject currentSubject) {
        if (currentSubject == null
                || currentSubject.getSubjcode() == null
                || currentSubject.getSubjcode().trim().isEmpty()) {
            return "NONE";
        }
        EntityManager m = getNewEntityManager();
        List<BedCurriculum> r
                = m.createQuery("SELECT DISTINCT(s.currcode) "
                        + "FROM BedCurriculumDetail s "
                        + "WHERE s.subjcode.subjcode = ?1", BedCurriculum.class)
                .setParameter(1, currentSubject.getSubjcode())
                .getResultList();
        m.close();
        if (r.isEmpty()) {
            return "NONE";
        }

        String s = "" + r.size() + " (";
        int count = 0;
        for (BedCurriculum object : r) {
            s += object.getStringRepresentation();
            if (count++ < r.size() - 1) {
                s += ", ";
            }
        }
        s += ")";

        return s;
    }

}
