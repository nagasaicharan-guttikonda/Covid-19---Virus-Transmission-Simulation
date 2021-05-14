package edu.neu.info6205;

/**
 * Main
 *
 * @author Joseph Yuanhao Li
 * @date 4/4/21 20:32
 */

import edu.neu.info6205.helper.CSVUtil;
import edu.neu.info6205.helper.ConfigParser;
import edu.neu.info6205.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Main {
    // The entry of the whole program
    public static void main(String[] args){
        // output data
        List<String> logs = new ArrayList<String>();

        Properties fileProps = ConfigParser.parseConfig("file.properties");

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

        Timeline timeline = new Timeline(residence, measure);

        Thread timelineThread = new Thread(timeline);

        // set Daemon Thread
        Thread t = new Thread(() -> {
            while (true) {
                timelineThread.run();

                String s = String.format("%-15d,%-15d,%-15d,%-15d,%-15d,%-15d,%-10.4f,%-10.4f", timeline.getDays(), residence.getSusceptible(), residence.getExposed(), residence.getInfected(), residence.getRecovered(), residence.getDead(), timeline.getR(),timeline.getK());
                logs.add(s);
                System.out.println(s.replace(',', ' '));

                if(residence.getExposed() == 0 && residence.getInfected() == 0){
                    System.out.printf("After fighting with Virus for %d days, the pandemic is over and human win!\n", timeline.getDays());

                    String path = fileProps.getProperty("csvDir") + residence.getVirus().getName() + "_" + residence.getName() + "_" + new Date().getTime() + ".csv";
                    if(CSVUtil.exportCsv(new File(path), logs)) System.out.println("Write CSV Success: " + path);
                    break;
                }

//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        t.setDaemon(true);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
