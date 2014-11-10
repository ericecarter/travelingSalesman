package travelingSalesman;

public class Node {
	private int cost; // this is the cost of getting to this node from the
						// initial city
	private byte[][] visitedCities;
	private Node Parent; // this is the node that generated the current node
	private byte[] city; // this is the current city

	public Node(Node parent, byte distance, byte[] city) {
		this.Parent = parent; // assign the parent
		this.city = city; // assign current city
		// the root will have a null parent
		if (parent != null){
			this.cost = parent.cost + distance; // assign cost
			this.visitedCities = parent.visitedCities; // assign previous cities
		}
		else
			this.cost = distance; // root cost will be zero
		
		
		// add the new city to the visited city array
		for (byte i = 0; i < this.visitedCities.length; i++) {
			if (this.visitedCities[i] == null) {
				this.visitedCities[i] = city;
				break;
			}
		}
	}

	public int getCost() {
		return cost;
	}

	public byte[] getCity() {
		return city;
	}

	public byte[][] getVisitedCities() {
		return visitedCities;
	}

	public Node getParent() {
		return Parent;
	}
}
