/**
 * This is where the user can list the ads
 * they have written.
 * 
 * @author George Coomber and Michael Feist
 */

public class ListCallback implements Callback
{
	public ListCallback() {}
	
	@Override
	public int callback() {
		return Callback.OK;
	}
}