package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Game {

	private Board board;
	private List<Bone> bones;

	Game(int width, int height, List<Integer> values, List<Bone> bones) {
		this.board = new Board(height, width, values);
		this.bones = bones;
	}

	public static void main(String[] args) {
		List<Bone> bones = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		Game game = new Game(3, 4, values, bones);
	}
}
