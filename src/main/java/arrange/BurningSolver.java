package arrange;

import java.util.Random;

import common.Solution;

public class BurningSolver {

	private final int iterationsCount = 100;
	private final int step = 50;
		
	private Random r = new Random();
	private int n;
	private int w, h;
	private int[][] edges;
	
	private Solution[] dynamic;

	public BurningSolver(int n, int w, int h, int[][] edges) {
		this.n = n;
		this.w = w;
		this.h = h;
		this.edges = edges;
	}
	
	private Solution nextSolution(Solution a, int moveIndex) {
		Solution result = a.copy();
		result.mutate(w, h, r, moveIndex, step);
		return result;
	}
	
	public Solution[] solveByBurning() {
		
		dynamic = new Solution[iterationsCount];
		
		Solution s = Solution.generateSolution(n, w, h, edges, r, step);
		
		for(int i = 0; i < iterationsCount; i++) {
			dynamic[i] = s.copy();
			Solution c = nextSolution(s, 10);
			if(c.getScore() < s.getScore()) s = c; else {
				int difference = (int)(c.getScore() - s.getScore());
				double threshold = 1 / (Math.sqrt(difference) / (iterationsCount - i));
				if(threshold > 1) threshold = 1;
				if(r.nextDouble() * 10 < threshold) s = c;
			}
		}
		
		return dynamic;
	}

}
