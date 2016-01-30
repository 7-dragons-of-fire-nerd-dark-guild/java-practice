package com.sevendragons.utils.graph;

import java.util.List;

class GraphNodes {
    private List<Edge> links;
    private List<Node> nodes;

    GraphNodes(List<Edge> links, List<Node> nodes) {
        this.links = links;
        this.nodes = nodes;
    }

    public List<Edge> getLinks() {
        return links;
    }


    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GraphNodes graphNodes = (GraphNodes) obj;
        if ((this.links == null) ? (graphNodes.getLinks() != null) : !this.links.equals(graphNodes.getLinks())) {
            return false;
        }
        if ((this.nodes == null) ? (graphNodes.getNodes() != null) : !this.nodes.equals(graphNodes.getNodes())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.links != null ? this.links.hashCode() : 0);
        hash = 31 * hash + (this.nodes != null ? this.nodes.hashCode() : 0);
        return hash;
    }
}




