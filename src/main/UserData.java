package main;
/**
 * Holds data for a user from the users table.
 * 
 * @author George Coomber
 */
public class UserData implements DatabaseRow
{
	private String _email;
	private String _name;
	private String _last_login;
	private String _ad_count;
	private String _avg_rating;
	private boolean _isSmall;
	
	public UserData(String email,
			String name,
			String last_login,
			String ad_count,
			String avg_rating)
	{
		_email = email;
		_name = name;
		_last_login = last_login;
		_ad_count = ad_count;
		_avg_rating = avg_rating;
		_isSmall = false;
	}
	
	public UserData(String email,
			String name)
	{
		_email = email;
		_name = name;
		_isSmall = true;
	}
	
	public String getID()
	{
		return _email;
	}

	
	public void setID(String email)
	{
		this._email = email;
	}

	
	public String getName()
	{
	
		return _name;
	}

	
	public void setName(String name)
	{
	
		this._name = name;
	}
	
	public String getLastLogin()
	{
	
		return _last_login;
	}

	
	public void setLastLogin(String last_login)
	{
	
		this._last_login = last_login;
	}
	
	
	public void setAdCount(String ad_count)
	{
		this._ad_count = ad_count;
	}
	
	public String getAdCount()
	{
		return _ad_count;
	}
	
	public void setAvgRating(String avg_rating)
	{
		this._avg_rating = avg_rating;
	}
	
	public String getAvgRating()
	{
		return _avg_rating;
	}
	
	public String toString()
	{
		if (_isSmall == false){
			String format = "%1$21s %2$21s %3$10s %4$10s";
			return String.format(format, _email, _name, _ad_count, _avg_rating);
		}
		else
		{
			String format = "%1$21s\t%2$s";
			return String.format(format, _email, _name);
		}
	}
}