package util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Iterator;
import action.AstarPathFinder;
import entity.location;
import map.data;
import map.poly;

public class AstarPathFinderTest {
	

	
	public static void main(String[] args) {
		
	
		List <location> path = new ArrayList<location>();
//		Stack <location> path = new Stack<location>();
	
		AstarPathFinder AstarPath = new AstarPathFinder();
		
		data file = new data();
		//file.importHashMap_Road();
		
		double[][] node = file.getNodes();
		double[][] road = file.getRoad();	
		HashMap<String, Double[]>road_map = file.getRoadMap();
		HashMap<Double, location>node_map = file.getNodeMap();
		
		// polygonal constraints represented by 5 nodes coordinates.
		// stored in 二维数组；五边形五个顶点均不属于nodes节点
		//因此，将所有在五边形内部的节点在初始化close list后加入close list
		poly poly = new poly();
		double[][]poly_nodes = poly.getPoly();
		//依据id 随机从node 表中选择源点、终点	
		Random r1 = new Random();		
		int range = node.length - 1 + 1;
		int randNumStart =r1.nextInt(range) + 1;
		int randNumEnd = r1.nextInt(range) + 1;
		//固定一组起点和终点结点的id
		int fixedNumStart =10661-1 ;
		int fixedNumEnd=16938-1;
		//创建location类型源点、终点
		//fixedNumStart, fixedNumEnd 分别替换randNumStart, randNumEnd.	
		location startNode = new location(node[fixedNumStart][1], node[fixedNumStart][2],node[fixedNumStart][0] );
		location endNode = new location(node[fixedNumEnd][1], node[fixedNumEnd][2], node[fixedNumEnd][0]);
		System.out.println("Randomly selet two nodes from the dataset.");
		System.out.println("Start Node ID: "+ startNode.getId());
		System.out.println("End Node ID: " + endNode.getId());	
		System.out.println("The program is running.. Please wait.");		
		//设置timer 
		long t_start = System.currentTimeMillis();
		path = AstarPath.findPath(startNode, endNode, road, node, poly_nodes, road_map, node_map);	
		long t_end = System.currentTimeMillis();	
		long t_spend = t_end - t_start;
		if (!path.isEmpty()) {
			System.out.println("The path is displayed by node ID from goal to source.");
			Iterator<location> it = path.iterator();
			int num=0;
			while(it.hasNext()) {
				System.out.println(num + ": "+ it.next().getId() );
			//	it.next();
				num++;
			}	
			System.out.println("The shortest path consists of "+num+" of nodes.");
			
		} else {
			System.out.println("No path is found.Try another nodes.");
		}
		//输出程序运行时间
		System.out.println("The program takes " + t_spend + " milliseconds.");
		//寻找相邻walkable节点			
	}
}
