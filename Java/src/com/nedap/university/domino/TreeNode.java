package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class TreeNode<T> {

	T data;
	TreeNode<T> parent;
	List<TreeNode<T>> children = new ArrayList<>();

	/**
	 * Creates a <code>TreeNode</code> with the object as node value.
	 *
	 * The <code>TreeNode</code> does not have a parent and children.
	 */
	TreeNode(T data) {
		this.data = data;
	}

	T getData() {
		return data;
	}

	/**
	 * Gives a <code>Set</code> of all leaves of the <code>Node</code>
	 *
	 * @return a <code>Set</code> of the leaves of the <code>Node</code>
	 */
	Set<TreeNode<T>> getLeaves() {
		Set<TreeNode<T>> leaves = new HashSet<>();
		if (this.hasChildren()) {
			for (TreeNode<T> child : this.children) {
				leaves.addAll(child.getLeaves());
			}
		} else {
			leaves.add(this);
		}
		return leaves;
	}

	/**
	 * Generates the depth of a <code>Node</code> compared to the root.
	 * The root is defined as depth 0.
	 *
	 * @return the depth of the <code>Node</code>
	 */
	int getDepth() {
		int depth = 0;
		TreeNode<T> parent = this.getParent();
		while (parent != null) {
			depth++;
			parent = parent.getParent();
		}
		return depth;
	}

	TreeNode<T> getParent() {
		return this.parent;
	}

	List<TreeNode<T>> getChildren() {
		return this.children;
	}

	private void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * Adds a child<code>Node</code> to the <code>Node</code> and sets the parent of this
	 * child<code>Node</code> to this <code>Node</code>
	 */
	void addChildren(TreeNode child) {
		children.add(child);
		child.setParent(this);
	}

	/**
	 * @return <code>true</code> is the <code>Node</code> has children, <code>false</code> if it is
	 * a leaf
	 */
	boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public String toString() {
		String out = "";
		out += data + "\n";
		for (TreeNode<T> child : children) {
			out += "child: " + child;
		}
		return out;
	}

}
