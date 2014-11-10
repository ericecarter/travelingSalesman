package travelingSalesman;

public class Node {
	private int cost; // this is the cost of getting to this node from the
						// initial city
	private byte[][] visitedCities;
	private Node Parent; // this is the node that generated the current node
	private byte[] city; // this is the current city

	public Node(Node parent, byte distance, byte[] city) {
		Parent = parent; // assign the parent
		this.city = city; // assign current city

		cost = parent.cost + distance; // assign cost
		visitedCities = parent.visitedCities.clone(); // assign previous cities

		// add the new city to the visited city array
		// add it to the first spot that doesn't already hold a city
		for (byte i = 0; i < visitedCities.length; i++) {
			if (visitedCities[i][0] == 0 && visitedCities[i][1] == 0) {
				visitedCities[i] = city;
				break;
			}
		}
	}

	// this contructor is for making a root
	public Node(byte[][] data, byte[] city) {
		this.Parent = null; // the root has a null parent
		this.visitedCities = new byte[data.length][2]; // create new array
		visitedCities[0] = city; // add the city to the array
		this.cost = 0; // the cost to get to itself is zero
		this.city = city; // assign the current city
	}

	// this boolean checks to see if the visited cities array is full
	// with all non-zeros. This can only be true every index is
	// holding a city
	public Boolean allVisited() {
		for (byte i = 0; i < visitedCities.length; i++) {
			if (visitedCities[i][0] == 0 && visitedCities[i][1] == 0) {
				return false;
			}
		}
		return true;
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
