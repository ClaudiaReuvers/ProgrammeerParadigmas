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
		this.root = new TreeNode<>(null, this.board);
	}

	public TreeNode<Board> getRoot() {
		return this.root;
	}

	public Board getBoard() {
		return board;
	}

	public List<Bone> getBones() {
		return bones;
	}

	void createBones(int highestValue) {
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

	void putBone(int pos1, int pos2, Bone bone) {
		if (board
				.isValidMove(pos1, pos2, bone)) { //TODO: how do you let know if the move is invalid
			board.move(pos1, pos2, bone);
		}
	}

	List<Board> moves(Board board, Bone bone) {
		Board copy = board.deepcopy();
		List<Pair> pairs = board.getAllPairs();
		List<Board> boards = new ArrayList<>();
		for (Pair pair : pairs) {
			if (copy.isValidMove(pair.getFirst().getPosition(), pair.getSecond().getPosition(),
					bone)) {
				copy.move(pair.getFirst().getPosition(), pair.getSecond().getPosition(), bone);
				boards.add(copy);
				copy = board.deepcopy();
			}
		}
		return boards;
	}

	void createGameTree() {
		Bone firstBone = bones.get(0);
		List<Board> moves = moves(this.board, firstBone);
		bones.remove(firstBone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(root, move);
			root.addChildren(child);

		}
		for (TreeNode<Board> child : root.getChildren()) {
			extendGameTree(child, bones);
		}
		System.out.println(root);
	}

	private void extendGameTree(TreeNode<Board> node, List<Bone> bones) {
		Bone bone = bones.get(0);
		List<Board> moves = moves(node.getData(),  bone);
		bones.remove(bone);
		for (Board move : moves) {
			TreeNode<Board> child = new TreeNode<>(node, move);
			node.addChildren(child);
		}
		for (TreeNode<Board> child : node.getChildren()) {
			extendGameTree(child, bones);
		}
	}

	public static void main(String[] args) {
		List<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 1, 0, 2, 1, 0, 2, 2, 1, 2, 0));
		Game game = new Game(3, 4, values, 2);
		game.createGameTree();
		TreeNode<Board> root = game.getRoot();
	}

}
