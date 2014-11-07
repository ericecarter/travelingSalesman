package travelingSalesman;

public class VisitedCityFinder {
	public VisitedCityFinder() {
	}

	public Boolean hasCityBeenVisited(byte[] cities, byte city) {
		boolean foundCity = false;
		for (int i = 0; i < cities.length; i++) {
			if (cities[i] == (city))
				foundCity = true;
		}
		return foundCity;
	}
}
