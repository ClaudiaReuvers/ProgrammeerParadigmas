package com.nedap.university.domino;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class GameTest {

	private Game game;

	@Before
	public void setup() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0,1,1,0,2,1,0,2,2,1,2,0));
		game = new Game(3, 4, values, 2);
	}

	@Test
	public void testSetup() {
		assertEquals(3, game.getBoard().getWidth());
		assertEquals(4, game.getBoard().getHeight());
		assertEquals(12, game.getBoard().getFields().size());
		assertEquals(6, game.getBones().size());
		assertEquals(1, game.getBones().get(0).getNr());
		assertEquals(0, game.getBones().get(0).getPip1());
		assertEquals(0, game.getBones().get(0).getPip2());
		assertEquals(5, game.getBones().get(4).getNr());
		assertEquals(1, game.getBones().get(4).getPip1());
		assertEquals(2, game.getBones().get(4).getPip2());
		assertEquals(6, game.getBones().get(5).getNr());
		assertEquals(2, game.getBones().get(5).getPip1());
		assertEquals(2, game.getBones().get(5).getPip2());
	}

	@Test
	public void testMoves() {
		Bone bone = new Bone(0, 0, 1);
		List<Board> boards = game.moves(game.getBoard(), bone);
		assertEquals(2, boards.size());
		assertEquals(bone, boards.get(0).getField(0).getBone());
		assertEquals(bone, boards.get(0).getField(3).getBone());
		assertEquals(bone, boards.get(1).getField(3).getBone());
		assertEquals(bone, boards.get(1).getField(6).getBone());
	}

//	List<Board> moves(Bone bone) {
//		List<Pair> pairs = board.getAllPairs();
//		List<Board> boards = new ArrayList<>();
//		for (Pair pair : pairs) {
//			if (board.isValidMove(pair.getFirst().getPosition(), pair.getSecond().getPosition(),
//					bone)) {
//				Board movedBoard = board.move(pair.getFirst().getPosition(), pair.getSecond().getPosition(), bone);
//				boards.add(movedBoard);
//			}
//		}
//		return boards;
//	}
}
