import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NetworkNode 
{
	List<NetworkNode> parents, children;
	String name;	
	PrbtyDistrib distribution;
	
	public NetworkNode(String name) 
	{
		this.name = name;
		parents = new ArrayList<NetworkNode>();
		children = new ArrayList<NetworkNode>();
		distribution = new PrbtyDistrib(name);
	}
	
	// one parent
	void assignParent(NetworkNode parent) 
	{
		addParent(parent);
		parent.addChild(this);
		distribution = new PrbtyDistrib(parent.getName());
	}
	
	// two parents
	void assignParent(NetworkNode parent1, NetworkNode parent2) 
	{
		assignParent(parent1);
		assignParent(parent2);
		distribution = new PrbtyDistrib(parent1.getName(), parent2.getName());
	}
	
	void assignProbability(boolean b, double d) 
	{
		distribution.set(b, d);
		if (isRoot()) 
		{
			distribution.set(!b, 1.0 - d);
		}
	}
	
	void assignProbability(boolean b, boolean c, double d) 
	{
		distribution.set(b, c, d);
	}
	
	double assignProbability(Hashtable<String, Boolean> conditions) 
	{
		return distribution.assignProbability(conditions);
	}
	
	
	void addParent(NetworkNode node) 
	{
		if (!(parents.contains(node))) 
		{
			parents.add(node);
		}
	}
	
	void addChild(NetworkNode node) 
	{
		if (!(children.contains(node))) 
		{
			children.add(node);
		}
	}
	
	boolean isRoot() 
	{
		return (parents.size() == 0);
	}
	
	String getName() 
	{
		return name;
	}
	
	List<NetworkNode> getParents() 
	{
		return parents;
	}
	
	List<NetworkNode> getChildren() 
	{
		return children;
	}

}
