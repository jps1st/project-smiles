/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.services.BedEnrollmentService;
import entities.services.DbaseManager;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_enrollment")
@NamedQueries({
    @NamedQuery(name = "BedEnrollment.findAll", query = "SELECT b FROM BedEnrollment b")})
public class BedEnrollment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)

    @Column(name = "DATE_ENROLLED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnrolled;

    @Column(name = "GEN_AVG")
    private Double generalAverage;

    @Column(name = "honor_learner")
    private boolean honorLearner;

    @Column(name = "action_taken")
    private String actionTaken;

    @Column(name = "remarks_code")
    private String remarksCode;

    @Column(name = "remarks_required_info")
    private String remarksRequiredInfo;

    @Column(name = "eligible_for_transfer_to") // cert1
    private String eligibleForTransferTo;

    @Column(name = "missing_units_in") //cert2
    private String missingUnitsIn;

    @Column(name = "promoted_to") //cert4
    private String promotedTo;

    @Column(name = "retained_to") //cert5
    private String retainedTo;

    @Column(name = "print_seq")
    private Integer printSeq;

    @JoinColumn(name = "STUDENT", referencedColumnName = "STDNTID")
    @ManyToOne(optional = false)
    private JdStudent student;

    @JoinColumn(name = "SECTION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private BedSections section;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bedEnrollment")
    private BedEnrollmentNarrativeReport bedEnrollmentNarrativeReport;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bedEnrollment")
    private BedEnrollmentAttendanceRecord bedEnrollmentAttendanceRecord;

    public BedEnrollment() {
    }

    public BedEnrollment(Integer id) {
        this.id = id;
    }

    public BedEnrollment(Integer id, Date dateEnrolled) {
        this.id = id;
        this.dateEnrolled = dateEnrolled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(Date dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public BedEnrollmentNarrativeReport getBedEnrollmentNarrativeReport() {
        return bedEnrollmentNarrativeReport;
    }

    public void setBedEnrollmentNarrativeReport(BedEnrollmentNarrativeReport bedEnrollmentNarrativeReport) {
        this.bedEnrollmentNarrativeReport = bedEnrollmentNarrativeReport;
    }

    public JdStudent getStudent() {
        return student;
    }

    public void setStudent(JdStudent student) {
        this.student = student;
    }

    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }
    
    
    public BedEnrollmentAttendanceRecord getBedEnrollmentAttendanceRecord() {
        return bedEnrollmentAttendanceRecord;
    }

    public void setBedEnrollmentAttendanceRecord(BedEnrollmentAttendanceRecord bedEnrollmentAttendanceRecord) {
        this.bedEnrollmentAttendanceRecord = bedEnrollmentAttendanceRecord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedEnrollment)) {
            return false;
        }
        BedEnrollment other = (BedEnrollment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedEnrollment[ id=" + id + " ]";
    }

    public double updateGeneralAverage() {
        List<BedEnrollmentdetails> details = BedEnrollmentService.getInstance().getDetails(id);
        
        Double generalAv = 0d;
        int subjCount = 0;
        for (BedEnrollmentdetails detail : details) {
            boolean minor = (detail.getCurriDetail().getDisplayOrder() % 1) > 0;
            boolean invalid = detail.getFinals() == null || detail.getFinals() < 10;
            if (!minor && !invalid) {
                subjCount++;
                double finals = detail.getFinals();
                generalAv += finals;
            }
        }

        generalAv = generalAv / subjCount;

        if (generalAv.isNaN()) {
            generalAv = 0d;
        }

        setGeneralAverage(generalAv);
        BedEnrollmentService.getInstance().update(this);
        return generalAv;
    }

    public Double getGeneralAverage() {
        if (generalAverage == null) {
            return 0d;
        }
        return generalAverage;
    }

    public void setGeneralAverage(Double generalAverage) {
        this.generalAverage = generalAverage;
    }

    public String getActionTaken() {
        if (actionTaken == null || actionTaken.isEmpty()) {
            return "N/A";
        }
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getRemarksCode() {
        if (remarksCode == null || remarksCode.isEmpty()) {
            return "N/A";
        }
        return remarksCode;
    }

    public void setRemarksCode(String remarksCode) {
        this.remarksCode = remarksCode;
    }

    public String getRemarksRequiredInfo() {
        if (remarksRequiredInfo == null || remarksRequiredInfo.isEmpty()) {
            return "N/A";
        }
        return remarksRequiredInfo;
    }

    public void setRemarksRequiredInfo(String remarksRequiredInfo) {
        this.remarksRequiredInfo = remarksRequiredInfo;
    }

    public boolean isHonorLearner() {
        return honorLearner;
    }

    public void setHonorLearner(boolean honorLearner) {
        this.honorLearner = honorLearner;
    }

    public Integer getPrintSeq() {
        return printSeq;
    }

    public void setPrintSeq(Integer printSeq) {
        this.printSeq = printSeq;
    }

    public String getEligibleForTransferTo() {
        return eligibleForTransferTo;
    }

    public void setEligibleForTransferTo(String eligibleForTransferTo) {
        this.eligibleForTransferTo = eligibleForTransferTo;
    }

    public String getMissingUnitsIn() {
        return missingUnitsIn;
    }

    public void setMissingUnitsIn(String missing_units_in) {
        this.missingUnitsIn = missing_units_in;
    }

    public String getPromotedTo() {
        return promotedTo;
    }

    public void setPromotedTo(String promotedTo) {
        this.promotedTo = promotedTo;
    }

    public String getRetainedTo() {
        return retainedTo;
    }

    public void setRetainedTo(String retainedTo) {
        this.retainedTo = retainedTo;
    }

    public String getIncompleteThisYear() {
        StringBuilder sBuild = new StringBuilder();
        List<BedEnrollmentdetails> details = BedEnrollmentService.getInstance().getDetails(id);
        int count = 0;
        EntityManager m = DbaseManager.getNewEntityManager(); // to efficiently save changes of each detail when we invoike updateFinalAve()
        m.getTransaction().begin();
        for (BedEnrollmentdetails detail : details) {
            detail.updateFinalAve();
            m.merge(detail);
            if (detail.getFinals() < 75) {
                if (count++ > 0) {
                    sBuild.append(" / ");
                }
                sBuild.append(detail.getCurriDetail().getSubjcode().getSubjcode());
            }
        }
        m.getTransaction().commit();
        m.close();
        return sBuild.toString();

    }

}
