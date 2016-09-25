package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import action.AstarPathFinder;
import entity.location;

public class AstarPathFinderTest {
	

	
	public static void main(String[] args) {
		
	
		List <location> path = new ArrayList<location>();
		
		//int [][] map = maps.MAP1;
			
		 //location startLo = new location(1,1);
		
		// location destLo = new location(11,4);
		 //System.out.println(startLo);
		
		 AstarPathFinder AstarPath = new AstarPathFinder();
		 //System.out.println(destLo);
		 
		//path = AstarPath.findPath(startLo, destLo, map);
		
		
		//System.out.println(path);
		
//		int mat[][] = new int[3][4];
//		System.out.println(mat.length);
//		System.out.println(mat[0].length);
//		
//		Iterator<location> it = path.iterator();
//		while(it.hasNext()) {
//			
//		
//			System.out.println(it.next().toString() );
//		}
		//*************************************************************************
		//******************** read csv node file *********************************
		//*************************************************************************
		String csv_node = "/Users/apple/Downloads/giscup15data-shape/node.csv";
		
		BufferedReader br_node = null;
		
		String line ="";
		String csvSplitBy =",";
		
		String[][] node_import = new String[42409][3];
		
		double[][] node = new double [42408][3];
		
		try {
			br_node = new BufferedReader(new FileReader(csv_node));
			
			// 忽略数据第一行 列表名
			
			int i = 0;
			int w = -1;
			
			while((line = br_node.readLine()) != null) {
				
				//use , as separator
				String[] node_line = line.split(csvSplitBy);
				
				if ( i > 0 ) {
					
					for (int j=0;j<node_line.length;j++) {
						
						// node_import [i][j] = node_line[j];
						node[w][j] = Double.parseDouble(node_line[j]);
					}
				
				}
				for (int j=0; j < node_line.length; j++) {
					
					 node_import [i][j] = node_line[j];
					//System.out.println(Double.valueOf(node_line[j]).doubleValue());
				}
				
				
				
				//System.out.println(node[i][0]+"   "+node[i][1]+"   "+node[i][2]);
				
				i++;
				w++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Ok." + node.length + node[0].length);
		//*************************************************************************
		//************************ read csv road file *****************************
		//*************************************************************************
		
		String csv_road = "/Users/apple/Downloads/giscup15data-shape/road.csv";
		
		BufferedReader bf_road = null;
		
		double [][] road = new double[96850][5];
		
		try {
			
			bf_road = new BufferedReader(new FileReader(csv_road));
			int j = 0;
			int q = -1;
			
			while((line = bf_road.readLine()) !=null ) {
			
				String[] road_line = line.split(csvSplitBy);
				
				if ( j > 0) {
					
					for (int k=0; k<road_line.length; k++) {

						road[q][k] = Double.parseDouble(road_line[k]);
						
					}
					
					
				}
				j++;q++;
				
			}
					
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//依据id 随机从node 表中选择源点、终点
		
		Random r1 = new Random();
		
		int range = node.length - 1 + 1;
	
		int randNumStart =r1.nextInt(range) + 1;
		int randNumEnd = r1.nextInt(range) + 1;
	
		
		//System.out.println(randNumStart +" " + node[randNumStart][2]);
		//创建location类型源点、终点
		location startNode = new location(node[randNumStart][1], node[randNumStart][2],node[randNumStart][0] );
		location endNode = new location(node[randNumEnd][1], node[randNumEnd][2], node[randNumEnd][0]);
		
		System.out.println("Start Node ID: "+ startNode.getId());
		System.out.println("End Node ID: " + endNode.getId() );
		
		path = AstarPath.findPath(startNode, endNode, road, node);
		
		System.out.println(path);
		Iterator<location> it = path.iterator();
		while(it.hasNext()) {
			
		
			System.out.println(it.next().getId() );
		}

		//寻找相邻walkable节点
		
		
		
		
		
		

				
				
	}

}
