package travelingSalesman;

/*Classes Uniform Cost and InClass Heuristic need a priority queue.
 This class creates that queue.*/

public class PriorityQueue {
	private int maxSize;
	private Node[] queArray;
	private int nItems;

	public PriorityQueue(byte size) {
		maxSize = size;
		// the largest array my computer can handle is one with
		// 8^9 slots
		queArray = new Node[(int) Math.pow(8, 9)];
		nItems = 0;
	}

	public void insert(Node newNode) {
		int i;

		if (nItems == 0) {// if no items
			queArray[nItems++] = newNode; // insert at 0
		} else { // if items
			for (i = nItems - 1; i >= 0; i--) { // start at end
				if (newNode.getCost() > queArray[i].getCost()) { // if new item
																	// larger
					queArray[i + 1] = queArray[i]; // shift upward
				} else
					break; // done shifting
			}
			queArray[i + 1] = newNode; // insert it
			nItems++;
		}
	}

	public Node remove() {
		return queArray[--nItems];
	}

	public boolean isEmpty() {
		return (nItems == 0);
	}

	public boolean isFull() {
		return (nItems == maxSize);
	}
}
