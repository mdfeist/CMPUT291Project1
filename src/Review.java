/**
 * Holds data for a review
 * 
 * @author Michael Feist
 */
public class Review implements DatabaseRow {

	private String _rno;
	private float _rating;
	private String _text;
	private String _rdate;
	
	public Review(String rno,
			float rating,
			String text,
			String date)
	{
		_rno = rno;
		_rating = rating;
		_text = text;
		_rdate = date;
	}
	
	public String getID()
	{
	
		return _rno;
	}

	
	public void setID(String rno)
	{
	
		this._rno = rno;
	}
	
	public String getText()
	{
		return _text;
	}
	
	public String toString()
	{
		String text = _text;
		
		if (text.length() > 40)
		{
			text = text.substring(0, 40);
			text += "...";
		}
		
		String format = "%1$s %2$.2f %3$s";
		return String.format(format, _rdate, _rating, text);
	}

}
