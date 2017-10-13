package com.nedap.university.domino;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		game = new Game(3, 4, values);
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
		Board oneMove = boards.get(0);
		Bone secondBone = new Bone(1, 1, 4);
		List<Board> secondMove = game.moves(oneMove, secondBone);
		assertEquals(2, secondMove.size());
		Board fullBoard = createFullBoard();
		Board noOptionsBoard = createNoOptionsBoard();
		assertNull(game.moves(fullBoard, bone));
		assertNull(game.moves(noOptionsBoard, bone));
	}

	private Board createNoOptionsBoard() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		Board board = new Board(4, 3, values);
		board.move(0, 1, new Bone(0, 1, 2));
		board.move(3, 6, new Bone(0, 0, 1));
		board.move(2, 5, new Bone(1, 1, 4));
		board.move(4, 7, new Bone(2, 2, 6));
		board.move(10, 11, new Bone(0, 0, 0)); //this is actually not a valid move
		return board;
	}

	private Board createFullBoard() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		Board board = new Board(4, 3, values);
		board.move(0, 1, new Bone(0, 1, 2));
		board.move(3, 6, new Bone(0, 0, 1));
		board.move(2, 5, new Bone(1, 1, 4));
		board.move(4, 7, new Bone(2, 2, 6));
		board.move(9, 10, new Bone(1, 2, 5));
		board.move(8, 11, new Bone(0, 2, 3));
		return board;
	}

	@Test
	public void testCreateGameTree() {
		game.createGameTree();
		assertEquals(2, game.getRoot().getChildren().size());
		assertEquals(1, game.getRoot().getChildren().get(0).getChildren().size());
		assertEquals(1, game.getRoot().getChildren().get(1).getChildren().size());
		assertEquals(7, game.getRoot().getLeaves().size());
		assertEquals(6, game.getSolutions().size());
		System.out.println(game.getSolutions());
	}

	@Test
	public void testCreateGameTreeSmall() {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 0, 0, 1));
		Game smallGame = new Game(2, 2, values);
		smallGame.getBones().remove(2); // manipulate boneslist s.t. there are only two bones
		smallGame.createGameTree();
		assertEquals(2, smallGame.getRoot().getChildren().size());
		assertEquals(2, smallGame.getRoot().getLeaves().size());
		assertEquals(1, smallGame.getRoot().getChildren().get(0).getChildren().size());
		assertEquals(1, smallGame.getRoot().getChildren().get(1).getChildren().size());
		assertEquals(2, smallGame.getSolutions().size());
	}

}
