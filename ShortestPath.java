package dsaAssignment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.awt.*;
import javax.swing.*;

public class ShortestPath {
    private static int AmountNodes; // Number of nodes (Number of Airports)
    private static NodePlacement[] nodeArray;

    // label them with string and max nodes = 10
    private static String[] labelNodeArray ={"S", "B", "M", "P","A", "C", "I", "D", "O","T"};


    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);

        // Message displayed to the user
        System.out.println("*****************************************************************************************");
        System.out.println("*The program enables users to create several nodes (Airports), ranging from 3 to 10     *");
        System.out.println("*Then all the potential routes wil be created using algorithms                          *");
        System.out.println("*Using the algorithms, the shortest route will be determined and displayed              *");
        System.out.println("*The 10 Airports available in between which the shortest path will be calculated are:   *");
        System.out.println("*-------------------------------------------------------------------------------------  *");
        System.out.println("*--Sydney Kingsford Smith International Airport - S                                     *");
        System.out.println("*--Brisbane International Airport - B                                                   *");
        System.out.println("*--Melbourne International Airport - M                                                  *");
        System.out.println("*--Perth International Airport - P                                                      *");
        System.out.println("*--Adelaide International Airport - A                                                   *");
        System.out.println("*--Canberra International Airport - C                                                   *");
        System.out.println("*--Cairns International Airport - I                                                     *");
        System.out.println("*--Darwin International Airport - D                                                     *");
        System.out.println("*--Gold Coast Airport - G                                                               *");
        System.out.println("*--Townsville Airport - T                                                               *");
        System.out.println("*****************************************************************************************");
        System.out.println();
        System.out.println("--The number of nodes (Airports) should be more than 2--");
        System.out.println();

        do{
            System.out.print("Please enter the number of nodes (Airports): ");
            AmountNodes = sn.nextInt();
            if (AmountNodes <= 2)
                System.out.println("\nThe number of nodes input is invalid. It should be greater than 2");
        } while (AmountNodes <= 2);

        System.out.println();

        // generating random x-y Coordinates
        System.out.println("THE SYSTEM IS CHOOSING " + AmountNodes + " RANDOM X-Y COORDINATES TO GENERATE!");
        System.out.println("------------------------------------------------------------");

        // creating an array of node object
        nodeArray = new NodePlacement[AmountNodes]; // Empty array

        // Variable in which all nodes labels will be put
        String allNodes = ""; // empty

        // nodes creation --> start filling
        for (int i = 0; i < AmountNodes; i++) {
            nodeArray[i] = new NodePlacement(labelNodeArray[i]); // using Node constructor from class Node
            allNodes = allNodes + labelNodeArray[i];// string of all labels stuck together
            // why do we need the line 72? --> because the function getPermutationNodes needs a string
        }

        // To display the nodes alongside the randomly generated x-y coordinates (E.g: A (100,100))
        for (int i = 0; i < AmountNodes; i++) {
            NodePlacement n = nodeArray[i];
            System.out.print(n.getName() + " (" + n.getX() + "," + n.getY() + "). ");
        }

        // Need to use FACTORIAL to find the number of possible routes
        long possRoutesNum = factorial(AmountNodes);

        // Message to display number of possible routes
        System.out.println("\n\nNumber of routes accommodating " + AmountNodes + " nodes (Airports) are " + possRoutesNum);

        // set<string>:a list of string containing all the permutations of the
        // nodes(generates paths with no duplicate node)
        Set<String> set = getPermutationNodes(allNodes); // passing SBM to getPermutationNodes

        // AFTER PERMUTATION DONE --> SMB MSB MBS BMS BSM SBM

        // Possible routes displayed
        System.out.print("\nPossible routes are: " + set);

        // GUI::STRING ALLPATH --> to pass to GUI as String that contain all paths, blank because just initializing
        String allpath = "";

        // looping through the set to display its content in a certain way
        for (String path : set) {

            allpath += path; // filling all paths,concatenated
        }
        // GUI:: END OF ALLPATH
        System.out.println();

        // shortest path straight away since no choices-unique path
        // 2,3 --> (<4)--> (<=1 invalid as assumption)
        // Nodes valid: 2,3,4,5,....
        // 2,3
        // 4,5,....

        if (AmountNodes == 3) {
            System.out.println("Shortest routes are: ");
        } else {
            //System.out.println("\nPossible routes and their distance are: ");
        }

        // Calculating distance for each path String:path(key) Double:distance(key
        // value) //just initializing
        // distanceMap to store path and distance
        HashMap<String, Integer> distanceMap = new HashMap<>();

        // distanceShortestMap for shortest path
        HashMap<String, Double> distanceShortestMap = new HashMap<>();

        for (String path : set) // for each path that listed --> going to calculate distance
        {
            double distance = (int)calculateDistance(path);
            distanceMap.put(path, (int)distance); // PUT INTO distanceMap
            if (AmountNodes == 3) {
                System.out.printf("%s: %.2f\n", path, distance);
            }

            // Here we have already calculated each route's distance
            /*
             * EXAMPLE
             * SMB: 11.32
             * MSB: 11.32
             * MBS: 10.39
             * BMS: 10.39
             * BSM: 11.71
             * SBM: 11.71
             */

            distanceShortestMap.put(path, distance);
            // store the same as before in another Hash map
        }
        // System.out.println();

        // why to distinguish between number of nodes
        // why when number of nodes > 3 we should do like this
        // why less than 3 (that is 2 and 3) we need to do another way

        // using number of nodes as 3
        /*
         * WHAT OPTIONS ARE THERE: ACB BCA ABC CBA BAC CAB
         * EXAMINE ALL PATHS
         * Lets take first path ACB
         * Do we have another path where A starts and ends with B
         * NO
         *
         * //Using number of nodes as 4
         * BADC, BCDA, DCBA, DABC, CADB, DACB, CBDA, DBCA, ACBD, ADBC, BDAC, BCAD, ABCD,
         * ADCB, CDAB, CBAD, ACDB, ABDC, BACD, CDBA, BDCA, DCAB, CABD, DBAC
         * EXAMINE ALL PATHS
         * Lets take first path BADC
         * Do we have another path which B starts and ends with C
         * YES we have BDAC
         * Now we need to know which is the shortest among the 2 paths
         * BADC OR BDAC
         *
         */

        // >3 finding shortest path for every pair
        if (AmountNodes > 3) {
            // create an array list to store START and END
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < allNodes.length() - 1; i++) { // allNodes contain only this string: ABCD ,for 4 nodes
                for (int j = i + 1; j < allNodes.length(); j++) {
                    char start = allNodes.charAt(i); // i=0, allNodes[i]=A,
                    char end = allNodes.charAt(j); // when i=0,j=i+1=1 allNodes[j]=B
                    list.add(start + "-" + end); // A-B
                    list.add(end + "-" + start); // B-A
                }
            }
            // OUTPUT OF THE FOR LOOP
            System.out.println();
            System.out.println("Shortest Possible Route with a specific start and end node ");

            // calculating shortest path between starting node and every other node
            // for each start and end, we are going to retrieve the start and end
            // we have A-B
            // now we need to split
            for (String startend : list) // elements in list that startend will read= A-B,B-A,etc
            {

                String start = startend.split("-")[0]; // A //means for each "startend" in the "list", only choose the
                // letter before "-" in A-B, so A
                String end = startend.split("-")[1]; // B //means for each "startend" in the "list",only choose letter
                // after "-"
                // interesting enough,startend.split("-")[0]; and
                // startend.split("-")[1];
                // seems like "-" in split is like a pivot and does not count as
                // an
                // element, but only as a mark to know left or right handside

                Set<String> keyset = distanceMap.keySet(); // distanceMap.keySet().contain all the various possible
                // paths string(ABCD,ABDC etc)
                double distance = Double.MAX_VALUE; // maximum value the computed gives to a double value
                String path = null; // setting string to null,else it an error if we use uninialized string
                // going through loop to search which path starts with A and ends with B
                for (String s : keyset) {
                    if (s.startsWith("" + start) && s.endsWith("" + end)) {
                        if (distanceMap.get(s) < distance) {
                            distance = distanceMap.get(s);// shortest distance
                            path = s;// path
                        }

                    }
                }
                // printing the minimum
                System.out.print(start + " - " + end + "  ►►► Route: " + path);
                System.out.printf(" \t%.2f\n", distance);
                distanceShortestMap.put(path, distance);
            }

        }

        // obtain minimum distance of all paths
        // calculate shortest
        // retrieve the shortest
        // distSMAP content
        /*
         * EXAMPLE
         * ACB: 11.32
         * BCA: 11.32
         * ABC: 10.39
         * CBA: 10.39
         * BAC: 11.71
         * CAB: 11.71
         */
        Set<String> keyset = distanceShortestMap.keySet();
        double distance = Double.MAX_VALUE;

        // this for loop will give the shortest value of d which is 10.39
        for (String s : keyset) {
            if (distanceShortestMap.get(s) < distance) {
                distance = distanceMap.get(s);
            }
        }

        // this loop with check if there is only one path with the shortest distance
        // in this case, we have 2 paths giving us the shortest path
        // ABC: 10.39
        // CBA: 10.39

        // GUI:: MAKING SHORTEST PATH STRING
        // string that stored shortest path ,pass to GUI
        String shortPath = "";

        for (String path : keyset) {
            if (distanceShortestMap.get(path) == distance) {
                shortPath += path; // filling the shortest path(s),concatenated
                System.out.println();
                System.out.printf("Shortest Route → %s \t%.2f\n", path, distance);
            }
        }
        // GUI:: END OF STRING LOCS

        /*Making the GUI of the shortest Routes - Frame and Background*/
        // Setting Frame (Hardcoded)
        int height = 750;
        int width = 1000;

        JFrame frame = new JFrame();

        //Title of the frame --> Appears on the top border!
        frame.setTitle("GUI of Airports and Possible Routes");
        frame.setSize(width, height);

        CanvasAll dc = new CanvasAll(nodeArray, allpath, shortPath);

        //Set the background to a dark colour for more visual experience
        dc.setBackground(Color.DARK_GRAY);

        // remove layout of JPanel
        dc.setLayout(null);

        //Implemeting the frame
        frame.add(dc);

        //The application exits when clicking the close button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //To be able to see the frame(application), otherwise it does not open
        frame.setVisible(true);

        /*End of Frame and Background GUI*/

    }

    public static int getIndex(String label) {
        // go through the array one by one
        // look for the nodes and position
        // search function
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].getName().equals(label)) {
                return i;
            }
        }
        return -1;
    }

    public static double calculateDistance(String path) {
        double distance = 0;
        String[] array = path.split(""); // to obtain each letter A C B
        // System.out.println("Inside calculateDistance, path="+path+"\n----------");
        for (int i = 0; i < array.length - 1; i++)
        // why for loop we have got only A C
        // now we got C and B
        {
            int index1 = getIndex(array[i]);// getIndex
            int index2 = getIndex(array[i + 1]);
            distance = distance + euclideanDistance(nodeArray[index1], nodeArray[index2]);
            // retrieve info for A and C
        }
        return distance;
    }

    public static double euclideanDistance(NodePlacement n1, NodePlacement n2) {
        int x1 = n1.getX();
        int y1 = n1.getY();
        int x2 = n2.getX();
        int y2 = n2.getY();
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // generate possible paths
    // A recursive function which takes as parameter a string
    // which is all the node labels concatenated together.
    // For example, when N=4, the input string is ABCD.
    // The Set<String> data structure is used to store the
    // different permutations (paths).
    // Set is a data structure which stores unique values.
    // Hence there will no two similar paths.
    // this function simply does the permutations of ABC
    // Output : ABC ACB BAC BCA CAB CBA

    public static Set<String> getPermutationNodes(String str) {

        // create a set to avoid duplicate permutation
        Set<String> permutations = new HashSet<String>();

        // check if string is null
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            // terminating condition for recursion
            permutations.add("");
            return permutations;
        }

        // get the first character
        char first = str.charAt(0);

        // get the remaining substring
        String sub = str.substring(1);

        // make recursive call to getPermutationNodes()
        Set<String> words = getPermutationNodes(sub);

        // access each element from words
        for (String strNew : words) {
            for (int i = 0; i <= strNew.length(); i++) {

                // insert the permutation to the set
                permutations.add(strNew.substring(0, i) + first + strNew.substring(i));
            }
        }
        return permutations;
    }

    // simple method factorial --> example 3 ---> 6 (3!)
    public static long factorial(int num) {

        if (num == 0)
            return 1;

        else
            return num * factorial(num - 1);
    }

}