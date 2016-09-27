package action;
import java.util.List;
import java.util.ArrayList;
import entity.location;


public class AstarPathFinder {
	
	private List<location> openList;
	private List<location> closeList;
	
	public AstarPathFinder(){
		
		openList = new ArrayList<location>();
		closeList = new ArrayList<location>();
		
		
	}
	
	public List<location> findPath(location start, location dest, double[][]road, double[][]node, double[][]poly_nodes) {
		
	// 一旦确定startNode，需要初始化所有nodes 到startNode的G 值和 H 值
	//不初始化则为0；
		
		List<location> path = new ArrayList <location> ();
		
		openList.add(start);
		
		location current;
		
		//预先遍历所有node结点，凡事在多边形区域内的结点，则加入到close list，起到排除的目的
		boolean isInside = false;
		double[] temp = new double[3];
		
		for(int i=0; i<node.length; i++) {
			
			//二维数组赋值到一维数组，存储node结点id x y值
			//传入 isInside函数判断
			for (int k=0; k<node[0].length;k++) {
				
				temp[k] = node[i][k];
			}
			isInside = isInside(poly_nodes, temp);
			
			if (isInside) {
				
				location inLocation = new location(node[i][1], node[i][2], node[i][0]);
				
				closeList.add(inLocation);
				System.out.println("Node (ID) " + inLocation.getId() + " is added to close list");
				
			}
		}

		
		do{
			
			current = getLowestFscoreLocation(openList);
			
			closeList.add(current);

			openList.remove(current);
			
			if (closeList.contains(dest)) break;
			
			List <location> adjacentLocations = getWalkableAdjacentLocations(current, road, node);
				
			for (location lo : adjacentLocations) {
				
				//GO next node
				if (closeList.contains(lo)) {
					
					continue;
				
				}
				// 如果相邻点不在openlist，则加入openlist，并对F=G+H 进行计算
				//G 可以从 node属性中读出，H 调用函数
			   if (!openList.contains(lo)){
					
					//优化G 累加G值
					// +++ lo 到current 的G值
					lo.setGlength(lo.getGlength() + current.getGlength());
					
					//计算H值(欧几里得)并存储到lo节点
					lo.setHSteps(Hsteps(lo,dest));
					
					//重新计算并设置F值=G+H
					//同意 H G 计量单位
					lo.setFSteps(Hsteps(lo,dest) + lo.getGlength());
					
					//设置父节点
					lo.setPrevious(current);
					
					//添加每一个相邻可通过节点到openList 
					openList.add(lo);
					
					
				} else{
					
				//比较 G值大小，从源点到lo点 vs 从源点到current点+到lo点
				//	
    			//        ****
				// ****   ****	
				// ****   ****
					
					for (int i=0;i<road.length;i++) {
						
						if (road[i][1] == current.getId() && road[i][2] == lo.getId()) {
							
							if (current.getGlength() + lo.getGlength() < road[i][3]) {
								
								lo.setPrevious(current.getPrevious());
							}
							
						}
						
					}
//					if(current.getGlength() + lo.getGlength() < lo.getGlength() + 500){
//						
//						//lo.setGlength(1);
//						lo.setPrevious(current.getPrevious());
//						
//    					}
					
				}
				
			}
		}while(!openList.isEmpty() );
		
			location destination = null;
			
			//如closeList包含dest终点 则path找到
			
			if(closeList.contains(dest)){
				
				destination = current;
				
				path.add(destination);
				
				//从终点通过向前寻找父节点得到链表结构 path
				
				while(destination.getPrevious() != null){
					
					destination = destination.getPrevious();
					
					path.add(destination);
				}
			}
			
		
		return path;
		
		
	}
	
	private List<location> getWalkableAdjacentLocations(location current, double[][]road, double[][]node){
	
		List<location> walkableLos = new ArrayList<location>();
		
		
		//搜索二维数组map中与 current节点相链接的节点
		//已知：current节点的ID，利用ID在road表中寻找(road表中starid、endid为对称关系。所以当下只用搜索startid)
		
		int i, j;
		
		for(i=0; i < road.length; i++) {
			
			// STARTID
			if (road[i][1] == current.getId()) {
				
				
				//从node二维数组中寻找与current node 相互连通node 的坐标值
				
				for (j=0; j<node.length; j++) {
					
					//依据endid找
					//创建location 存储id x y
					if (road[i][2] == node[j][0]) {
					
						location lo = new location(node[j][0], node[j][1], node[j][2], road[i][3], road[i][4]);
						
						//把该点加入到current节点的 edge列表
					     //current.setNodeToEdge(lo);
						//把该点加入walkable list
						
						//lo.setPrevious(current);
						walkableLos.add(lo);
						
					}
					
					
				}
				//walkableLos.add(new location(road[i][3], , id))
			}
		}
		
		
		
		
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
///////    /二维长度 x map[0].length 
	
//		if(x+1 < map[0].length && (map[x+1][y] == 0)){
//			lo = new location(x+1,y);
//			lo.setPrevious(current);
//			walkableLos.add(lo);
//		}
//		if(x-1>0 && (map[x-1][y] == 0)){
//			lo = new location(x-1,y);
//			lo.setPrevious(current);
//			walkableLos.add(lo);
//		}
//		//一维长度 	
//		
//		if(y+1 < map.length && (map[x][y+1] == 0)){
//			lo = new location(x,y+1);
//			lo.setPrevious(current);
//			walkableLos.add(lo);
//		}
//		if(y-1>0 && (map[x][y-1] == 0)){
//			lo = new location(x,y-1);
//			lo.setPrevious(current);
//			walkableLos.add(lo);
//		}
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////		
		
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
		double minFSteps = openList.get(0).getFSteps();
		double tmpFSteps = 0;
		location lowestFlocation = openList.get(0);
		
		//从openlist遍历找到F值最小的lo
		
		for(location lo : openList){
			
			tmpFSteps = lo.getFSteps();
			
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
		return Math.sqrt(distanceX + distanceY);
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
				
				//交点x = (Y-y2)(x2-x1)/(y2-y1) + x2 > 
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
