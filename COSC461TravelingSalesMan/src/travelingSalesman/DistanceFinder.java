package travelingSalesman;

public class DistanceFinder {

	public DistanceFinder() {
	}

	public byte[][][] findAllDistances(byte[][] vertices) {
		byte[][][] foundDistances = new byte[vertices.length][vertices.length][1];
		for (int i = 0; i < vertices.length; i++) {
			for (int j = 0; j < vertices.length; j++) {
				foundDistances[i][j][0] = distanceFormula(vertices[i],
						vertices[j]);
			}
		}
		return foundDistances;
	}

	private byte distanceFormula(byte[] start, byte[] finish) {
		byte distance = 0;
		distance = (byte) Math.sqrt(Math.pow(start[0] - finish[0], 2)
				+ Math.pow(start[1] - finish[1], 2));
		return distance;
	}
}
