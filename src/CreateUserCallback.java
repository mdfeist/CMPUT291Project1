/**
 * This is where new users can create
 * a new account and login to the 
 * system.
 * 
 * @author Michael Feist
 */

public class CreateUserCallback implements Callback {

	public CreateUserCallback() {}
	
	public void createView()
	{	
		// User Info
		String email = null;
		String name = null;
		String password = null;
		String password2 = null;
		
		// Get Email
		System.out.println("\nCreate New User:\nPlease enter the following information:\n");
		System.out.println("email: ");
		email = Menu.getKeyBoard();
		
		System.out.println("name: ");
		name = Menu.getKeyBoard();
		
		// Get Password
		System.out.println("password: ");
		password = Menu.getKeyBoard();
		
		System.out.println("re-type password: ");
		password2 = Menu.getKeyBoard();
		
		boolean alreadyExitst = User.getInstance().userExist(email);
		
		if (alreadyExitst)
		{
			System.out.println("Sorry, the email you have choosen is already taken.");
			return;
		}
		
		if(!password.equals(password2))
		{
			System.out.println("Passwords not the same.");
			return;
		}
		
		User.getInstance().create(email, name, password);
	}
	
	@Override
	public int callback() {
		createView();
		
		if (User.getInstance().isValid())
			return Callback.BACK;
		
		return Callback.OK;
	}
}