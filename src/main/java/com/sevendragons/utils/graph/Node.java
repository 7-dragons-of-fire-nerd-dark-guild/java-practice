package com.sevendragons.utils.graph;

class Node {
    private String id;
    private int value;

    public Node(String id, int value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        if ((this.getId() == null) ? node.getId() != null : !this.getId().equals(node.getId())) {
            return false;
        }
        if (this.getValue() != node.getValue()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 3 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 3 * hash + this.getValue();
        return hash;
    }
}