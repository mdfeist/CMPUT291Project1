import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu {

	private ArrayList<Options> _options;

	public Menu() {
		_options = new ArrayList<Options>();
	}

	public void addOption(String command, String message, Callback callback) {
		_options.add(new Options(command, message, callback));
	}

	public void printOptions() {
		for (Options o : _options)
			System.out.println(o.getCommand() + " : " + o.getMessage());
	}

	public void run() {
		boolean exit = false;

		while (!exit) {
			printOptions();

			boolean commandFound = false;

			String select = Menu.getKeyBoard();

			for (Options o : _options) {
				if (select.equals(o.getCommand())) {
					commandFound = true;

					int code = o.call();

					switch (code) {
					case Callback.BACK:
						exit = true;
						break;
					case Callback.NULL_CALLBACK:
						System.out.println("\nWarning: Callback is null.\n");
						break;
					case Callback.ERROR:
						System.out.println("\nError: Unknown Error\n");
						break;
					}
				}
			}

			if (!commandFound)
				System.out.println("\nError: Command not found.\n");
		}
	}

	public static String getKeyBoard() {
		String str = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			str = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}
}
