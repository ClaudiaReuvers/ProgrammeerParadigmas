package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Board2 {

	private int height;
	private int width;
	private List<Field> fields;

	public Board2(int height, int width, List<Integer> values) {
		this.height = height;
		this.width = width;
		this.fields = new ArrayList<>(); //TODO: look if ArrayList is the fastest way to go
		for (int i = 0; i < this.height * this.width; i++) {
			fields.add(new Field(i, values.get(i)));
		}
	}

	private Board2(int height, int width) {
		this.height = height;
		this.width = width;
	}

	private void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Field> getFields() {
		return fields;
	}

	int getHeight() {
		return height;
	}

	int getWidth() {
		return width;
	}

	List<Pair> getAllPairs() {
		List<Pair> pairs = new ArrayList<>();
		for (Field field : fields) {
			for (Pair nghbrs : getPairsOfField(field)) {
				pairs.add(nghbrs);
			}
		}
		return pairs;
	}

	List<Pair> getPairsOfField(Field field) {
		List<Pair> pairs = new ArrayList<>();
		Field nghbrRight = getRightNeighbour(field);
		if (nghbrRight != null) {
			pairs.add(new Pair(field, nghbrRight));
		}
		Field nghbrBelow = getBelowNeighbour(field);
		if (nghbrBelow != null) {
			pairs.add(new Pair(field, nghbrBelow));
		}
		return pairs;
	}

	private Field getBelowNeighbour(Field field) {
		int posBelow = field.getPosition() + this.width;
		if (isOnBoard(posBelow) && getField(posBelow).isEmpty()) {
			return getField(posBelow);
		}
		return null;
	}

	private Field getRightNeighbour(Field field) {
		int posRight = field.getPosition() + 1;
		if (isOnBoard(posRight) && Math.floorMod(posRight, this.width) != 0 && getField(posRight)
				.isEmpty()) {
			return getField(posRight);
		}
		return null;
	}

	private boolean isOnBoard(int pos) {
		return pos >= 0 && pos < (this.height * this.width);
	}

	boolean isFull() {
		for (Field field : fields) {
			if (field.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	Field getField(int position) {
		if (isOnBoard(position)) {
			return this.fields.get(position);
		}
		return null;
	}

	boolean noOptions() {
		return getAllPairs().isEmpty();
	}

	void move(int pos1, int pos2, Bone bone) {
		putBone(pos1, bone);
		putBone(pos2, bone);
	}

	private void putBone(int pos, Bone bone) {
		getField(pos).setBone(bone);
	}

	boolean isValidMove(int pos1, int pos2, Bone bone) {
		Field firstField = getField(pos1);
		Field secondField = getField(pos2);
		if (firstField != null && secondField != null) {
			return firstField.isEmpty()
					&& secondField.isEmpty()
					&& bone.containsValue(firstField.getValue())
					&& bone.containsValue(secondField.getValue());

		}
		return false;
	}

	Board2 deepcopy() {
		Board2 copy = new Board2(this.height, this.width);
		copy.setFields(this.fields);
		return copy;
	}

	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < this.fields.size(); i++) {
			if (Math.floorMod(i, this.width) == 0) {
				out += "\n";
			}
			out += this.fields.get(i);
		}
		return out;
	}
}
