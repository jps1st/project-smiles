/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@Entity
@Table(name = "bed_book_record")
@NamedQueries({
    @NamedQuery(name = "BedBookRecord.findAll", query = "SELECT b FROM BedBookRecord b")})
public class BedBookRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "school_year_int")
    private Integer schoolYear;
    @Lob
    @Column(name = "book_title")
    private String bookTitle;
    @Lob
    @Column(name = "book_author")
    private String bookAuthor;
    @Column(name = "publication_year")
    private Integer publicationYear;
    @Basic(optional = false)
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "bookRecord")
    private Collection<BedCurriDetailBook> bedCurriDetailBookCollection;

    public BedBookRecord() {
    }

    public BedBookRecord(Integer id) {
        this.id = id;
    }

    public BedBookRecord(Integer id, Date modifiedDate) {
        this.id = id;
        this.modifiedDate = modifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Collection<BedCurriDetailBook> getBedCurriDetailBookCollection() {
        return bedCurriDetailBookCollection;
    }

    public void setBedCurriDetailBookCollection(Collection<BedCurriDetailBook> bedCurriDetailBookCollection) {
        this.bedCurriDetailBookCollection = bedCurriDetailBookCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedBookRecord)) {
            return false;
        }
        BedBookRecord other = (BedBookRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.BedBookRecord[ id=" + id + " ]";
    }

    /**
     * @return the schoolYear
     */
    public Integer getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

}
