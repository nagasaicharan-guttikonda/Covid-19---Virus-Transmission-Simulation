package edu.neu.info6205.model;

/**
 * City
 *
 * @author Joseph Yuanhao Li
 * @date 4/4/21 20:32
 */

import edu.neu.info6205.helper.ConfigParser;
import edu.neu.info6205.helper.PersonStatus;
import edu.neu.info6205.helper.Point;

import java.util.*;
import java.util.List;

public class Residence {
    private String name; // residence's name

    private Virus virus; // the virus spreading in the residence

    private long population; // the total population.
    private double density; // population density in residence (/km2)

    private double radius; // the radius of the residence (m)

    private Person[] residents; // Person objects' array

    private long susceptible; //  the stock of susceptible population
    private long exposed;   // the stock of exposed population(Incubation period patient)
    private long infected; // the stock of infected population
    private long removed;  // the stock of removed population ( = dead + recovered )

    private long dead; // total number of dead residents ( = removed - recovered)

    /* Drawing Info */
    private double drawRadius; // radius of the residence when drawing

    // Coordinates of the residence center (for drawing)
    private double drawX;
    private double drawY;

    private double scale; // drawRadius / radius

    private static final Random random = new Random(31);

    public Residence(){}

    public static Residence buildByConfig(String filePath){
        return (Residence) ConfigParser.configToClass(filePath, Residence.class);
    }

    public void init(){
        radius = Math.sqrt(population / density) * 1000; // km -> m
        scale = drawRadius / radius;

        Person.setVirus(virus);

        this.generateResidents();
        this.generateInfected();
    }

    /* Getter and Setter */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Virus getVirus() {
        return virus;
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public void setDensity(long density) {
        this.density = density;
    }

    public double getRadius() {
        return radius;
    }

    public long getSusceptible() {
        return susceptible;
    }

    public long getExposed() {
        return exposed;
    }

    public long getInfected() {
        return infected;
    }

    public void setInfected(long infected) {
        this.infected = infected;
    }

    public long getRemoved() {
        return removed;
    }

    public long getDead() {
        return dead;
    }

    public void setDead(long dead) {
        this.dead = dead;
    }

    public long getRecovered() {
        return removed - dead;
    }

    // reset S E I R population
    public void resetSEIR(){
        susceptible = 0;
        exposed = 0;
        infected = 0;
        removed = 0;

        dead = 0;
    }

    // set S E I R population
    public void setSEIR(long susceptible, long exposed, long infected, long removed, long dead){
        this.susceptible = susceptible;
        this.exposed = exposed;
        this.infected = infected;
        this.removed = removed;

        this.dead = dead;
    }

    public Person[] getResidents() {
        return residents;
    }

    public double getDrawRadius() {
        return drawRadius;
    }

    public void setDrawRadius(double drawRadius) {
        this.drawRadius = drawRadius;
    }

    public double getDrawX() {
        return drawX;
    }

    public void setDrawX(double drawX) {
        this.drawX = drawX;
    }

    public double getDrawY() {
        return drawY;
    }

    public void setDrawY(double drawY) {
        this.drawY = drawY;
    }

    /* Public Methods */
    // check if the location is out of the city area
    public boolean ifOutOfArea(Person p){
        if(Math.abs(p.getX()) > radius || Math.abs(p.getY()) > radius) return true;
        return false;
    }

    // calculate the status of city and residents in next day
//    public void update(){
//        Arrays.sort(residents, Person.xComparator);
//        spreadVirus();
//
//        Arrays.sort(residents, Person.yComparator);
//        spreadVirus();
//
//        resetSEIR();
//
//        // count S E I R population
//        for(Person p : residents){
//            p.update();
//
//            if(ifOutOfArea(p)) p.setLocation(randomPoint());
//
//            switch (p.getStatus()) {
//                case Susceptible: {  // susceptible
//                    susceptible++;
//                    break;
//                }
//                case Exposed: { // exposed
//                    exposed++;
//                    break;
//                }
//
//                case Infected: { // infected
//                    infected++;
//                    break;
//                }
//                case Removed : { // Recovered or dead
//                    removed++;
//                    break;
//                }
//                default:{
//                    System.out.println("Draw person: Wrong Status!");
//                }
//            }
//        }
//
//        System.out.printf("update finish\n");
//    }
//
//    private void spreadVirus(){
//        for (int i = 0; i < residents.length; i++){
//            Person p1 = residents[i];
//
//            if(p1.getStatus() == PersonStatus.Removed) continue;
//
//            for(int j = i + 1; j < Math.min(residents.length, i + 10); j++){
//                Person p2 = residents[j];
//
//                if(p2.getX() - p1.getX() > virus.getInfectiousRadius()) continue;
//
//                if(p2.getStatus() == PersonStatus.Removed) continue;
//
//                if(p1.isContagious() ^ p2.isContagious()){
//                    if(Person.distance(p1, p2) <= virus.getInfectiousRadius()){
//                        if(p1.isContagious()) p2.setStatus(PersonStatus.Exposed);
//                        else p1.setStatus(PersonStatus.Exposed);
//                    }
//                }
//            }
//        }
//    }

    /* Private Methods */
    // generate residents (total number is population)
    private void generateResidents(){
        residents = new Person[(int) population];
        for(int i = 0; i < population; i++){
            residents[i] = personFactory();
        }
    }

    //generate initial infected people
    private void generateInfected(){
        for(int i = 0; i < infected; i++){
            residents[i].setStatus(PersonStatus.Infected);
        }

        susceptible = population - infected;
    }

    // nextGaussian() 99.74% in [-3,3]
    private Person personFactory(){
        return new Person(randomPoint());
    }

    public Point randomPoint(){
        return new Point(radius / 3.0 * random.nextGaussian(), radius / 3.0 * random.nextGaussian());
    }

    // convert Coordinates from reference system of city to reference system of drawing area
    public double mappingToDrawing(double x){
        return x * scale;
    }
}
