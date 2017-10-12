package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 09/10/2017.
 */
public class Field {

	private int position;
	private int value;
	private Bone bone;
	/**
	 * Creates a <code>Field</code> with a certain position and value
	 */
	Field(int position, int value) {
		this.position = position;
		this.value = value;
	}

	/**
	 * Return whether the <code>Field</code> is empty.
	 *
	 * @return <code>true</code> is there is no <code>Bone</code> placed on the <code>Field</code>,
	 * and <code>false</code> if there is <code>Bone</code>
	 */
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

	public void setPosition(int position) {
		this.position = position;
	}

	public void setValue(int value) {
		this.value = value;
	}

	void setBone(Bone bone) {
		this.bone = bone;
	}

	public static void main(String[] args) {
		Field f = new Field(0, 1);
		System.out.println(f);
		f.setBone(new Bone(0, 0, 1));
		System.out.println(f);
	}

	public boolean equal(Field other) {
		return this.getPosition() == other.getPosition()
				&& this.getValue() == other.getValue()
				&& ((this.getBone() == null && other.getBone() == null) || this.getBone()
				.equals(other.getBone()));
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

}
