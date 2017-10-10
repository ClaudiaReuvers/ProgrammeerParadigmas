package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Game {

	private Board2 board;
	private List<Bone> bones;

	Game(int width, int height, List<Integer> values, int highestPipValue) {
		this.board = new Board2(height, width, values);
		createBones(highestPipValue);
	}

	public Board2 getBoard() {
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
		if (board.isValidMove(pos1, pos2, bone)) { //TODO: how do you let know if the move is invalid
			board.move(pos1, pos2, bone);
		}
	}


}
