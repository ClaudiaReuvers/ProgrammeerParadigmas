package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Board {

	private int height;
	private int width;
	private List<Field> fields;

	public Board(int height, int width, List<Integer> values) {
		this.height = height;
		this.width = width;
		this.fields = new ArrayList<>();
		for (int i = 0; i < this.height * this.width; i++) {
			fields.add(new Field(i, values.get(i)));
		}
	}

	public boolean isFull() {
		for (Field field : this.fields) {
			if (field.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public boolean hasPairs() {
		for (Field field : this.fields) {
			if (!getPairs(field).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public List<Pair> getPairs(Field field) {
		List<Field> nghbrs = getTwoNeighbours(field);
		List<Pair> pairs = new ArrayList<>();
		for (Field nghbr : nghbrs) {
			pairs.add(new Pair(field, nghbr));
		}
		return pairs;
	}

	List<Field> getAllNeighbours(Field field) {
		List<Field> nghbrs = getTwoNeighbours(field);
		Field left = getNeighbourLeft(field);
		if (left != null) {
			nghbrs.add(left);
		}
		Field above = getNeighbourAbove(field);
		if (above != null) {
			nghbrs.add(above);
		}
		return nghbrs;
	}

	public List<Field> getTwoNeighbours(Field field) {
		List<Field> f = new ArrayList<>();
		Field right = getNeighbourRight(field);
		if (right != null) {
			f.add(right);
		}
		Field below = getNeigbourBelow(field);
		if (below != null) {
			f.add(below);
		}
		return f;
	}

	private Field getNeighbourRight(Field field) {
		int posRight = field.getPosition() + 1;
		if (isOnBoard(posRight) && Math.floorMod(posRight, this.width) != 0 && isEmpty(getField(posRight))) {
			return getField(posRight);
		}
		return null;
	}

	private Field getNeigbourBelow(Field field) {
		int posBelow = field.getPosition() + this.width;
		if (isOnBoard(posBelow) && isEmpty(getField(posBelow))) {
			return getField(posBelow);
		}
		return null;
	}

	private Field getNeighbourAbove(Field field) {
		int posAbove = field.getPosition() - this.width;
		if (isOnBoard(posAbove) && isEmpty(getField(posAbove))) {
			return getField(posAbove);
		}
		return null;
	}

	private Field getNeighbourLeft(Field field) {
		int posLeft = field.getPosition() - 1;
		if (isOnBoard(posLeft) && Math.floorMod(posLeft, this.width) != (this.width - 1) && isEmpty(getField(posLeft))) {
			return getField(posLeft);
		}
		return null;
	}

	private boolean isOnBoard(int pos) {
		return pos >= 0 && pos < (this.height * this.width);
	}

	public Field getField(int position) {
		return this.fields.get(position);
	}

	public boolean isEmpty(Field field) {
		return field.isEmpty();
	}

//	public Board move(int pos1, int pos2, Bone bone) {
//		Field field1 = getField(pos1);
//		LinkedList<Field> fields2 = new LinkedList<>();
//		fields2.
//	}
}
