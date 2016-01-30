package com.sevendragons.utils.graph;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by pierre on 30/01/2016.
 */
public class VisualizeGraphTest {

    private static VisualizeGraph visualizeGraph = new VisualizeGraph();

    @Test
    public void test_generate_empty_json() {
        GraphNodes graphNodes = new GraphNodes(new ArrayList<>(), new ArrayList<>());
        String expected = "{\"links\":[],\"nodes\":[]}";
        assertEquals(expected, visualizeGraph.generateJson(graphNodes));
    }

    @Test
    public void test_generate_with_data_json() {
        GraphNodes graphNodes = new GraphNodes(new ArrayList<>(), new ArrayList<>());
        graphNodes.getNodes().add(new Node("0", 4));
        String expected = "{\"links\":[],\"nodes\":[{\"id\":\"0\",\"value\":4}]}";
        assertEquals(expected, visualizeGraph.generateJson(graphNodes));
    }

    @Test
    public void testCreateGraphNodes() throws Exception {
        int[][] matrix = {{0, 1}, {1, 0}};
        int offset = 5;
        GraphNodes graphNodes = visualizeGraph.createGraphNodes(matrix, null,offset);
        GraphNodes graphNodesExpected = new GraphNodes(new ArrayList<>(), new ArrayList<>());
        graphNodesExpected.getNodes().add(new Node("0", offset));
        graphNodesExpected.getNodes().add(new Node("1", offset));
        graphNodesExpected.getLinks().add(new Edge(0, 1, 1));
        assertEquals(graphNodesExpected, graphNodes);

    }

}