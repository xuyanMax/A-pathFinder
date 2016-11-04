package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import entity.location;
import map.data;

public class BFS {
	private double[][] road;
	private double[][] nodes;
	private HashMap<Double, location>node_map;
	private HashMap<String, Double[]>road_map;
	private Queue<location> queue;

	public BFS(){
		
		data file = new data();
		node_map = file.getNodeMap();
		road_map = file.getRoadMap();
		road = file.getRoad();
		nodes = file.getNodes();
		queue = new LinkedList<location>();
		
	}
	public List<location> findPath(location start, location dest, double[][]road, double[][]node,
			HashMap<String, Double[]> road_map, HashMap<Double, location> node_map){
		
		List<location>path = new ArrayList<location>();
		List<location>adj = null;
		location current=null;
		start.setVisited();
		queue.add(start);
		
		while (!queue.isEmpty()) {
			current = queue.remove();
			if(current.getId() == dest.getId()) break;
			adj = getAdj(current);
			for (location lo:adj){
				if (!lo.getVisited()){
					queue.add(lo);
					lo.setPrevious(current);
				}
			}
		}
		location goal = dest;
		path.add(goal);
		while(goal.getPrevious()!=null){
			
			path.add(goal.getPrevious());
		}
		
		
		
		return path;
	}
	public List<location> getAdj(location lo) {
		List<location>adj = new ArrayList<location>();
		List<location>edge = new ArrayList<location>();
		location current = null;
		if (node_map.containsKey(lo.getId())){
			current = node_map.get(lo.getId());
			edge = current.getEdge();
			Iterator<location> it = edge.iterator();
			while (it.hasNext()) {
				location tmp = new location((location) it.next());
				adj.add(tmp);
			}
			
		}
		return adj;
		
	}
	
	public double[][] getRoad() {
		return road;
	}
	public double[][] getNodes() {
		return nodes;
	}
	public HashMap<Double, location> getNode_map() {
		return node_map;
	}
	public HashMap<String, Double[]> getRoad_map() {
		return road_map;
	}
	public static void main(String[] args){
		
		BFS bfs = new BFS();
		int fixedNumStart =10661-1 ;
		int fixedNumEnd=16938-1;
		double[][]node = bfs.getNodes();
		double[][]road = bfs.getRoad();
		HashMap<Double, location>node_map = bfs.getNode_map();
		HashMap<String, Double[]>road_map = bfs.getRoad_map();
		
		location startNode = new location(node[fixedNumStart][1], node[fixedNumStart][2],node[fixedNumStart][0] );
		location endNode = new location(node[fixedNumEnd][1], node[fixedNumEnd][2], node[fixedNumEnd][0]);
		List<location> path = new ArrayList<location>();
		path = bfs.findPath(startNode, endNode, road, node, road_map, node_map);
		location lo = null;
		if(!path.isEmpty()) {
			Iterator <location> it = path.iterator();
			int count=0;
			while(it.hasNext()) {
				lo = new location ((location) it.next());
				count++;
				System.out.println(count+": "+lo.getId());
				
			}
			
		} else System.out.println("No path found.");
		
		
	}
}
