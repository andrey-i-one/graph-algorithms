package util;

import java.util.Random;

public class GraphGenerator {

	public static void generateEdges() {
		int n = 60;
		int m = 160;
		int[][] a = new int[n][n];
		Random r = new Random();
		System.out.print("[");
		for(int i = 0; i < m; i++) {
			int x = r.nextInt(n);
			int y = r.nextInt(n);
			int z = r.nextInt(100);
			if(a[x][y] != 0) continue;
			a[x][y] = a[y][x] = z;
			System.out.print("[" + x + "," + y + "],");
		}
		System.out.println("]");
		
		/*System.out.print("[");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(a[i][j] == 0) continue;
				System.out.print("[" + i + "," + j + "," + a[i][j] + "]");
			}
		}
		System.out.println("]");*/
	}
	
	public static void generatePoints(int pointsCount, int w, int h, int step) {
		int wC = w / step - 1;
		int hC = h / step - 1;
		Random r = new Random();
		System.out.print("{\"vertices\":[");
		for(int i = 0; i < pointsCount; i++) {
			if(i > 0) System.out.print(",");
			while(true) {
				int x = r.nextInt(wC) * step + step / 2;
				int y = r.nextInt(hC) * step + step / 2;
				if(x != y) {
					System.out.print("{\"color\":\"#FF0000\",\"x\":" + x + ",\"y\":" + y + "}");
					break;
				}
			}
			
		}
		System.out.println("],\"edges\":[]}");
	}
	
	public static void main(String[] args) {
		generatePoints(50, 1000, 600, 50);
	}
	
}
