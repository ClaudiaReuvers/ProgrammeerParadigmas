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
public class BoardTest {

	private Board board;

	@Before
	public void setUp() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		board = new Board(4, 3, values);
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
		testAfterOneMove();
	}

	private void testAfterOneMove() {
		assertFalse(board.isFull());
		assertFalse(board.noOptions());
		assertEquals(13, board.getAllPairs().size());
	}

	@Test
	public void testIsValidMove() {
		Bone bone1 = new Bone(0, 0, 1);
		Bone bone2 = new Bone(0, 1, 2);
		Pair pair = new Pair(new Field(0, 0), new Field(1, 1));
		assertFalse(board.isValidMove(pair, bone1));
		assertTrue(board.isValidMove(pair, bone2));
	}

	@Test
	public void testDeepCopy() {
		Board copy = board.deepcopy();
		assertTrue(board.equals(copy));
		board.move(0, 0, new Bone(0, 0, 1));
		Board copyAfterMove = board.deepcopy();
		assertTrue(board.equals(copyAfterMove));
	}

	@Test
	public void testFullBoard() {
		board.move(0, 1, new Bone(0, 1, 2));
		board.move(3, 6, new Bone(0, 0, 1));
		board.move(2, 5, new Bone(1, 1, 4));
		board.move(4, 7, new Bone(2, 2, 6));
		board.move(9, 10, new Bone(1, 2, 5));
		assertFalse(board.isFull());
		assertFalse(board.noOptions());
		assertEquals(1, board.getAllPairs().size());
		board.move(8, 11, new Bone(0, 2, 3));
		assertTrue(board.isFull());
		assertTrue(board.noOptions());
	}

	@Test
	public void testNoOptions() {
		board.move(0, 1, new Bone(0, 1, 2));
		board.move(3, 6, new Bone(0, 0, 1));
		board.move(2, 5, new Bone(1, 1, 4));
		board.move(4, 7, new Bone(2, 2, 6));
		board.move(10, 11, new Bone(0, 0, 0)); //this is actually not a valid move
		assertFalse(board.isFull());
		assertTrue(board.noOptions());
	}

}
