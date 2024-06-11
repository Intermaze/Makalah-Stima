import java.util.Comparator;
import java.util.Scanner;

public class Main{
	private static final int MAX_NODE = 16;
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int start, end;
		int algorithm;
		
		
		System.out.println("Selamat datang ke program rute terpendek menuju labtek V! ");
		System.out.print("Mau mulai dari node berapa? [0-"+ (MAX_NODE-1) +"]: ");
		start = in.nextInt();
		end = 0; //Labtek V
	

		while (start < 0 || start >= MAX_NODE){
			System.err.println("Node tidak berada dalam rentang [0-"+ (MAX_NODE-1) +"]");
			System.out.print("Mau mulai dari node berapa? [0-"+ (MAX_NODE-1) +"]: ");
			start = in.nextInt();
		}

		System.out.println("====== List Algoritma ======");
		System.out.println("1: UCS (Uniform Cost Search)");
		System.out.println("2: GBFS (Greedy Best First Search)");
		System.out.println("3: A* Search");
		System.out.println("============================");
		System.out.print("Algorithm yang dipakai: ");
		algorithm = in.nextInt();

		long startTime = System.currentTimeMillis();

		RouteQueue rq;
		
		if (algorithm == 1){
			rq = new RouteQueue(new UCS(), start, end);
			System.out.println("Memakai UCS...");
		}
		else if (algorithm == 2){
			rq = new RouteQueue(new GBFS(), start, end);
			System.out.println("Memakai GBFS...");
		}
		else{
			//Kalau input selain 1 dan 2, automatis dipilih algoritma A*
			rq = new RouteQueue(new Astar(), start, end);
			System.out.println("Memakai A* Search...");
		}

		while (!rq.isDone()){
			rq.processNext();
		}

		if (rq.bufferIsEmpty()){
			System.out.println("Solusi tidak dapat diraih dari input.");
			rq.printVisitedOnly();
		}
		else{
			rq.printSolution();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Waktu eksekusi:  " + (endTime - startTime) + "ms");
		
		in.close();
	}

}

abstract class Algorithm implements Comparator<Node>{
	abstract int fn(Node n);

	public int compare(Node n1, Node n2){
		if (fn(n1) > fn(n2)) return 1;
		else if (fn(n1) < fn(n2)) return -1;
		else return 0;
	}
}

class UCS extends Algorithm{
	int fn(Node n){
		return n.g;
	}
}

class GBFS extends Algorithm{
	int fn(Node n){
		return n.h;
	}
}

class Astar extends Algorithm{
	int fn(Node n){
		return n.g + n.h;
	}
}

