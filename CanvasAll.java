package dsaAssignment;

import java.awt.*;
import javax.swing.*;

public class CanvasAll extends JPanel {

    private NodePlacement[] arrayNode;
    private String normalRoutes;
    private String shortestRoutes;

    //To draw the nodes (Airports)
    private DrawNode[] dcArray;
    private PathLine[] dlArray;

    // constructor to get height and width of frame, and array of Node
    CanvasAll(NodePlacement[] nodeA, String normalPath, String shortestPath) {

        this.arrayNode = nodeA;
        this.normalRoutes = normalPath;
        this.shortestRoutes = shortestPath;

    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g2d);

        // AntiAliasing code
        // AntiAliasing is added so that the paths appear smoothly on the canvas
        RenderingHints rh = new RenderingHints(	RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        // to display node names
        // if there are no names, they can be displayed out of the frame
        g2d.translate(20, 20); // move the x and y 0,0 coordinate to x=20,y=20. So that the nodes do not appear on the border of the frame

        // for all paths on the canva
        drawLines(g2d, normalRoutes, false);

        // for shortest path : visible but below nodes (Behind the airports)
        drawLines(g2d, shortestRoutes, true);

        // plotting nodes : top level (On top)
        drawNodes(g2d);

    }

    public void drawNodes(Graphics2D g2d) {
        int arrayL = arrayNode.length;
        //Placement of the nodes' label
        int width = 55;
        int height = 55;

        // giving the arrayDrawNode the same length of arrayNode
        dcArray = new DrawNode[arrayL];

        // for each nodes, draw it in arrayNode.
        for (int i = 0; i < arrayL; i++) {
            // get the x and y coordinate of 1 node, to 1 drawNode object, which is an airport
            dcArray[i] = new DrawNode(arrayNode[i]);

            // Making labels for each nodes (Airports) and adding to the panel [ S - T ]
            JLabel nameNode = new JLabel(arrayNode[i].getName());

            // Changing font color of the labels to white
            nameNode.setForeground(Color.white);

            // Changing font style and font size
            nameNode.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
            
            //So that the labels appear near the nodes
            if (arrayNode[i].getX() < 250) {
                if (arrayNode[i].getY() < 250) {
                    // left x, y up quadrant
                    nameNode.setBounds(arrayNode[i].getX() - 15, arrayNode[i].getY() - 15, width, height);
                } else {
                    // left x, y down quadrant
                    nameNode.setBounds(arrayNode[i].getX() - 15, arrayNode[i].getY() - 10, width, height);
                }

            } else if (arrayNode[i].getX() >= 250) {
                if (arrayNode[i].getY() < 250) {
                    // right x, y up quadrant
                    nameNode.setBounds(arrayNode[i].getX() + 15, arrayNode[i].getY() - 15, width, height);
                } else {
                    // right x, y down quadrant
                    nameNode.setBounds(arrayNode[i].getX() + 15, arrayNode[i].getY() - 10, width, height);
                }
            }
            this.add(nameNode);

            // Implement it using function drawNode() in class DrawNode
            dcArray[i].drawNode(g2d);
        }
    }

    // To draw the lines (Routes)
    public void drawLines(Graphics2D g2d, String allRoute, boolean shortestRoute) {

        String[] array = allRoute.split("");
        dlArray = new PathLine[array.length];

        // Since there are n-1 edges for every n nodes, the for loop finishes at array. length-1
        for (int i = 0; i < array.length - 1; i++) {

            //check to see if index(Name of node) in the String array exists in the Nodes array
            int index1 = searchInd(array[i]);
            int index2 = searchInd(array[i + 1]);

            // if yes
            dlArray[i] = new PathLine(arrayNode[index1], arrayNode[index2], shortestRoute);

            // draw line by calling drawLine function from DrawLine Class.
            dlArray[i].drawLine(g2d);
        }
    }

    // The Search Algorithm
    public int searchInd(String label) {
        // one by one through the array, using the position search and nodes search functions.
        for (int i = 0; i < arrayNode.length; i++) {
            if (arrayNode[i].getName().equals(label)) {
                return i;
            }
        }
        return -1;
    }
}

