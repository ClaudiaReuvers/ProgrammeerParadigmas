package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Board {

	private int height;
	private int width;
	private List<Field> fields;

	public Board(int height, int width, List<Integer> values) {
		this.height = height;
		this.width = width;
		this.fields = new ArrayList<>(); //TODO: look if ArrayList is the fastest way to go
		for (int i = 0; i < this.height * this.width; i++) {
			fields.add(new Field(i, values.get(i)));
		}
	}

	private Board(int height, int width) {
		this.height = height;
		this.width = width;
		this.fields = new ArrayList<>();
	}

	List<Field> getFields() {
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
			if (field.isEmpty()) {
				for (Pair nghbrs : getPairsOfField(field)) {
					pairs.add(nghbrs);
				}
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

	boolean isValidMove(Pair pair, Bone bone) {
		if (pair.getFirst() != null && pair.getSecond() != null) {
			return pair.getFirst().isEmpty()
					&& pair.getSecond().isEmpty()
					&& bone.containsValue(pair);

		}
		return false;
	}

	Board deepcopy() {
		Board copy = new Board(this.height, this.width);
		for (int i = 0; i < this.fields.size(); i++) {
			Field field = fields.get(i);
			copy.setField(field.getValue(), field.getPosition(), field.getBone());
		}
		return copy;
	}

	private void setField(int value, int position, Bone bone) {
		Field field = new Field(position, value);
		field.setBone(bone);
		this.fields.add(position, field);
	}

	public boolean equals(Board other) {
		for (Field field : fields) {
			Field otherField = other.getField(field.getPosition());
			if (!field.equal(otherField)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String out = "\n";
		for (int i = 0; i < this.fields.size(); i++) {
			if (Math.floorMod(i, this.width) == 0) {
				out += "\n";
			}
			out += this.fields.get(i) + " ";
		}
		return out;
	}
}
