/**
 * Returns the back command. Used to return out
 * of a current menu.
 * 
 * @author Michael Feist
 */
public class ExitCallback implements Callback
{
	public ExitCallback() {}

	@Override
	public int callback() {
		return Callback.BACK;
	};
	
	
}
