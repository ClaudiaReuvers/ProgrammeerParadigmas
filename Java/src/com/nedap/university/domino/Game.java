package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Game {

	private Board board;
	private List<Bone> bones;
	private TreeNode<Board> root;

	/**
	 * Creates a <code>Game</code> which can solve all possibilities for a set of <code>Bone</code>s
	 * to be placed on the <code>Board</code>.
	 *
	 * @param width width of the <code>Board</code>
	 * @param height height of the <code>Board</code>
	 * @param values values of the <code>Board</code>
	 */
	Game(int width, int height, List<Integer> values) {
		this.board = new Board(height, width, values);
		createBones(Collections.max(values));
		this.root = new TreeNode<>(this.board);
	}

	TreeNode<Board> getRoot() {
		return this.root;
	}

	Board getBoard() {
		return board;
	}

	List<Bone> getBones() {
		return bones;
	}

	/**
	 * Returns all the possible solutions for the <code>Game</code>.
	 */
	List<Board> getSolutions() {
		List<Board> solutions = new ArrayList<>();
		for (TreeNode<Board> node : root.getLeaves()) {
			Board board = node.getData();
			if (board.isFull()) {
				solutions.add(board);
			}
		}
		return solutions;
	}

	public static void main(String[] args) {
		//Small grid (3x4)
		List<Integer> smallGrid = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));

		//Examples of the assignment description (8x7)
		List<Integer> ex1 = new ArrayList<>(
				Arrays.asList(6, 6, 2, 6, 5, 2, 4, 1, 1, 3, 2, 0, 1, 0, 3, 4, 1, 3, 2, 4, 6, 6, 5,
						4, 1, 0, 4, 3, 2, 1, 1, 2, 5, 1, 3, 6, 0, 4, 5, 5, 5, 5, 4, 0, 2, 6, 0, 3,
						6, 0, 5, 3, 4, 2, 0, 3));
		List<Integer> ex2 = new ArrayList<>(
				Arrays.asList(5, 4, 3, 6, 5, 3, 4, 6, 0, 6, 0, 1, 2, 3, 1, 1, 3, 2, 6, 5, 0, 4, 2,
						0, 5, 3, 6, 2, 3, 2, 0, 6, 4, 0, 4, 1, 0, 0, 4, 1, 5, 2, 2, 4, 4, 1, 6, 5,
						5, 5, 3, 6, 1, 2, 3, 1));
		List<Integer> ex3 = new ArrayList<>(
				Arrays.asList(4, 2, 5, 2, 6, 3, 5, 4, 5, 0, 4, 3, 1, 4, 1, 1, 1, 2, 3, 0, 2, 2, 2,
						2, 1, 4, 0, 1, 3, 5, 6, 5, 4, 0, 6, 0, 3, 6, 6, 5, 4, 0, 1, 6, 4, 0, 3, 0,
						6, 5, 3, 6, 2, 1, 5, 3));
		Game game = new Game(8, 7, ex1);
		game.createGameTree();
		List<Board> solutions = game.getSolutions();
		printSolutions(solutions);


	}

	private static void printSolutions(List<Board> solutions) {
		System.out.println("There are " + solutions.size() + " solutions for the given grid: ");
		for (Board sol : solutions) {
			System.out.println(sol);
		}
	}

	/**
	 * Creates a all <code>Bone</code>s which need to be placed on the <code>Board</code>
	 *
	 * The <code>Bone</code>s are numbered starting by number 1. The first <code>Bone</code> has
	 * pips 0 and 0, the last <code>highestValue</code> and <code>highestValue</code>. All pip
	 * combinations are unique.
	 */
	private void createBones(int highestValue) {
		List<Bone> bones = new ArrayList<>();
		int nr = 0;
		for (int pip1 = 0; pip1 <= highestValue; pip1++) {
			for (int pip2 = pip1; pip2 <= highestValue; pip2++) {
				nr++;
				bones.add(new Bone(pip1, pip2, nr));
			}
		}
		this.bones = bones;
	}

	/**
	 * Calculates all possible next <code>Board</code>s for the current <code>Board</code> and given
	 * <code>Bone</code>
	 *
	 * @param board the current state of the <code>Board</code>
	 * @param bone <code>Bone</code> to be placed
	 * @return all possible new <code>Board</code>s, <code>null</code> if the <code>Board</code> is
	 * full or there are no pairs left on the <code>Board</code>
	 */
	List<Board> moves(Board board, Bone bone) {
		if (board.noOptions() || board.isFull()) {
			return null;
		}
		Board copy = board.deepcopy();
		List<Pair> pairs = board.getAllPairs();
		List<Board> boards = new ArrayList<>();
		for (Pair pair : pairs) {
			if (copy.isValidMove(pair, bone)) {
				copy.move(pair.getFirst().getPosition(), pair.getSecond().getPosition(), bone);
				boards.add(copy);
				copy = board.deepcopy();
			}
		}
		return boards;
	}

	/**
	 * Generates a Tree of all possible moves, where the <code>root</code> is an empty board.
	 *
	 * The <code>Bone</code>s are placed in the order of the list. If a <code>Bone</code> could be
	 * placed at more than one location, two branches are created. The tree is extended until 1) no
	 * bones are left and the <code>Board</code> is full or 2) there are no <code>Pair</code>s at
	 * the <code>Board</code> left.
	 *
	 * TODO: optimalize algorithm
	 */
	void createGameTree() {
		Bone bone = bones.get(0);
		List<Board> moves = moves(this.board, bone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			root.addChildren(child);
			extendGameTree(child);
		}
	}

	/**
	 * Extends the tree at the given <code>TreeNode</code> for all possible moves.
	 *
	 * See also <b>createGameTree</b>
	 */
	void extendGameTree(TreeNode<Board> node) {
		if (node.getData().isFull() || node.getData().noOptions()) {
			return;
		}
		int depth = node.getDepth();
		List<Board> moves = moves(node.getData(), bones.get(depth));
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			node.addChildren(child);
			extendGameTree(child);
		}
	}

}
