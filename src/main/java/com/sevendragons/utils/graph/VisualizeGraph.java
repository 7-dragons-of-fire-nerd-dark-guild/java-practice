package com.sevendragons.utils.graph;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;

import java.util.*;
import java.util.List;

/**
 * Created by pierre on 30/01/2016.
 */
public class VisualizeGraph {

    private final static Logger logger = LoggerFactory.getLogger(VisualizeGraph.class);
    /**
     * This method is kind of crazy but fun :)
     * I did it because of cross request when javascript getting json file.
     * and yes i could create the right html file with data embded :)
     * @param json
     */
    private void display(String json) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange httpExchange) throws IOException {
                    String response = null;
                    try {
                        response = createResponse();
                        httpExchange.sendResponseHeaders(200, response.length());
                        OutputStream os = httpExchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (URISyntaxException e) {
                        logger.error("unable create response page",e);
                    }
                }
            });
            server.createContext("/json", new HttpHandler() {
                @Override
                public void handle(HttpExchange httpExchange) throws IOException {
                    httpExchange.sendResponseHeaders(200, json.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                }
            });
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            logger.error("unable to create a little httpserver :(", e);
        }

        try {
            Desktop.getDesktop().browse(URI.create("http://localhost:8000/"));
        } catch (IOException e) {
            logger.error("unable to launch browser",e);
        }
    }

    private String createResponse() throws IOException, URISyntaxException {
        String name = "visual.html";
        URL urlSource = VisualizeGraph.class.getResource(name);
        File file = new File(urlSource.toURI());

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }


    /**
     *
     * @param matrix link between node
     * @param nodeWeight node with their weight
     * @param offset size by default of node
     * @return
     */
    public GraphNodes createGraphNodes(int[][] matrix, int[] nodeWeight,int offset) {
        int length = matrix.length;

        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            nodes.add(new Node(String.valueOf(i), (nodeWeight == null) ? offset : nodeWeight[i] + offset));// add offset to see dot
    }
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (i != j && matrix[i][j] != 0) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                }

            }
        }
        return new GraphNodes(edges, nodes);
    }

    public String generateJson(Object graphNodes) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(graphNodes);
    }


    public static void main(String[] args) {
        int[][] matrix = {{0, 160, 10, 1, 1, 0}, {160, 0, 10, 45, 78, 4}, {10, 10, 0, 47, 8, 4}, {10, 10, 0, 47, 8, 4}, {10, 10, 0, 47, 8, 4}, {10, 10, 0, 47, 8, 4}};
        int[] matrixNode = {0, 10, 0, 0, 0, 0};
        visualize(matrix, matrixNode);

    }

    public static void visualize(int[][] matrix, int[] matrixNode) {
        VisualizeGraph graph = new VisualizeGraph();
        GraphNodes graphNodes = graph.createGraphNodes(matrix, matrixNode,5);
        String json = graph.generateJson(graphNodes);
        graph.display(json);
    }
}
