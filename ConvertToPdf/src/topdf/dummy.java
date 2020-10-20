package topdf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;


class Student{
	
	private String sname;
	private int sno;
	
	
	@Override
	public int hashCode() {
//		int hashCode=super.hashCode();
		return 10;
	}
	
	@Override
	public boolean equals(Object obj) {
		Student stu=(Student)obj;
		return this.sname.equalsIgnoreCase(stu.sname) || this.sno== stu.sno;
	}
	
	public Student(String sname, int sno) {
		super();
		this.sname = sname;
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	
	@Override
	public String toString() {
		return "Student [sname=" + sname + ", sno=" + sno + "]";
	}
	
	
}

public class dummy {

	public static void main(String[] args) {
	
		HashMap<Student, Integer> hm=new HashMap<Student, Integer>();
		
		for(int i=0; i<1000000; i++) {
			hm.put(new Student("vikas",1),100);
		}

		System.out.println(hm);
		Student student=new Student("vikas", 1);
		if(hm.containsKey(student)){
			System.out.println("student found");
		}else {
			System.out.println("student not found");
		}
	
	
	}
}
