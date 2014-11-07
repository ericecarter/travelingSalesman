package travelingSalesman;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDrawer extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1365, 765);
	private static mxGraph graph = new mxGraph();
	private static Object parent = graph.getDefaultParent();
	private static int first, second, x, y;

	public GraphDrawer() {
	}

	@SuppressWarnings("deprecation")
	public void drawGraph(byte[][][] graphData) {
		byte[][] vertices = graphData[0];
		byte[][] edges = graphData[1];
		insertVertices(vertices); // puts the vertices on graph
		insertEdges(edges); // draws the desired edges

		mxGraphComponent graphComponent = new mxGraphComponent(graph);

		GraphDrawer frame = new GraphDrawer();
		frame.add(graphComponent);
		frame.pack();
		frame.resize(DEFAULT_SIZE);
		frame.setVisible(true);

	}

	private void insertEdges(byte[][] edges) {
		Object[] cells = graph.getChildCells(parent);
		graph.getModel().beginUpdate();
		try {
			for (int i = 0; i < edges.length; i++) {
				graph.insertEdge(parent, null, Byte.toString(edges[i][2]) + " #"
						+ String.valueOf(i), cells[edges[i][0]],
						cells[edges[i][1]]);
			}

		} finally {
			graph.getModel().endUpdate();
		}

	}

	private void insertVertices(byte[][] vertices) {
		graph.getModel().beginUpdate();
		try {
			for (int i = 0; i < vertices.length; i++) {
				String string = null;
				first = vertices[i][0];
				second = vertices[i][1];
				string = getString(first, second, i);
				x = getx(first);
				y = gety(second);
				graph.insertVertex(parent, null, string, (double) x,
						(double) y, 3, 3);
			}
		} finally {
			graph.getModel().endUpdate();
		}

	}

	/*
	 * getx() and gety() methods translate the given x and y values to new x and
	 * y values. This translation creates a simulated 100x100 board with (0,0)
	 * in the upper left corner of the screen and (100,100) in the bottom right
	 * corner of the screen.
	 */
	private static int getx(int number) {
		int returnedValue = 0;
		returnedValue = Math.round(((float) number / 100) * 1290) + 17;
		return returnedValue;
	}

	private static int gety(int number) {
		int returnedValue = 0;
		returnedValue = Math.round(((float) number / 100) * 690) + 4;
		return returnedValue;
	}

	// this method creates a string to serve as the label for the vertex
	private static String getString(int first, int second, int vertexNumber) {
		String returnedString = null;
		returnedString = "(" + Integer.toString(first) + "   "
				+ Integer.toString(second) + ") \n"
				+ Integer.toString(vertexNumber);
		return returnedString;
	}
}
