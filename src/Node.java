import java.util.LinkedList;
import java.util.Queue;

public class Node {
	public int info;
	public int g, h;
	public Queue<Integer> thread;

	public Node(int info, int g, int h){
		this.info = info;
		this.g = g;
		this.h = h;
		this.thread = new LinkedList<Integer>();
	}

	public void pushThread(int val){
		thread.add(val);
	}

	public void copyThread(Queue<Integer> newThread){
		this.thread = new LinkedList<Integer>(newThread);
	}

	public Integer popThread(){
		return thread.remove();
	}

}
