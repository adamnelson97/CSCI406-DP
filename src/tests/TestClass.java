package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import solutions.RecursiveSolution;

public class TestClass {
	
	public static RecursiveSolution solver;

	@Before
	public void getReady() {
		 solver = new RecursiveSolution("input.txt");
	}
	
	@Test
	public void testInput() {
		assertEquals(solver.getNumDays(),15);
		assertEquals(solver.getTankCapacity(),6);
		assertEquals(solver.getDeliveryCost(),17);
		assertEquals(solver.getStorageCost(),4);
		assertEquals(solver.getGasSoldPerDay()[0],4);
		assertEquals(solver.getGasSoldPerDay()[2],1);
		assertEquals(solver.getGasSoldPerDay()[4],2);
		assertEquals(solver.getGasSoldPerDay()[11],1);
	}
	
}
