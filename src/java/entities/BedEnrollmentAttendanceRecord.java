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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_enrollment_attendance_record")
@NamedQueries({
    @NamedQuery(name = "BedEnrollmentAttendanceRecord.findAll", query = "SELECT b FROM BedEnrollmentAttendanceRecord b")})
public class BedEnrollmentAttendanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    
    @Column(name = "june")
    private double june;
    @Basic(optional = false)
    
    @Column(name = "july")
    private double july;
    @Basic(optional = false)
    
    @Column(name = "august")
    private double august;
    @Basic(optional = false)
    
    @Column(name = "september")
    private double september;
    @Basic(optional = false)
    
    @Column(name = "october")
    private double october;
    @Basic(optional = false)
    
    @Column(name = "november")
    private double november;
    @Basic(optional = false)
    
    @Column(name = "december")
    private double december;
    @Basic(optional = false)
    
    @Column(name = "january")
    private double january;
    @Basic(optional = false)
    
    @Column(name = "february")
    private double february;
    @Basic(optional = false)
    
    @Column(name = "march")
    private double march;
    @Basic(optional = false)
    
    @Column(name = "april")
    private double april;
    @Basic(optional = false)
    
    @Column(name = "tardy_june")
    private double tardyJune;
    @Basic(optional = false)
    
    @Column(name = "tardy_july")
    private double tardyJuly;
    @Basic(optional = false)
    
    @Column(name = "tardy_aug")
    private double tardyAug;
    @Basic(optional = false)
    
    @Column(name = "tardy_sept")
    private double tardySept;
    @Basic(optional = false)
    
    @Column(name = "tardy_oct")
    private double tardyOct;
    @Basic(optional = false)
    
    @Column(name = "tardy_nov")
    private double tardyNov;
    @Basic(optional = false)
    
    @Column(name = "tardy_dec")
    private double tardyDec;
    @Basic(optional = false)
    
    @Column(name = "tardy_jan")
    private double tardyJan;
    @Basic(optional = false)
    
    @Column(name = "tardy_feb")
    private double tardyFeb;
    @Basic(optional = false)
    
    @Column(name = "tardy_march")
    private double tardyMarch;
    
    @JoinColumn(name = "id", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BedEnrollment bedEnrollment;

    public BedEnrollmentAttendanceRecord() {
    }

    public BedEnrollmentAttendanceRecord(Integer id) {
        this.id = id;
    }

    public BedEnrollmentAttendanceRecord(Integer id, double june, double july, double august, double september, double october, double november, double december, double january, double february, double march, double tardyJune, double tardyJuly, double tardyAug, double tardySept, double tardyOct, double tardyNov, double tardyDec, double tardyJan, double tardyFeb, double tardyMarch) {
        this.id = id;
        this.june = june;
        this.july = july;
        this.august = august;
        this.september = september;
        this.october = october;
        this.november = november;
        this.december = december;
        this.january = january;
        this.february = february;
        this.march = march;
        this.tardyJune = tardyJune;
        this.tardyJuly = tardyJuly;
        this.tardyAug = tardyAug;
        this.tardySept = tardySept;
        this.tardyOct = tardyOct;
        this.tardyNov = tardyNov;
        this.tardyDec = tardyDec;
        this.tardyJan = tardyJan;
        this.tardyFeb = tardyFeb;
        this.tardyMarch = tardyMarch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getJune() {
        return june;
    }

    public void setJune(double june) {
        this.june = june;
    }

    public double getJuly() {
        return july;
    }

    public void setJuly(double july) {
        this.july = july;
    }

    public double getAugust() {
        return august;
    }

    public void setAugust(double august) {
        this.august = august;
    }

    public double getSeptember() {
        return september;
    }

    public void setSeptember(double september) {
        this.september = september;
    }

    public double getOctober() {
        return october;
    }

    public void setOctober(double october) {
        this.october = october;
    }

    public double getNovember() {
        return november;
    }

    public void setNovember(double november) {
        this.november = november;
    }

    public double getDecember() {
        return december;
    }

    public void setDecember(double december) {
        this.december = december;
    }

    public double getJanuary() {
        return january;
    }

    public void setJanuary(double january) {
        this.january = january;
    }

    public double getFebruary() {
        return february;
    }

    public void setFebruary(double february) {
        this.february = february;
    }

    public double getMarch() {
        return march;
    }

    public void setMarch(double march) {
        this.march = march;
    }

    public double getTardyJune() {
        return tardyJune;
    }

    public void setTardyJune(double tardyJune) {
        this.tardyJune = tardyJune;
    }

    public double getTardyJuly() {
        return tardyJuly;
    }

    public void setTardyJuly(double tardyJuly) {
        this.tardyJuly = tardyJuly;
    }

    public double getTardyAug() {
        return tardyAug;
    }

    public void setTardyAug(double tardyAug) {
        this.tardyAug = tardyAug;
    }

    public double getTardySept() {
        return tardySept;
    }

    public void setTardySept(double tardySept) {
        this.tardySept = tardySept;
    }

    public double getTardyOct() {
        return tardyOct;
    }

    public void setTardyOct(double tardyOct) {
        this.tardyOct = tardyOct;
    }

    public double getTardyNov() {
        return tardyNov;
    }

    public void setTardyNov(double tardyNov) {
        this.tardyNov = tardyNov;
    }

    public double getTardyDec() {
        return tardyDec;
    }

    public void setTardyDec(double tardyDec) {
        this.tardyDec = tardyDec;
    }

    public double getTardyJan() {
        return tardyJan;
    }

    public void setTardyJan(double tardyJan) {
        this.tardyJan = tardyJan;
    }

    public double getTardyFeb() {
        return tardyFeb;
    }

    public void setTardyFeb(double tardyFeb) {
        this.tardyFeb = tardyFeb;
    }

    public double getTardyMarch() {
        return tardyMarch;
    }

    public void setTardyMarch(double tardyMarch) {
        this.tardyMarch = tardyMarch;
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
        if (!(object instanceof BedEnrollmentAttendanceRecord)) {
            return false;
        }
        BedEnrollmentAttendanceRecord other = (BedEnrollmentAttendanceRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedEnrollmentAttendanceRecord[ id=" + id + " ]";
    }

    public double getApril() {
        return april;
    }

    public void setApril(double april) {
        this.april = april;
    }
    
}
