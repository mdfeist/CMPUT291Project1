package main;

/**
 * Creates a new key id for the database.
 * Used for posting new ads and purchases.
 * 
 * @author Michael Feist
 *
 */

public class IDGenerator {
	
	public static char[] characters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
		'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	
	public static int numChars = characters.length;
	
	public static int getIndex(char x)
	{
		int i = 0;
		for (char c : characters)
		{
			if (c == x)
				return i;
			
			i++;
		}
		
		return -1;
	}
	
	public static String newID(String oldMax)
	{
		char c1 = oldMax.charAt(0);
		char c2 = oldMax.charAt(1);
		char c3 = oldMax.charAt(2);
		char c4 = oldMax.charAt(3);
		
		int i1 = getIndex(c1);
		int i2 = getIndex(c2);
		int i3 = getIndex(c3);
		int i4 = getIndex(c4);
		
		if (i1 == -1 ||
				i2 == -1 ||
				i3 == -1 ||
				i4 == -1)
		{
			System.out.println("ERROR: Charactes in ad aid not known characters.");
			return null;
		}
		
		i4++;
		
		if (i4 >= numChars)
		{
			i4 = 0;
			i3++;
			
			if (i3 >= numChars)
			{
				i3 = 0;
				i2++;
				
				if (i2 >= numChars)
				{
					i2 = 0;
					i1++;
				}
			}
		}
		
		return String.format("%c%c%c%c", 
				characters[i1], characters[i2], characters[i3], characters[i4]);
	}
}
