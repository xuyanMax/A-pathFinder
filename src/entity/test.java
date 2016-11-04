package entity;

import java.util.Iterator;
import java.util.PriorityQueue;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		location a = new location();
		a.setGlength(300);a.setId(1);
		location b = new location();
		b.setGlength(200);b.setId(2);
		location c = new location();
		c.setGlength(900);c.setId(3);
		
		PriorityQueue<location> OPEN = new PriorityQueue<location>(10, new nodeGlengthComparator());
		OPEN.add(a);
		OPEN.add(b);
		OPEN.add(c);
		OPEN.add(a);
		OPEN.add(b);
		OPEN.add(c);

		boolean rm = OPEN.remove(a);
//		System.out.println(rm);
		while(OPEN.size()!=0)
		System.out.println(OPEN.poll().getId());

	}

}
