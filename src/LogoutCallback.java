/**
 * Returns the back command and logs the user out of
 * the system.
 * 
 * @author Michael Feist
 */
public class LogoutCallback implements Callback
{
	public LogoutCallback() {}

	@Override
	public int callback() {
		User.getInstance().logout();
		return Callback.BACK;
	};
}
