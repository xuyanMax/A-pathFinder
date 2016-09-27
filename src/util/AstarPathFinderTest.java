package util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.border.EmptyBorder;

import java.util.Iterator;
import action.AstarPathFinder;
import entity.location;
import map.data;
import map.poly;

public class AstarPathFinderTest {
	

	
	public static void main(String[] args) {
		
	
		List <location> path = new ArrayList<location>();
		
		 AstarPathFinder AstarPath = new AstarPathFinder();
		
		data file = new data();
		double[][] node = file.getNodes();
		double[][] road = file.getRoad();	
		
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
	
		
		//System.out.println(randNumStart +" " + node[randNumStart][2]);
		//创建location类型源点、终点
		location startNode = new location(node[randNumStart][1], node[randNumStart][2],node[randNumStart][0] );
		location endNode = new location(node[randNumEnd][1], node[randNumEnd][2], node[randNumEnd][0]);
		System.out.println("Randomly selet two nodes from the dataset.");
		System.out.println("Start Node ID: "+ startNode.getId());
		System.out.println("End Node ID: " + endNode.getId() );
		
		System.out.println("The program is running.. Please wait.");
		path = AstarPath.findPath(startNode, endNode, road, node, poly_nodes);
		System.out.println("The path is displayed by node ID from back to forth");
		//System.out.println(path);
		if (!path.isEmpty()) {
			
			Iterator<location> it = path.iterator();
			int num=0;
			
			while(it.hasNext()) {
				
			
				System.out.println(num + ": "+ it.next().getId() );
				num++;
			}
		} else {
			System.out.println("No path is found.Try another nodes.");
		}

		//寻找相邻walkable节点
				
	}
	
	
	

}
