package travelingSalesman;

/*This class is used by the UniformCost and InClassHeuristic.
 These two class contain algorithm where a priorityqueue is used.
 This class updates that priority queue.*/

public class FrontierGenerator {
	private VisitedCityFinder visitedCityFinder;
	private byte[][] data;
	private DistanceFinder distanceFinder;
	private byte[][][] allDistances;
	private CityFinder cityFinder;
	private CrossingChecker crossingChecker;

	public FrontierGenerator(byte[][] data) {
		visitedCityFinder = new VisitedCityFinder();
		this.data = data;
		distanceFinder = new DistanceFinder();
		allDistances = distanceFinder.findAllDistances(data);
		cityFinder = new CityFinder(data);
		crossingChecker = new CrossingChecker();
	}

	public void updateFrontier(PriorityQueue priorityQueue, Node currentCity) {
		byte index; // index used to find the parentNode's city

		// traverse through data to find the city
		index = cityFinder.findCity(currentCity.getCity());

		// traverse through data, adding nodes with unvisited cities
		for (byte i = 0; i < data.length; i++) {
			if (!visitedCityFinder.hasCityBeenVisited(
					currentCity.getVisitedCities(), data[i])) {
				Node newNode = new Node(currentCity, allDistances[index][i][0],
						data[i]);
				priorityQueue.insert(newNode);
			}
		}
	}
	
	public void updateFrontierCross(PriorityQueue priorityQueue, Node currentCity) {
		byte index; // index used to find the parentNode's city

		// traverse through data to find the city
		index = cityFinder.findCity(currentCity.getCity());

		// traverse through data, adding nodes with unvisited cities
		// also check if we have found a cross
		for (byte i = 0; i < data.length; i++) {
			if (!visitedCityFinder.hasCityBeenVisited(
					currentCity.getVisitedCities(), data[i])&&
					!crossingChecker.foundACross(currentCity,data[i])) {
				Node newNode = new Node(currentCity, allDistances[index][i][0],
						data[i]);
				priorityQueue.insert(newNode);//insert node based on its cost
			}
		}
	}
}
