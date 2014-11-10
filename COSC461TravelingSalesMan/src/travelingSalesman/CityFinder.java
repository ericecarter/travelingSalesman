package travelingSalesman;

/*this class is used to find the index of a city based off its cordinates
 * the index of a city in the data array is used for many functions
 */

public class CityFinder {
	private byte[][] data;

	public CityFinder(byte[][] data) {
		this.data = data;
	}

	public byte findCity(byte[] city) {
		byte index;
		for (index = 0; index < data.length; index++) {
			if (data[index][0] == city[0] && data[index][1] == city[1])
				break;
		}
		return index;
	}
}
