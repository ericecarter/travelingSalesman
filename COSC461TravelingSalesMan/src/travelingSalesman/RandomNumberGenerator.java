package travelingSalesman;

import java.util.Random;

public class RandomNumberGenerator {
	Random rand;

	public RandomNumberGenerator() {
		rand = new Random();
	}

	public byte generateStartingNumber(int numberOfCities) {
		byte randomNumber = 0;
		randomNumber = (byte) rand.nextInt(numberOfCities);
		return randomNumber;
	}
}
