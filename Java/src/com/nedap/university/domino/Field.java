package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Field {

	public void setPosition(int position) {
		this.position = position;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private int position;
	private int value;
	private Bone bone;

	Field(int position, int value) {
		this.position = position;
		this.value = value;
	}

	boolean isEmpty() {
		return bone == null;
	}

	int getValue() {
		return this.value;
	}

	int getPosition() {
		return this.position;
	}

	Bone getBone() {
		return this.bone;
	}

	void setBone(Bone bone) {
		this.bone = bone;
	}

	public boolean equal(Field other) {
		return this.getPosition() == other.getPosition()
				&& this.getValue() == other.getValue()
				&& ((this.getBone() == null && other.getBone() == null) || this.getBone().equals(other.getBone()));
	}

	@Override
	public String toString() {
		String output = "" + this.value;
		if (!isEmpty()) {
			output += "(" + this.bone.getNr() + ")";
		} else {
			output += "   ";
		}
		return output;
	}

	public static void main(String[] args) {
		Field f = new Field(0, 1);
		System.out.println(f);
		f.setBone(new Bone(0,0,1));
		System.out.println(f);
	}

}
