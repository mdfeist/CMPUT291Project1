/**
 * This is where the user can query the database
 * for users.
 * 
 * @author George Coomber and Michael Feist
 */

public class UserSearchCallback implements Callback
{
	public UserSearchCallback() {}
	
	@Override
	public int callback() {
		return Callback.OK;
	}
}