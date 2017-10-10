package com.nedap.university.domino;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Board2Test {

	private Board2 board;

	@Before
	public void setUp() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0,1,1,0,2,1,0,2,2,1,2,0));
		board = new Board2(4, 3, values);
	}

	@Test
	public void testSetup() {
		assertEquals(4, board.getHeight());
		assertEquals(3, board.getWidth());
		assertEquals(12, board.getFields().size());
		assertFalse(board.isFull());
		assertEquals(17, board.getAllPairs().size());
		assertFalse(board.noOptions());
	}

	@Test
	public void testGetPairsOfField() {
		List<Pair> twoPairs = board.getPairsOfField(board.getField(0));
		assertEquals(2, twoPairs.size());
		assertTrue(twoPairs.get(0).contains(board.getField(1)));
		assertTrue(twoPairs.get(1).contains(board.getField(board.getWidth())));

		List<Pair> onePair = board.getPairsOfField(board.getField(2));
		assertEquals(1, onePair.size());
		assertTrue(onePair.get(0).contains(board.getField(2 + board.getWidth())));

		List<Pair> noPairs = board.getPairsOfField(board.getField(11));
		assertTrue(noPairs.isEmpty());
	}

	@Test
	public void getField() {
		assertEquals(0, board.getField(0).getPosition());
		assertEquals(11, board.getField(11).getPosition());
		assertNull(board.getField(-1));
		assertNull(board.getField(12));
	}

	@Test
	public void testMove() {
		Bone bone = new Bone(0, 0, 1);
		board.move(0, 1, bone);
		assertFalse(board.getField(0).isEmpty());
		assertEquals(bone, board.getField(0).getBone());
		assertFalse(board.getField(1).isEmpty());
		assertEquals(bone, board.getField(1).getBone());
	}

	@Test
	public void testIsValidMove() {
		Bone bone1 = new Bone (0,0,1);
		Bone bone2 = new Bone (0, 1, 2);
		assertFalse(board.isValidMove(0, 1, bone1));
		assertTrue(board.isValidMove(0 , 1, bone2));
		assertFalse(board.isValidMove(-1, 0 , bone1));
		board.move(0, 1, bone2);
		assertFalse(board.isValidMove(1, 2, bone1));
	}

	//TODO: test getAllPairs if stones are laid
	//TODO: test isFull if the board is full
	//TODO: test noOptions if there are no options
}
