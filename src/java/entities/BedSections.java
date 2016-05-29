/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import controllers.utils.Utils;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author John Philip
 */
@Entity
@Table(name = "bed_sections")
@NamedQueries({
    @NamedQuery(name = "BedSections.findAll", query = "SELECT b FROM BedSections b")})
public class BedSections implements Serializable {

    @JoinColumn(name = "ADVISER", referencedColumnName = "ID")
    @ManyToOne
    private BedTeacher adviser;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;
    @Lob

    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "YEAR_LVL")
    private Integer year;

    @Column(name = "ROOM")
    private String room;

    @Column(name = "school_year_int")
    private int sy;

    @Column(name = "population")
    private int population = -1;

    @Column(name = "CHAR_TRAIT_GROUP")
    private String charTraitGroup = "a";

    @JoinColumn(name = "CURRICULUM", referencedColumnName = "CURRICODE")
    @ManyToOne(optional = false)
    private BedCurriculum curriculum;
    @OneToMany(mappedBy = "section")
    private List<BedSectionHiddenSubject> bedSectionHiddenSubjects;

    public BedSections() {
    }

    public BedSections(Integer id) {
        this.id = id;
    }

    public BedSections(Integer id, String name, String charTraitGroup) {
        this.id = id;
        this.name = name;
        this.charTraitGroup = charTraitGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getSy() {
        return sy;
    }

    public void setSy(int sy) {
        this.sy = sy;
    }

    public String getCharTraitGroup() {
        return charTraitGroup;
    }

    public void setCharTraitGroup(String charTraitGroup) {
        this.charTraitGroup = charTraitGroup;
    }

    public BedCurriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(BedCurriculum curriculum) {
        this.curriculum = curriculum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedSections)) {
            return false;
        }
        BedSections other = (BedSections) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedSections[ id=" + id + " ]";
    }

    public BedTeacher getAdviser() {
        return adviser;
    }

    public void setAdviser(BedTeacher adviser) {
        this.adviser = adviser;
    }

    /**
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    public String getAvatarFileName() {
        if (id == null) {
            return null;
        }
        return Utils.getMD5EncryptedPassword("BedSections"+id);
    }
}
