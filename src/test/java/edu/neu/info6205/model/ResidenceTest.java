package edu.neu.info6205.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.neu.info6205.helper.Point;

public class ResidenceTest {

	
	
	
	@Test
	public void isOutOfAreaTest() {
		Point point = new Point(3.0, 5.0);
		Person p = new Person(point);
		Residence r = new Residence();
		assertTrue(r.ifOutOfArea(p));
	}
	
	
	
	
	@Test(expected  = NullPointerException.class)
	public void isOutOfAreaExceptionTest() {
		Point point = new Point(-1, -2);
		Person p = null;
		Residence r = new Residence();
		r.ifOutOfArea(p);
	}
	
	
	
	
	@Test
	public void isInAreaTest() {
		Point point = new Point(1.0, 1.0);
		Person p = new Person(point);
		Residence r = new Residence();
		assertTrue(r.ifOutOfArea(p));
	}

}
