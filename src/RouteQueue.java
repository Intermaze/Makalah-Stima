import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class RouteQueue {
	
	private	static int[][] graph = {
			{0, 0, 0, 0, 0, 0, 80, 0, 0, 40, 0, 0, 0, 0, 0, 125},		//Labtek V (0) 
			{0, 0, 0, 0, 0, 0, 0, 0, 208, 328, 150, 0, 0, 0, 0, 0},		//Parkir Sipil (1)
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 140, 0, 0, 0, 0},			//Gerbang Depan (2)
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 132, 0, 0, 0},			//Parkir SR (3)
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 114, 0},			//Gerbang Tamansari (4)
			{0, 0, 0, 0, 0, 0, 145, 0, 0, 0, 0, 0, 0, 0, 142, 0},		// 5
			{80, 0, 0, 0, 0, 145, 0, 85, 0, 0, 0, 0, 0, 0, 0, 150},		// 6
			{0, 0, 0, 0, 0, 0, 85, 0, 102, 0, 0, 0, 0, 0, 0, 0},		// 7
			{0, 208, 0, 0, 0, 0, 0, 102, 0, 165, 0, 0, 0, 0, 0, 0},		// 8
			{40, 328, 0, 0, 0, 0, 0, 0, 165, 0, 95, 0, 0, 145, 0, 0},	// 9
			{0, 150, 0, 0, 0, 0, 0, 0, 0, 95, 0, 66, 0, 0, 0, 0},		// 10
			{0, 0, 140, 0, 0, 0, 0, 0, 0, 0, 66, 0, 65, 0, 0, 0},		// 11
			{0, 0, 0, 132, 0, 0, 0, 0, 0, 0, 0, 65, 0, 94, 0, 0},		// 12
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 145, 0, 0, 94, 0, 0, 0},		// 13
			{0, 0, 0, 0, 114, 142, 0, 0, 0, 0, 0, 0, 0, 0, 0, 131},		// 14
			{125, 0, 0, 0, 0, 0, 150, 0, 0, 0, 0, 0, 0, 0, 131, 0}		// 15
		};
		
	private	static int[] heuristik = {0, 269, 280, 320, 325, 200, 70, 150, 173, 40, 135, 160, 192, 150, 220, 115};
	private static int end;
	private static final int MAX_NODE = 16;
		
	private PriorityQueue<Node> buffer;
	private Set<Integer> visited;

	public RouteQueue(Comparator<Node> alg, int start, int endInput){
		this.buffer = new PriorityQueue<Node>(alg);
		this.visited = new HashSet<Integer>();
		end = endInput;
		buffer.add(new Node(start, 0, heuristik[start]));
	}

	public void processNext(){
		Node curr = this.buffer.poll();
		ArrayList<Integer> nextInfo = findNext(curr.info);
		visited.add(curr.info);
		for (Integer val : nextInfo){
			if (!visited.contains(val)){
				int gn = curr.g + graph[curr.info][val];
				int hn = heuristik[val];
				Node n = new Node(val, gn, hn);
				n.copyThread(curr.thread);
				n.pushThread(curr.info);
				buffer.add(n);
				visited.add(val);
			}
		}
	}

	public boolean isDone(){
		if (bufferIsEmpty()) return true;
		else{
			//Kalau tidak kosong, cek dulu kalau ketemu solusi
			if (buffer.peek().info == end){
				return true;
			}
			//Kalau belum ketemu, lanjut dengan return false
			return false;
		}
	}

	//Prekondisi: rq.isDone()
	public void printSolution(){
		Node solution = this.buffer.poll();
		
		while (!solution.thread.isEmpty()){
			System.out.print(solution.popThread() + " -> ");
		}
		System.out.println(solution.info);
		System.out.println("Jumlah visited: " + visited.size());
		System.out.println("Jumlah jarak: " + solution.g + "m");
	}

	public void printVisitedOnly(){
		System.out.println("Jumlah visited: " + visited.size());
	}

	public boolean bufferIsEmpty(){
		return buffer.isEmpty();
	}


	private ArrayList<Integer> findNext(int node){
		ArrayList<Integer> next = new ArrayList<>();
		for (int i=0; i<MAX_NODE; i++){
			if (graph[node][i] != 0){
				next.add(i);
			}
		}
		return next;
	}

}
