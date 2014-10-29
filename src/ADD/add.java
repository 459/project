package ADD;

import Conn.connDB;

public class add {
	private String partners;
	private double percents;
	private double prices;
	private String id;
	private double money;
	public String GetPartners()
	{
		return partners;
	}
	public double GetPercents()
	{
		return percents;
	}
	public double GetPrices()
	{
		return prices;
	}
	public String GetID()
	{
		return id;
	}
	public void SetPartners(String partners)
	{
		this.partners = partners;
	}
	public void SetPercents(double percents)
	{
		this.percents = percents;
	}
	public void SetPrices(double prices)
	{
		this.prices = prices;
	}
	public void SetID(String id)
	{
		this.id = id;
	}
	public void SetMoney(double prices,float percents)
	{
		this.money = prices * percents / 100;
	}
    public String Add(){
    	connDB connadd = new connDB();
		String sql = "";
		int getstate = 0;
		sql = "insert into details values( "+ partners +","+ percents +","+ money +")";
		getstate =  connadd.executeUpdate(sql);
		connadd.close();
		if(getstate != 0){
    	  return "添加成功!";
		}
		else{
			return "添加失败!";
		}
    }
    public String Delete(){
    	return "删除成功!";
    }
    
}
