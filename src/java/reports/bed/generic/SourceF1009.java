/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.bed.generic;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
public class SourceF1009 implements JRDataSource {

    private class Data {

        String column;
        String row;
        String data;
        String data1;
        String data2;
        
    }

    public SourceF1009(int schoolYear) {//TODO: change this to specify school and school year
    }

    @Override
    public boolean next() throws JRException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
