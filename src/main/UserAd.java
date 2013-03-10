package main;
/**
 * Holds data for an ad when user lists their ads.
 * 
 * @author Michael Feist
 */

public class UserAd implements DatabaseRow
{
	private String _aid;
	private String _title;
	private String _type;
	private float _price;
	private String _date;
	private String _hasPromos;
	private int _daysLeft;
	
	public  UserAd(String aid,
			String title,
			String type,
			float price,
			String date,
			String promos,
			int daysLeft)
	{
		_aid = aid;
		_title = title;
		_type = type;
		_price = price;
		_date = date;
		_hasPromos = promos;
		_daysLeft = daysLeft;
	}
	
	public String getID()
	{
	
		return _aid;
	}

	
	public void setID(String aid)
	{
	
		this._aid = aid;
	}

	
	public String getTitle()
	{
	
		return _title;
	}

	
	public void setTitle(String title)
	{
	
		this._title = title;
	}

	
	public String getType()
	{
	
		return _type;
	}

	
	public void setType(String type)
	{
	
		this._type = type;
	}

	
	public float getPrice()
	{
	
		return _price;
	}

	
	public void setPrice(float price)
	{
	
		this._price = price;
	}

	
	public String getDate()
	{
	
		return _date;
	}

	
	public void setDate(String date)
	{
	
		this._date = date;
	}
	
	public String toString()
	{
		String format = "%1$20s %2$10.2f %3$s %4$s %5$3s %6$d";
		return String.format(format, _title, _price, _date, _type, _hasPromos, _daysLeft);
	}
}
