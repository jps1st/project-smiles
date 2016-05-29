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
@Table(name = "bed_loading")
@NamedQueries({
    @NamedQuery(name = "BedLoading.findAll", query = "SELECT b FROM BedLoading b")})
public class BedLoading implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "TEACHER", referencedColumnName = "ID")
    @ManyToOne
    private BedTeacher teacher;
    @JoinColumn(name = "SECTION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private BedSections section;
    @JoinColumn(name = "SUBJECT", referencedColumnName = "ID")
    @ManyToOne
    private BedCurriculumDetail subject;

    public BedLoading() {
    }

    public BedLoading(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BedTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(BedTeacher teacher) {
        this.teacher = teacher;
    }

    public BedSections getSection() {
        return section;
    }

    public void setSection(BedSections section) {
        this.section = section;
    }

    public BedCurriculumDetail getSubject() {
        return subject;
    }

    public void setSubject(BedCurriculumDetail subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedLoading)) {
            return false;
        }
        BedLoading other = (BedLoading) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BedLoading[ id=" + id + " ]";
    }
    
}
