/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.UUID;
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
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@Entity
@Table(name = "bed_parent")
@NamedQueries({
    @NamedQuery(name = "BedParent.findAll", query = "SELECT b FROM BedParent b")})
public class BedParent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
   
    
    @Column(name = "parent_id")
    private String parentId;
    @Basic(optional = false)
   
    
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
   
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "contact_num")
    private String contactNum;
    @Column(name = "suffix")
    private String suffix;
    
    @Column(name = "password")
    private String password;

    public BedParent() {
    }

    public BedParent(Integer id) {
        this.id = id;
    }

    public BedParent(Integer id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BedParent)) {
            return false;
        }
        BedParent other = (BedParent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        if (parentId == null || parentId.trim().isEmpty()) {
            parentId = UUID.randomUUID().toString().substring(0, 5);
        }
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getFullName() {
        return lastName + ", " + firstName + " " + (middleName == null ? "" : middleName) + " " + (suffix == null ? "" : suffix);
    }

}
