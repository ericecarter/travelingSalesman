package travelingSalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulatedAnnealing {
	private static final int ROUNDS = 1500000;
	private static final int NUMBER_OF_TIMES_TO_DO_ALGORITHM = 200;
	private static int temperature = 60;

	private static byte[][] data; // the cities to be tested
	private static DistanceFinder distanceFinder;
	private static byte[][][] allDistances;
	private static Random random;

	public SimulatedAnnealing() {
		distanceFinder = new DistanceFinder();
		random = new Random();
	}

	public byte[][] getData() {
		return data;
	}

	@SuppressWarnings("static-access")
	public void setData(byte[][] data) {
		this.data = data;
	}

	public byte[][][] doThisAlgorithm() {
		int bestTourCost = Integer.MAX_VALUE;
		// find the distance between all the cities
		allDistances = new byte[data.length][data.length][1];
		allDistances = distanceFinder.findAllDistances(data);

		// create a new 3-d array for the results
		byte[][][] results = new byte[2][data.length][data.length];
		// the data coming in is all the vertices so just put that into
		// results array
		byte[][] vertices = data; // the cities are vertices on graph
		results[0] = vertices;
		byte[] bestTour = new byte[data.length + 1];

		for (int p = 0; p < NUMBER_OF_TIMES_TO_DO_ALGORITHM; p++) {
			// this byte array holds the indexs in order of how the cities
			// are visited. Index 0 is the starting city and index
			// data.length is the final city
			byte[] currentTour = new byte[data.length + 1]; // room for all
															// cities
															// plus the start
															// city
															// placed at the end

			// create a random tour to start with
			currentTour = setupTour();

			// set the temperature and number of tours at each "degree"

			while (temperature > 0) {
				for (int i = 0; i < ROUNDS; i++) {
					byte[] next = findNextTour(currentTour);
					int costOfCurrent = findCost(currentTour);
					int costOfNext = findCost(next);
					if (costOfNext < costOfCurrent)
						currentTour = next;
					else if (costOfNext == costOfCurrent) {
						// do not take new tour if it offers no improvement
					} else {
						if ((double) Math
								.exp(((double) costOfCurrent - (double) costOfNext)
										/ (double) temperature) > random
								.nextDouble())
							currentTour = next;
					}
				}
				temperature--; // decrease temperature
			}

			if (findCost(currentTour) < bestTourCost) {
				bestTour = currentTour.clone();
				bestTourCost = findCost(currentTour);
			}
		}
		// add edges to results so it can be graphed
		results[1] = setUpEdges(bestTour);

		// print out the found cost
		System.out.println("The cost is " + findCost(bestTour));
		return results;
	}

	// this method creates an arraylist of the indices of the cities
	// then the Collections shuffle method can be used to create a
	// random tour
	private static byte[] setupTour() {
		ArrayList<Byte> newTour = new ArrayList<Byte>();
		// we want indices 1 through to the last city
		for (byte i = 1; i < data.length; i++) {
			newTour.add(i);
		}
		Collections.shuffle(newTour);
		byte[] byteTour = new byte[data.length + 1];
		// the start city is index 0
		byteTour[0] = 0;
		// add middle cities
		for (byte i = 1; i < newTour.size() + 1; i++) {
			byteTour[i] = newTour.get(i - 1);
		}
		byteTour[data.length] = 0; // end at start city index 0
		return byteTour;
	}

	// this method finds the cost of the current tour
	private static int findCost(byte[] thisTour) {
		int totalCost = 0; // initialize variable
		for (int i = 0; i < (thisTour.length - 1); i++) {
			// sum the cost of traveling from one city to another
			totalCost += allDistances[thisTour[i]][thisTour[(i + 1)]][0];
		}
		return totalCost;
	}

	// this method random picks another tour to look at
	private byte[] findNextTour(byte[] currentTour) {
		byte[] newTour = currentTour.clone();
		// we take newTour.length - 2 so random will never pick the
		// third to last index
		// we add 1 so random never picks very first index
		// and now can pick up to the second to last index
		// start city (index 0) and end city(index data.length) are
		// never picked
		int swapStartIndex = (1 + random.nextInt(newTour.length - 2));
		if (swapStartIndex != (newTour.length - 2)) { // haven't selected second
														// to last index
			// swap to right
			byte temp = newTour[swapStartIndex];
			newTour[swapStartIndex] = newTour[swapStartIndex + 1];
			newTour[swapStartIndex + 1] = temp;
		} else { // instead swap to left
			byte temp = newTour[swapStartIndex];
			newTour[swapStartIndex] = newTour[swapStartIndex - 1];
			newTour[swapStartIndex - 1] = temp;
		}
		return newTour;
	}

	private byte[][] setUpEdges(byte[] currentTour) {
		// [edge number][starting vertex, finishing vertex, distance]
		byte[][] edges = new byte[data.length][3];

		// create new edges by linking adjoining cities
		for (byte i = 0; i < data.length; i++) {
			byte[] newTuple = { currentTour[i], currentTour[i + 1],
					allDistances[currentTour[i]][currentTour[i + 1]][0] };
			edges[i] = newTuple;
		}
		return edges;
	}
}
