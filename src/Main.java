/**
 * Main function to start the program.
 * 
 * @author Michael Feist
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Start Screen
		Menu startScreen = new Menu("Login Screen");
		startScreen.addOption("l", "log into an existing account",
				new LoginCallback());
		startScreen.addOption("c", "create new account",
				new CreateUserCallback());
		startScreen.addOption("q", "quit", new ExitCallback());
		
		// Main Menu
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.addOption("q", "to logout", new LogoutCallback());
		
		boolean run = true;
		
		// Run the main loop
		while (run)
		{
			// Have the user login
			startScreen.run();
			
			if (User.getInstance().isValid())
			{
				mainMenu.run();
			} else
			{
				run = false;
			}
		}
	}

}
