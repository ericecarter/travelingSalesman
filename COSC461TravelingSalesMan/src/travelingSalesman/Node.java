package travelingSalesman;

public class Node {
	private int cost; // this is the cost of getting to this node from the
						// initial city
	private byte[][] visitedCities;
	Node Parent; // this is the node that generated the current node

	public Node(Node parent, byte distance, byte[] city) {
		this.Parent = parent; // assign the parent
		cost = parent.cost + distance; // assign cost
		this.visitedCities = parent.visitedCities; // assign previous cities
		// add the new city to the visited city array
		for (int i = 0; i < this.visitedCities.length; i++) {
			if (this.visitedCities[i] == null) {
				this.visitedCities[i] = city;
				break;
			}
		}
	}
	
	public int getCost() {
		return cost;
	}

	public byte[][] getVisitedCities() {
		return visitedCities;
	}

	public Node getParent() {
		return Parent;
	}
}
