package main;
/**
 * Main function to start the program.
 * 
 * @author Michael Feist and George Coomber
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
		mainMenu.addOption("s", "to search for ads", new AdSearchCallback());
		mainMenu.addOption("l", "to list your own ads", new ListCallback());
		mainMenu.addOption("u", "to search for users", new UserSearchCallback());
		mainMenu.addOption("p", "to post an ad", new PostCallback());
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
