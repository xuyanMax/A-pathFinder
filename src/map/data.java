package map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import entity.location;

public class data {

    private double[][] nodes = new double[42408][3];

    //road length 5 -> 6
    //road[][road[0].length] 表示该结点 是否支持单双向。0：false；1:true
    private double[][] road = new double[96850][6];


    //hashMap only accepts Object as key and value, does not allow primitive data types as arguments.
    private HashMap<Double, location> node_map;
    private HashMap<String, Double[]> road_map;

    public data() {


        //key:node_id, value: location
        node_map = new HashMap<Double, location>(nodes.length * 2);

        //key:startNode_id, value:String a = {"endNode_id","Glength","spd"};
        road_map = new HashMap<String, Double[]>(road.length * 2);


        String csv_node = "/Users/apple/Downloads/giscup15data-shape/node.csv";

        BufferedReader br_node = null;

        String line = "";
        String csvSplitBy = ",";

        //String[][] node_import = new String[42409][3];

        //double[][] node = new double [42408][3];

        try {
            br_node = new BufferedReader(new FileReader(csv_node));

            // 忽略数据第一行 列表名

            int i = 0;
            int w = -1;

            while ((line = br_node.readLine()) != null) {

                //use , as separator
                String[] node_line = line.split(csvSplitBy);

                if (i > 0) {

                    for (int j = 0; j < node_line.length; j++) {

                        // node_import [i][j] = node_line[j];
                        this.nodes[w][j] = Double.parseDouble(node_line[j]);
                    }

                }

                i++;
                w++;
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        //*************************************************************************
        //************************ read csv road file *****************************
        //*************************************************************************

        String csv_road = "/Users/apple/Downloads/giscup15data-shape/road.csv";

        BufferedReader bf_road = null;

        //double [][] road = new double[96850][5];

        try {

            bf_road = new BufferedReader(new FileReader(csv_road));
            int j = 0;
            int q = -1;

            while ((line = bf_road.readLine()) != null) {

                String[] road_line = line.split(csvSplitBy);

                if (j > 0) {

                    for (int k = 0; k < road_line.length; k++) {

                        this.road[q][k] = Double.parseDouble(road_line[k]);

                    }
                }
                j++;
                q++;

            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        //＊＊＊＊＊＊＊＊＊＊＊＊＊＊
        initData(nodes, road);

    }

    public double[][] getNodes() {

        return this.nodes;

    }

    public double[][] getRoad() {

        return this.road;

    }

    public HashMap<String, Double[]> getRoadMap() {

        return this.road_map;
    }

    public HashMap<Double, location> getNodeMap() {


        return this.node_map;
    }

    public void initData(double[][] node, double[][] road) {


        //**1.
        String concate = new String();
        for (int i = 0; i < node.length; i++) {

            node_map.put(node[i][0], new location(node[i][1], node[i][2], node[i][0]));

        }
        //**1.
        for (int j = 0; j < road.length; j++) {

            concate = road[j][0] + "_" + road[j][1];

            road_map.put(concate, new Double[]{road[j][2], road[j][3], road[j][4]});
            //System.out.println(road_map.get(road[j][1])[1]);

        }

        //**2

        Double[] endID_temp;
        String concate_ = new String();


        for (int i = 0; i < node.length; i++) {

            for (int j = 0; j < road.length; j++) {

                if (node[i][0] == road[j][1]) {

                    concate_ = road[j][0] + "_" + node[i][0];
                    location locStart_temp = new location(node_map.get(node[i][0]));

                    if (road_map.containsKey(concate_)) {

                        //Double[]
                        endID_temp = (road_map.get(concate_));

                        location locEnd_temp = new location(node_map.get(endID_temp[0]));
                        locEnd_temp.setGlength(endID_temp[1]);
                        locEnd_temp.setSpd(endID_temp[2]);

                        locStart_temp.setNodeToEdge(locEnd_temp);

                        node_map.put(node[i][0], locStart_temp);

                    }
                }

            }
        }
    }

    // 暂时不能工作
    //NotSerializableException
    public void exportHashMap_Node() {

        try {

            FileOutputStream fileOut_node = new FileOutputStream("/Users/apple/Downloads/giscup15data-shape/node.ser");
            ObjectOutputStream objectOut_node = new ObjectOutputStream(fileOut_node);
            objectOut_node.writeObject(this.node_map);
            objectOut_node.flush();
            objectOut_node.close();

            fileOut_node.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");


        } catch (FileNotFoundException e) {


            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void exportHashMap_Road() {

        try {

            FileOutputStream fileOut_node = new FileOutputStream("/Users/apple/Downloads/giscup15data-shape/road.ser");
            ObjectOutputStream objectOut_node = new ObjectOutputStream(fileOut_node);
            objectOut_node.writeObject(this.road_map);
            objectOut_node.flush();
            objectOut_node.close();

            fileOut_node.close();
            System.out.printf("Serialized data is saved in road.ser");


        } catch (FileNotFoundException e) {


            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public HashMap<Double, location> importHashMap_Node() {

        HashMap<Double, location> node_mapping = new HashMap<Double, location>();

        try {
            FileInputStream fileIn = new FileInputStream("/Users/apple/Downloads/giscup15data-shape/node.ser");

            ObjectInputStream objIn_node = new ObjectInputStream(fileIn);

            node_mapping = (HashMap<Double, location>) objIn_node.readObject();

            objIn_node.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return node_mapping;

    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Double[]> importHashMap_Road() {

        HashMap<String, Double[]> road_mapping = new HashMap<String, Double[]>();

        try {
            FileInputStream fileIn = new FileInputStream("/Users/apple/Downloads/giscup15data-shape/road.ser");

            ObjectInputStream objIn_road = new ObjectInputStream(fileIn);

            road_mapping = (HashMap<String, Double[]>) objIn_road.readObject();

            objIn_road.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Getting a Set of Key-value pairs
        Set<Entry<String, Double[]>> entrySet = road_mapping.entrySet();

        // Obtaining an iterator for the entry set
        Iterator<Entry<String, Double[]>> it = entrySet.iterator();

        // Iterate through HashMap entries(Key-Value pairs)
        System.out.println("HashMap Key-Value Pairs : ");
        int i = 0;
        while (it.hasNext()) {
            //不按照顺序输出

            Map.Entry me = (Map.Entry) it.next();
            System.out.println("Key is: " + i + " " + me.getKey() +
                    " & " +
                    " value is: " + me.getValue().toString());
            i++;
        }
        return road_mapping;

    }

    int k = 0;

    public void road_bid_test() {

        for (int i = 0; i < road.length; i++) {

            for (int j = 0; j < road.length; j++) {


                if (road[i][0] == road[j][0] * (-1)) {

                    road[i][5] = road[j][5] = 1;
                    //83846
                    //System.out.println(k);
                    k++;
                } else {

                    //
                    road[i][5] = road[j][5] = 0;

                }

            }

        }
    }


}
