package travelingSalesman;

/*This Class carrys out the Greedy Algorithm.  It's primary function is to
return a 3-D array that will be graphed.*/

public class Greedy {
	private byte[][] data;
	private RandomNumberGenerator randomNumberGenerator;
	private byte[] visitedCities;
	private byte currentCity;
	private byte startingCity;
	private VisitedCityFinder visitedCityFinder;
	private DistanceFinder distanceFinder;
	private byte[][][] allDistances;
	private int totalDistance;

	public Greedy() {
		randomNumberGenerator = new RandomNumberGenerator();
		visitedCityFinder = new VisitedCityFinder();
		distanceFinder = new DistanceFinder();
	}

	public byte[][] getData() {
		return data;
	}

	public void setData(byte[][] data) {
		this.data = data;
	}

	private byte findNearestUnVisitedCity(byte currentCity) {
		byte indexOfNearestCity = 0;
		byte currentDistanceLength = Byte.MAX_VALUE;
		for (byte i = 0; i < data.length; i++) {
			// first check to see if city has been visited
			// then check to see if city is closer than current
			// closest city
			if (!visitedCityFinder.hasCityBeenVisited(visitedCities,
					(byte) (i + 1))
					&& allDistances[currentCity][i][0] < currentDistanceLength) {
				currentDistanceLength = allDistances[currentCity][i][0];
				indexOfNearestCity = i;
			}
		}
		return indexOfNearestCity;
	}

	/*
	 * this algorithm performs the greedy algorithm and puts the results into a
	 * 3 dimensional array. The first index in the array is the 2 dimensional
	 * array of vertices i.e. an array of [[index],[x,y] while the second index
	 * is a 2-d array of edges in the form of [[index],[starting vertex,ending
	 * vertex, distance]
	 */
	public byte[][][] doThisAlgorithm() {
		// find the distance between all the cities
		allDistances = new byte[data.length][data.length][1];
		allDistances = distanceFinder.findAllDistances(data);

		// this byte is the count for the Visited Cities Array
		byte count = 0;

		// start tracking the total distance traveled
		totalDistance = 0;

		// create a new 3-d array for the results
		byte[][][] results = new byte[2][data.length][data.length];
		// the data coming in is all the vertices so just put that into
		// results array
		byte[][] vertices = data; // the cities are vertices on graph
		results[0] = vertices;

		// create a new byte array to place vertices' indexes when they have
		// been visited and a new byte [][] to hold edges
		visitedCities = new byte[data.length];

		// [edge number][starting vertex, finishing vertex, distance]
		byte[][] edges = new byte[data.length][3];

		// pick a random city to start at and add to visitedCities array
		// also capture the original city so we can get back to it
		currentCity = randomNumberGenerator.generateStartingNumber(data.length);
		startingCity = currentCity;
		// add one to value to bypass the fact that array is initialized
		// with all zeroes
		visitedCities[count] = (byte) (currentCity + 1);

		// the algorithm will run for one less than the total amount of
		// cities. At the end we will add one more edge from last city
		// to original city
		for (byte i = 0; i < data.length - 1; i++) {
			// initialize the variable of closestCity
			byte closestCity = 0;
			// call the method to find the closest city
			closestCity = findNearestUnVisitedCity(currentCity);
			// increase the distance traveled which is the distance
			// from current city to closest city
			totalDistance += allDistances[currentCity][closestCity][0];
			// create a new edge tuple
			byte[] newTuple = { currentCity, closestCity,
					allDistances[currentCity][closestCity][0] };
			edges[i] = newTuple;
			currentCity = closestCity;
			visitedCities[++count] = (byte) (currentCity + 1);
		}
		// create an edge from last city to starting city
		byte[] newTuple = { currentCity, startingCity,
				allDistances[currentCity][startingCity][0] };
		edges[count] = newTuple;

		// add edges to results array
		results[1] = edges;

		// Print out the total distance and starting city
		System.out.println("\nThe total distance traveled is " + totalDistance);
		System.out.println("The starting city was number "
				+ String.valueOf(startingCity));
		System.out.println();

		return results;
	}
}
