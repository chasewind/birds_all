package com.bird.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private int            id;
    private int            pid;
    private List<TreeNode> children;

    public TreeNode(int pid, int id){
        this.id = id;
        this.pid = pid;
        this.children = new ArrayList<TreeNode>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

}
