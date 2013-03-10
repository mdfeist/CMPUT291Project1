package main;

/**
 * Holds data for a category row from the Categories table
 * 
 * @author George Coomber
 */
public class Category implements DatabaseRow
{
	private String _cat;

	
	public Category(String cat)
	{
		_cat = cat;
	}

	public String toString()
	{

		String format = "%1$21s";
		return String.format(format, _cat);

	}

	@Override
	public String getID()
	{
		return _cat;
	}
}
