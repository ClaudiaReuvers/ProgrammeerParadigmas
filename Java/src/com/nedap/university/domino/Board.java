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

	/**
	 * Creates a board of the given width and height, filled in with the values. The values are
	 * given to the fields from left to right, top to bottom. All field are empty: there are no
	 * bones placed on the field.
	 *
	 * @param values values of the fields (left to right, top to bottom)
	 */
	public Board(int height, int width, List<Integer> values) {
		this.height = height;
		this.width = width;
		this.fields = new ArrayList<>(); //TODO: look if ArrayList is the fastest way to go
		for (int i = 0; i < this.height * this.width; i++) {
			fields.add(new Field(i, values.get(i)));
		}
	}

	/**
	 * Creates a board of the given width and height.
	 * The field do not have a value.
	 */
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

	/**
	 * Returns all pairs of empty fields on the board.
	 *
	 * @return empty pairs of the board
	 */
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

	/**
	 * @return <code>true</code> if all fields are occupied with a <code>Bone</code>
	 */
	boolean isFull() {
		for (Field field : fields) {
			if (field.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the <code>Pair</code>s of which the <code>Field</code> is a part of.
	 * This is compared to its right and lower neighbour and both fields have to be empty.
	 *
	 * @param field <code>Field</code> of which you want to know it is a <code>Pair</code>
	 * @return all <code>Pair</code>s the <code>Field</code> is part of
	 */
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

	/**
	 * Returns the <code>Field</code> on the given position
	 *
	 * @return <code>Field</code> if the position is on the <code>Board</code>, otherwise
	 * <code>null</code>
	 */
	Field getField(int position) {
		if (isOnBoard(position)) {
			return this.fields.get(position);
		}
		return null;
	}

	/**
	 * Determines if there are options to place a <code>Bone</code>
	 *
	 * @return <code>true</code> if there are no <code>Pair</code>s of empty fields left on the
	 * board
	 */
	boolean noOptions() {
		return getAllPairs().isEmpty();
	}

	/**
	 * Places the given <code>Bone</code> on the position.
	 * It does not check whether this move is valid.
	 */
	void move(int pos1, int pos2, Bone bone) {
		putBone(pos1, bone);
		putBone(pos2, bone);
	}

	private void putBone(int pos, Bone bone) {
		getField(pos).setBone(bone);
	}

	/**
	 * Checks if a <code>Bone</code> may be placed on the given fields of the <code>Pair</code>. A
	 * move is valid if the values of both <code>Field</code>s are the same as the pips of the
	 * <code>Bone</code> and the <code>Field</code>s are empty
	 *
	 * @param pair <code>Pair</code> of empty <code>Field</code>s
	 * @param bone <code>Bone</code> to be placed
	 * @return <code>true</code> if the placement of the <code>Bone</code> on the
	 * <code>Field</code>s is a valid move
	 */
	boolean isValidMove(Pair pair, Bone bone) {
		if (pair.getFirst() != null && pair.getSecond() != null) {
			return pair.getFirst().isEmpty()
					&& pair.getSecond().isEmpty()
					&& bone.containsValue(pair);

		}
		return false;
	}

	/**
	 * Creates a copy of the <code>Board</code>
	 */
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
