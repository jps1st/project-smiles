 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.services.BedCurriculumDetailService;
import java.io.Serializable;
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

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_curriculum_detail")
@NamedQueries({
    @NamedQuery(name = "BedCurriculumDetail.findAll", query = "SELECT j FROM BedCurriculumDetail j")})
public class BedCurriculumDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CR_UNITS")
    private Double crUnits;
    @Column(name = "YR_LEVEL")
    private Integer yrLevel;
    @Basic(optional = false)

    @Column(name = "DISPLAY_ORDER")
    private double displayOrder;
    @JoinColumn(name = "CURRCODE", referencedColumnName = "CURRICODE")
    @ManyToOne
    private BedCurriculum currcode;
    @JoinColumn(name = "SUBJCODE", referencedColumnName = "id")
    @ManyToOne
    private BedSubject subjcode;

    public BedCurriculumDetail() {
    }

    public BedCurriculumDetail(Integer id) {
        this.id = id;
    }

    public BedCurriculumDetail(Integer id, double displayOrder) {
        this.id = id;
        this.displayOrder = displayOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCrUnits() {
        return crUnits;
    }

    public void setCrUnits(Double crUnits) {
        this.crUnits = crUnits;
    }

    public Integer getYrLevel() {
        return yrLevel;
    }

    public void setYrLevel(Integer yrLevel) {
        this.yrLevel = yrLevel;
    }

    public double getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(double displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BedCurriculum getCurrcode() {
        return currcode;
    }

    public void setCurrcode(BedCurriculum currcode) {
        this.currcode = currcode;
    }

    public BedSubject getSubjcode() {
        return subjcode;
    }

    public void setSubjcode(BedSubject subjcode) {
        this.subjcode = subjcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedCurriculumDetail)) {
            return false;
        }
        BedCurriculumDetail other = (BedCurriculumDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getSubjcode().getDisplayDesc();
    }

    public boolean isAggregate() {
        return BedCurriculumDetailService.getInstance().hasComponents(this);
    }

}
