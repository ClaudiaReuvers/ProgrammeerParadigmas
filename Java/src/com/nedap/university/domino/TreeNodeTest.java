package com.nedap.university.domino;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by claudia.reuvers on 11/10/2017.
 */
public class TreeNodeTest {

	TreeNode<Integer> root;

	@Before
	public void setup() {
		root = new TreeNode<>( 1);
	}

	@Test
	public void testSetup() {
		assertEquals(new Integer(1), root.getData());
		assertNull(root.getParent());
		assertFalse(root.hasChildren());
		assertEquals(0, root.getDepth());
	}

	@Test
	public void testAddChildren() {
		TreeNode<Integer> child = new TreeNode(2);
		root.addChildren(child);
		assertTrue(root.hasChildren());
		assertEquals(1, root.getChildren().size());
		assertEquals(root, child.getParent());
		assertEquals(1, root.getLeaves().size());
		assertEquals(1, child.getDepth());
	}

	@Test
	public void testGetLeaves() {
		TreeNode<Integer> child2 = new TreeNode(2);
		TreeNode<Integer> child3 = new TreeNode(3);
		root.addChildren(child2);
		root.addChildren(child3);
		assertEquals(2, root.getLeaves().size());
		TreeNode<Integer> child4 = new TreeNode(4);
		TreeNode<Integer> child5 = new TreeNode(5);
		TreeNode<Integer> child6 = new TreeNode(6);
		TreeNode<Integer> child7 = new TreeNode(7);
		child2.addChildren(child4);
		child2.addChildren(child5);
		child3.addChildren(child6);
		child3.addChildren(child7);
		assertEquals(4, root.getLeaves().size());
		assertTrue(root.getLeaves().contains(child4));
		assertEquals(2, child4.getDepth());
		assertTrue(root.getLeaves().contains(child5));
		assertEquals(2, child5.getDepth());
		assertTrue(root.getLeaves().contains(child6));
		assertEquals(2, child6.getDepth());
		assertTrue(root.getLeaves().contains(child7));
		assertEquals(2, child7.getDepth());
	}
}
