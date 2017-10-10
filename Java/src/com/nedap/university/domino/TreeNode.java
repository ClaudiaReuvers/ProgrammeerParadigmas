package com.nedap.university.domino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudia.reuvers on 10/10/2017.
 */
public class TreeNode<T> {

	TreeNode<T> parent;
	List<TreeNode<T>> children = new ArrayList<>();

	TreeNode(TreeNode<T> parent) {
		this.parent = parent;
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

}
