package weibo_mystring_service;

public class Temfriends {
	private int projectid;
	private String name;
	private int payprice;
	
	public Temfriends(){}
	public Temfriends(int projectid, String name, int payprice) {
		super();
		this.projectid = projectid;
		this.name = name;
		this.payprice = payprice;
	}
	
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPayprice() {
		return payprice;
	}
	public void setPayprice(int payprice) {
		this.payprice = payprice;
	}
	
}
