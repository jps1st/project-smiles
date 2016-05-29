/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_enrollment_character_traits")
@NamedQueries({
    @NamedQuery(name = "BedEnrollmentCharacterTraits.findAll", query = "SELECT b FROM BedEnrollmentCharacterTraits b")})
public class BedEnrollmentCharacterTraits implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "p1_value")
    private String p1Value;
    @Column(name = "p2_value")
    private String p2Value;
    @Column(name = "p3_value")
    private String p3Value;
    @Column(name = "p4_value")
    private String p4Value;
    @Column(name = "final_value")
    private String finalValue;
    @JoinColumn(name = "bed_enrollment", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private BedEnrollment bedEnrollment;
    @JoinColumn(name = "bed_character_trait", referencedColumnName = "ct_id")
    @ManyToOne(optional = false)
    private BedCharacterTraits bedCharacterTrait;

    public BedEnrollmentCharacterTraits() {
    }

    public BedEnrollmentCharacterTraits(Integer id) {
        this.id = id;
    }

    public BedEnrollmentCharacterTraits(Integer id, String p1Value, String p2Value, String p3Value, String p4Value, String finalValue) {
        this.id = id;
        this.p1Value = p1Value;
        this.p2Value = p2Value;
        this.p3Value = p3Value;
        this.p4Value = p4Value;
        this.finalValue = finalValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getP1Value() {
        return p1Value;
    }

    public void setP1Value(String p1Value) {
        this.p1Value = p1Value;
    }

    public String getP2Value() {
        return p2Value;
    }

    public void setP2Value(String p2Value) {
        this.p2Value = p2Value;
    }

    public String getP3Value() {
        return p3Value;
    }

    public void setP3Value(String p3Value) {
        this.p3Value = p3Value;
    }

    public String getP4Value() {
        return p4Value;
    }

    public void setP4Value(String p4Value) {
        this.p4Value = p4Value;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }

    public BedEnrollment getBedEnrollment() {
        return bedEnrollment;
    }

    public void setBedEnrollment(BedEnrollment bedEnrollment) {
        this.bedEnrollment = bedEnrollment;
    }

    public BedCharacterTraits getBedCharacterTrait() {
        return bedCharacterTrait;
    }

    public void setBedCharacterTrait(BedCharacterTraits bedCharacterTrait) {
        this.bedCharacterTrait = bedCharacterTrait;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedEnrollmentCharacterTraits)) {
            return false;
        }
        BedEnrollmentCharacterTraits other = (BedEnrollmentCharacterTraits) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedEnrollmentCharacterTraits[ id=" + id + " ]";
    }
    
}
