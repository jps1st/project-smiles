/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedEnrollment;
import entities.BedEnrollmentNarrativeReport;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class NarrativeReportService extends NewAbstractEntityService<BedEnrollmentNarrativeReport> {

    private static final long serialVersionUID = 1L;

    private NarrativeReportService() {
    }

    private static NarrativeReportService instance;

    public static NarrativeReportService getInstance() {
        if (instance == null) {
            instance = new NarrativeReportService();
        }
        return instance;
    }

    public void initNarrativeReportRecords(Integer sectionId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> unInitEnrollments = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 AND c.bedEnrollmentNarrativeReport IS NULL", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        if (unInitEnrollments.isEmpty()) {
            m.close();
            return;
        }

        m.getTransaction().begin();
        for (BedEnrollment e : unInitEnrollments) {
            BedEnrollmentNarrativeReport record = new BedEnrollmentNarrativeReport();
            record.setId(e.getId());
            record.setBedEnrollment(e);
            m.merge(record);
        }

        m.getTransaction().commit();
        m.close();
    }

    @Override
    public Class getEntityClass() {
        return BedEnrollmentNarrativeReport.class;
    }

}
