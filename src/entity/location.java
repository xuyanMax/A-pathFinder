package entity;
import java.util.ArrayList;
import java.util.List;

public class location {
	private double x;
	private double y;
	private double id;
	private double Glength;
	private double spd;
	
	private List<location> edge;
	private double Hsteps;//h
	private double Fsteps;//f
	private location previous;
	
	
	public List<location> getEdge() {
		return edge;
	}


	public void setNodeToEdge(location node) {
		
		this.edge.add(node);
		
	}
	
	public location (double id, double x, double y, double Glength, double spd){
		
		this.id = id;
		this.x = x;
		this.y = y;
		this.Glength = Glength;
		this.spd = spd;
		this.edge = new ArrayList<location>();
		
	}

	
	public location (double x, double y, double id) {
		
		this.x  = x;
		this.y  = y;
		this.id = id;

		this.edge = new ArrayList<location>();
		
	}
	
	
	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}
	
	

	public double getGlength() {
		return Glength;
	}


	public void setGlength(double glength) {
		Glength = glength;
	}


	public double getSpd() {
		return spd;
	}


	public void setSpd(double spd) {
		this.spd = spd;
	}


	public boolean equals (Object o){
		
		if (o instanceof location) {
			
			location l = (location) o;
			if (l.x == this.x && l.y == this.y)
				return true;
		}
		return false;
	}
	
	public String toString(){
		
		return "["+x+", "+y+"]";
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

//	public double getMovedSteps() {
//		return movedSteps;
//	}
//
//	public void setMovedSteps(double moveSteps) {
//		this.movedSteps = moveSteps;
//	}

	public double getHSteps() {
		return Hsteps;
	}

	public void setHSteps(double Hsteps) {
		this.Hsteps = Hsteps;
	}

	public double getFSteps() {
		return Fsteps;
	}

	public void setFSteps(double Fsteps) {
		this.Fsteps = Fsteps;
	}

	public location getPrevious() {
		return previous;
	}

	public void setPrevious(location previous) {
		this.previous = previous;
	}
	
	
	

}
