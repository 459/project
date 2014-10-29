package ADD;

import Conn.connDB;

public class add {
	private String partners;
	private double percents;
	private double money;
	private String projectid;
	private String id;
	private double prices;

	public String GetPartners()
	{
		return partners;
	}
	public double GetPercents()
	{
		return percents;
	}
	public String GetID()
	{
		return id;
	}
	public double GetPrices()
	{
		return prices;
	}
	public void SetPartners(String partners)
	{
		this.partners = partners;
	}
	public void SetPercents(double percents)
	{
		this.percents = percents;
	}
	public void SetMoney(double prices,float percents)
	{
		this.money = prices * percents / 100;
	}
	public void SetProjectID(String id)
	{
		this.projectid = id;
	}
	public void SetID(String id)
	{
		this.id = id;
	}
	public void SetPrices(double prices)
	{
		this.prices = prices;
	}
    public String Add()
    {
    	connDB connadd = new connDB();
		String sql = "";
		int getstate = 0;
		sql = "insert into details values( "+ partners +","+ percents +","+ money +","+ projectid +")";
		sql = "insert into projectid values( "+ id +","+ prices +")";
		getstate =  connadd.executeUpdate(sql);
		connadd.close();
		if(getstate != 0){
    	  return "SUCESS";
		}
		else{
			return "Failed!";
		}
    }
    public String Delete()
    {
		connDB connadd = new connDB();
		String sql = "";
		int getstate = 0;
		sql = "delete from details where projetid = "+ id +"";
		sql = "delete from projects where id = "+ id +"";
		getstate =  connadd.executeDelete(sql);
		connadd.close();
		if(getstate != 0)
			return "Delete book successful!";
		else
			return "Failed!";
	}   
}
