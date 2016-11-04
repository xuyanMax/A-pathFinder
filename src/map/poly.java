package map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class poly {
	
	private double[][] poly = new double[4][3];
	
	
	public poly() {
		
		String path = "/Users/apple/Downloads/giscup15data-shape//poly.csv";
		BufferedReader br = null;
		
		String line ="";
		String csvSplitBy =",";
		
		try {
			int i = 0;
			int q =-1;
			br = new BufferedReader(new FileReader(path));
			
			while((line = br.readLine()) != null) {
				
				String[] poly_line = line.split(csvSplitBy);
			
				if (i > 0) {
					
						for (int j=0; j<poly_line.length; j++) {
							
							this.poly[q][j] = Double.parseDouble(poly_line[j]);
						}
				}
				
				i++;
				q++;
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}

	}
	
	public double[][] getPoly(){
		
		//四个定点是依次连接成的四边形
		
		return this.poly;
	}

}
