package com.nedap.university.domino;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class BoardTest {

	private Board board;

	@Before
	public void setUp() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0,1,1,0,2,1,0,2,2,1,2,0));
		board = new Board(4, 3, values);
	}

	@Test
	public void testisFull() {
		assertFalse(board.isFull());
		// TODO: place stones untill board is full
	}

	@Test
	public void testGetTwoNeighbours() {
		Field first = board.getField(0);
		List<Field> f = board.getTwoNeighbours(first);
		assertEquals(2, f.size());
		assertTrue(f.get(0).isEmpty());
		assertEquals(1, f.get(0).getPosition());
		assertTrue(f.get(1).isEmpty());
		assertEquals(3, f.get(1).getPosition());

		Field right = board.getField(2);
		List<Field> f2 = board.getTwoNeighbours(right);
		assertEquals(1, f2.size());
		assertTrue(f.get(0).isEmpty());
		assertEquals(5, f2.get(0).getPosition());

		Field below = board.getField(9);
		List<Field> f3 = board.getTwoNeighbours(below);
		assertEquals(1, f3.size());
		assertTrue(f.get(0).isEmpty());
		assertEquals(10, f3.get(0).getPosition());

		Field rightbelow = board.getField(11);
		List<Field> f4 = board.getTwoNeighbours(rightbelow);
		assertTrue(f4.isEmpty());
	}

	@Test
	public void testGetAllNeighbous() {
		Field first = board.getField(0);
		List<Field> f = board.getAllNeighbours(first);
		assertEquals(2, f.size());
		//TODO: assert that has 1 and 3

		Field second = board.getField(1);
		List<Field> f2 = board.getAllNeighbours(second);
		assertEquals(3, f2.size());
		//TODO

		Field third = board.getField(2);
		List<Field> f3 = board.getAllNeighbours(third);
		assertEquals(2, f3.size());
		//TODO

		Field fourth = board.getField(3);
		List<Field> f4 = board.getAllNeighbours(fourth);
		assertEquals(3, f4.size());
		//TODO

		Field fifth = board.getField(4);
		List<Field> f5 = board.getAllNeighbours(fifth);
		assertEquals(4, f5.size());
		//TODO
	}

	@Test
	public void testHasPairs() {
		assertTrue(board.hasPairs());
		//TODO: add tests when bones are placed
	}

}
