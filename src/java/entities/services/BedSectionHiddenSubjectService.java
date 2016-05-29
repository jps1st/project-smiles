package entities.services;

import entities.BedCurriculumDetail;
import entities.BedSectionHiddenSubject;
import entities.BedSections;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedSectionHiddenSubjectService extends NewAbstractEntityService<BedSectionHiddenSubject> {

    private static final long serialVersionUID = 1L;

    private BedSectionHiddenSubjectService() {
    }

    @Override
    public Class getEntityClass() {
        return BedSectionHiddenSubject.class;
    }

    private static BedSectionHiddenSubjectService instance;

    public static BedSectionHiddenSubjectService getInstance() {
        if (instance == null) {
            instance = new BedSectionHiddenSubjectService();
        }
        return instance;
    }
    
     public Long countHiddenSubjects(BedSections section) {
        EntityManager m = getNewEntityManager();
        Long rs = m.createQuery("SELECT COUNT(c) FROM BedSectionHiddenSubject c WHERE c.section.id = ?1 ", Long.class).setParameter(1, section.getId()).getSingleResult();
        m.close();
        return rs;
    }

    public List<BedSectionHiddenSubject> getHiddenSubjects(BedSections section) {
        EntityManager m = getNewEntityManager();
        List<BedSectionHiddenSubject> rs = m.createQuery("SELECT c FROM BedSectionHiddenSubject c WHERE c.section.id = ?1 ", BedSectionHiddenSubject.class).setParameter(1, section.getId()).getResultList();
        m.close();
        return rs;
    }

    public void addHiddenSubject(BedSections section, String hiddenSubject) {
        BedSectionHiddenSubject s = find(section, hiddenSubject);
        update(s);
    }

    private BedSectionHiddenSubject find(BedSections section, String subject) {
        EntityManager m = getNewEntityManager();
        List<BedSectionHiddenSubject> rs = m.createQuery("SELECT c FROM BedSectionHiddenSubject c WHERE c.section.id = ?1 AND c.subjectCode like ?2", BedSectionHiddenSubject.class)
                .setParameter(1, section.getId())
                .setParameter(2, subject)
                .getResultList();
        if (rs.isEmpty()) {

            BedSectionHiddenSubject s = new BedSectionHiddenSubject();
            s.setSection(section);
            s.setSubjectCode(subject);
            return s;
        }

        m.close();
        return rs.get(0);
    }

}
