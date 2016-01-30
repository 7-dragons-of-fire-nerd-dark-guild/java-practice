package com.sevendragons.utils.graph;

class Edge {
    private int source;
    private int target;
    private int value;

    public Edge(int source, int target, int value) {
        setSource(source);
        setTarget(target);
        setValue(value);
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge edge = (Edge) obj;
        if (this.source != edge.getSource()) {
            return false;
        }
        if (this.target != edge.getTarget()) {
            return false;
        }
        if (this.value != edge.getValue()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 31 * hash + this.source;
        hash = 31 * hash + this.target;
        hash = 31 * hash + this.value;
        return hash;
    }
}