package entities.services;

import controllers.utils.JsfUtil;
import entities.BedCurriculumDetail;
import entities.BedEnrollment;
import entities.BedEnrollmentAttendanceRecord;
import entities.BedEnrollmentCharacterTraits;
import entities.BedEnrollmentNarrativeReport;
import entities.BedEnrollmentdetails;
import entities.BedLoading;
import entities.BedSections;
import entities.JdStudent;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public final class BedSectionService extends NewAbstractEntityService<BedSections> {

    private static final long serialVersionUID = 1L;

    private BedSectionService() {
    }
    private static BedSectionService instance;

    public static BedSectionService getInstance() {
        if (instance == null) {
            instance = new BedSectionService();
        }
        return instance;
    }

    public List<BedSections> getAdvisory(int teacherId) {
        EntityManager m = getNewEntityManager();
        List<BedSections> resultList = m.createQuery("SELECT c FROM BedSections c WHERE c.adviser.id = ?1 AND c.sy =?2 ORDER BY c.name", BedSections.class)
                .setParameter(1, teacherId)
                .setParameter(2, BedSettingsService.getInstance().getActiveSchoolYear())
                .getResultList();
        m.close();
        return resultList;
    }

    public Integer getYearLevel(int sectionId) {
        EntityManager m = getNewEntityManager();
        Integer yearLevel = m.createQuery("SELECT c.year FROM BedSections c WHERE c.id = ?1", Integer.class).setParameter(1, sectionId).getSingleResult();
        m.close();
        return yearLevel;
    }

    public BedSections createNewSection() {
        BedSections n = new BedSections();
        n.setSy(BedSettingsService.getInstance().getActiveSchoolYear());
        return n;
    }

    public List<BedSections> search(String keyword) {

        if (keyword == null) {
            keyword = "";
        }
        EntityManager m = getNewEntityManager();
        List<BedSections> data = m.createQuery("SELECT c FROM BedSections c WHERE c.sy = ?1 AND (c.name LIKE ?2 ) ORDER BY c.name",
                BedSections.class).setParameter(1, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(2, "%" + keyword + "%")
                .getResultList();

        for (BedSections s : data) {
            if (s.getPopulation() == -1 || s.getPopulation() == 0) {
                if (!m.getTransaction().isActive()) {
                    m.getTransaction().begin();
                }
                s.setPopulation(getEnrolled(s.getId()));
                m.flush();
            }
        }
        if (m.getTransaction().isActive()) {
            m.getTransaction().commit();
        }
        m.close();
        return data;
    }

    public int getEnrolled(int sectionId) {

        EntityManager m = getNewEntityManager();
        Long r = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c WHERE c.section.id = ?1", Long.class).setParameter(1, sectionId).getSingleResult();
        m.close();
        return Integer.parseInt(r.toString());
    }

    public List<BedEnrollment> getEnrolledBySex(int sectionId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> enrolled = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 ORDER BY c.student.sex DESC, c.student.lastname, c.student.firstname", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        m.close();
        return enrolled;
    }

    public List<BedEnrollment> getEnrolledByName(int sectionId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> rs = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 "
                + "ORDER BY c.student.lastname, c.student.firstname", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        m.close();
        return rs;
    }

    /**
     *
     * @param sectionId
     * @param sex 'M' or 'F'
     * @return
     */
    public List<BedEnrollment> getEnrolled(int sectionId, String sex) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> rs = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1 "
                + "AND c.student.sex LIKE ?2 ORDER BY c.student.lastname, c.student.firstname", BedEnrollment.class)
                .setParameter(2, sex).setParameter(1, sectionId).getResultList();

        for (BedEnrollment r : rs) {
            m.refresh(r);
        }

        m.close();
        return rs;
    }

    public List<BedEnrollment> getEnrolledF(int sectionId) {
        return getEnrolled(sectionId, "F");
    }

    public List<BedEnrollment> getEnrolledM(int sectionId) {
        return getEnrolled(sectionId, "M");
    }

    public List<BedEnrollment> getEnrolledInPrintSequence(int sectionId, String sex) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> rs = m.createQuery("SELECT c FROM BedEnrollment c "
                + "WHERE c.section.id = ?1 AND c.student.sex LIKE ?2 "
                + "ORDER BY c.printSeq, c.student.lastname", BedEnrollment.class)
                .setParameter(2, sex)
                .setParameter(1, sectionId).getResultList();
        m.close();
        return rs;
    }

    public BedEnrollment transferStudent(String studentId, int sectionId) {

        int sy = BedSettingsService.getInstance().getActiveSchoolYear();
        BedEnrollment enrollment = BedEnrollmentService.getInstance().getEnrollmentRecord(studentId, sy);

        if (enrollment != null) {
            if (sectionId == enrollment.getSection().getId()) {
                JsfUtil.addWarningMessage("Aborted. ", "Student is already enrolled in this section.");
                return null;
            } else {
                EntityManager m = getNewEntityManager();
                m.getTransaction().begin();

                String previousSectionName = enrollment.getSection().getName();
                BedSections currentSection = m.find(BedSections.class, sectionId);
                enrollment.setSection(currentSection);
                enrollment = m.merge(enrollment);
                m.getTransaction().commit();

                JsfUtil.addInfoMessage("Saved.", "Student is successfully transfered from " + previousSectionName + " to " + currentSection.getName() + ".");
                m.close();
            }

        } else {
            enrollment = enrollStudentToSection(studentId, sectionId);
        }

        return enrollment;

    }

    public boolean unenrollStudent(String studentId, int sectionId) {
        if (studentId == null) {
            JsfUtil.addWarningMessage("Aborted", "Please select a student.");
            return false;
        }

        EntityManager manager = getNewEntityManager();
        manager.getTransaction().begin();

        BedEnrollment enrollmentToDelete = BedStudentService.getInstance().getEnrollmentRecord(studentId, sectionId);
        enrollmentToDelete = manager.merge(enrollmentToDelete);

        if (enrollmentToDelete == null) {
            JsfUtil.addWarningMessage("Aborted. Student not enrolled.", "Student not enrolled in class");
            return false;
        }

        BedEnrollmentAttendanceRecord attendanceRecord = enrollmentToDelete.getBedEnrollmentAttendanceRecord();
        if (attendanceRecord != null) {
            manager.remove(manager.merge(attendanceRecord));
        }

        List<BedEnrollmentCharacterTraits> traits = BedEnrollmentService.getInstance().getCharacterRecords(enrollmentToDelete.getId());
        for (BedEnrollmentCharacterTraits t : traits) {
            manager.remove(manager.merge(t));
        }

        BedEnrollmentNarrativeReport narrativeReports = enrollmentToDelete.getBedEnrollmentNarrativeReport();
        if (narrativeReports != null) {
            manager.remove(manager.merge(narrativeReports));
        }

        List<BedEnrollmentdetails> gradeRecords = BedEnrollmentService.getInstance().getDetails(enrollmentToDelete.getId());
        if (gradeRecords != null) {
            for (BedEnrollmentdetails record : gradeRecords) {
                manager.remove(manager.merge(record));
            }
        }

        manager.remove(enrollmentToDelete);
        manager.getTransaction().commit();
        manager.close();
        JsfUtil.addInfoMessage("Student removed", "Successfully removed student from the class.");
        return true;
    }

    public BedEnrollment enrollStudentToSection(String studentId, int sectionId) {

        int sy = BedSettingsService.getInstance().getActiveSchoolYear();
        BedEnrollment enrollment = BedEnrollmentService.getInstance().getEnrollmentRecord(studentId, sy);
        if (enrollment != null) {
            if (enrollment.getSection().getId().equals(sectionId)) {
                JsfUtil.addWarningMessage("Aborted. ", "Student is already enrolled in this section.");
                return null;
            } else {
                JsfUtil.addWarningMessage("Aborted. ", "Student is already enrolled in " + enrollment.getSection().getName() + ". Use transfer instead.");
            }
            return null;
        }

        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();

        BedEnrollment newEnrollment = new BedEnrollment();
        newEnrollment.setSection(m.find(BedSections.class, sectionId));
        newEnrollment.setStudent(m.find(JdStudent.class, studentId));
        newEnrollment.setDateEnrolled(new java.util.Date());
        m.merge(newEnrollment);

        m.getTransaction().commit();
        m.close();
        JsfUtil.addInfoMessage("Saved.", "Student is now enrolled to the class.");
        return newEnrollment;
    }

    public void removeLoadsAndGradeRecords(int sectionId) {

        //remove loads
        EntityManager m = getNewEntityManager();
        m.getTransaction().begin();
        List<BedLoading> rs = m.createQuery("SELECT c FROM BedLoading c WHERE c.section.id = ?1", BedLoading.class).setParameter(1, sectionId).getResultList();
        for (BedLoading b : rs) {
            m.remove(b);
        }

        //remove student grades
        List<BedEnrollment> e = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        for (BedEnrollment en : e) {
            List<BedEnrollmentdetails> detailList = BedEnrollmentService.getInstance().getDetails(en.getId());
            for (BedEnrollmentdetails d : detailList) {
                m.remove(m.merge(d));
            }
        }

        m.getTransaction().commit();
        m.close();

        JsfUtil.addInfoMessage("Saved", "Success on year level change.");

    }

    public List<BedSections> completeSections(String key) {

        if (key == null) {
            key = "";
        } else {
            key = key.trim();
        }

        EntityManager m = getNewEntityManager();
        int sy = BedSettingsService.getInstance().getActiveSchoolYear();

        List<BedSections> sections;

        if (key.isEmpty()) {
            sections = m.createQuery("SELECT c FROM BedSections c WHERE c.sy = ?1 ORDER BY c.year, c.name",
                    BedSections.class).setParameter(1, sy).getResultList();
        } else {
            sections = m.createQuery("SELECT c FROM BedSections c WHERE c.sy = ?1 AND c.name "
                    + "LIKE ?2 ORDER BY c.year, c.name", BedSections.class).setParameter(2, "%" + key + "%")
                    .setParameter(1, sy).getResultList();
        }

        m.close();
        return sections;
    }

//<editor-fold defaultstate="collapsed" desc="getters/setters">
    @Override
    public Class getEntityClass() {
        return BedSections.class;
    }
//</editor-fold>

    private Long countByActionTaken(Integer sectionId, String actionTaken, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.student.sex = ?4 AND c.actionTaken LIKE ?2", Long.class)
                .setParameter(4, gender)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, sectionId)
                .setParameter(2, actionTaken)
                .getSingleResult();
        m.close();
        return singleResult;
    }

    public Long countPromoted(Integer id, String gender) {
        return countByActionTaken(id, "Promoted", gender) + countByActionTaken(id, "ALS Passed", gender);
    }

    public Long countIrregulars(Integer id, String gender) {
        return countByActionTaken(id, "Irregular", gender);
    }

    public Long countRetained(Integer id, String gender) {
        return countByActionTaken(id, "Retained", gender);
    }

    public Long countAdvanced(Integer id, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.generalAverage >= 90 AND c.student.sex = ?4", Long.class)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, id)
                .setParameter(4, gender)
                .getSingleResult();

        m.close();
        return singleResult;
    }

    public Long countDeveloping(Integer id, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.generalAverage >= 75 AND c.generalAverage < 80 AND c.student.sex = ?4", Long.class)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, id)
                .setParameter(4, gender)
                .getSingleResult();
        m.close();
        return singleResult;
    }

    public Long countBeginning(Integer id, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.generalAverage < 75 AND c.student.sex = ?4", Long.class)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, id)
                .setParameter(4, gender)
                .getSingleResult();
        m.close();
        return singleResult;
    }

    public Long countAproachingEff(Integer id, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.generalAverage >= 80 AND c.generalAverage < 85 AND c.student.sex = ?4", Long.class)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, id)
                .setParameter(4, gender)
                .getSingleResult();
        m.close();
        return singleResult;
    }

    public Long countProf(Integer id, String gender) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.id = ?1 AND c.generalAverage >= 85 AND c.generalAverage < 90 AND c.student.sex = ?4", Long.class)
                .setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear())
                .setParameter(1, id)
                .setParameter(4, gender)
                .getSingleResult();
        m.close();
        return singleResult;
    }

    private Long gcountByActionTaken(Integer yearLvl, String actionTaken) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.actionTaken LIKE ?2", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, yearLvl).setParameter(2, actionTaken).getSingleResult();
        m.close();
        return singleResult;
    }

    public Long gcountPromoted(Integer yearlvl) {
        return gcountByActionTaken(yearlvl, "Promoted") + gcountByActionTaken(yearlvl, "ALS Passed");
    }

    public Long gcountIrregulars(Integer id) {
        return gcountByActionTaken(id, "Irregular");
    }

    public Long gcountAdvanced(Integer yearLvl) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.generalAverage >= 90", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, yearLvl).getSingleResult();
        m.close();
        return singleResult;
    }

    public Long gcountDeveloping(Integer id) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.generalAverage >= 75 AND c.generalAverage < 80", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, id).getSingleResult();
        m.close();
        return singleResult;
    }

    public Long gcountBeginning(Integer id) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.generalAverage < 75", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, id).getSingleResult();
        m.close();
        return singleResult;
    }

    public Long gcountAproachingEff(Integer id) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.generalAverage >= 80 AND c.generalAverage < 85", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, id).getSingleResult();
        m.close();
        return singleResult;
    }

    public Long gcountProf(Integer id) {
        EntityManager m = getNewEntityManager();
        Long singleResult = m.createQuery("SELECT COUNT(c) FROM BedEnrollment c "
                + "WHERE c.section.sy = ?3 AND c.section.year = ?1 AND c.generalAverage >= 85 AND c.generalAverage < 90", Long.class).setParameter(3, BedSettingsService.getInstance().getActiveSchoolYear()).setParameter(1, id).getSingleResult();
        m.close();
        return singleResult;
    }

    public List<BedCurriculumDetail> getSubjects(BedSections selectedSection, String subjectName) {
        EntityManager m = getNewEntityManager();
        List<BedCurriculumDetail> rs = m.createQuery(""
                + "SELECT c FROM BedCurriculumDetail c "
                + "WHERE (c.currcode.curricode = ?1 AND c.yrLevel = ?2) "
                + "AND (c.subjcode.subjcode "
                + "LIKE ?3 "
                + "OR c.subjcode.subjdesc "
                + "LIKE ?3)", BedCurriculumDetail.class)
                .setParameter(3, "%" + subjectName + "%")
                .setParameter(2, selectedSection.getYear())
                .setParameter(1, selectedSection.getCurriculum().getCurricode()).getResultList();
        m.close();
        return rs;
    }

    public BedLoading getSectionSubjectLoadRecord(BedSections section, BedCurriculumDetail subject) {
        EntityManager m = getNewEntityManager();
        List<BedLoading> rs = m.createQuery("SELECT c FROM BedLoading c "
                + "WHERE c.section.id = ?1 "
                + "AND c.subject.id = ?2", BedLoading.class)
                .setParameter(1, section.getId())
                .setParameter(2, subject.getId())
                .getResultList();

        BedLoading l;
        if (rs.isEmpty()) {
            l = new BedLoading();
            l.setSection(section);
            l.setSubject(subject);
            l.setTeacher(null);
            l = BedLoadingService.getInstance().update(l);

        } else {

            l = null;
            for (BedLoading bedLoading : rs) {
                if (l == null) {
                    l = bedLoading;
                } else if (bedLoading.getTeacher() != null) {
                    l = bedLoading;
                    break;
                }
            }

        }
        m.close();
        return l;
    }

    public void updateEnrollmentAttendanceRecords(Integer sectionId) {
        EntityManager m = getNewEntityManager();
        List<BedEnrollment> rs = m.createQuery("SELECT c FROM BedEnrollment c WHERE c.section.id = ?1", BedEnrollment.class).setParameter(1, sectionId).getResultList();
        for (BedEnrollment r : rs) {
            if (r.getBedEnrollmentAttendanceRecord() == null) {
                BedEnrollmentService.getInstance().getAttendanceRecord(r.getId());
            }
        }
    }

}
