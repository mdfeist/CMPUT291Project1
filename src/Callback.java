
public interface Callback
{
	public static final int ERROR = -1;
	public static final int OK = 0;
	public static final int BACK = 1;
	public static final int NULL_CALLBACK = 2;
	
	public int callback();
}
