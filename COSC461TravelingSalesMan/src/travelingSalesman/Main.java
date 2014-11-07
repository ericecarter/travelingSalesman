package travelingSalesman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static final byte[][] data = new byte[120][2]; //holds all 120 cites
	private static byte[][][] graphData; // array of vertices and edges
	private static GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
	private static Greedy greedy = new Greedy();
	private static InClassHeuristic inClassHeuristic = new InClassHeuristic();
	private static SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
	private static UniformCost uniformCost = new UniformCost();
	private static GraphDrawer myGraphDrawer = new GraphDrawer();
	
	public static void main(String[] args) {
		readInFile(); // read in data from txt file
		int cmd = -1; // cmd used to control the program
		int numberOfCities = 0; // variable that holds number of cities to test
		Scanner keyboard = new Scanner(System.in);
		while (cmd != 0) {
			System.out.println("Select from the following options - ");
			System.out.println("Enter 1 to perform the Greed Algorithm");
			System.out.println("Enter 2 to perform the Uniform Cost Algorithm");
			System.out
					.println("Enter 3 to perform the In Class Heurstic Algorithm");
			System.out.println("Enter 4 to perform the Genetic Algorithm");
			System.out
					.println("Enter 5 to perform the Simulated Annealing Algorithm");
			System.out.println("Enter 0 to Quit");
			cmd = keyboard.nextInt();
			if (cmd != 0) {
				System.out.println("How many cities to evaluate?");
				numberOfCities = keyboard.nextInt();
			}
			// create a temp array to hold exact number of cities tested
			// and fill this array
			byte[][] temp = new byte[numberOfCities][2];
			for (int i = 0; i < numberOfCities; i++) {
				temp[i] = data[i];
			}
			switch (cmd) {
			case 0:
				keyboard.close();
				break;
			case 1:
				greedy.setData(temp);
				graphData = greedy.doThisAlgorithm();
				myGraphDrawer.drawGraph(graphData);
				break;
			case 2:
				uniformCost.setData(temp);
				graphData = uniformCost.doThisAlgorithm();
				myGraphDrawer.drawGraph(graphData);
				break;
			case 3:
				inClassHeuristic.setData(temp);
				graphData = inClassHeuristic.doThisAlgorithm();
				myGraphDrawer.drawGraph(graphData);
				break;
			case 4:
				geneticAlgorithm.setData(temp);
				graphData = geneticAlgorithm.doThisAlgorithm();
				myGraphDrawer.drawGraph(graphData);
				break;
			case 5:
				simulatedAnnealing.setData(temp);
				graphData = simulatedAnnealing.doThisAlgorithm();
				myGraphDrawer.drawGraph(graphData);
				break;
			}		
		}
	}

	private static void readInFile() {
		Scanner inputStream = null;
		byte i, first, second;
		i = 0;
		try {
			inputStream = new Scanner(new FileInputStream("TSPDataComma.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		while (inputStream.hasNext()) {
			first = (byte) inputStream.nextInt();
			second = (byte) inputStream.nextInt();
			data[i] = new byte[] { first, second };
			i++;
		}
		inputStream.close();
	}
}
