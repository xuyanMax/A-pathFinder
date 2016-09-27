package map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class data {
	
	private double[][] nodes = new double[42409][3];
	private double [][] road = new double[96850][5];
	
	public data (){
		
		String csv_node = "/Users/apple/Downloads/giscup15data-shape/node.csv";
		
		BufferedReader br_node = null;
		
		String line ="";
		String csvSplitBy =",";
		
		String[][] node_import = new String[42409][3];
		
		//double[][] node = new double [42408][3];
		
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
						this.nodes[w][j] = Double.parseDouble(node_line[j]);
					}
				
				}
				for (int j=0; j < node_line.length; j++) {
					
					 node_import [i][j] = node_line[j];
			
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
			
			while((line = bf_road.readLine()) !=null ) {
			
				String[] road_line = line.split(csvSplitBy);
				
				if ( j > 0) {
					
					for (int k=0; k<road_line.length; k++) {

						this.road[q][k] = Double.parseDouble(road_line[k]);
						
					}
					
					
				}
				j++;q++;
				
			}
					
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	public double[][] getNodes(){
		
		return this.nodes;
		
	}
	
	public double[][] getRoad(){
		
		return this.road;
		
	}

}
