package edu.neu.info6205.model;

/**
 * Person
 *
 * @author Joseph Yuanhao Li
 * @date 4/1/21 20:32
 */

import edu.neu.info6205.helper.PersonStatus;
import edu.neu.info6205.helper.Point;
import edu.neu.info6205.helper.RandomUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Person implements Comparable<Person> {
    private static Virus virus; // Virus spreading in people

    /* The rate of people go outside everyday */
    static private double activityRate;

    /* The active radius */
    static private double activityRadius;

    private Point location; // person's coordinate

    private PersonStatus status = PersonStatus.Susceptible; // person's status

    private boolean superSpreader = false; // if is superSpreader

    private long duration = 0;  // The duration of current status

    private boolean dead; // if this guy dead

    private List<Person> reproductList; // record people who infected by this guys


    static public double getActivityRadius(){
        return  Person.activityRadius;
    }

    private static final Random random = RandomUtil.random();

    public Person(Point location ){
        this.location = location;
    }

    public Person(Point location, PersonStatus status){
        this.location = location;
        this.status = status;
    }


    /* Setter and Getter */
    public static void setVirus(Virus virus){
        Person.virus = virus;
    }

    public void setStatus(PersonStatus status){
        this.status = status;
    }

    public PersonStatus getStatus(){
        if(status == PersonStatus.Infected && duration > virus.getRecoveryPeriod()){
            duration = 0;
            setStatus(PersonStatus.Removed);
        }else if(status == PersonStatus.Exposed && duration > virus.getLatentPeriod()){
            dead = this.randomDead();
            setStatus(PersonStatus.Infected);
        }

        return status;
    }

    public boolean isSuperSpreader() {
        return superSpreader;
    }

    public void setSuperSpreader(boolean superSpreader) {
        this.superSpreader = superSpreader;
    }

    // check if this person dead according parameters(if receive treatment / recoveryRate)
    private static boolean randomDead(){
        return random.nextDouble() > virus.getRecoveryRate();
    }

    public boolean isDead() {
        return dead;
    }

    static public void setActivityRate(double activityRate){
        Person.activityRate = activityRate;
    }

    static public double getActivityRate(){
        return Person.activityRate;
    }

    static public void setActivityRadius(double activityRadius){
        Person.activityRadius = activityRadius;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getX(){
        return location.getX();
    }

    public double getY(){
        return location.getY();
    }

    public List<Person> getReproductList() {
        return reproductList;
    }

    // record person who infected by this guys
    public void addReproduct(Person p){
        if(reproductList == null) reproductList = new ArrayList<>();
        reproductList.add(p);
    }

    // the size of reproductList
    public int getReproductNum() {
        return reproductList == null ? 0 : reproductList.size();
    }

    //if this person can infect others(people who has been infected or has been exposed for more than half of virus's latentPeriod)
    public boolean isContagious(){
        return status == PersonStatus.Infected || (status == PersonStatus.Exposed && duration > virus.getLatentPeriod());
    }

    public void update(){
        // count the days which virus exist in this person
        if(status == PersonStatus.Exposed || status == PersonStatus.Infected) duration++;

        randomMove();
    }

    public void randomMove(){
        double dx = Person.activityRadius / 2.0 * random.nextGaussian();
        double dy = Person.activityRadius / 2.0 * random.nextGaussian();

        location = new Point(location.getX() + dx, location.getY() + dy);
    }

    public int compareTo(Person p){
        int cmp = Double.compare(getX(), p.getX());
        if(cmp != 0) return cmp;

        return Double.compare(getY(), getY());
    }

    public static double distance(Person p1, Person p2){
        return Point.distance(p1.location, p2.location);
    }

    // sort by x coordinate
    public static Comparator<Person> xComparator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            int cmp = Double.compare(o1.getX(), o2.getX());
            if(cmp != 0) return cmp;

            return Double.compare(o1.getY(), o2.getY());
        }
    };

    // sort by y coordinate
    public static Comparator<Person> yComparator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            int cmp = Double.compare(o1.getY(), o2.getY());
            if(cmp != 0) return cmp;

            return Double.compare(o1.getX(), o2.getX());
        }
    };

    // sort by number of people infected by this guy
    public static Comparator<Person> rComparator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getReproductNum() - o2.getReproductNum();
        }
    };
}
