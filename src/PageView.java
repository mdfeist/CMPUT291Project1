import java.util.ArrayList;

/**
 * Abstract class to handle a page.
 * 
 * Options:
 * s: to select
 * p: previous page
 * n: next page
 * q: to exit
 * 
 * @author George Coomber and Michael Feist
 */

abstract public class PageView implements Callback
{
	protected ArrayList<DatabaseRow> rows = null;
	protected String pageTitle = null;
	
	protected boolean run;
	protected int pageNumber;
	protected int elementIndex;
	protected int pageSize;
	
	abstract public void view();
	abstract public void getRowInfo(String id);
	
	public void printCustomOptions() {}
	public boolean doCustomOptions() { return false; }
	
	public void printOptions()
	{
		System.out.println("\nOptions");
		System.out.println("s: to select");
		System.out.println("p: previous page");
		System.out.println("n: next page");
		printCustomOptions();
		System.out.println("q: to exit");
	}
	
	protected void selectCommand()
	{
		System.out.println("Enter the row number:");
		String input = Menu.getKeyBoard();
		
		Integer adNumber = null;
		
		try
		{
			adNumber = new Integer(input);
		} catch (Exception e) {
			System.out.println("Error: Not an Integer.");
			return;
		}
		
		int row = adNumber.intValue();
		
		if (row >= 1 && row <= rows.size())
		{
			getRowInfo(rows.get(row - 1).getID());
		}
		
		elementIndex = pageSize*(pageNumber - 1);
	}
	
	protected void prevCommand()
	{
		if (pageNumber > 1)
		{
			pageNumber--;
			elementIndex = pageSize*(pageNumber - 1);
		} else
		{
			elementIndex = pageSize*(pageNumber - 1);
		}
	}
	
	protected void nextCommand()
	{
		if (elementIndex < rows.size())
		{
			elementIndex = pageSize*pageNumber;
			pageNumber++;
		} else
		{
			elementIndex = pageSize*(pageNumber - 1);
		}
	}
	
	protected void stop()
	{
		run = false;
	}
	
	public void doCommand(String command)
	{
		if (command.equals("s")) { selectCommand(); } 
		else if (command.equals("p")) { prevCommand(); }
		else if (command.equals("n")) { nextCommand(); }
		else if (command.equals("q")) { stop(); }
		else if (doCustomOptions()) {}
		else
		{
			elementIndex = pageSize*(pageNumber - 1);
			System.out.println("Error: Unknown command.");
		}
	}
	
	public void pageView(int size)
	{
		pageSize = size;
		
		if (rows == null)
		{
			System.out.println("\nERROR: No rows in view.");
			return;
		}
		
		run = true;
		pageNumber = 1;
		elementIndex = 0;
		while (run)
		{
			if (pageTitle != null)
				System.out.println(pageTitle);
			
			while (elementIndex < pageSize*pageNumber && elementIndex < rows.size())
			{
				System.out.println((elementIndex + 1) + ": " + rows.get(elementIndex).toString());
				elementIndex++;
			}
			
			printOptions();
			
			String command = Menu.getKeyBoard();
			
			doCommand(command);
		}
	}
	
	@Override
	public int callback() {
		view();
		return Callback.OK;
	}
}
