package travelingSalesman;

/*This Class determines whether a city has been visited yet.
 It's is used in many other classes.
 */
public class VisitedCityFinder {
	public VisitedCityFinder() {
	}

	public Boolean hasCityBeenVisited(byte[] cities, byte city) {
		for (byte i = 0; i < cities.length; i++) {
			if (cities[i] == city)
				return true;
		}
		return false;
	}
	
	public Boolean hasCityBeenVisited(byte[][] cities, byte[] city) {
		for (byte i = 0; i < cities.length; i++) {
			if (cities[i][0] == city[0] && cities[i][1] == city[1])
				return true;
		}
		return false;
	}
}
