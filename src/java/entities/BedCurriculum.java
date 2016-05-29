/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_curriculum")
@NamedQueries({
    @NamedQuery(name = "BedCurriculum.findAll", query = "SELECT j FROM BedCurriculum j")})
public class BedCurriculum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CURRICODE")
    private Integer curricode;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "WORD_FOR_GRADE")
    private String wordForGrade;

    @Column(name = "effective_sy")
    private int effectiveSY;

    public BedCurriculum() {
    }

    public BedCurriculum(Integer curricode) {
        this.curricode = curricode;
    }

    public Integer getCurricode() {
        return curricode;
    }

    public void setCurricode(Integer curricode) {
        this.curricode = curricode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (curricode != null ? curricode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedCurriculum)) {
            return false;
        }
        BedCurriculum other = (BedCurriculum) object;
        if ((this.curricode == null && other.curricode != null) || (this.curricode != null && !this.curricode.equals(other.curricode))) {
            return false;
        }
        return true;
    }

    public String getStringRepresentation() {
        return toString();
    }

    @Override
    public String toString() {
        return getShortName() + " ";
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the effectiveSY
     */
    public int getEffectiveSY() {
        return effectiveSY;
    }

    /**
     * @param effectiveSY the effectiveSY to set
     */
    public void setEffectiveSY(int effectiveSY) {
        this.effectiveSY = effectiveSY;
    }

    /**
     * @return the wordForGrade
     */
    public String getWordForGrade() {
        return wordForGrade;
    }

    /**
     * @param wordForGrade the wordForGrade to set
     */
    public void setWordForGrade(String wordForGrade) {
        this.wordForGrade = wordForGrade;
    }
}
