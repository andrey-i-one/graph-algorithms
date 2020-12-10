package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import arrange.BurningSolver;
import arrange.GeneticSolver;
import mst.Kruskal;
import mst.Prim;
import shortest.path.Dijkstra;

public class GraphArranger {

	public Object arrange(int width, int height, int[][] edges, String type) {
		int n = 0;
		for(int i = 0; i < edges.length; i++) {
			if(n < edges[i][0]) n = edges[i][0];
			if(n < edges[i][1]) n = edges[i][1];
		}
		n++;
		
		GeneticSolver gs = new GeneticSolver(n, width, height, edges);
		BurningSolver bs = new BurningSolver(n, width, height, edges);
		Solution[] result = null;
		
		if(type.equals("genetic")) {
			result = bs.solveByBurning();
		}
		if(type.equals("burning")) {
			result = gs.solveByGenetic();
		}
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < result.length; i++) {
			list.add(result[i].toHashMap());
		}
		res.put("graphs", list);
		return res;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GraphArranger ga = new GraphArranger();
		Object r = ga.arrange(500, 300, new int[][] {{0, 1}, {2, 1}, {2, 3}, {0, 3}, {3, 1}, {0, 2}}, "genetic");
	}
	
	@SuppressWarnings("unchecked")
	public static Object getArrangement(Object input) {
		GraphArranger ga = new GraphArranger();
		ArrayList<Object> graph = (ArrayList<Object>)(input);
		int[][] inp = new int[graph.size()][2];
		for(int i = 0; i < inp.length; i++) {
			ArrayList<Object> cur = (ArrayList<Object>)(graph.get(i));
			inp[i][0] = Integer.valueOf(cur.get(0).toString());
			inp[i][1] = Integer.valueOf(cur.get(1).toString());
		}
		ArrayList<Object> result = new ArrayList<Object>();
		Object r = null;
		r = ga.arrange(600, 1000, inp, "burning");
		result.add(r);
		r = ga.arrange(600, 1000, inp, "genetic");		
		result.add(r);
		
		return result;
	}
	
	public Object solveByDijkstra(int[][] points, int[][] edges) {
		ShortestPathSolution[] result = Dijkstra.getShortestPath(points, edges);
				
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < result.length; i++) {
			list.add(result[i].toHashMap());
		}
		res.put("graphs", list);
		return res;
	}
	
	public Object solveByPrim(int[][] points, int[][] edges) {
		ShortestPathSolution[] result = Prim.getMST(points, edges);
				
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < result.length; i++) {
			list.add(result[i].toHashMap());
		}
		res.put("graphs", list);
		return res;
	}
	
	public Object solveByKruskal(int[][] points, int[][] edges) {
		ShortestPathSolution[] result = Kruskal.getMST(points, edges);
				
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < result.length; i++) {
			list.add(result[i].toHashMap());
		}
		res.put("graphs", list);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static Object getShortestPath(Object input) {
		
		try {
			GraphArranger ga = new GraphArranger();
			
			LinkedHashMap<String, Object> graphCompleteDescription = (LinkedHashMap<String, Object>)(input);
	
			ArrayList<Object> points = (ArrayList<Object>)graphCompleteDescription.get("vertices");
			ArrayList<Object> edges = (ArrayList<Object>)graphCompleteDescription.get("edges");
			
			int[][] currentEdges = new int[edges.size()][2];
			int[][] currentPoints = new int[points.size()][2];
	
			for(int i = 0; i < points.size(); i++) {
				HashMap<String, Object> cur = (HashMap<String, Object>)points.get(i);
				int x = Integer.parseInt(cur.get("x").toString());
				int y = Integer.parseInt(cur.get("y").toString());
				currentPoints[i][0] = x;
				currentPoints[i][1] = y;
			}
	
			for(int i = 0; i < edges.size(); i++) {
				HashMap<String, Object> cur = (HashMap<String, Object>)edges.get(i);
				int x = Integer.parseInt(cur.get("from").toString());
				int y = Integer.parseInt(cur.get("to").toString());
				currentEdges[i][0] = x;
				currentEdges[i][1] = y;
			}
	
			ArrayList<Object> result = new ArrayList<Object>();
			Object r = ga.solveByDijkstra(currentPoints, currentEdges);
			result.add(r);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object getMST(Object input) {
		
		try {
			GraphArranger ga = new GraphArranger();
			
			LinkedHashMap<String, Object> graphCompleteDescription = (LinkedHashMap<String, Object>)(input);
	
			ArrayList<Object> points = (ArrayList<Object>)graphCompleteDescription.get("vertices");
			ArrayList<Object> edges = (ArrayList<Object>)graphCompleteDescription.get("edges");
			
			int[][] currentEdges = new int[edges.size()][2];
			int[][] currentPoints = new int[points.size()][2];
	
			for(int i = 0; i < points.size(); i++) {
				HashMap<String, Object> cur = (HashMap<String, Object>)points.get(i);
				int x = Integer.parseInt(cur.get("x").toString());
				int y = Integer.parseInt(cur.get("y").toString());
				currentPoints[i][0] = x;
				currentPoints[i][1] = y;
			}
	
			for(int i = 0; i < edges.size(); i++) {
				HashMap<String, Object> cur = (HashMap<String, Object>)edges.get(i);
				int x = Integer.parseInt(cur.get("from").toString());
				int y = Integer.parseInt(cur.get("to").toString());
				currentEdges[i][0] = x;
				currentEdges[i][1] = y;
			}
	
			ArrayList<Object> result = new ArrayList<Object>();
			Object r = ga.solveByPrim(currentPoints, currentEdges);
			result.add(r);
			r = ga.solveByKruskal(currentPoints, currentEdges);
			result.add(r);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
