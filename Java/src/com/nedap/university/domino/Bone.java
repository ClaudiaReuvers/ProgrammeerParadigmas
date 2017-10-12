package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Bone {

	private int pip1;
	private int pip2;
	private int nr;

	/**
	 * Creates a <code>Bone</code> with two pips and the number of the <code>Bone</code>
	 */
	Bone(int pip1, int pip2, int nr) {
		this.pip1 = pip1;
		this.pip2 = pip2;
		this.nr = nr;
	}

	int getPip1() {
		return this.pip1;
	}

	int getPip2() {
		return this.pip2;
	}

	int getNr() {
		return this.nr;
	}

	/**
	 * Checks if the values of the given <code>Pair</code> match the pips of the <code>Bone</code>
	 *
	 * @return <code>true</code> if both values of the <code>Pair</code> are equal to the pips of
	 * the <code>Bone</code>
	 */
	boolean containsValue(Pair pair) {
		int field1 = pair.getFirst().getValue();
		int field2 = pair.getSecond().getValue();
		return (pip1 == field1 && pip2 == field2) || (pip1 == field2 && pip2 == field1);
	}

	boolean equals(Bone other) {
		return this.getPip1() == other.getPip1()
				&& this.getPip2() == other.getPip2()
				&& this.getNr() == other.getNr();
	}

	@Override
	public String toString() {
		return this.pip1 + " | " + this.pip2 + " (" + this.nr + ")";
	}
}
