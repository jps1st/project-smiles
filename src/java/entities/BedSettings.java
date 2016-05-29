/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "bed_settings")
@NamedQueries({
    @NamedQuery(name = "BedSettings.findAll", query = "SELECT b FROM BedSettings b")})
public class BedSettings implements Serializable {

    private static long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
   
    @Lob
    @Column(name = "school_principal_name")
    private String schoolPrincipalName;
    @Basic(optional = false)
   
    @Column(name = "june_days")
    private int juneDays;
    @Basic(optional = false)
   
    @Column(name = "july_days")
    private int julyDays;
    @Basic(optional = false)
   
    @Column(name = "aug_days")
    private int augDays;
    @Basic(optional = false)
   
    @Column(name = "sept_days")
    private int septDays;
    @Basic(optional = false)
   
    @Column(name = "oct_days")
    private int octDays;
    @Basic(optional = false)
   
    @Column(name = "nov_days")
    private int novDays;
    @Basic(optional = false)
   
    @Column(name = "dec_days")
    private int decDays;
    @Basic(optional = false)
   
    @Column(name = "jan_days")
    private int janDays;
    @Basic(optional = false)
   
    @Column(name = "feb_days")
    private int febDays;
    @Basic(optional = false)
   
    @Column(name = "march_days")
    private int marchDays;
    
    @Column(name = "april_days")
    private int aprilDays;
    
    @Column(name = "may_days")
    private int mayDays;
    
    @Column(name = "active_year")
    private boolean activeYear;

    @Column(name = "sy_start")
    @Temporal(TemporalType.DATE)
    private Date syStart;
    @Column(name = "sy_end")
    @Temporal(TemporalType.DATE)
    private Date syEnd;
    @Lob
    @Column(name = "division_representative")
    private String divisionRepresentative;
    @Lob
    @Column(name = "schools_division_superintendent")
    private String schoolsDivisionSuperintendent;

    @Column(name = "school_id")
    private String schoolId;

    @Lob
    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "division")
    private String division;

    @Column(name = "district")
    private String district;
    
    @Column(name = "region")
    private String region;

    public BedSettings() {
    }

    public BedSettings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolPrincipalName() {
        return schoolPrincipalName;
    }

    public void setSchoolPrincipalName(String schoolPrincipalName) {
        this.schoolPrincipalName = schoolPrincipalName;
    }

    public int getJuneDays() {
        return juneDays;
    }

    public void setJuneDays(int juneDays) {
        this.juneDays = juneDays;
    }

    public int getJulyDays() {
        return julyDays;
    }

    public void setJulyDays(int julyDays) {
        this.julyDays = julyDays;
    }

    public int getAugDays() {
        return augDays;
    }

    public void setAugDays(int augDays) {
        this.augDays = augDays;
    }

    public int getSeptDays() {
        return septDays;
    }

    public void setSeptDays(int septDays) {
        this.septDays = septDays;
    }

    public int getOctDays() {
        return octDays;
    }

    public void setOctDays(int octDays) {
        this.octDays = octDays;
    }

    public int getNovDays() {
        return novDays;
    }

    public void setNovDays(int novDays) {
        this.novDays = novDays;
    }

    public int getDecDays() {
        return decDays;
    }

    public void setDecDays(int decDays) {
        this.decDays = decDays;
    }

    public int getJanDays() {
        return janDays;
    }

    public void setJanDays(int janDays) {
        this.janDays = janDays;
    }

    public int getFebDays() {
        return febDays;
    }

    public void setFebDays(int febDays) {
        this.febDays = febDays;
    }

    public int getMarchDays() {
        return marchDays;
    }

    public void setMarchDays(int marchDays) {
        this.marchDays = marchDays;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof BedSettings)) {
            return false;
        }
        BedSettings other = (BedSettings) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedSettings[ id=" + getId() + " ]";
    }

    /**
     * @return the activeYear
     */
    public boolean isActiveYear() {
        return activeYear;
    }

    /**
     * @param activeYear the activeYear to set
     */
    public void setActiveYear(boolean activeYear) {
        this.activeYear = activeYear;
    }

    /**
     * @return the syStart
     */
    public Date getSyStart() {
        return syStart;
    }

    /**
     * @param syStart the syStart to set
     */
    public void setSyStart(Date syStart) {
        this.syStart = syStart;
    }

    /**
     * @return the syEnd
     */
    public Date getSyEnd() {
        return syEnd;
    }

    /**
     * @param syEnd the syEnd to set
     */
    public void setSyEnd(Date syEnd) {
        this.syEnd = syEnd;
    }

    /**
     * @return the divisionRepresentative
     */
    public String getDivisionRepresentative() {
        return divisionRepresentative;
    }

    /**
     * @param divisionRepresentative the divisionRepresentative to set
     */
    public void setDivisionRepresentative(String divisionRepresentative) {
        this.divisionRepresentative = divisionRepresentative;
    }

    /**
     * @return the schoolsDivisionSuperintendent
     */
    public String getSchoolsDivisionSuperintendent() {
        return schoolsDivisionSuperintendent;
    }

    /**
     * @param schoolsDivisionSuperintendent the schoolsDivisionSuperintendent to
     * set
     */
    public void setSchoolsDivisionSuperintendent(String schoolsDivisionSuperintendent) {
        this.schoolsDivisionSuperintendent = schoolsDivisionSuperintendent;
    }

    /**
     * @return the schoolId
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    public int getAprilDays() {
        return aprilDays;
    }

    public void setAprilDays(int aprilDays) {
        this.aprilDays = aprilDays;
    }

    public int getMayDays() {
        return mayDays;
    }

    public void setMayDays(int mayDays) {
        this.mayDays = mayDays;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
