package dsaAssignment;

import java.util.Random;

public class NodePlacement
{
    //name of node (Airport Name)
    private String name;

    // value of  x in coordinate
    private int x;

    //value of y in coordinate
    private int y;

    //Constructor
    public NodePlacement(String coordinateName)
    {
        this.name = coordinateName;
        this.x = new Random().nextInt(650 ) ;
        this.y = new Random().nextInt(650 ) ;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

}
