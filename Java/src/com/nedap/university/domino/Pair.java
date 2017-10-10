package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Pair {

	private Field first;
	private Field second;

	Pair(Field first, Field second) {
		this.first = first;
		this.second = second;
	}

	Field getFirst() {
		return this.first;
	}

	Field getSecond() {
		return this.second;
	}

	boolean contains(Field field) {
		return (first.equals(field) || second.equals(field));
	}

}
