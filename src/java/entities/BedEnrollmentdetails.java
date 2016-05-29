/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.services.BedEnrollmentDetailService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_enrollment_detail")
@NamedQueries({
    @NamedQuery(name = "BedEnrollmentdetails.findAll", query = "SELECT b FROM BedEnrollmentdetails b")})
public class BedEnrollmentdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "P1")
    private Double p1;
    @Column(name = "P1A")
    private Character p1a;
    @Column(name = "P2")
    private Double p2;
    @Column(name = "P2A")
    private Character p2a;
    @Column(name = "P3")
    private Double p3;
    @Column(name = "P3A")
    private Character p3a;
    @Column(name = "P4")
    private Double p4;
    @Column(name = "P4A")
    private Character p4a;
    @Column(name = "FINALS")
    private Double finals;
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "P1LASTEDIT")
    @Temporal(TemporalType.DATE)
    private Date p1lastedit;
    @Column(name = "P2LASTEDIT")
    @Temporal(TemporalType.DATE)
    private Date p2lastedit;
    @Column(name = "P3LASTEDIT")
    @Temporal(TemporalType.DATE)
    private Date p3lastedit;
    @Column(name = "P4LASTEDIT")
    @Temporal(TemporalType.DATE)
    private Date p4lastedit;
    @Column(name = "P1C")
    private Double p1c;
    @Column(name = "P2C")
    private Double p2c;
    @Column(name = "P3C")
    private Double p3c;
    @Column(name = "P4C")
    private Double p4c;
    @JoinColumn(name = "CURRI_DETAIL", referencedColumnName = "ID")
    @ManyToOne
    private BedCurriculumDetail curriDetail;
    @JoinColumn(name = "BED_ENROLLMENT", referencedColumnName = "ID")
    @ManyToOne
    private BedEnrollment bedEnrollment;

    public void updateFinalAve() {

        if (p1 == null) {
            p1 = 0d;
        }

        if (p2 == null) {
            p2 = 0d;
        }

        if (p3 == null) {
            p3 = 0d;
        }

        if (p4 == null) {
            p4 = 0d;
        }

        double total = (p1 + p2 + p3 + p4);
        if (total == 0) {
            setFinals(0d);
        }

        setFinals(total / 4);

        if (finals == 0) {
            setRemarks("");
        } else {
            if (finals > 74.999) {
                setRemarks("P");
            } else {
                setRemarks("F");
            }
        }

        //do not save here. as it will be redundant when you input grades
    }

    public BedEnrollmentdetails() {
        p1 = 0d;
        p1c = 0d;
        p2 = 0d;
        p2c = 0d;
        p3 = 0d;
        p3c = 0d;
        p4 = 0d;
        p4c = 0d;
        finals = 0d;
    }

    public BedEnrollmentdetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getP1() {
        if (p1 == null) {
            return 0d;
        }
        return p1;
    }

    public void setP1(Double p1) {
        this.p1 = p1;
        updateFinalAve();
    }

    public Character getP1a() {
        return p1a;
    }

    public void setP1a(Character p1a) {
        this.p1a = p1a;
    }

    public Double getP2() {
        if (p2 == null) {
            return 0d;
        }
        return p2;
    }

    public void setP2(Double p2) {
        this.p2 = p2;
        updateFinalAve();
    }

    public Character getP2a() {
        return p2a;
    }

    public void setP2a(Character p2a) {
        this.p2a = p2a;
    }

    public Double getP3() {
        if (p3 == null) {
            return 0d;
        }
        return p3;
    }

    public void setP3(Double p3) {
        this.p3 = p3;
        updateFinalAve();
    }

    public Character getP3a() {
        return p3a;
    }

    public void setP3a(Character p3a) {
        this.p3a = p3a;
    }

    public Double getP4() {
        if (p4 == null) {
            return 0d;
        }
        return p4;
    }

    public void setP4(Double p4) {
        this.p4 = p4;
        updateFinalAve();
    }

    public Character getP4a() {
        return p4a;
    }

    public void setP4a(Character p4a) {
        this.p4a = p4a;
    }

    public Double getFinals() {
        return finals;
    }

    public void setFinals(Double finals) {
        this.finals = finals;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getP1lastedit() {
        return p1lastedit;
    }

    public void setP1lastedit(Date p1lastedit) {
        this.p1lastedit = p1lastedit;
    }

    public Date getP2lastedit() {
        return p2lastedit;
    }

    public void setP2lastedit(Date p2lastedit) {
        this.p2lastedit = p2lastedit;
    }

    public Date getP3lastedit() {
        return p3lastedit;
    }

    public void setP3lastedit(Date p3lastedit) {
        this.p3lastedit = p3lastedit;
    }

    public Date getP4lastedit() {
        return p4lastedit;
    }

    public void setP4lastedit(Date p4lastedit) {
        this.p4lastedit = p4lastedit;
    }

    public Double getP1c() {
        return p1c;
    }

    public void setP1c(Double p1c) {
        this.p1c = p1c;
    }

    public Double getP2c() {
        return p2c;
    }

    public void setP2c(Double p2c) {
        this.p2c = p2c;
    }

    public Double getP3c() {
        return p3c;
    }

    public void setP3c(Double p3c) {
        this.p3c = p3c;
    }

    public Double getP4c() {
        return p4c;
    }

    public void setP4c(Double p4c) {
        this.p4c = p4c;
    }

    public BedCurriculumDetail getCurriDetail() {
        return curriDetail;
    }

    public void setCurriDetail(BedCurriculumDetail curriDetail) {
        this.curriDetail = curriDetail;
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
        if (!(object instanceof BedEnrollmentdetails)) {
            return false;
        }
        BedEnrollmentdetails other = (BedEnrollmentdetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entities.BedEnrollmentdetails[ id=" + id + " ]";
    }

}
