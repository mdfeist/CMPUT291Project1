
public class LoginCallback implements Callback
{
	public LoginCallback() {}
	
	public void loginView()
	{
		String email = null;
		String password = null;
		
		System.out.println("Please enter the following information:\n");
		System.out.println("email: ");
		email = Menu.getKeyBoard();
		
		System.out.println("password: ");
		password = Menu.getKeyBoard();
		
	}

	@Override
	public int callback() {
		loginView();
		return Callback.OK;
	};
	
	
}
