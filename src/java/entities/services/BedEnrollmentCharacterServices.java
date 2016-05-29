/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCharacterTraits;
import entities.BedEnrollment;
import entities.BedEnrollmentCharacterTraits;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedEnrollmentCharacterServices extends NewAbstractEntityService<BedEnrollmentCharacterTraits> {
    private static final long serialVersionUID = 1L;

    private BedEnrollmentCharacterServices() {
    }
    private static BedEnrollmentCharacterServices instance;

    public static BedEnrollmentCharacterServices getInstance() {
        if (instance == null) {
            instance = new BedEnrollmentCharacterServices();
        }
        return instance;
    }

    @Override
    public Class getEntityClass() {
        return BedEnrollmentCharacterTraits.class;
    }

    public List<BedEnrollmentCharacterTraits> getCharacterTraits(String gender, Integer sectionId, Integer traitId) {
        EntityManager manager = getNewEntityManager();
        List<BedEnrollmentCharacterTraits> traits = new ArrayList();

        List<BedEnrollment> enrolled;
        if (gender.equals("F")) {
            enrolled = BedSectionService.getInstance().getEnrolledF(sectionId);
        } else {
            enrolled = BedSectionService.getInstance().getEnrolledM(sectionId);
        }

        BedCharacterTraits trait = manager.merge(manager.find(BedCharacterTraits.class, traitId));
        for (BedEnrollment b : enrolled) {
            List<BedEnrollmentCharacterTraits> tl = manager.createQuery("SELECT c FROM BedEnrollmentCharacterTraits c WHERE c.bedEnrollment.id = ?1 AND c.bedCharacterTrait.ctId = ?2", BedEnrollmentCharacterTraits.class)
                    .setParameter(1, b.getId()).setParameter(2, traitId).getResultList();
            if (!tl.isEmpty()) {
                traits.add(tl.get(0));
            } else {
                if (!manager.getTransaction().isActive()) {
                    manager.getTransaction().begin();
                }
                BedEnrollmentCharacterTraits newTrait = new BedEnrollmentCharacterTraits();
                newTrait.setBedCharacterTrait(trait);
                newTrait.setBedEnrollment(b);
                newTrait.setFinalValue("");
                newTrait.setP1Value("");
                newTrait.setP2Value("");
                newTrait.setP3Value("");
                newTrait.setP4Value("");
                newTrait = manager.merge(newTrait);
                manager.flush();
                traits.add(newTrait);
            }
        }

        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
        }

        manager.close();

        return traits;

    }

    public List<BedCharacterTraits> getTraitList(String traitGroup) {
        EntityManager m = getNewEntityManager();
        List<BedCharacterTraits> traitList = m.createQuery("SELECT c FROM BedCharacterTraits c WHERE c.charGroup LIKE ?1 ORDER BY c.ctOrder", BedCharacterTraits.class)
                .setParameter(1, "%" + traitGroup + "%").getResultList();
        m.close();
        return traitList;
    }

}
