package weibo_mystring_service;

public class Project {

	
	private int projectid;
	private String name;
	private int price;
	private int membernum;
	
	public Project(){}
	public Project(int projectid, String name, int price, int membernum) {
		super();
		this.projectid = projectid;
		this.name = name;
		this.price = price;
		this.membernum = membernum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMembernum() {
		return membernum;
	}
	public void setMembernum(int membernum) {
		this.membernum = membernum;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	
}
