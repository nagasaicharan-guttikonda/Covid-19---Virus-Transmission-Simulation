package edu.neu.info6205.model;

import edu.neu.info6205.helper.ConfigParser;

/**
 * Virus
 *
 * @author Joseph Yuanhao Li
 * @date 4/1/21 20:32
 */

public class Virus {
    private String name; // Virus's name

    private double R; // Basic reproduction number
    private double superSpreaderRate; // superSpreaderRate


    private double infectiousRadius; // infectious radius (/m)
    private double infectiousRate = 0.27; // represents the probability of the disease being transmitted between a susceptible and an infectious individual (0.27 according to literature)

    private double recoveryRate; //  the rate which infected people can recover
    private double incidenceRate; // the rate at which an exposed person becomes infective (0.2 according to literature).

    private double latentPeriod; // Average time of an individual is pre-infectious
    private double infectiousPeriod; // Average time of an individual is infectious

    public Virus(){}

    public static Virus buildByConfig(String filePath){
        return (Virus) ConfigParser.configToClass(filePath, Virus.class);
    }

    /* Setter and Getter */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public double getSuperSpreaderRate() {
        return superSpreaderRate;
    }

    public void setSuperSpreaderRate(double superSpreaderRate) {
        this.superSpreaderRate = superSpreaderRate;
    }

    public double getInfectiousRadius() {
        return infectiousRadius;
    }

    public void setInfectiousRadius(double infectiousRadius) {
        this.infectiousRadius = infectiousRadius;
    }

    public double getInfectiousRate() {
        return infectiousRate;
    }

    public void setInfectiousRate(double infectiousRate) {
        this.infectiousRate = infectiousRate;
    }

    public double getRecoveryRate() {
        return recoveryRate;
    }

    public void setRecoveryRate(double recoveryRate) {
        this.recoveryRate = recoveryRate;
    }

    public double getIncidenceRate() {
        return incidenceRate;
    }

    public void setIncidenceRate(double incidenceRate) {
        this.incidenceRate = incidenceRate;
    }

    public double getLatentPeriod() {
        return latentPeriod;
    }

    public void setLatentPeriod(double latentPeriod) {
        this.latentPeriod = latentPeriod;
    }

    public double getInfectiousPeriod() {
        return infectiousPeriod;
    }

    public void setInfectiousPeriod(double infectiousPeriod) {
        this.infectiousPeriod = infectiousPeriod;
    }

    /* Public Methods */
    // latentPeriod + infectiousPeriod
    public double getRecoveryPeriod() {
        return latentPeriod + infectiousPeriod;
    }
}
