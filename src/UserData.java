
public class UserData implements DatabaseRow
{
	private String _email;
	private String _name;
	private String _pass;
	private String _last_login;
	
	public UserData(String email,
			String name,
			String pass,
			String last_login)
	{
		_email = email;
		_name = name;
		_pass = pass;
		_last_login = last_login;
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

	
	public String getPass()
	{
	
		return _pass;
	}

	
	public void setPass(String pass)
	{
	
		this._pass = pass;
	}

	
	public String getLastLogin()
	{
	
		return _last_login;
	}

	
	public void setLastLogin(String last_login)
	{
	
		this._last_login = last_login;
	}
	
	public String toString()
	{
		String format = "%1$20s %2$s %3$s %4$s";
		return String.format(format, _email, _name, _pass, _last_login);
	}
}