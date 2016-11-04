package action;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import entity.location;
import entity.nodeGlengthComparator;


public class AstarPathFinder {
	
	private List<location> openList;
	private List<location> closeList;
	
	private PriorityQueue<location> OPEN;
	
	public AstarPathFinder(){
		
		// arraList 用于get set
		//linkedList 用于 remover add
//		openList = new ArrayList<location>();
		
		closeList = new ArrayList<location>();
		OPEN = new PriorityQueue<location>(500, new nodeGlengthComparator());
				
	}
	
	public List<location> findPath(location start, location dest, double[][]road, double[][]node, double[][]poly_nodes, 
									HashMap<String, Double[]> road_map, HashMap<Double, location> node_map) {
		
		List<location> path = new ArrayList <location>();
//		openList.add(start);
		OPEN.add(start);
		location current;
		double[] temp = new double[3];
		
		for(int i=0; i<node.length; i++) {
			for (int k=0; k<node[0].length;k++) 
				temp[k] = node[i][k];
			if (isInside(poly_nodes, temp)) {
				location inLocation = new location(node[i][1], node[i][2], node[i][0]);			
				closeList.add(inLocation);			
				System.out.println("Node (ID) " + inLocation.getId() + " is added to close list and removed from node hashmap.");				
			}
		}
		do{
			
//			current = getLowestFscoreLocation(openList);
			current = OPEN.poll();// retrieves and removes the best (lowest F value.)
			//System.out.println(current.getGlength());		
			closeList.add(current);		
			double G_tmp = 0;
			//openList.remove(current);	
			if (closeList.contains(dest)) break;	
			List <location> adjacentLocations = getWalkableAdjacentLocations(current, road, node, node_map);		
			for (location lo : adjacentLocations) {
				if (closeList.contains(lo)) 					
					continue;				
			    if (!OPEN.contains(lo)){
					
					//优化G 累加G值
					// +++ lo 到current 的G值
			    	G_tmp = lo.getGlength() + current.getGlength();
					lo.setGlength(G_tmp);
					
					//计算H值(欧几里得)并存储到lo节点
					lo.setHSteps(Hsteps(lo,dest));
					
					//重新计算并设置F值=G+H
					//同意 H G 计量单位
					lo.setFSteps(Hsteps(lo,dest) + lo.getGlength());
					
					//设置父节点
					lo.setPrevious(current);
					
					node_map.get(lo.getId()).setCostSoFar(G_tmp);
				

//					//测试
//					System.out.println(lo.getGlength() +" "+lo.getFSteps());
					
					//添加每一个相邻可通过节点到openList 
					//openList.add(lo);
					
					OPEN.add(lo);
					/*
					 * 新添加部分，更新walkable 对应openlist 使用priority queue
					 * openlist 使用arraylist时 注释掉
					 */
					
//					int index = adjacentLocations.indexOf(lo);
//					adjacentLocations.remove(index);
//					adjacentLocations.add(lo);
					//current.setNodeToEdge(lo);
					
					
				} else{
					
					/**
					 * openlist by ArrayList
					 * 
					 */
				
//					int index = openList.indexOf(lo);
//					location lo_compare = openList.get(index);
//					
//					List<location> adj = current.getEdge();
//					int index_adj = adj.indexOf(lo);
//					location lo_tmp = adj.get(index_adj);
//					
//					if(lo_tmp.getGlength() + current.getGlength() < lo_compare.getGlength()) {
//						
//						lo_compare.setGlength(lo_tmp.getGlength() + current.getGlength());
//						lo_compare.setFSteps(Hsteps(lo,dest) + lo_compare.getGlength());
//						lo_compare.setPrevious(current);
//						openList.remove(lo);
//						
//						openList.add(lo_compare);
					//	System.out.println("----");
						
					
					/**
					 * OPEN by PriorityQueue
					 * 
					 * 
					 */
//					Iterator iterator = OPEN.iterator();
//					while(iterator.hasNext()) {
//						if (iterator.next().equals(lo)))
//
//					}
//					List<location> adj = current.getEdge();
//					int index_adj = adj.indexOf(lo);
//					location lo_tmp = adj.get(index_adj);
//					System.out.println(lo_tmp.getGlength() +" "+lo.getGlength());
					//问题处在 lo_temp 与lo指向同一个对地址 值相同 lo_tmp.getGlength()==lo.getGlength()
					//地址指向问题 由于无法get到priority queue 中的lo 所以使用的诗walkable中的lo
					//因此，此lo为最原始状态，不包含积累的g值
					//todo 解决指向问题：1、遍历open
					if( lo.getGlength() + current.getGlength() < node_map.get(lo.getId()).getCostSoFar()){
						//OPEN.remove(lo);
						System.out.println("not");
						double tmp = lo.getGlength() + current.getGlength();
						lo.setGlength(tmp);
						lo.setFSteps(Hsteps(lo,dest) + lo.getGlength());
						lo.setPrevious(current);
						node_map.get(lo.getId()).setCostSoFar(tmp);
						OPEN.add(lo);
//						
					}
				}//else
				
			}//while(!openList.isEmpty() );
		}while(OPEN.size()!=0);
		
			location destination = null;
			
			//如closeList包含dest终点 则path找到
			
			if(closeList.contains(dest)){
				
				destination = current;
				
				destination.setGlength(current.getGlength());
				
				//todo：得到终点total Glength 消耗及 total 时间消耗。
				
				System.out.println("The total length between Node " + start.getId() + " and "+ dest.getId()+" is "+ destination.getGlength());
				
				path.add(destination);
//				path.push(destination);
				//从终点通过向前寻找父节点得到链表结构 path
				
				while(destination.getPrevious() != null){
					
					destination = destination.getPrevious();
					
				//path.push(destination);
					path.add(destination);
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
	// used only for openList not OPEN...
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
	
	
	// node_info 包括 横纵坐标
	
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
	//*** 
	//**初始化数据，
	//**1.初始化两个hashmap
	//**2.初始化每一个loc的所有相连接的loc到 edge 链表。
	
	
	
	
	

}
