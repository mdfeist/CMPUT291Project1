package main;
/**
 * This is where the user can post an ad.
 * 
 * @author George Coomber and Michael Feist
 */

public class PostCallback implements Callback
{
	public PostCallback() {}
	
	@Override
	public int callback() {
		return Callback.OK;
	}
}