public class LoginCallback implements Callback
{
	public LoginCallback() {}
	
	public void login()
	{	
		// User Info
		String email = null;
		String password = null;
		
		// Get Email
		System.out.println("\nLogin Menu:\nPlease enter the following information:\n");
		System.out.println("email: ");
		email = Menu.getKeyBoard();
		
		// Get Password
		System.out.println("password: ");
		password = Menu.getKeyBoard();
		
		System.out.println("\nLogging In ...\n");
		
		User.getInstance().login(email, password);
		
	}

	@Override
	public int callback() {
		login();
		return Callback.OK;
	}
}
