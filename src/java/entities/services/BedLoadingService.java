/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import entities.BedLoading;
import entities.BedSections;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedLoadingService extends NewAbstractEntityService<BedLoading> {

    private static final long serialVersionUID = 1L;

    private BedLoadingService() {
    }
    private static BedLoadingService instance;

    public static BedLoadingService getInstance() {
        if (instance == null) {
            instance = new BedLoadingService();
        }
        return instance;
    }

    public List<BedLoading> getLoadings(BedSections s) {
        if (s == null) {
            return new ArrayList();
        }
        EntityManager m = getNewEntityManager();
        List<BedLoading> rs = m.createQuery("SELECT c FROM BedLoading c WHERE c.section.id = ?1 "
                + "ORDER BY c.subject.displayOrder", BedLoading.class)
                .setParameter(1, s.getId())
                .getResultList();
        m.close();
        return rs;
    }

    public List<BedEnrollmentdetails> getEnrollmentDetails(List<BedEnrollment> elist, Integer loadId) {

        if (loadId == null) {
            return new ArrayList();
        }

        BedLoading load = find(loadId);

        EntityManager m = getNewEntityManager();
        Integer sectId = load.getSection().getId();
        Integer subjectid = load.getSubject().getId();
        List<BedEnrollmentdetails> eDetails
                = m.createQuery("SELECT c FROM BedEnrollmentdetails c WHERE c.bedEnrollment.section.id = ?1 AND c.curriDetail.id = ?2", BedEnrollmentdetails.class)
                .setParameter(1, sectId)
                .setParameter(2, subjectid)
                .getResultList();

        List<BedEnrollmentdetails> toReturn = new ArrayList();
        BedCurriculumDetail subject = m.merge(load.getSubject());

        for (BedEnrollment enrollment : elist) {

            String stdntid = enrollment.getStudent().getStdntid();
            boolean found = false;

            for (BedEnrollmentdetails det : eDetails) {
                String eStdid = det.getBedEnrollment().getStudent().getStdntid();
                if (stdntid.equals(eStdid)) {
                    toReturn.add(det);
                    eDetails.remove(det);
                    found = true;
                    break;
                }
            }

            if (!found) {
                if (!m.getTransaction().isActive()) {
                    m.getTransaction().begin();
                }

                BedEnrollmentdetails newDetail = new BedEnrollmentdetails();
                newDetail.setBedEnrollment(enrollment);
                newDetail.setCurriDetail(subject);
                newDetail = m.merge(newDetail);
                m.flush();
                toReturn.add(newDetail);
            }
        }

        if (m.getTransaction().isActive()) {
            m.getTransaction().commit();
        }

        for (BedEnrollmentdetails t : toReturn) {
            m.refresh(t);
        }

        m.close();

        return toReturn;
    }

    @Override
    public Class getEntityClass() {
        return BedLoading.class;
    }

    public long countUseInLoading(Integer id) {
        EntityManager m = getNewEntityManager();
        long usages
                = m.createQuery(
                        "SELECT COUNT(c) FROM BedLoading c "
                        + "WHERE c.subject.id = ?1", Long.class)
                .setParameter(1, id)
                .getSingleResult();
        m.close();
        return usages;
    }

}
