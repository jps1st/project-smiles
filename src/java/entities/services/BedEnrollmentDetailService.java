/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.services;

import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentdetails;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedEnrollmentDetailService extends NewAbstractEntityService<BedEnrollmentdetails> {

    private static final long serialVersionUID = 1L;

    private BedEnrollmentDetailService() {
    }

    private static BedEnrollmentDetailService instance;

    public static BedEnrollmentDetailService getInstance() {
        if (instance == null) {
            instance = new BedEnrollmentDetailService();
        }
        return instance;
    }

    private boolean updateParentDetail(double displayOrder, BedEnrollment enrollment, BedCurriculumDetail curridetail) {
        EntityManager m = getNewEntityManager();

        m.getTransaction().begin();
        //double displayOrder = detail.getCurriDetail().getDisplayOrder();
        if (displayOrder % 1 == 0) {
            return false; //You are editing a parent account. 
        }

        double mainDispOrder = displayOrder - (displayOrder % 1);
        //get the main bed_curri detail 
        List<BedCurriculumDetail> getParent = m.createQuery("SELECT c FROM BedCurriculumDetail c WHERE c.displayOrder = ?1 AND c.currcode = ?2 AND c.yrLevel = ?3", BedCurriculumDetail.class)
                .setParameter(1, mainDispOrder)
                .setParameter(2, curridetail.getCurrcode())
                .setParameter(3, curridetail.getYrLevel()).getResultList();
        if (getParent.isEmpty()) {
            m.close();
            return true;
        }
        BedCurriculumDetail mainCurriDetail = getParent.get(0);
        //if its bed_enrollmentdetails for the student is not present then create it
        List<BedEnrollmentdetails> rs1 = m.createQuery("SELECT c FROM BedEnrollmentdetails c WHERE c.curriDetail.id = ?1 AND c.bedEnrollment.id = ?2", BedEnrollmentdetails.class)
                .setParameter(1, mainCurriDetail.getId())
                .setParameter(2, enrollment.getId()).getResultList();

        BedEnrollmentdetails studentEDetail;
        if (rs1.isEmpty()) {
            studentEDetail = new BedEnrollmentdetails();
            studentEDetail.setCurriDetail(mainCurriDetail);
            studentEDetail.setBedEnrollment(enrollment);
            studentEDetail = m.merge(studentEDetail);
            m.flush();
        } else {
            studentEDetail = rs1.get(0);
        }
        //get all dependents
        List<BedEnrollmentdetails> rs2 = m.createQuery("SELECT c FROM BedEnrollmentdetails c WHERE c.bedEnrollment.id = ?5 and c.curriDetail.displayOrder > ?1 and c.curriDetail.displayOrder < ?2 and c.curriDetail.currcode = ?3 and c.curriDetail.yrLevel = ?4", BedEnrollmentdetails.class)
                .setParameter(1, mainDispOrder)
                .setParameter(2, mainDispOrder + 1)
                .setParameter(3, mainCurriDetail.getCurrcode())
                .setParameter(4, mainCurriDetail.getYrLevel())
                .setParameter(5, enrollment.getId())
                .getResultList();

        double average1 = 0;
        double average2 = 0;
        double average3 = 0;
        double average4 = 0;

        for (BedEnrollmentdetails object : rs2) {
            try {
                average1 += object.getP1();
            } catch (Exception e) {
            }
            try {
                average2 += object.getP2();
            } catch (Exception e) {
            }
            try {
                average3 += object.getP3();
            } catch (Exception e) {
            }
            try {
                average4 += object.getP4();
            } catch (Exception e) {
            }
        }

        average1 = average1 / rs2.size();
        studentEDetail.setP1(average1);
        average2 = average2 / rs2.size();
        studentEDetail.setP2(average2);
        average3 = average3 / rs2.size();
        studentEDetail.setP3(average3);
        average4 = average4 / rs2.size();
        studentEDetail.setP4(average4);
        m.getTransaction().commit();
        m.close();

        return true;
    }

    public boolean updateParentDetail(BedEnrollmentdetails detail) {
        double displayOrder = detail.getCurriDetail().getDisplayOrder();
        return updateParentDetail(displayOrder, detail.getBedEnrollment(), detail.getCurriDetail());
    }

    @Override
    public Class getEntityClass() {
        return BedEnrollmentdetails.class;
    }

    public static String getProficiencyLevel(Double value) {
        if (value == null) {
            return "";
        } else if (value.equals(0d)) {
            return "";
        }
        try {
            if (value < 75) {
                return "B";
            } else if (value > 74 && value < 80) {
                return "D";
            } else if (value > 79 && value < 85) {
                return "AP";
            } else if (value > 84 && value < 90) {
                return "P";
            } else if (value > 89) {
                return "A";
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public long countUseInEnrollment(Integer curriSubjectId) {
        EntityManager m = getNewEntityManager();
        Long usages = m
                .createQuery("SELECT COUNT(c) "
                        + "FROM BedEnrollmentdetails c "
                        + "WHERE c.curriDetail.id = ?1", Long.class)
                .setParameter(1, curriSubjectId).getSingleResult();
        m.close();
        return usages;
    }

    public BedEnrollmentdetails getEnrollmentDetail(BedCurriculumDetail subject, BedEnrollment enrollment) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollmentdetails> result = m.createQuery("SELECT c FROM BedEnrollmentdetails c WHERE c.curriDetail.id = ?1 AND c.bedEnrollment.id = ?2", BedEnrollmentdetails.class)
                .setParameter(1, subject.getId())
                .setParameter(2, enrollment.getId())
                .getResultList();
        m.close();
        if (result.isEmpty()) {
            BedEnrollmentdetails enDetail = new BedEnrollmentdetails();
            enDetail.setCurriDetail(subject);
            enDetail.setBedEnrollment(enrollment);
            enDetail.setP1(0d);
            enDetail.setP2(0d);
            enDetail.setP3(0d);
            enDetail.setP4(0d);
            enDetail = update(enDetail);
            return enDetail;
        }
        return result.get(0);
    }

}
