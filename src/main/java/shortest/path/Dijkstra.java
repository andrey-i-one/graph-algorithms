package shortest.path;

import java.util.ArrayList;
import java.util.Arrays;

import common.ShortestPathSolution;

public class Dijkstra {

	private final int INF = 1000000000;
	
	class Edge {
		int from, to;
		int index;
		double d;
	}
	
	private Edge[] edges;
	private int[][] points;
	private int[][] graph;
	private int n;
	
	private int[][] a = new int[1024][1024];
	private int[] aSize = new int[1024];
	
	private double calcDistance(int p1, int p2) {
		double q = 
				(points[p1][0] - points[p2][0]) * (points[p1][0] - points[p2][0]) + 
				(points[p1][1] - points[p2][1]) * (points[p1][1] - points[p2][1]);
		return Math.sqrt(q);
	}

	private void buildGraph() {
		n = points.length;
		
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
		
		edges = new Edge[2 * gC + 2];
		for(int i = 0; i < gC; i++) {
			edges[2 * i] = new Edge();
			edges[2 * i].from = graph[i][0];
			edges[2 * i].to = graph[i][1];
			edges[2 * i].d = calcDistance(graph[i][0], graph[i][1]);
			edges[2 * i].index = i;
			
			a[edges[2 * i].from][aSize[edges[2 * i].from]++] = 2 * i;
			
			edges[2 * i + 1] = new Edge();
			edges[2 * i + 1].from = graph[i][1];
			edges[2 * i + 1].to = graph[i][0];
			edges[2 * i + 1].d = calcDistance(graph[i][0], graph[i][1]);
			edges[2 * i + 1].index = i;
			a[edges[2 * i + 1].from][aSize[edges[2 * i + 1].from]++] = 2 * i + 1;

		}
	}
	
	public Dijkstra(int[][] pts, int[][] gr) {
		points = pts;
		graph = gr;
		buildGraph();
	}
	
	private int[] f;
	private double[] d;
	private int[] p;
	
	public ShortestPathSolution[] solve(int from) {
		f = new int[n];
		d = new double[n];
		p = new int[n];
		Arrays.fill(p, -1);
		Arrays.fill(f, 0);
		Arrays.fill(d, INF);
		d[from] = 0;
		
		ArrayList<ShortestPathSolution> sols = new ArrayList<ShortestPathSolution>();
		
		ShortestPathSolution sps = new ShortestPathSolution(graph, points);
		
		while(true) {
			sols.add(sps);
			int index = -1;
			double best = INF;
			for(int i = 0; i < n; i++) {
				if(f[i] == 1) continue;
				if(best > d[i]) {
					best = d[i];
					index = i;
				}
			}
			if(index == -1) break;
			f[index] = 1;
			sps = sps.copy();
			if(p[index] != -1) sps.edgeColor[edges[p[index]].index] = "#00FF00";
			sps.verticeColor[index] = "#00FF00";
			for(int i = 0; i < aSize[index]; i++) {
				Edge e = edges[a[index][i]];
				if( d[e.to] > d[index] + e.d && f[e.to] == 0) {
					d[e.to] = d[index] + e.d;
					p[e.to] = a[index][i];
					sps.verticeColor[e.to] = "#0000FF";
				}
			}
		}
		sols.add(sps);
		sps = sps.copy();
		for(int i = 0; i < sps.edgeColor.length; i++) {
			if(sps.edgeColor[i].equals("#000000")) {
				//sps.edgeColor[i] = "#FFFFFF";
			}
		}
		sols.add(sps);
		
		ShortestPathSolution[] solutions = new ShortestPathSolution[sols.size()];
		for(int i = 0; i < sols.size(); i++) {
			solutions[i] = sols.get(i);
		}
		
		return solutions;
	}
	
	public static ShortestPathSolution[] getShortestPath(int[][] points, int[][] edges) {
		Dijkstra d = new Dijkstra(points, edges);
		return d.solve(0);
	}
	
}
