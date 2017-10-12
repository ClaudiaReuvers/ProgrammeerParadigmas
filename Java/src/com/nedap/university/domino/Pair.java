package com.nedap.university.domino;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class Pair {

	private Field first;
	private Field second;

	/**
	 * Creates a <code>Pair</code> of two <code>Fields</code>
	 */
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

	/**
	 * Checks if the <code>Pair</code> contains a certain <code>Field</code>
	 *
	 * @return <code>true</code> is the given <code>Field</code> is part of the <code>Pair</code>
	 */
	boolean contains(Field field) {
		return (first.equals(field) || second.equals(field));
	}

}
