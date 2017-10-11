package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Game {

	private Board board;
	private List<Bone> bones;
	private TreeNode<Board> root;

	Game(int width, int height, List<Integer> values, int highestPipValue) {
		this.board = new Board(height, width, values);
		createBones(highestPipValue);
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

	public static void main(String[] args) {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		Game game = new Game(3, 4, values, 2);
		game.createGameTree();
		List<Board> solutions = game.findSolutions();
		System.out.println(solutions);
	}

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

	void createGameTree2() {
		Bone bone = bones.get(0);
		List<Board> moves = moves(this.board, bone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			root.addChildren(child);
			extendGameTree2(child);
		}
	}

	private void extendGameTree2(TreeNode<Board> node) {
		if (node.getData().isFull() || node.getData().noOptions()) {
			return;
		}
		int depth = node.getDepth();
		List<Board> moves = moves(node.getData(), bones.get(depth));
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			node.addChildren(child);
			extendGameTree2(child);
		}
	}

	void createGameTree() {
		Bone firstBone = bones.get(0);
		List<Board> moves = moves(this.board, firstBone);
		bones.remove(firstBone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			root.addChildren(child);
		}
		for (TreeNode<Board> child : root.getChildren()) {
			extendGameTree(child, bones);
		}
	}

	void extendGameTree(TreeNode<Board> node, List<Bone> bones) {
		if (node.getData().isFull() || node.getData().noOptions()) {
			return;
		}
		Bone bone = bones.get(0);
		List<Board> moves = moves(node.getData(), bone);
		bones.remove(bone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(move);
			node.addChildren(child);
			if (move.isFull() || move.noOptions()) {
				return;
			} else {
				extendGameTree(child, bones);
			}
		}
//		for (TreeNode<Board> child : node.getChildren()) {
//			extendGameTree(child, bones);
//		}
	}

	List<Board> findSolutions() {
		List<Board> solutions = new ArrayList<>();
		for (TreeNode<Board> node : root.getLeaves()) {
			Board board = node.getData();
			if (board.isFull()) {
				solutions.add(board);
			}
		}
		return solutions;
	}

}
