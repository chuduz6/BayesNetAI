import java.util.Hashtable;
import java.util.Iterator;


public class ExtensionHelp 
{
	public boolean matches(String variable, boolean value) 
	{
		if (value) 
		{
			return isTrue(variable);
		} 
		else
		{
			return isFalse(variable);
		}
		
	}
	
	public boolean isTrue(String symbol) 
	{
		Boolean status = (Boolean) eh1.get(symbol);
		return status.booleanValue();
	}
	
	
	public boolean isFalse(String symbol) 
	{
		Boolean status = (Boolean) eh1.get(symbol);
		return !status.booleanValue();
	}
	
	
	public ExtensionHelp extend(String s, boolean b) 
	{
		ExtensionHelp eh2 = new ExtensionHelp();
		Iterator<String> iterator = this.eh1.keySet().iterator();
		while (iterator.hasNext()) 
		{
			String keyword = iterator.next();
			Boolean value = eh1.get(keyword);
			eh2.eh1.put(keyword, value);
		}
		eh2.eh1.put(s, new Boolean(b));
		return eh2;
	}
	
	
	
	
	Hashtable<String, Boolean> eh1 = new Hashtable<String, Boolean>();
}