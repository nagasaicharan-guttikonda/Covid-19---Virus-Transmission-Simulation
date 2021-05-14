package edu.neu.info6205;

import edu.neu.info6205.helper.ConfigParser;
import edu.neu.info6205.model.*;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Joseph Yuanhao Li
 * @date 4/18/21 17:38
 */
public class Timeline_Test {
    @Test
    public void testTimeline(){
        // Initial virus
        Virus virus = Virus.buildByConfig("Virus/Covid-19.properties");

        // Initial Person class
        Properties personProps = ConfigParser.parseConfig("Person.properties");
        Person.setActivityRadius(Double.parseDouble(personProps.getProperty("activityRadius")));
        Person.setActivityRate(Double.parseDouble(personProps.getProperty("activityRate")));

        // Initial residence and residents
        Residence residence = Residence.buildByConfig("Residence/Boston.properties");
        residence.setVirus(virus);
        residence.init();

        // Initial measure
        Measure measure = Measure.buildByConfig("Measure/Boston.properties");

        Timeline timeline = new Timeline(residence, measure);

        assertEquals(residence.getResidents().length, residence.getSusceptible() + residence.getExposed() + residence.getInfected() + residence.getRemoved(), 0);

        for (int i = 0; i < 2; i++){
            assertEquals(timeline.getDays(), i, 0);
            timeline.run();
        }

        assertEquals(residence.getResidents().length, residence.getSusceptible() + residence.getExposed() + residence.getInfected() + residence.getRemoved(), 0);
    }
}
