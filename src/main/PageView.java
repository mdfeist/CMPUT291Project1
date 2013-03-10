package main;
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
	
	/**
	 * Needs to be implemented. Initial view of page.
	 */
	abstract public void view();
	/**
	 * Needs to be implemented. Displays information of a row.
	 * @param id
	 */
	abstract public void getRowInfo(String id);
	
	/**
	 * Can be overwritten to add custom options.
	 */
	public void printCustomOptions() {}
	
	/**
	 * Can be overwritten to return custom options.
	 * @return
	 */
	public boolean doCustomOptions() { return false; }
	
	/**
	 * Can be overwritten to handle custom end statement. 
	 * Called at the end of the PageView loop.
	 */
	public void doCustomFinish() { }
	
	/**
	 * Needs to be implemented to get all rows.
	 * @return rows from database
	 */
	public ArrayList<DatabaseRow> getRows() { return null; }
	
	/**
	 * To be called if data in database is changed.
	 */
	public void update() { rows = getRows(); }
	
	public void printOptions()
	{
		System.out.println("\nOptions");
		System.out.println("s: to select");
		System.out.println("p: previous page");
		System.out.println("n: next page");
		printCustomOptions();
		System.out.println("q: to exit");
	}
	
	/**
	 * Users can select a row from the rows array.
	 */
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
	
	/**
	 * Shows the previous page of rows.
	 */
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
	
	/**
	 * Shows the next page of rows.
	 */
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
	
	/**
	 * Ends the PageView.
	 */
	protected void stop()
	{
		run = false;
	}
	
	/**
	 * Handle user input for selecting an option.
	 * @param command
	 */
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
		
		doCustomFinish();
	}
	
	/**
	 * Displays the page infromation and gets user input.
	 * @param size
	 */
	public void pageView(int size)
	{
		pageSize = size;
		
		run = true;
		pageNumber = 1;
		elementIndex = 0;
		while (run)
		{
			if (rows == null)
			{
				System.out.println("\nERROR: No rows in view.");
				return;
			}
			
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
