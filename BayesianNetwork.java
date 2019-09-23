import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class BayesianNetwork 
{
	
	List<NetworkNode> roots = new ArrayList<NetworkNode>();
	List<NetworkNode> followingNodes;
	
	// 
	public BayesianNetwork(NetworkNode root1, NetworkNode root2) 
	{
		roots.add(root1);
		roots.add(root2);
	}	

	public double getProbability(String name, Boolean value, Hashtable<String, Boolean> evidence) 
	{
		NetworkNode node = getNodeOfName(name);
		
		List<NetworkNode> parentNodes = node.getParents();
		if (parentNodes.size() != 0) 
		{
			Hashtable<String, Boolean> parentValues = new Hashtable<String, Boolean>();
			for (NetworkNode parent : parentNodes) 
			{
				parentValues.put(parent.getName(), evidence.get(parent
						.getName()));
			}
			double prob = node.assignProbability(parentValues);
			if (value.equals(Boolean.TRUE)) 
			{
				return prob;
			} else 
			{
				return (1.0 - prob);
			}		

		}
		else 
		{
			Hashtable<String, Boolean> YTable = new Hashtable<String, Boolean>();
			YTable.put(name, value);

			double prob = node.assignProbability(YTable);
			return prob;

		}
	}
	
	public List<String> getNamesOfFollowingNodes() 
	{
		followingNodes = getNonRootNodes();
		List<String> nodeNames = new ArrayList<String>();
		for (NetworkNode variableNode : followingNodes) 
		{
			nodeNames.add(variableNode.getName());
		}
		return nodeNames;
	}

	private List<NetworkNode> getNonRootNodes() 
	{
		if (followingNodes == null) 
		{
			List<NetworkNode> nodes = new ArrayList<NetworkNode>();
			List<NetworkNode> parents = roots;
			List<NetworkNode> visitedParents = new ArrayList<NetworkNode>();
			while (parents.size() != 0) 
			{
				List<NetworkNode> newParents = new ArrayList<NetworkNode>();
				for (NetworkNode parent : parents) 
				{
					if (!(visitedParents.contains(parent))) 
					{
						nodes.add(parent);
						List<NetworkNode> children = parent.getChildren();
						for (NetworkNode child : children)
						{
							if (!newParents.contains(child)) 
							{
								newParents.add(child);
							}
						}
						visitedParents.add(parent);
					}
				}

				parents = newParents;
			}
			followingNodes = nodes;
		}

		return followingNodes;
	}

	public NetworkNode getNodeOfName(String name) 
	{
		List<NetworkNode> nodes = getNonRootNodes();
		for (NetworkNode node : nodes) 
		{
			if (node.getName().equals(name)) 
			{
				return node;
			}
		}
		return null;
	}
	

}