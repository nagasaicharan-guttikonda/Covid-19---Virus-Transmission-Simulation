package edu.neu.info6205;

import edu.neu.info6205.helper.ConfigParser;
import edu.neu.info6205.model.Measure;
import edu.neu.info6205.model.Person;
import edu.neu.info6205.model.Residence;
import edu.neu.info6205.model.Virus;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Joseph Yuanhao Li
 * @date 4/18/21 17:25
 */
public class Model_Test {

    @Test
    public void testPerson(){
        Properties personProps = ConfigParser.parseConfig("Person.properties");
        Person.setActivityRadius(Double.parseDouble(personProps.getProperty("activityRadius")));
        Person.setActivityRate(Double.parseDouble(personProps.getProperty("activityRate")));

        assertEquals(Person.getActivityRadius(), 2000, 0);
        assertEquals(Person.getActivityRate(), 0.9, 0);
    }

    @Test
    public void testVirus(){
        Virus virus = Virus.buildByConfig("Virus/Covid-19.properties");
        assertEquals(virus.getInfectiousRadius(), 200, 0);
    }

    @Test
    public void testMeasure(){
        Measure measure = Measure.buildByConfig("Measure/Boston.properties");
        assertEquals(measure.getBarrierRate(), 0.5, 0);
    }

    @Test
    public void testResidence(){
        Virus virus = Virus.buildByConfig("Virus/Covid-19.properties");

        // Initial Person class
        Properties personProps = ConfigParser.parseConfig("Person.properties");
        Person.setActivityRadius(Double.parseDouble(personProps.getProperty("activityRadius")));
        Person.setActivityRate(Double.parseDouble(personProps.getProperty("activityRate")));

        // Initial residence and residents
        Residence residence = Residence.buildByConfig("Residence/Boston.properties");
        residence.setVirus(virus);
        residence.init();

        assertEquals(residence.getResidents().length, residence.getPopulation(), 0);
        assertEquals(residence.getResidents().length, residence.getSusceptible() + residence.getExposed() + residence.getInfected() + residence.getRemoved(), 0);
    }
}
