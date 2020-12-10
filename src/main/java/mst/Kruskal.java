package mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import common.ShortestPathSolution;

public class Kruskal {

	class Edge implements Comparable<Edge> {
		int from, to;
		int index;
		double d;

		@Override
		public int compareTo(Edge o) {
			return (int)(d - o.d);
		}
	}
	
	private Edge[] edges;
	private int[][] points;
	private int[][] graph;
	
	private double calcDistance(int p1, int p2) {
		double q = 
				(points[p1][0] - points[p2][0]) * (points[p1][0] - points[p2][0]) + 
				(points[p1][1] - points[p2][1]) * (points[p1][1] - points[p2][1]);
		return Math.sqrt(q);
	}

	private void buildGraph() {		
		int m = points.length * (points.length - 1) / 2;
		graph = new int[m][2];
		int gC = 0;
		for(int i = 0; i < points.length; i++) {
			for(int j = i + 1; j < points.length; j++) {
				if(i == j) continue;
				if(points[i][0] == points[j][0] || points[i][1] == points[j][1]) {
					graph[gC][0] = i;
					graph[gC++][1] = j;
				}
			}
		}
		
		edges = new Edge[2 * gC];
		for(int i = 0; i < gC; i++) {
			edges[2 * i] = new Edge();
			edges[2 * i].from = graph[i][0];
			edges[2 * i].to = graph[i][1];
			edges[2 * i].d = calcDistance(graph[i][0], graph[i][1]);
			edges[2 * i].index = i;
			
			edges[2 * i + 1] = new Edge();
			edges[2 * i + 1].from = graph[i][1];
			edges[2 * i + 1].to = graph[i][0];
			edges[2 * i + 1].d = calcDistance(graph[i][0], graph[i][1]);
			edges[2 * i + 1].index = i;
		}
		
		Arrays.sort(edges);
	}
	
	public Kruskal(int[][] pts, int[][] gr) {
		points = pts;
		graph = gr;
		buildGraph();
	}
	
	Random r = new Random();
	private int[] p = new int[1024];

	private int getPart(int x) {
		if(p[x] == x) return x;
		return getPart(p[x]);
	}
	
	private void merge(int x, int y) {
		x = getPart(x);
		y = getPart(y);
		if(r.nextBoolean()) {
			p[x] = y;
		} else {
			p[y] = x;
		}
	}
	
	public ShortestPathSolution[] solve() {
		
		for(int i = 0; i < p.length; i++) {
			p[i] = i;
		}
		
		ArrayList<ShortestPathSolution> sols = new ArrayList<ShortestPathSolution>();
		ShortestPathSolution sps = new ShortestPathSolution(graph, points);

		for(int i = 0; i < edges.length; i++) {
			if(getPart(edges[i].from) == getPart(edges[i].to)) continue;
			sols.add(sps);
			merge(edges[i].from, edges[i].to);
			sps = sps.copy();
			sps.edgeColor[edges[i].index] = "#00FF00";
		}
		sols.add(sps);
		
		ShortestPathSolution[] solutions = new ShortestPathSolution[sols.size()];
		for(int i = 0; i < sols.size(); i++) {
			solutions[i] = sols.get(i);
		}
		
		return solutions;
	}
	
	public static ShortestPathSolution[] getMST(int[][] points, int[][] edges) {
		Kruskal d = new Kruskal(points, edges);
		return d.solve();
	}
	
}
