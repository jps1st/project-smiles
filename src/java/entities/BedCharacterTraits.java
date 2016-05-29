/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_character_traits")
@NamedQueries({
    @NamedQuery(name = "BedCharacterTraits.findAll", query = "SELECT b FROM BedCharacterTraits b")})
public class BedCharacterTraits implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ct_id")
    private Integer ctId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ct_order")
    private Double ctOrder;
    @Lob
    
    @Column(name = "trait_name")
    private String traitName;
    
    @Column(name = "char_group")
    private String charGroup;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bedCharacterTrait")
    private List<BedEnrollmentCharacterTraits> bedEnrollmentCharacterTraitsList;

    public BedCharacterTraits() {
    }

    public BedCharacterTraits(Integer ctId) {
        this.ctId = ctId;
    }

    public Integer getCtId() {
        return ctId;
    }

    public void setCtId(Integer ctId) {
        this.ctId = ctId;
    }

    public Double getCtOrder() {
        return ctOrder;
    }

    public void setCtOrder(Double ctOrder) {
        this.ctOrder = ctOrder;
    }

    public String getTraitName() {
        return traitName;
    }

    public void setTraitName(String traitName) {
        this.traitName = traitName;
    }

    public String getCharGroup() {
        return charGroup;
    }

    public void setCharGroup(String charGroup) {
        this.charGroup = charGroup;
    }

    public List<BedEnrollmentCharacterTraits> getBedEnrollmentCharacterTraitsList() {
        return bedEnrollmentCharacterTraitsList;
    }

    public void setBedEnrollmentCharacterTraitsList(List<BedEnrollmentCharacterTraits> bedEnrollmentCharacterTraitsList) {
        this.bedEnrollmentCharacterTraitsList = bedEnrollmentCharacterTraitsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctId != null ? ctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedCharacterTraits)) {
            return false;
        }
        BedCharacterTraits other = (BedCharacterTraits) object;
        if ((this.ctId == null && other.ctId != null) || (this.ctId != null && !this.ctId.equals(other.ctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedCharacterTraits[ ctId=" + ctId + " ]";
    }
    
}
