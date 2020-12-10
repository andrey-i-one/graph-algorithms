package arrange;

import java.util.Arrays;
import java.util.Random;

import common.Solution;

public class GeneticSolver {

	private final int iterationsCount = 100;
	private final int solutionsCount = 100;
	private final int bestCount = 20;
	private final int step = 50;
		
	private Random r = new Random();
	private int n;
	private int w, h;
	private int[][] edges;
	
	private Solution[] solutions;
	private Solution[] tempSolutions;
	private Solution[] dynamic;

	public GeneticSolver(int n, int w, int h, int[][] edges) {
		this.n = n;
		this.w = w;
		this.h = h;
		this.edges = edges;
	}
	
	private Solution mergeSolutions(Solution a, Solution b) {
		Solution result = new Solution();
		result.point = new int[n][2];
		result.graph = a.graph;
		
		for(int i = 0; i < a.point.length; i++) {
			if(r.nextBoolean()) {
				result.point[i][0] = a.point[i][0];
				result.point[i][1] = a.point[i][1];
			} else {
				result.point[i][0] = b.point[i][0];
				result.point[i][1] = b.point[i][1];
			}
		}
		return result;
	}
	
	public Solution[] solveByGenetic() {
		
		dynamic = new Solution[iterationsCount];
		
		solutions = new Solution[solutionsCount];
		for(int i = 0; i < solutions.length; i++) {
			solutions[i] = Solution.generateSolution(n, w, h, edges, r, step);
		}
		
		tempSolutions = new Solution[bestCount * bestCount];
		
		for(int i = 0; i < iterationsCount; i++) {
			System.out.println(i);
			Arrays.sort(solutions);
			dynamic[i] = solutions[0];
			
			int tempCount = 0;
			for(int j = 0; j < bestCount; j++) {
				for(int k = 0; k < bestCount; k++) {
					tempSolutions[tempCount++] = mergeSolutions(solutions[j], solutions[k]);
				}
			}
			
			Random r = new Random();
			for(int j = 0; j < tempSolutions.length; j++) {
				if(r.nextDouble() < 0.3) {
					tempSolutions[j].mutate(w, h, r, 10, step);
				}
				if(r.nextDouble() > 0.5) {
					tempSolutions[j] = Solution.generateSolution(n, w, h, edges, r, step);
				}
			}
			
			Arrays.sort(tempSolutions);
			for(int j = bestCount; j < solutions.length; j++) {
				solutions[j] = tempSolutions[j - bestCount];
			}
			
		}
		
		return dynamic;
	}

}
