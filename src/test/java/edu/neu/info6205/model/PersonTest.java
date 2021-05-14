package edu.neu.info6205.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.neu.info6205.helper.PersonStatus;
import edu.neu.info6205.helper.Point;

public class PersonTest {

	Virus v = new Virus();

	@Test
	public void isContagiousTest() {
		Point point = new Point(3.0, 3.0);
		Person p = new Person(point);
		p.setStatus(PersonStatus.Infected);
		assertTrue(p.isContagious());

	}

	@Test
	public void isContagiousTestFalse() {
		Point point = new Point(3.0, 3.0);
		Person p = new Person(point);
		p.setStatus(PersonStatus.Removed);
		assertFalse(p.isContagious());

	}

	@Test(expected = NullPointerException.class)
	public void isContagiousExceptionTest() {
		Point point = new Point(3.0, 3.0);
		Person p = new Person(point);
		p.setStatus(PersonStatus.Exposed);
		p.isContagious();

	}

	
	
	
	@Test
	public void distanceTest() {
		Point point = new Point(3.0, 3.0);
		Point point1 = new Point(2.0, 2.0);
		Person p1 = new Person(point);
		Person p2 = new Person(point1);
		assertEquals(1.4142135623730951, Point.distance(p1.getLocation(), p2.getLocation()), 0.001);
	}
	
	
	
	
	@Test
	public void randomeDistance() {
		Point point = new Point(3.0, 3.0);
		Point point1 = new Point(2.0, 2.0);
		Person p1 = new Person(point);
		Person p2 = new Person(point1);
		assertEquals(1.4142135623730951, Point.distance(p1.getLocation(), p2.getLocation()), 0.001);
	}
	

	@Test
	public void distanceNotEqualsTest() {
		Point point = new Point(4.0, 3.0);
		Point point1 = new Point(2.0, 5.0);
		Person p1 = new Person(point);
		Person p2 = new Person(point1);
		assertNotEquals(2.345, Point.distance(p1.getLocation(), p2.getLocation()));
	}

	
	@Test(expected = NullPointerException.class)
	public void isDistanceExceptionTest() {
		Point point = new Point(3.0, 3.0);
		Person p = new Person(point);
		p.setStatus(PersonStatus.Exposed);
		p.isContagious();

	}
	
	
	@Test
	public void returnListTest() {
		Point point = new Point(3.0, 5.0);
		Person p = new Person(point);
		// reproductList.add(p);
		p.addReproduct(p);
		assertEquals(1, p.getReproductList().size());
	}

	
	
	
	@Test(expected = NullPointerException.class)
	public void returnEmptyListTest() {
		Point point = new Point(3.0, 5.0);
		Person p = new Person(point);
		p.getReproductList().size();
	}

}
