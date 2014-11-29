package travelingSalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {
	private static final int POPULATION_SIZE = 25;
	private static final int SAMPLE_POP_SIZE = 5;
	private static final double MUTATION_RATE = 0.015;
	private static final int NUMBER_OF_GENERATIONS = 1500000;

	private static byte[][] data;
	private static DistanceFinder distanceFinder;
	private static byte[][][] allDistances;
	private static Random random;
	private static byte[][] population;

	public Genetic() {
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
		// find the distance between all the cities
		allDistances = new byte[data.length][data.length][1];
		allDistances = distanceFinder.findAllDistances(data);

		// create a new 3-d array for the results
		byte[][][] results = new byte[2][data.length][data.length];
		// the data coming in is all the vertices so just put that into
		// results array
		byte[][] vertices = data; // the cities are vertices on graph
		results[0] = vertices;

		int bestTourCost = Integer.MAX_VALUE;
		byte[] bestTour = new byte[data.length + 1];

		for (int p = 0; p < 10; p++) {
			// create the initial population
			population = generatePopulation();

			// evolve the population for the number of generation
			for (int i = 0; i < NUMBER_OF_GENERATIONS; i++) {
				int weakestIndex = weakestLink(population); // find most unfit
				byte[] parent1 = findFitParent(population); // find suitable
															// parents
				byte[] parent2 = findFitParent(population);
				byte[] child1 = breed(parent1, parent2); // create first child

				population[weakestIndex] = child1; // insert child

				weakestIndex = weakestLink(population); // find another weak
														// link

				byte[] child2 = breed(parent2, parent1); // breed second child

				population[weakestIndex] = child2; // insert child

				// find another set of parents and breed two more children
				weakestIndex = weakestLink(population);
				byte[] parent3 = findFitParent(population); // find suitable
															// parents
				byte[] parent4 = findFitParent(population);
				byte[] child3 = breed(parent3, parent4); // create third child

				population[weakestIndex] = child3; // insert child

				weakestIndex = weakestLink(population); // find another weak
														// link

				byte[] child4 = breed(parent4, parent3); // breed fourth child

				population[weakestIndex] = child4; // insert child

				for (int j = 0; j < POPULATION_SIZE; j++)
					mutate(population[j]);
			}

			byte[] fittestTour = findFittest(population);

			if (findCost(fittestTour) < bestTourCost) {
				bestTour = fittestTour.clone();
				bestTourCost = findCost(fittestTour);
			}
		}
		System.out.println("The cost is " + findCost(bestTour));

		results[1] = setUpEdges(bestTour);

		return results;
	}

	// this method adds some variation to the population by swapping cities
	private void mutate(byte[] tour) {
		// only indices 1 to the second to last are swapped
		for (int i = 1; i < tour.length - 2; i++) {
			if (random.nextDouble() < MUTATION_RATE) {
				byte temp = tour[i];
				// only index from 1 to second to last can be selected
				int swapIndex = (1 + random.nextInt(tour.length - 2));
				tour[i] = tour[swapIndex];
				tour[swapIndex] = temp;
			}
		}
	}

	// this method combines two parent tours to make a child tour
	// a random range of indices from the first parent is added to the child
	// then the cities not present are added in order form the second parent
	private byte[] breed(byte[] parent1, byte[] parent2) {
		// initialize the child tour
		byte[] child = new byte[data.length + 1];

		// initialize the random start and finish indices
		int start = 0;
		int finish = 0;

		// find two random indices with the start being less than the
		// finish. Also do not select 0 or the very last index
		while (start >= finish) {
			start = (1 + random.nextInt(parent1.length - 2));
			finish = (1 + random.nextInt(parent1.length - 2));
		}

		// copy the range to the child
		for (int i = start; i <= finish; i++) {
			child[i] = parent1[i];
		}

		// create an arrayList of the second parent and remove the cities added
		// from the first parent
		ArrayList<Byte> parent2Array = new ArrayList<Byte>(parent2.length - 2);
		for (int i = 1; i < parent2.length - 1; i++)
			parent2Array.add(parent2[i]);
		for (int i = start; i <= finish; i++)
			parent2Array.remove((Byte) child[i]);

		// add the remaining indicies to the child
		if (finish == (child.length - 2)) { // just add to start
			for (int i = 1; i < start; i++)
				child[i] = parent2Array.get(i - 1);
		} else {
			int arrayIndex = 0;
			// add the indicies in two parts
			for (int i = 1; i < start; i++) {
				// add up to indicies already set
				child[i] = parent2Array.get(arrayIndex);
				arrayIndex++;
			}
			for (int i = finish + 1; i <= child.length - 2; i++) {
				// fill remaining after break
				child[i] = parent2Array.get(arrayIndex);
				arrayIndex++;
			}
		}
		return child;
	}

	// this method finds the cost of the current tour
	// this will determine the fitness of a tour
	private static int findCost(byte[] thisTour) {
		int totalCost = 0; // initialize variable
		for (int i = 0; i < (thisTour.length - 1); i++) {
			// sum the cost of traveling from one city to another
			totalCost += allDistances[thisTour[i]][thisTour[(i + 1)]][0];
		}
		return totalCost;
	}

	// this method generates a population or array of possible tours
	// the byteTour is an array of indices
	private byte[][] generatePopulation() {
		byte[][] newPop = new byte[POPULATION_SIZE][data.length + 1];
		ArrayList<Byte> newTour = new ArrayList<Byte>();
		// we want indices 1 through to the last city
		for (byte i = 1; i < data.length; i++) {
			newTour.add(i);
		}
		for (int j = 0; j < POPULATION_SIZE; j++) {
			Collections.shuffle(newTour);
			byte[] byteTour = new byte[data.length + 1];
			// the start city is index 0
			byteTour[0] = 0;
			// add middle cities
			for (byte i = 1; i < newTour.size() + 1; i++) {
				byteTour[i] = newTour.get(i - 1);
			}
			byteTour[data.length] = 0; // end at start city index 0
			newPop[j] = byteTour;
		}
		return newPop;
	}

	// this method return the fittest tour from a sample of the current
	// population
	private byte[] findFitParent(byte[][] population) {
		byte[][] samplePopulation = new byte[SAMPLE_POP_SIZE][data.length + 1];

		// populate the array with random tours
		for (int i = 0; i < SAMPLE_POP_SIZE; i++) {
			samplePopulation[i] = population[random.nextInt(POPULATION_SIZE)]
					.clone();
		}

		// select the fittest tour from the sample
		byte[] fittest = findFittest(samplePopulation);

		return fittest;
	}

	// this method finds the index of the least fit tour
	private int weakestLink(byte[][] population) {
		int index = 0; // initialize index to first tour
		byte[] weakest = population[0]; // initialize tour
		for (int i = 0; i < population.length; i++) {
			// if more unfit tour found, make it the weakest link
			if (findCost(weakest) < findCost(population[i]))
				index = i;
		}
		return index;
	}

	// this method finds the fittest tour within a given population
	private byte[] findFittest(byte[][] population) {
		byte[] fittest = population[0];
		for (int i = 1; i < population.length; i++) {
			// found a fitter tour
			if (findCost(fittest) > findCost(population[i]))
				fittest = population[i];
		}
		return fittest;
	}

	// this method takes the best tour and creates edges to be graphed
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
