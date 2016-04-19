package com.bird.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeMain {

    public static void main(String[] args) {
        TreeNode pNode = createNode();
        readNode(pNode);
    }

    public static TreeNode createNode() {
        List<TreeNode> tree = new ArrayList<TreeNode>();
        TreeNode node1 = new TreeNode(0, 1);
        TreeNode node2 = new TreeNode(1, 2);
        TreeNode node3 = new TreeNode(1, 3);
        TreeNode node4 = new TreeNode(2, 4);
        TreeNode node5 = new TreeNode(2, 7);
        TreeNode node6 = new TreeNode(2, 8);
        TreeNode node7 = new TreeNode(3, 6);
        TreeNode node8 = new TreeNode(3, 9);
        TreeNode node9 = new TreeNode(7, 5);
        TreeNode node10 = new TreeNode(5, 10);

        tree.add(node1);
        tree.add(node2);
        tree.add(node3);
        tree.add(node4);
        tree.add(node5);
        tree.add(node6);
        tree.add(node7);
        tree.add(node8);
        tree.add(node9);
        tree.add(node10);

        for (TreeNode node : tree) {
            // 找到当前节点的子节点
            int id = node.getId();
            for (TreeNode inner : tree) {
                int innerPId = inner.getPid();
                if (id == innerPId) {
                    //
                    List<TreeNode> children = node.getChildren();
                    if (children == null) {
                        children = new ArrayList<TreeNode>();
                    }
                    children.add(inner);
                }
            }
        }
        return node1;

    }

    public static void readNode(TreeNode pNode) {
        List<TreeNode> children = pNode.getChildren();
        if (children != null && children.size() > 0) {
            System.out.println("parent Node--->" + pNode.getId());
            for (TreeNode child : children) {
                readNode(child);
            }
        } else {
            System.out.println("single Node--->" + pNode.getId());
        }

    }
}
