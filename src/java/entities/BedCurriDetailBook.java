/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author John Philip O. Solano
 * johnphilipsolano@gmail.com
 */
@Entity
@Table(name = "bed_curri_detail_book")
@NamedQueries({
    @NamedQuery(name = "BedCurriDetailBook.findAll", query = "SELECT b FROM BedCurriDetailBook b")})
public class BedCurriDetailBook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "school_year")
    private String schoolYear;
    @JoinColumn(name = "curri_detail", referencedColumnName = "ID")
    @ManyToOne
    private BedCurriculumDetail curriDetail;
    @JoinColumn(name = "book_record", referencedColumnName = "id")
    @ManyToOne
    private BedBookRecord bookRecord;

    public BedCurriDetailBook() {
    }

    public BedCurriDetailBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public BedCurriculumDetail getCurriDetail() {
        return curriDetail;
    }

    public void setCurriDetail(BedCurriculumDetail curriDetail) {
        this.curriDetail = curriDetail;
    }

    public BedBookRecord getBookRecord() {
        return bookRecord;
    }

    public void setBookRecord(BedBookRecord bookRecord) {
        this.bookRecord = bookRecord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedCurriDetailBook)) {
            return false;
        }
        BedCurriDetailBook other = (BedCurriDetailBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.BedCurriDetailBook[ id=" + id + " ]";
    }

}
