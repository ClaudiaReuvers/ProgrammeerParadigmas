package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

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

	TreeNode(TreeNode<T> parent, T data) {
		this.parent = parent;
		this.data = data;
	}

	void addChildren(TreeNode child) {
		children.add(child);
	}

	TreeNode<T> getParent() {
		return this.parent;
	}

	List<TreeNode<T>> getChildren() {
		return this.children;
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
