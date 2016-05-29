/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCurriculum;
import entities.BedCurriculumDetail;
import entities.BedSectionHiddenSubject;
import entities.BedSections;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedCurriculumDetailService extends NewAbstractEntityService<BedCurriculumDetail> {

    private static final long serialVersionUID = 1L;

    private BedCurriculumDetailService() {
    }

    @Override
    public Class getEntityClass() {
        return BedCurriculumDetail.class;
    }

    private static BedCurriculumDetailService instance;

    public static BedCurriculumDetailService getInstance() {
        if (instance == null) {
            instance = new BedCurriculumDetailService();
        }
        return instance;
    }

    public List<BedCurriculumDetail> getCurriDetails(Integer curriculum, int grade) {
        EntityManager m = getNewEntityManager();
        List<BedCurriculumDetail> details = m.createQuery(
                "SELECT c FROM BedCurriculumDetail c "
                + "WHERE c.currcode.curricode = ?1 AND c.yrLevel = ?2 "
                + "ORDER BY c.displayOrder", BedCurriculumDetail.class)
                .setParameter(1, curriculum)
                .setParameter(2, grade)
                .getResultList();
        m.close();
        return details;
    }

    public List<BedCurriculumDetail> getComponents(BedCurriculumDetail curriDetail) {
        boolean parent = curriDetail.getDisplayOrder() % 1 == 0;
        if (!parent) {
            return new ArrayList<>();
        }

        EntityManager m = getNewEntityManager();
        List<BedCurriculumDetail> components = m.createQuery("SELECT c FROM BedCurriculumDetail c WHERE c.currcode.curricode = ?2 AND (c.displayOrder > ?1 AND c.displayOrder < (?1 + 1)) AND c.yrLevel = ?3 ", BedCurriculumDetail.class)
                .setParameter(1, curriDetail.getDisplayOrder())
                .setParameter(2, curriDetail.getCurrcode().getCurricode())
                .setParameter(3, curriDetail.getYrLevel())
                .getResultList();
        m.close();
        return components;

    }

    public boolean hasComponents(BedCurriculumDetail aThis) {
        boolean parent = aThis.getDisplayOrder() % 1 == 0;
        if (!parent) {
            return false;
        }

        EntityManager m = getNewEntityManager();
        long r = m.createQuery("SELECT COUNT(c) FROM BedCurriculumDetail c WHERE c.currcode.curricode = ?2 AND c.yrLevel = ?3 AND (c.displayOrder > ?1 AND c.displayOrder < (?1+1))", Long.class)
                .setParameter(1, aThis.getDisplayOrder())
                .setParameter(2, aThis.getCurrcode().getCurricode())
                .setParameter(3, aThis.getYrLevel())
                .getSingleResult();

        m.close();

        return r > 0;
    }

    public List<BedCurriculumDetail> search(BedCurriculum curriculum, Integer year, String key) {
        EntityManager m = getNewEntityManager();
        List<BedCurriculumDetail> rs = m.createQuery("SELECT c FROM BedCurriculumDetail c WHERE c.currcode.curricode = ?1 AND c.yrLevel = ?2 AND c.subjcode.subjcode LIKE ?3", BedCurriculumDetail.class)
                .setParameter(1, curriculum.getCurricode())
                .setParameter(2, year)
                .setParameter(3, "%" + key + "%").getResultList();
        m.close();
        return rs;
    }


}
