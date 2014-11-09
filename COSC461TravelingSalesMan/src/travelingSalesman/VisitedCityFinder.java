package travelingSalesman;

public class VisitedCityFinder {
	public VisitedCityFinder() {
	}

	public Boolean hasCityBeenVisited(byte[] cities, byte city) {
		boolean foundCity = false;
		for (byte i = 0; i < cities.length; i++) {
			if (cities[i] == city)
				foundCity = true;
		}
		return foundCity;
	}
	
	public Boolean hasCityBeenVisited(byte[][] cities, byte[] city) {
		boolean foundCity = false;
		for (byte i = 0; i < cities.length; i++) {
			if (cities[i][0] == city[0] && cities[i][1] == city[1])
				foundCity = true;
		}
		return foundCity;
	}
}
