package edu.neu.info6205;

import edu.neu.info6205.helper.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author Joseph Yuanhao Li
 * @date 4/18/21 17:03
 */
public class Helper_Test {
    @Test
    public void testProperties() {
        Properties props = ConfigParser.parseConfig("Virus/Covid-19.properties");

        assertEquals(Double.parseDouble(props.getProperty("R")), 3.8, 0);
        assertEquals(Double.parseDouble(props.getProperty("K")), 0.1, 0);
    }

    @Test
    public void testCSV(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("Days,Susceptible,Exposed,Infected, Removed");
        dataList.add("1,10000,0,10,0");

        Properties fileProps = ConfigParser.parseConfig("file.properties");

        String path = fileProps.getProperty("csvDir") + "java-test.csv";
        boolean isSuccess = CSVUtil.exportCsv(new File(path), dataList);
        assertTrue(isSuccess);
    }

    @Test
    public void testPoint(){
        Point p = new Point(1.5, 2.0);
        assertEquals(p.getX(), 1.5, 0);
        assertEquals(p.getY(), 2.0, 0);
    }
}
