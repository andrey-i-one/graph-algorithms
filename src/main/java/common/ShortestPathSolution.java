package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortestPathSolution {

	public int[][] point;
	public int[][] graph;
	public String[] edgeColor;
	public String[] verticeColor;
	
	public ShortestPathSolution(int[][] graph, int[][] point) {
		this.graph = graph;
		this.point = point;
		verticeColor = new String[point.length];
		edgeColor = new String[graph.length];
		for(int i = 0; i < verticeColor.length; i++) {
			verticeColor[i] = "#FF0000";
		}
		for(int i = 0; i < edgeColor.length; i++) {
			edgeColor[i] = "#FFFFFF";
		}
	}
	
	public ShortestPathSolution copy() {
		ShortestPathSolution s = new ShortestPathSolution(new int[graph.length][2], new int[point.length][2]);

		s.edgeColor = new String[edgeColor.length];
		s.verticeColor = new String[verticeColor.length];
		
		for(int i = 0; i < edgeColor.length; i++) {
			s.edgeColor[i] = new String(edgeColor[i]);
		}
		
		for(int i = 0; i < verticeColor.length; i++) {
			s.verticeColor[i] = new String(verticeColor[i]);
		}
		
		for(int i = 0; i < graph.length; i++) {
			s.graph[i][0] = graph[i][0];
			s.graph[i][1] = graph[i][1];
		}
		
		for(int i = 0; i < point.length; i++) {
			s.point[i][0] = point[i][0];
			s.point[i][1] = point[i][1];
		}
		
		return s;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> currentGraph = new HashMap<String, Object>();
		List<Object> vertices = new ArrayList<Object>();
		for(int j = 0; j < point.length; j++) {
			HashMap<String, Object> currentVertice = new HashMap<String, Object>();
			currentVertice.put("x", point[j][0]);
			currentVertice.put("y", point[j][1]);
			currentVertice.put("color", verticeColor[j]);
			vertices.add(currentVertice);
		}
		currentGraph.put("vertices", vertices);

		List<Object> currentEdges = new ArrayList<Object>();
		for(int j = 0; j < graph.length; j++) {
			if(edgeColor[j].equals("#FFFFFF")) continue;
			HashMap<String, Object> currentEdge = new HashMap<String, Object>();
			currentEdge.put("from", graph[j][0]);
			currentEdge.put("to", graph[j][1]);
			currentEdge.put("color", edgeColor[j]);
			currentEdges.add(currentEdge);
		}
		currentGraph.put("edges", currentEdges);
		return currentGraph;
	}
	
}
