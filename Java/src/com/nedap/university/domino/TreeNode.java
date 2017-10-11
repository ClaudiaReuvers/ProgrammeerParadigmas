package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class TreeNode<T> {

	public T getData() {
		return data;
	}

	T data;
	TreeNode<T> parent;
	List<TreeNode<T>> children = new ArrayList<>();

	TreeNode(T data) {
		this.data = data;
	}

	public Set<TreeNode<T>> getLeaves() {
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

	public int getDepth() {
		int depth = 0;
		TreeNode<T> parent = this.getParent();
		while (parent != null) {
			depth++;
			parent = parent.getParent();
		}
		return depth;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	void addChildren(TreeNode child) {
		children.add(child);
		child.setParent(this);
	}

	TreeNode<T> getParent() {
		return this.parent;
	}

	List<TreeNode<T>> getChildren() {
		return this.children;
	}

	boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public String toString() {
		String out = "";
		out += data + "\n";
		for (TreeNode<T> child : children)  {
			out += "child: " + child;
		}
		return out;
	}

}
