/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_enrollment_narrative_report")
@NamedQueries({
    @NamedQuery(name = "BedEnrollmentNarrativeReport.findAll", query = "SELECT b FROM BedEnrollmentNarrativeReport b")})
public class BedEnrollmentNarrativeReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    
    @Lob
    
    @Column(name = "first_grading")
    private String firstGrading;
    @Basic(optional = false)
    
    @Lob
    
    @Column(name = "second_grading")
    private String secondGrading;
    @Basic(optional = false)
    
    @Lob
    
    @Column(name = "third_grading")
    private String thirdGrading;
    @Basic(optional = false)
    
    @Lob
    
    @Column(name = "fourth_grading")
    private String fourthGrading;
    @JoinColumn(name = "id", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BedEnrollment bedEnrollment;

    public BedEnrollmentNarrativeReport() {
    }

    public BedEnrollmentNarrativeReport(Integer id) {
        this.id = id;
    }

    public BedEnrollmentNarrativeReport(Integer id, String firstGrading, String secondGrading, String thirdGrading, String fourthGrading) {
        this.id = id;
        this.firstGrading = firstGrading;
        this.secondGrading = secondGrading;
        this.thirdGrading = thirdGrading;
        this.fourthGrading = fourthGrading;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstGrading() {
        return firstGrading;
    }

    public void setFirstGrading(String firstGrading) {
        if (firstGrading == null || firstGrading.isEmpty()) {
            firstGrading = " ";
        }
        this.firstGrading = firstGrading;
    }

    public String getSecondGrading() {
        return secondGrading;
    }

    public void setSecondGrading(String secondGrading) {
        if (secondGrading == null || secondGrading.isEmpty()) {
            secondGrading = " ";
        }
        this.secondGrading = secondGrading;
    }

    public String getThirdGrading() {
        return thirdGrading;
    }

    public void setThirdGrading(String thirdGrading) {
        if (thirdGrading == null || thirdGrading.isEmpty()) {
            thirdGrading = " ";
        }

        this.thirdGrading = thirdGrading;
    }

    public String getFourthGrading() {
        return fourthGrading;
    }

    public void setFourthGrading(String fourthGrading) {
        if (fourthGrading == null || fourthGrading.isEmpty()) {
            fourthGrading = " ";
        }
        this.fourthGrading = fourthGrading;
    }

    public BedEnrollment getBedEnrollment() {
        return bedEnrollment;
    }

    public void setBedEnrollment(BedEnrollment bedEnrollment) {
        this.bedEnrollment = bedEnrollment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedEnrollmentNarrativeReport)) {
            return false;
        }
        BedEnrollmentNarrativeReport other = (BedEnrollmentNarrativeReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedEnrollmentNarrativeReport[ id=" + id + " ]";
    }

}
