/**
 * Holds data for an Ad when user lists their ads
 * 
 * @author Michael Feist
 */

public class UserAd implements DatabaseRow
{
	private String _aid;
	private String _title;
	private String _type;
	private String _price;
	private String _date;
	
	public  UserAd(String aid,
			String title,
			String type,
			String price,
			String date)
	{
		_aid = aid;
		_title = title;
		_type = type;
		_price = price;
		_date = date;
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

	
	public String getPrice()
	{
	
		return _price;
	}

	
	public void setPrice(String price)
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
		String format = "%1$20s %2$s %3$s %4$s";
		return String.format(format, _title, _price, _date, _type);
	}
}
