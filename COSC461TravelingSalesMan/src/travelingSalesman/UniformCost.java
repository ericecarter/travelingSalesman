package travelingSalesman;

public class UniformCost {
	private byte[][] data;
	private PriorityQueue frontier;
	private CityFinder cityFinder;
	private FrontierGenerator frontierGenerator;
	private DistanceFinder distanceFinder;
	private byte[][][] allDistances;
	private byte[] startingCity;
	private int totalDistance;
	private Node root;
	private Node solution;

	public UniformCost() {
		distanceFinder = new DistanceFinder();
	}

	public byte[][] getData() {
		return data;
	}

	public void setData(byte[][] data) {
		this.data = data;
	}

	/*
	 * this method takes a node in as an argument and travels back through the
	 * nodes so it can find edges also it tallies the total cost
	 */
	private byte[][] setEdges(Node solution) {
		// these byte are the indexes of the cities in the data array
		// one byte to store the current distance
		byte start, end, currentDistance;

		// find the distance between all the cities
		allDistances = new byte[data.length][data.length][1];
		allDistances = distanceFinder.findAllDistances(data);

		// set total distance to zero
		totalDistance = 0;

		// create the 2-d array that will be returned
		byte[][] edges = new byte[data.length][3];

		// start at second to last index and fill array to index 0
		byte index = (byte) (data.length - 2);

		// start with the solution node's city and work backwards
		Node currentCity = solution;

		while (currentCity.getParent() != null) { // end at root
			// find the index of the start and end cities
			start = cityFinder.findCity(currentCity.getParent().getCity());
			end = cityFinder.findCity(currentCity.getCity());

			// find current distance
			currentDistance = allDistances[start][end][0];
			totalDistance += currentDistance; // update total distance

			// add the new edge to the edge array
			edges[index] = new byte[] { start, end, currentDistance };

			index--; // decrement index
			currentCity = currentCity.getParent();
		}
		return edges;
	}

	/*
	 * Here is the actual uniform cost search it goes through the frontier
	 * examining nodes with the lowest cost first searching for the goal state.
	 * The Node with the goal state is returned
	 */

	private Node uniformCostSearch(Node root) {
		// instantiate the frontier generator
		frontierGenerator = new FrontierGenerator(data);

		// instantiate the frontier itself
		frontier = new PriorityQueue((byte) data.length);

		// update the frontier with the new root node
		frontierGenerator.updateFrontier(frontier, root);
		Node current;

		do {
			if (frontier.isEmpty())
				return null;
			else {
				// choose the lowest cost node from top of queue
				current = frontier.remove();

				// if that node has visited all the cities or
				// its visited cities array size is equal to the
				// data array size return the solution
				if (current.allVisited()) {
					return current;
				} else {
					frontierGenerator.updateFrontier(frontier, current);
				}
			}
		} while (true);
	}

	public byte[][][] doThisAlgorithm() {
		// initialize the city finder
		cityFinder = new CityFinder(data);

		// create a new 3-d array for the results
		byte[][][] results = new byte[2][data.length][data.length];

		// the data coming in is all the vertices so just put that into
		// results array
		byte[][] vertices = data; // the cities are vertices on graph
		results[0] = vertices;

		// the starting city is the first city
		startingCity = data[0];

		/*
		 * create a new node to serve as the root its parent is null, distance
		 * is 0 (distance to itself) and the city we currently are in is itself
		 * the node's visited city array should have the original city in it the
		 * constructer for a new Node does this
		 */
		root = new Node(data, startingCity);

		// next start the seach which will end in a solution
		solution = uniformCostSearch(root);

		// create a 2-d array to hold the edges
		byte[][] edges = new byte[data.length][3];

		// start at goal and capture all the edges
		edges = setEdges(solution);

		// create an edge from last city to starting city
		byte[] newTuple = {
				cityFinder.findCity(solution.getCity()),
				cityFinder.findCity(startingCity),
				allDistances[cityFinder.findCity(solution.getCity())][cityFinder
						.findCity(startingCity)][0] };

		// add the last edge to the edge array
		edges[data.length - 1] = newTuple;

		// update total distance
		totalDistance += allDistances[cityFinder.findCity(solution.getCity())][cityFinder
				.findCity(startingCity)][0];

		// add edges to results array
		results[1] = edges;

		// Print out the total distance and starting city
		System.out.println("\nThe total distance traveled is " + totalDistance);
		System.out.println("The starting city was number "
				+ cityFinder.findCity(startingCity));
		System.out.println();
		return results;
	}
}
