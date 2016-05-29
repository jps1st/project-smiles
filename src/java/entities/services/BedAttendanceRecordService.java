package entities.services;

import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedAttendanceRecordService extends NewAbstractEntityService<BedEnrollmentAttendanceRecord> {
    
    private static final long serialVersionUID = 1L;

    private BedAttendanceRecordService() {
    }

    public void initAttendanceRecords(Integer sectionId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> unInitEnrollments = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 AND c.bedEnrollmentAttendanceRecord IS NULL", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        if (unInitEnrollments.isEmpty()) {
            m.close();
            return;
        }

        m.getTransaction().begin();
        for (BedEnrollment e : unInitEnrollments) {
            BedEnrollmentAttendanceRecord record = new BedEnrollmentAttendanceRecord();
            record.setId(e.getId());
            record.setBedEnrollment(e);
            m.merge(record);
        }

        m.getTransaction().commit();
        m.close();
    }

    @Override
    public Class getEntityClass() {
        return BedEnrollmentAttendanceRecord.class;
    }

    private static BedAttendanceRecordService instance;

    public static BedAttendanceRecordService getInstance() {
        if (instance == null) {
            instance = new BedAttendanceRecordService();
        }
        return instance;
    }

}
