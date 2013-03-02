public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Menu startScreen = new Menu("Main Menu");
		startScreen.addOption("l", "log into an existing account", new LoginCallback());
		startScreen.addOption("c", "create new account", null);
		startScreen.addOption("q", "quit", new ExitCallback());
		startScreen.run();
	}

}
