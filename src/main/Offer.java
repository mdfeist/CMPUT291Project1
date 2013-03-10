package main;

public class Offer implements DatabaseRow {

	private int _ono;
	private int _ndays;
	private float _price;
	
	public Offer(int ono, int days, float price)
	{
		_ono = ono;
		_ndays = days;
		_price = price;
	}
	
	public String getID()
	{
		return String.valueOf(_ono);
	}
	
	public String toString()
	{
		return String.format("%d4 %d4 %5f", _ono, _ndays, _price);
	}
}
