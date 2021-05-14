package edu.neu.info6205;

/**
 * Simulation
 *
 * @author Joseph Yuanhao Li
 * @date 4/4/21 20:32
 */

import edu.neu.info6205.helper.ConfigParser;
import edu.neu.info6205.model.Measure;
import edu.neu.info6205.model.Residence;
import edu.neu.info6205.model.Person;
import edu.neu.info6205.model.Virus;
import edu.neu.info6205.visualization.Panel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Simulation {
    // The entry of the whole program
    public static void main(String[] args){
        // output data
        List<String> logs = new ArrayList<String>();

        // Initial virus
        Virus virus = Virus.buildByConfig("Virus/Covid-19.properties");
        logs.addAll(ConfigParser.printObject(virus));


        // Initial Person class
        Properties personProps = ConfigParser.parseConfig("Person.properties");
        Person.setActivityRadius(Double.parseDouble(personProps.getProperty("activityRadius")));
        Person.setActivityRate(Double.parseDouble(personProps.getProperty("activityRate")));

        // Initial residence and residents
        Residence residence = Residence.buildByConfig("Residence/Boston.properties");
        residence.setVirus(virus);
        residence.init();
        logs.addAll(ConfigParser.printObject(residence));

        // Initial measure
        Measure measure = Measure.buildByConfig("Measure/Boston.properties");
        logs.addAll(ConfigParser.printObject(measure));


        String head = String.format("%-15s,%-15s,%-15s,%-15s,%-15s,%-15s,%-10s,%-10s", "Days", "Susceptible", "Exposed", "Infected", "Recovered", "Dead", "R", "K");
        logs.add(head);
        System.out.println(head.replace(',', ' '));

        // Draw
        Panel p = new Panel(residence, measure, logs);
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(  1400, 980);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("SEIR Model Simulation - " + virus.getName()  + "/" + residence.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }
}
