
public class PriorityQ<T extends Comparable<T>> {

	private static boolean DEBUG = false;
	
	private int size = 0;
	private T[] heap =  (T[]) new Comparable[20];
	
	public int size() {return size;}
	
	public T dequeue() {
		
		if (isEmpty())
			return null;
		
		T temp = heap[1];
		heap[1] = heap[size];		
		heap[size] = null;
		size--;
		pushDn(1);
		return temp;
		
		
	}
	
	
	public void enqueue(T t) {
		
		if (size == heap.length -1)
			resize(size*2);
	
		size++;
		
		heap[size] = t;
		
		
		pushUp(size);
		
		p(this.toString());
		
		return ;
		
	}
	
	public boolean isEmpty() {return size == 0;}
	
	
	@Override
	public String toString(){
		
		String s = "(size = "+ size+ ") {";
		
		for (int i = 1; i <= size; i++)
			s += (heap[i] + " ");
	
		s += "}";
		
		return s;
		
	}
	
	private void resize(int newSize){
		
		if (newSize <= size)
			return;
		
		T[] temp = (T[]) new Comparable[size+1];
		
		for (int idx = 1; idx <= size; idx++)
			temp[idx] = heap[idx];
		
		heap = (T[]) new Comparable[newSize];
		
		for (int idx = 1; idx <= size; idx++)
			heap[idx] = temp[idx];
		
		
		
		
	}
	
	
	
	private void pushUp(int i){
	    while (i > 1 && heap[i/2].compareTo(heap[i]) >0) {
            swap(i, i/2);
            i /= 2;
        }
	}
	
	
	
	
	private void pushDn(int i){
		int j;
        while ((j = i*2) <= size) {
        	if (j < size && heap[j].compareTo(heap[j+1]) >0) 
            	j++;
            if (heap[j].compareTo(heap[i]) >0) 
            	break;
            swap(i, j);
            i = j;
        }
		
		
		
	}

	public static void main(String[] args){
		
		PriorityQ <Integer>Q = new PriorityQ<Integer>();
		
		Q.enqueue(6);
		Q.enqueue(2);
		Q.enqueue(8);
		Q.enqueue(17);
		Q.enqueue(5);
		Q.enqueue(9);
		Q.enqueue(11);
		Q.enqueue(1);
		Q.enqueue(3);
		Q.enqueue(25);
										
		
		while (!Q.isEmpty()){
			//p(Q.toString());
			System.out.println(Q.dequeue());
		}
			
		
	}

	
	private void swap(int i, int j) {
	        T temp = heap[i];
	        heap[i] = heap[j];
	        heap[j] = temp;
	}
	
	
	private static void p(String s){
		if (DEBUG)
			System.out.println(s);
		
	}
	
	
}
