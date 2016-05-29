/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import entities.BedTeacher;
import entities.services.BedTeacherService;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 *
 */
public class SourceF1001 implements JRDataSource {

    private int count;

    private BedTeacher current;
    private final Iterator<BedTeacher> iterator;

      public SourceF1001(List<BedTeacher> activeTeachers) {
        this.iterator = activeTeachers.iterator();
    }

    @Override
    public boolean next() throws JRException {
        boolean hasNext = this.iterator.hasNext();
        if (hasNext) {
            current = this.iterator.next();
        }
        count++;
        return hasNext;
    }

    //FIELD NAMES: no , id, name, pass
    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        String n = jrf.getName();
        if (n.equals("no")) {
            return count + "";
        }

        if (n.equals("id")) {
            return current.getTeacherId();
        }

        if (n.equals("name")) {
            return current.getLastName() + ", " + current.getFirstName() + " " + current.getMiddleName();
        }

        if (n.equals("pass")) {
            String password = current.getPassword();

            if (password.length() <= BedTeacherService.TEACHER_DEFAULT_PASS_LENGTH) {
                return "!" + password;
            } else {
                return "*restricted";
            }
        }

        return "%error%";
    }

}
