package common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Solution implements Comparable<Solution> {

	public static final int threshold = 200;
	
	public int[][] point;
	public int[][] graph;
	
	public Solution copy() {
		Solution s = new Solution();
		s.graph = new int[graph.length][2];
		s.point = new int[point.length][2];
		
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

	public static Solution generateSolution(int n, int w, int h, int[][] graph, Random r, int step) {
		Solution s = new Solution();
		s.point = new int[n][2];
		s.graph = graph;
		
		int wC = w / step - 1;
		int hC = h / step - 1;
		
		for (int i = 0; i < n; i++) {
			s.point[i][0] = r.nextInt(hC) * step + step / 2;
			s.point[i][1] = r.nextInt(wC) * step + step / 2;
		}
		return s;
	}
	
	public void mutate(int w, int h, Random r, int moveIndex, int step) {
		for(int i = 0; i < point.length; i++) {
			if(r.nextBoolean()) {
				//double c = r.nextInt(moveIndex);
				if(r.nextBoolean()) {
					point[i][0] += step;
					if(point[i][0] > h) point[i][0] = h - step;
				} else {
					point[i][0] -= step;
					if(point[i][0] < 10) point[i][0] = step;
				}
			}
			if(r.nextBoolean()) {
				//double c = r.nextInt(100);
				if(r.nextBoolean()) {
					point[i][1] += step;
					if(point[i][1] > w) point[i][1] = w - step;
				} else {
					point[i][1] -= step;
					if(point[i][1] < 10) point[i][1] = step;
				}
			}
		}
	}
	
	private boolean differentSide(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
		long k1 = (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1); 
		long k2 = (x4 - x1) * (y2 - y1) - (y4 - y1) * (x2 - x1);
		return k1 * k2 < 0;
	}
	
	private boolean isIntersect(int p1, int p2, int p3, int p4) {
		int x1 = point[p1][0];
		int y1 = point[p1][1];

		int x2 = point[p2][0];
		int y2 = point[p2][1];
		
		int x3 = point[p3][0];
		int y3 = point[p3][1];
		
		int x4 = point[p4][0];
		int y4 = point[p4][1];
		
		return differentSide(x1, y1, x2, y2, x3, y3, x4, y4) && differentSide(x3, y3, x4, y4, x1, y1, x2, y2);
	}

	private static int getPointSectionDistance(long x1, long y1, long x2, long y2, long x3, long y3) {		
		double A = (y2 - y1) * (y2 - y1);
		double B = (x2 - x1) * (x2 - x1);
		
		double d = Math.abs((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / Math.sqrt(A + B);
				
		return (int)d;
	}
	
	public int getDistance(long x1, long y1, long x2, long y2) {
		long q = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		return threshold * threshold - (int)q < 0 ? 0 : threshold * threshold - (int)q;
	}
	
	public double getScore() {
		double intersectionCount = 0;
		
		for(int i = 0; i < point.length; i++) {
			for(int j = i + 1; j < point.length; j++) {
				//intersectionCount += getDistance(point[i][0], point[i][1], point[j][0], point[j][1]);
			}
		}
		
		for(int i = 0; i < graph.length; i++) {
			for(int j = i + 1; j < graph.length; j++) {
				if(isIntersect(graph[i][0], graph[i][1], graph[j][0], graph[j][1])) {
					intersectionCount += threshold * threshold;
					//intersectionCount++;
				}
			}
			for(int j = 0; j < point.length; j++) {
				double h = getPointSectionDistance(
						point[graph[i][0]][0], 
						point[graph[i][0]][1], 
						point[graph[i][1]][0], 
						point[graph[i][1]][1], 
						point[j][0],
						point[j][1]);
				
				h = threshold - h;
				if(h < 0) h = 0;
				
				intersectionCount += h * h;
			}
		}
		return intersectionCount;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> currentGraph = new HashMap<String, Object>();
		List<Object> vertices = new ArrayList<Object>();
		for(int j = 0; j < point.length; j++) {
			HashMap<String, Object> currentVertice = new HashMap<String, Object>();
			currentVertice.put("x", point[j][0]);
			currentVertice.put("y", point[j][1]);
			currentVertice.put("color", "#FF0000");
			vertices.add(currentVertice);
		}
		currentGraph.put("vertices", vertices);
		currentGraph.put("score", getScore());

		List<Object> currentEdges = new ArrayList<Object>();
		for(int j = 0; j < graph.length; j++) {
			HashMap<String, Object> currentEdge = new HashMap<String, Object>();
			currentEdge.put("from", graph[j][0]);
			currentEdge.put("to", graph[j][1]);
			currentEdge.put("color", "#000000");
			currentEdges.add(currentEdge);
		}
		currentGraph.put("edges", currentEdges);
		return currentGraph;
	}
	
	@Override
	public int compareTo(Solution o) {
		return (int)(getScore() - o.getScore());
	}

	public static void main(String[] args) {
		Solution s = new Solution();
		
		s.point = new int[][] {{925, 375}, {475, 475}, {75, 225}, {875, 125}};
		s.graph = new int[][] {{0,1},{2,3},{1,2},{1,3},{0,2}};
		
		boolean d = s.isIntersect(1, 3, 0, 2);
		
		System.out.println(d);
		
		System.out.println(s.getScore());
	}
	
}
