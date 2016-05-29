/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@Entity
public class BedTeacherLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private BedTeacher teacher;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date logTimeStamp;

    private int logType; //login: 1, Logout: 0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BedTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(BedTeacher teacher) {
        this.teacher = teacher;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public Date getLogTimeStamp() {
        return logTimeStamp;
    }

    public void setLogTimeStamp(Date logTimeStamp) {
        this.logTimeStamp = logTimeStamp;
    }
    
}
