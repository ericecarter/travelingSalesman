package travelingSalesman;

/*This class is used in the InClassHeuristic which is an adjusted
 Uniform cost algorithm.  The difference is paths that cross other
 paths already established are not considered.*/

public class CrossingChecker {

	public CrossingChecker() {

	}

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	private boolean onSegment(byte[] pointP, byte[] pointQ, byte[] pointR) {
		if (pointP[0] <= Math.max(pointQ[0], pointR[0])
				&& pointP[0] >= Math.max(pointQ[0], pointR[0])
				&& pointP[1] <= Math.max(pointQ[1], pointR[1])
				&& pointP[1] >= Math.max(pointQ[1], pointR[1]))
			return true;
		return false;
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	int orientation(byte[] pointP, byte[] pointQ, byte[] pointR) {
		// See 10th slides from following link for derivation of the formula
		// http://www.dcs.gla.ac.uk/~pat/52233/slides/Geometry1x1.pdf
		int val = (pointQ[1] - pointP[1]) * (pointR[0] - pointQ[0])
				- (pointQ[0] - pointP[0]) * (pointR[1] - pointQ[1]);

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	private boolean doIntersect(byte[] p1, byte[] q1, byte[] p2, byte[] q2) {
		// for our purposes points on top of each other do not cross
		if ((p1[0] == p2[0] && p1[1] == p2[1])
				|| (p1[0] == q2[0] && p1[1] == q2[1])
				|| (q1[0] == p2[0] && q1[1] == p2[1])
				|| (q1[0] == q2[0] && q1[1] == q2[1])) {
			return false;
		}

		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and p2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false; // Doesn't fall in any of the above cases
	}

	public boolean foundACross(Node currentCity, byte[] city) {
		byte[][][] edges = new byte[currentCity.getVisitedCities().length][currentCity
				.getVisitedCities().length][2];
		byte[] start, end;
		byte index;
		index = 0;
		while (currentCity.getParent() != null) { // end at root
			// find the index of the start and end cities
			start = currentCity.getParent().getCity();
			end = currentCity.getCity();
			edges[index] = new byte[][] { start, end };
			index++;
			currentCity = currentCity.getParent();
		}

		for (byte i = 0; i < edges.length; i++) {
			if (doIntersect(edges[i][0], edges[i][1], city,
					currentCity.getCity())) {
				return true;
			}
		}
		return false;
	}
}
