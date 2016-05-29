package entities.services;

import entities.BedTeacher;
import entities.BedTeacherLog;
import java.util.Date;

public final class BedTeacherLogService extends NewAbstractEntityService<BedTeacherLog> {

    private static final long serialVersionUID = 1L;

    private BedTeacherLogService() {
    }

    public void teacherLoggedIn(BedTeacher teacher) {
        BedTeacherLog log = new BedTeacherLog();
        log.setTeacher(teacher);
        log.setLogType(1);
        log.setLogTimeStamp(new Date());
        insert(log);
    }

    public void teacherLoggedOut(BedTeacher teacher) {
        BedTeacherLog log = new BedTeacherLog();
        log.setTeacher(teacher);
        log.setLogType(0);
        log.setLogTimeStamp(new Date());
        insert(log);
    }

    @Override
    public Class getEntityClass() {
        return BedTeacherLog.class;
    }

    private static BedTeacherLogService instance;

    public static BedTeacherLogService getInstance() {
        if (instance == null) {
            instance = new BedTeacherLogService();
        }
        return instance;
    }

}
