import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class PrbtyDistrib 
{
	// innerclass
	private class InnerClass 
	{
		public InnerClass(ExtensionHelp eh, double probability) 
		{
			this.m1 = eh;
			this.prob = probability;
		}	
		
		ExtensionHelp m1;
		double prob;		
		
	}
	
	
	
	private List<InnerClass> i1;
	
	String[] variableNames;
	
	
	public PrbtyDistrib(String[] variableNames)
	{
		this.variableNames = variableNames;
		i1 = new ArrayList<InnerClass>();
	}
	
	public PrbtyDistrib(String v1, String v2) 
	{
		this(new String[] { v1, v2 });
	}
	
	public PrbtyDistrib(String vn) 
	{
		this(new String[] { vn });
	}
	
	public void set(boolean v1, boolean v2, double prob) 
	{
		set(new boolean[] { v1, v2 }, prob);
	}
	
	public void set(boolean v1, double prob) 
	{
		set(new boolean[] { v1 }, prob);
	}
	
	public void set(boolean[] v, double prob) 
	{
		ExtensionHelp eh2 = new ExtensionHelp();
		for (int i = 0; i < variableNames.length; i++) 
		{
			eh2 = eh2.extend(variableNames[i], v[i]);
		}
		i1.add(new InnerClass(eh2, prob));
	}
	
	public double assignProbability(String vn, boolean b) 
	{
		Hashtable<String, Boolean> hash = new Hashtable<String, Boolean>();
		hash.put(vn, new Boolean(b));
		return assignProbability(hash);
	}
	
	public double assignProbability(Hashtable<String, Boolean> conditions) 
	{
		double prob = 0.0;
		for (InnerClass row : i1) 
		{
			Iterator<String> it = conditions.keySet().iterator();
			boolean meet = true;
			while (it.hasNext()) 
			{
				String variable = (String) it.next();
				boolean value = ((Boolean) conditions.get(variable)).booleanValue();
				if (!(row.m1.matches(variable, value))) 
				{
					meet = false;
					break;
				}
			}
			if (meet==true) 
			{
				prob = prob + row.prob;
			}
		}
		return prob;
	}
	
	
	
	 
	
	
	
	
	
	
	
	


}
