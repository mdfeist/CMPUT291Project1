package main;
/**
 * Calls a generic menu.
 * 
 * @author Michael Feist
 */
public class MenuCallback implements Callback
{
	private Menu menu;
	
	public MenuCallback(Menu menu)
	{
		this.menu = menu;
	}

	@Override
	public int callback() {
		menu.run();
		return Callback.OK;
	};
}
