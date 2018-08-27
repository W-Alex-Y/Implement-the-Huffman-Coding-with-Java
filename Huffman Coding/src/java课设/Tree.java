package java课设;

public class Tree {
	String l;
	String r;
	String p;
	public Tree(String a, String b, String c) {
		l = a;
		r = b;
		p = c;
	}
	public Tree() {
	}
	public String getParents() {
		return p;
	}
	public String getLchild() {
		return l;
	}
	public String getRchild() {
		return r;
	}
	public static void main(String[] args) {
		
	}
}
