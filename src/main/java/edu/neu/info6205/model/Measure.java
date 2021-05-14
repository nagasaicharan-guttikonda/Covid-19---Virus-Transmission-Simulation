package edu.neu.info6205.model;

import edu.neu.info6205.helper.ConfigParser;

/**
 * Measures to respond to the epidemic
 *
 * @author Joseph Yuanhao Li
 * @date 4/11/21 20:34
 */
public class Measure {
    private String name;
    private boolean enable; // turn on or off this measure
    private double barrierRate; // [0,1] reduce people activity rate; effective activity rate = original activity rate * barrier
    private double maskUseRate; // [0,1]
    private double maskEffectiveness; //[0,1]
    private double contactTracking; // [0,1]
    private long vaccine; // Number of vaccine per day
    private double vaccineEffectiveness; // [0,1]
    private long virusTesting; // Number of test per day

    public Measure(){}

    public static Measure buildByConfig(String filePath){
        return (Measure) ConfigParser.configToClass(filePath, Measure.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getBarrierRate() {
        return barrierRate;
    }

    public void setBarrierRate(double barrierRate) {
        this.barrierRate = barrierRate;
    }

    public double getMaskUseRate() {
        return maskUseRate;
    }

    public void setMaskUseRate(double maskUseRate) {
        this.maskUseRate = maskUseRate;
    }

    public double getMaskEffectiveness() {
        return maskEffectiveness;
    }

    public void setMaskEffectiveness(double maskEffectiveness) {
        this.maskEffectiveness = maskEffectiveness;
    }

    public double getContactTracking() {
        return contactTracking;
    }

    public void setContactTracking(double contactTracking) {
        this.contactTracking = contactTracking;
    }

    public long getVaccine() {
        return vaccine;
    }

    public void setVaccine(long vaccine) {
        this.vaccine = vaccine;
    }

    public double getVaccineEffectiveness() {
        return vaccineEffectiveness;
    }

    public void setVaccineEffectiveness(double vaccineEffectiveness) {
        this.vaccineEffectiveness = vaccineEffectiveness;
    }

    public long getVirusTesting() {
        return virusTesting;
    }

    public void setVirusTesting(long virusTesting) {
        this.virusTesting = virusTesting;
    }

    public double getQuarantineRate() {
        return quarantineRate;
    }

    public void setQuarantineRate(double quarantineRate) {
        this.quarantineRate = quarantineRate;
    }

    private double quarantineRate; // The possibility of people who is tracked or tested positive or infected obey quarantine
}
