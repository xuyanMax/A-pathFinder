package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import entity.location;

public class astar_pq {
	
	private List<location> openList;
	private List<location> closedList;
	
	public astar_pq(){
		
		this.openList = new ArrayList<location>();
		this.closedList = new ArrayList<location>();
		
	}
	public Stack<location> findPath(location start, location dest, double[][]road, double[][]node, double[][]poly_nodes, 
			HashMap<String, Double[]> road_map, HashMap<Double, location> node_map){
				
		Stack<location> path = new Stack<location>();
		
		openList.add(start);
		location current;
		
		double[] temp = new double[3];
		
		for(int i=0; i<node.length; i++) {
			
			//二维数组赋值到一维数组，存储node结点id x y值
			//传入 isInside函数判断
			for (int k=0; k<node[0].length;k++) 
				temp[k] = node[i][k];
			
			
			if (isInside(poly_nodes, temp)) {
				
				location inLocation = new location(node[i][1], node[i][2], node[i][0]);
				
				closedList.add(inLocation);
				
				System.out.println("Node (ID) " + inLocation.getId() + " is added to close list and removed from node hashmap.");
				
			}
		}
		
		
		return path;
		
		
		
	}
	private List<location> getWalkableAdjacentLocations(location current, double[][]road, double[][]node, HashMap<Double, location>node_map){
		
		List<location> walkableLos = new ArrayList<location>();

		
		//********************* 对预处理的数据开始调用 *******************
		//******************   *******************   ****************
		if (node_map.containsKey(current.getId())) {
			
			//System.out.println("Contains.");
			
			location curr = node_map.get(current.getId());
			
			
			List<location> curr_edge = curr.getEdge();
			
			Iterator<location> it = curr_edge.iterator();
			
			while (it.hasNext()) {
				
				location curr_edge_node = new location(it.next());
				//测试部分
				//System.out.println(curr_edge_node.getGlength() +" spd: "+ curr_edge_node.getSpd());
				walkableLos.add(curr_edge_node);
				curr_edge_node.setPrevious(current);
			}
		}
		//********************* 对预处理的数据调用 结束 *******************
		//******************   *******************   ****************
		
		
		
		
		return walkableLos;
	}
	/**
	 * 找到F值最低的位置
	 * F = G + H
	 * @param openList
	 * @return
	 */
	
	//从walkable list 选出来后经过计算其中每个节点的F = G + H值
	//然后从中选择出F值最低的的节点作为新一轮的当前节点 current
	
	private location getLowestFscoreLocation(List<location> openList){
		
		if(openList == null || openList.size() == 0){
			return null;
		}
		double minFSteps = openList.get(0).getGlength();
		double tmpFSteps = 0;
		location lowestFlocation = openList.get(0);
		
		//从openlist遍历找到F值最小的lo
		//By Distance

		
		for(location lo : openList){
			
			tmpFSteps = lo.getGlength();
			
			if(tmpFSteps < minFSteps){
				
				
				minFSteps = tmpFSteps;
				
				lowestFlocation = lo;
			}
		}

		return lowestFlocation;
	}
	
	/**
	 * 评估H值
	 * @param current
	 * @param dest
	 * @return
	 */
	private double Hsteps(location current,location dest){ // H function
		
		double distanceX = dest.getX() - current.getX();
		
		double distanceY = dest.getY() - current.getY();
		
		if (distanceX < 0){
			
			distanceX = distanceX * -1;
			
		}
		if(distanceY < 0){
			distanceY = distanceY * -1;
		}
		
		//欧几里得距离估算 H值
		return 100*Math.sqrt(distanceX*distanceX+ distanceY*distanceY);
	}
	private boolean isInside(double[][] poly_nodes, double[] node) {
		
		boolean success =false;
		int numOfIntersection =0;
		
		for (int i=0, j = poly_nodes.length-1; i < poly_nodes.length; j = i++) {
			
			// 当前点的y坐标在polygon相邻两点组成边竖直方向(y分量)的中部
			//不同是满足 != 即node的y值在相邻两点y值的中间
			//i:1;j:2
			
			if ((poly_nodes[i][2] > node[2]) != (poly_nodes[j][2] > node[2])) {
				
				//交点x = (Y-y2)(x2-x1)/(y2-y1) + x2 > 测试点的横坐标
				// 计算所有比测试点横坐标小的的焦点，如果为奇数，则在多边形内部，否则为外部
				//references: http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
				
				if ( ( (node[2]-poly_nodes[j][2])*(poly_nodes[j][1]-poly_nodes[i][1])/(poly_nodes[j][2]-poly_nodes[i][2]) + poly_nodes[j][1] ) > node[1] ) {
					
					numOfIntersection++;
					
				}
			}
		}
		
		if (numOfIntersection % 2 == 1) 
			
			success = true;
		
		
		return success;
	}

}
