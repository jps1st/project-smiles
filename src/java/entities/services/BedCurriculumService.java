/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCurriculum;
import entities.BedCurriculumDetail;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedCurriculumService extends NewAbstractEntityService<BedCurriculum> {
    
    private static final long serialVersionUID = 1L;

    private BedCurriculumService() {
    }

    public List<BedCurriculum> getAllCurriculumsByEffectiveSY() {
        EntityManager m = getNewEntityManager();
        List<BedCurriculum> curriculums = m.createQuery("SELECT c FROM BedCurriculum c ORDER BY c.effectiveSY", BedCurriculum.class).getResultList();
        m.close();
        return curriculums;
    }

    public List<BedCurriculumDetail> getBedCurriculumDetails(Integer curriId, int yearLevel) {
        EntityManager m = getNewEntityManager();

        List<BedCurriculumDetail> rs = m.createQuery("SELECT c FROM BedCurriculumDetail c "
                + "WHERE c.yrLevel = ?1 AND c.currcode.curricode = ?2 "
                + "ORDER BY c.displayOrder", BedCurriculumDetail.class).setParameter(1, yearLevel).setParameter(2, curriId).getResultList();
        m.close();
        return rs;
    }

    @Override
    public Class getEntityClass() {
        return BedCurriculum.class;
    }

    private static BedCurriculumService instance;

    public static BedCurriculumService getInstance() {
        if (instance == null) {
            instance = new BedCurriculumService();
        }
        return instance;
    }

    public List<BedCurriculum> searchCurriculum(String key) {
        if (key == null) {
            key = "";
        }
        EntityManager m = getNewEntityManager();
        List<BedCurriculum> items = m.createQuery("SELECT c FROM BedCurriculum c WHERE c.shortName LIKE ?1 ORDER BY c.effectiveSY desc", BedCurriculum.class).setParameter(1, "%" + key + "%").getResultList();
        m.close();
        return items;
    }

    public long countDetails(Integer curricode) {
        EntityManager m = getNewEntityManager();
        Long result = m.createQuery("SELECT COUNT(c) FROM BedCurriculumDetail c WHERE c.currcode.curricode = ?1", Long.class).setParameter(1, curricode).getSingleResult();
        m.close();
        return result;
    }

}
