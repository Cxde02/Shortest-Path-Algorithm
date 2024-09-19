package dsaAssignment;

import java.awt.*;
import java.awt.geom.*;

public class DrawNode {
    // coordinates of x and y of an airport (node)
    private double x;
    private double y;

    // size of the node on GUI
    private double size = 10.0d;

    //To color the node
    private Color color;

    // node object to store relevant node data
    private NodePlacement node;

    // constructor to put the node inside the constructor of CanvasAll
    DrawNode(NodePlacement n) {
        this.node = n;
        this.x = n.getX();
        this.y = n.getY();

    }

    public void drawNode(Graphics2D g2d) {

        // to make node at the center of coordinates
        Ellipse2D.Double e = new Ellipse2D.Double(x - size * 0.5d, y - size * 0.5d, size, size);

        // set color of nodes to Yellow!
        g2d.setColor(color.YELLOW);

        // draw the node on GUI, otherwise it won't appear on the GUI
        g2d.fill(e);
    }

}

