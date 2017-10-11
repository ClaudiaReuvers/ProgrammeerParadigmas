package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Bone {

	private int pip1;
	private int pip2;
	private int nr;

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

	void setPip1(int pip1) {
		this.pip1 = pip1;
	}

	void setPip2(int pip2) {
		this.pip2 = pip2;
	}

	void setNr(int nr) {
		this.nr = nr;
	}

	public boolean containsValue(Pair pair) {
		int field1 = pair.getFirst().getValue();
		int field2 = pair.getSecond().getValue();
		return (pip1 == field1 && pip2 == field2) || (pip1 == field2 && pip2 == field1);
	}

	public boolean equals(Bone other) {
		return this.getPip1() == other.getPip1()
				&& this.getPip2() == other.getPip2()
				&& this.getNr() == other.getNr();
	}
	@Override
	public String toString() {
		return this.pip1 + " | " + this.pip2 +  " (" + this.nr + ")";
	}
}
