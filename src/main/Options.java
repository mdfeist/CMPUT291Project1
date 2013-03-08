package main;
/**
 * Used by a menu. Holds a command, message, and a
 * callback that is called when the user selects
 * a menu item.
 * 
 * @author Michael Feist
 */
public class Options {
	
	private String command;
	private String message;
	private Callback callback;
	
	public Options(String command, String message, Callback callback)
	{
		this.command = command;
		this.message = message;
		this.callback = callback;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public void setCallback(Callback callback)
	{
		this.callback = callback;
	}
	
	public Callback getCallback()
	{
		return this.callback;
	}
	
	public int call()
	{
		if (this.callback != null)
		{
			return this.callback.callback();
		}
		
		return Callback.NULL_CALLBACK;
	}
}
