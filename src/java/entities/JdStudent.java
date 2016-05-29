/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import controllers.utils.Utils;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "jd_student")
@NamedQueries({
    @NamedQuery(name = "JdStudent.findAll", query = "SELECT j FROM JdStudent j")})
public class JdStudent implements Serializable {

    private static long serialVersionUID = 1L;

    @Column(name = "password")
    private String password;
    @JoinColumn(name = "parent_guardian", referencedColumnName = "id")
    @ManyToOne
    private BedParent parentGuardian;
    @JoinColumn(name = "parent_mother", referencedColumnName = "id")
    @ManyToOne
    private BedParent parentMother;
    @JoinColumn(name = "parent_father", referencedColumnName = "id")
    @ManyToOne
    private BedParent parentFather;
    @Transient
    private String sectionEnrolled;//fill @ find query on studentMain.xhtml

    @Id
    @Basic(optional = false)

    @Column(name = "STDNTID")
    private String stdntid;
    @Basic(optional = false)

    @Column(name = "LASTNAME")
    private String lastname;
    @Basic(optional = false)

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "MIDDLENAME")
    private String middlename;

    @Column(name = "SEX")
    private String sex;
    @Column(name = "BIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "EMAIL_ADD")
    private String emailAdd;

    @Column(name = "CONTACT_NUM")
    private String contactNum;

    @Column(name = "BIRTHPLACE")
    private String birthplace;

    @Column(name = "RELIGION")
    private String religion;

    @Column(name = "TRIBE")
    private String tribe;

    @Column(name = "PASS")
    private String pass;

    @Column(name = "SUFFIX")
    private String suffix;

    @Column(name = "REMARKS")
    private String remarks;

    @Lob
    @Column(name = "address_street")
    private String addressStreetHouseNo;

    @Lob
    @Column(name = "address_barangay")
    private String addressBarangay;

    @Lob
    @Column(name = "address_municipality")
    private String addressMunicipality;

    @Lob
    @Column(name = "address_province")
    private String addressProvince;

    @Column(name = "guardian_relationship")
    private String guardianRelationship;

    @Column(name = "learner_id_no")
    private String learnerIdNo;

    @Column(name = "mother_tongue")
    private String motherTongue;

    public JdStudent() {
        this.stdntid = UUID.randomUUID().toString().substring(0, 10);
    }

    public JdStudent(String stdntid) {
        this.stdntid = stdntid;
    }

    public JdStudent(String stdntid, String lastname, String firstname) {
        this.stdntid = stdntid;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getStdntid() {
        return stdntid;
    }

    public void setStdntid(String stdntid) {
        this.stdntid = stdntid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSex() {
        if (sex == null) {
            sex = "M";
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getTribe() {
        return tribe;
    }

    public void setTribe(String tribe) {
        this.tribe = tribe;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getStdntid() != null ? getStdntid().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof JdStudent)) {
            return false;
        }
        JdStudent other = (JdStudent) object;
        if ((this.getStdntid() == null && other.getStdntid() != null) || (this.getStdntid() != null && !this.stdntid.equals(other.stdntid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.JdStudent[ stdntid=" + getStdntid() + " ]";
    }

    public String getFullName() {
        return "" + getLastname() + ", " + getFirstname() + " " + getMiddlename();
    }

    private static transient DecimalFormat formatter = new DecimalFormat("##.#");

    public double getAge() {
        double age = Utils.getAge(birthdate, new java.util.Date());
        return Double.parseDouble(formatter.format(age));
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the addressStreetHouseNo
     */
    public String getAddressStreetHouseNo() {
        return addressStreetHouseNo;
    }

    /**
     * @param addressStreetHouseNo the addressStreetHouseNo to set
     */
    public void setAddressStreetHouseNo(String addressStreetHouseNo) {
        this.addressStreetHouseNo = addressStreetHouseNo;
    }

    /**
     * @return the addressBarangay
     */
    public String getAddressBarangay() {
        return addressBarangay;
    }

    /**
     * @param addressBarangay the addressBarangay to set
     */
    public void setAddressBarangay(String addressBarangay) {
        this.addressBarangay = addressBarangay;
    }

    /**
     * @return the addressMunicipality
     */
    public String getAddressMunicipality() {
        return addressMunicipality;
    }

    /**
     * @param addressMunicipality the addressMunicipality to set
     */
    public void setAddressMunicipality(String addressMunicipality) {
        this.addressMunicipality = addressMunicipality;
    }

    /**
     * @return the addressProvince
     */
    public String getAddressProvince() {
        return addressProvince;
    }

    /**
     * @param addressProvince the addressProvince to set
     */
    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    /**
     * @return the guardianRelationship
     */
    public String getGuardianRelationship() {
        return guardianRelationship;
    }

    /**
     * @param guardianRelationship the guardianRelationship to set
     */
    public void setGuardianRelationship(String guardianRelationship) {
        this.guardianRelationship = guardianRelationship;
    }

    /**
     * @return the learnerIdNo
     */
    public String getLearnerIdNo() {
        if (learnerIdNo == null) {
            return stdntid;
        }
        return learnerIdNo;
    }

    /**
     * @param learnerIdNo the learnerIdNo to set
     */
    public void setLearnerIdNo(String learnerIdNo) {
        this.learnerIdNo = learnerIdNo;
    }

    /**
     * @return the motherTongue
     */
    public String getMotherTongue() {
        return motherTongue;
    }

    /**
     * @param motherTongue the motherTongue to set
     */
    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BedParent getParentGuardian() {
        return parentGuardian;
    }

    public void setParentGuardian(BedParent parentGuardian) {
        this.parentGuardian = parentGuardian;
        interpretGuardianRelationship();

    }

    public BedParent getParentMother() {
        return parentMother;
    }

    public void setParentMother(BedParent parentMother) {
        this.parentMother = parentMother;
        interpretGuardianRelationship();
    }

    public BedParent getParentFather() {
        return parentFather;
    }

    public void setParentFather(BedParent parentFather) {
        this.parentFather = parentFather;
        interpretGuardianRelationship();
    }

    public void interpretGuardianRelationship() {
        if (parentGuardian == null) {
            guardianRelationship = "n/a";
            return;
        }
        boolean father = this.parentGuardian.equals(this.parentFather);
        boolean mother = this.parentMother.equals(this.parentGuardian);
        if (father) {
            guardianRelationship = "Father";
        } else if (mother) {
            guardianRelationship = "Mother";
        } else {
            guardianRelationship = "n/a";
        }

    }

    public String getFullAddress() {
        return getAddressStreetHouseNo() + ", " + getAddressBarangay() + ", " + getAddressMunicipality() + ", " + getAddressProvince();
    }

    /**
     * @return the sectionEnrolled
     */
    public String getSectionEnrolled() {
        return sectionEnrolled;
    }

    /**
     * @param sectionEnrolled the sectionEnrolled to set
     */
    public void setSectionEnrolled(String sectionEnrolled) {
        this.sectionEnrolled = sectionEnrolled;
    }

}
