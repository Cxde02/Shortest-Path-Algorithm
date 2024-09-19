package dsaAssignment;

import java.awt.*;
import java.awt.geom.*;
import java.awt. Stroke;
import java.awt.BasicStroke;

public class PathLine {

    // Coordinate of Node 1
    private NodePlacement node1;

    // Coordinate of Node 2
    private NodePlacement node2;

    // Class used to increase the thickness of a line
    final static BasicStroke bigger = new BasicStroke(3.5f); // Increase thickness by 3.5f

    // boolean to know if path is shortest or not
    private boolean shortest;

    // constructor sets the Boolean and node
    PathLine(NodePlacement n1, NodePlacement n2, boolean shortest) {
        this.node1 = n1;
        this.node2 = n2;
        this.shortest = shortest;
    }

    // this function is used in CanvasAll, function::
    // paintComponent(Graphics g)
    public void drawLine(Graphics2D g2d) {
        // will use Line2D, much easier and better than path
        Line2D.Double Route = new Line2D.Double(node1.getX(), node1.getY(), node2.getX(), node2.getY());

        if (shortest == true) {

            g2d.setStroke(bigger); // To immediately see the shortest path, the width of the line is thicker
            g2d.setColor(Color.GREEN); // Green route for the shortest path

        } else if (shortest == false) {

            //increase thickness for the lines
            Stroke stk = new BasicStroke (1.5f); // line thickness
            g2d.setStroke (stk);
            
            // color for normal paths
            g2d.setColor(Color.WHITE);
            
        } else {
            System.out.println("Error in boolean Shortest value"); //If error occurs
        }

        // for drawing the path with g2d.draw(p); on the GUI.
        g2d.draw(Route);
    }
}

