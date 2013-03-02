
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
