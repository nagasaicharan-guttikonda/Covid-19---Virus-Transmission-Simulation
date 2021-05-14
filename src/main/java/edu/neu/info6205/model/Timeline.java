package edu.neu.info6205.model;

import edu.neu.info6205.helper.PersonStatus;
import edu.neu.info6205.helper.RandomUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * Calculate the latest daily epidemic situation
 *
 * @author Joseph Yuanhao Li
 * @date 4/11/21 02:47
 */
public class Timeline implements Runnable {

    private int days; // World time

    private double R; // Basic reproduction number
    private double K; // Dispersion factor

    private Residence residence;

    private Measure measure;

    private final Random random = RandomUtil.random();

    public Timeline(Residence residence, Measure measure) {
        this.days = 0;

        this.R = 0.0;
        this.K = 0.0;

        this.residence = residence;
        this.measure = measure;

        Person.setActivityRate(Person.getActivityRadius() * measure.getBarrierRate());
        Person.setActivityRadius(Person.getActivityRadius() * measure.getBarrierRate());
    }

    /* Getter and Setter */
    public int getDays() {
        return days;
    }

    public double getR() {
        return R;
    }

    public double getK() {
        return K;
    }

    @Override
    public void run() {
        update(residence.getResidents());
        calculateRK(residence.getResidents());
        applyMeasure(residence.getResidents());
        days++;
    }

    // calculate the status of city and residents in next day
    public void update(Person[] residents) {
        Arrays.sort(residents, Person.xComparator);
        spreadVirus(residents);

        Arrays.sort(residents, Person.yComparator);
        spreadVirus(residents);

        // reset S E I R population
        residence.resetSEIR();

        long susceptible = 0, exposed = 0, infected = 0, removed = 0, dead = 0;

        // count S E I R population
        for (Person p : residents) {
            p.update();

            if (residence.ifOutOfArea(p)) p.setLocation(residence.randomPoint());

            switch (p.getStatus()) {
                case Susceptible: {  // susceptible
                    susceptible++;
                    break;
                }
                case Exposed: { // exposed
                    exposed++;
                    break;
                }

                case Infected: { // infected
                    infected++;
                    break;
                }
                case Removed: { // Recovered or dead
                    removed++;
                    if (p.isDead()) dead++;
                    break;
                }
                default: {
                    System.out.println("Draw person: Wrong Status!");
                }
            }
        }

        residence.setSEIR(susceptible, exposed, infected, removed, dead);
    }

    private void spreadVirus(Person[] residents) {
        Virus virus = residence.getVirus();

        for (int i = 0; i < residents.length; i++) {
            Person p1 = residents[i];

            if (p1.getStatus() == PersonStatus.Removed) continue;

            for (int j = i + 1; j < Math.min(residents.length, i + 10); j++) {
                Person p2 = residents[j];

                if (p2.getX() - p1.getX() > virus.getInfectiousRadius()) continue;

                if (p2.getStatus() == PersonStatus.Removed) continue;

                if (p1.isContagious() ^ p2.isContagious()) {
                    // if distance < infectiousRadius
                    if (Person.distance(p1, p2) < virus.getInfectiousRadius()) {
                        if (p1.isContagious()) infect(p1, p2, virus);
                        else infect(p2, p1, virus);
                    }
                }
            }
        }
    }

    private void infect(Person source, Person target, Virus virus) {
        // if target has face mask and face mask effective, return
        if(random.nextFloat() < measure.getMaskUseRate() && random.nextFloat() < measure.getMaskEffectiveness()) return;

        // random result is infected or source is super spreader, infect target
        if(source.isSuperSpreader() || random.nextFloat() < virus.getInfectiousRate()){
            target.setStatus(PersonStatus.Exposed);
            source.addReproduct(target);

            source.setSuperSpreader(random.nextFloat() < virus.getSuperSpreaderRate());
        }
    }

    private void calculateRK(Person[] residents) {
        Arrays.sort(residents, Person.rComparator); // sort to calculate K; The person who infect most people is at first.

        long EISum = residence.getExposed() + residence.getInfected(); // Exposed + Infected + Removed

        long EISum80 = (long) (EISum * 0.8);

        long topSpreaders = 0;

//        long reproducer = 0; // population of people who infect others(use to calculate R factor)
        for (Person p : residents) {
            if (p.getReproductList() == null) continue;
//            reproducer++;
            if (EISum80 > 0) {
                topSpreaders++;
                EISum80 -= p.getReproductList().size();
            }
        }

//        this.R = reproducer == 0 ? 0 : (double) EISum / reproducer;
        this.R = residence.getInfected() == 0 ? 0: (double) residence.getExposed() / residence.getInfected();
        this.K = EISum == 0 ? 0 : (double) topSpreaders / EISum;
    }

    private void applyMeasure(Person[] residents){
        shuffleArray(residents);

        // get vaccine
        long vaccine = measure.getVaccine();
        for (Person p : residents){
            if(vaccine == 0) break;

            if (p.getStatus() == PersonStatus.Susceptible){
                if(random.nextFloat() < measure.getVaccineEffectiveness()) p.setStatus(PersonStatus.Removed);
                vaccine--;
            }
        }

        shuffleArray(residents);

        //covid test
        long testing = measure.getVirusTesting();
        for (Person p : residents){
            if(testing == 0) break;

            if ((p.getStatus() == PersonStatus.Exposed || p.getStatus() == PersonStatus.Infected) && random.nextFloat() < measure.getQuarantineRate()){
                p.setStatus(PersonStatus.Removed);
            }

            testing--;
        }
    }

    private static void shuffleArray(Person[] arr)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Person a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

}
