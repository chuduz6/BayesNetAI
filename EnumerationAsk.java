import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class EnumerationAsk 
{

	public static double enumerateAll(BayesianNetwork net, List<String> hiddenVariables, Hashtable<String, Boolean> conditionEvents) {
		if (!(hiddenVariables.size() == 0) )
		{
			double sum=0;
			double probGivenFalseConditions;
			double probGivenTrueConditions;
			double givenConditions;
			double trueProbability;
			double falseProbability;
			double individualProbability;
			
			String Y = (String) hiddenVariables.get(0);
			if (!(conditionEvents.keySet().contains(Y))) 
			{				
				Hashtable<String, Boolean> temp = tempCopy(conditionEvents);
				Hashtable<String, Boolean> temp2 = tempCopy(conditionEvents);				
				
				temp2.put(Y, false);
				probGivenFalseConditions = net.getProbability(Y, false, temp2);
				individualProbability = enumerateAll(net, remainingList(hiddenVariables), temp2);
				falseProbability = probGivenFalseConditions * individualProbability;
				
				temp.put(Y, true);
				probGivenTrueConditions = net.getProbability(Y, true, temp);
				individualProbability = enumerateAll(net, remainingList(hiddenVariables), temp);
				trueProbability = probGivenTrueConditions * individualProbability;
				sum = trueProbability + falseProbability;
				return sum;
			} 
			else 
			{
				givenConditions = net.getProbability(Y, conditionEvents.get(Y), conditionEvents);
				sum = enumerateAll(net, remainingList(hiddenVariables), conditionEvents);
				return givenConditions * sum;			
				
			}		
			
		} 
		
		else 
		{
			return 1.0;
		}
	}

	public static double askGivenEvidence(ProbabilityQuery query, BayesianNetwork net)
	{
		double finalProbability = 1;
		ArrayList<Double> probability = new ArrayList<Double>();		
		String[] queryNames = query.getQueryNames();		
		Hashtable<String, Boolean> queryInfoTable = query.getqueryInfoTable();		
		Hashtable<String, Boolean> evidenceInfoTable = query.getEvidenceInfoTable();
		for (int i = 0; i < queryNames.length; i++) 
		{
			evidenceInfoTable.put(queryNames[i], new Boolean(queryInfoTable.get(queryNames[i])));
		}
		
		for (int i = 0; i < queryNames.length; i++) 
		{
			if (evidenceInfoTable.containsKey(queryNames[i])) 
			{
				double[] prob = new double[2];
				boolean status = queryInfoTable.get(queryNames[i]);
				evidenceInfoTable.put(queryNames[i], new Boolean(true));
				prob[0] = enumerateAll(net, net.getNamesOfFollowingNodes(), evidenceInfoTable);
				evidenceInfoTable.put(queryNames[i], new Boolean(false));
				prob[1] = enumerateAll(net, net.getNamesOfFollowingNodes(), evidenceInfoTable);				
				double normalized[] = normalize(prob);
				if (status == true) 
				{
					probability.add(new Double(normalized[0]));
				} 
				else if (status == false) 
				{
					probability.add(new Double(normalized[1]));
				}
				evidenceInfoTable.remove(queryNames[i]);
			}
		}
		
		
		for (int i = 0; i < probability.size(); i++) {
			finalProbability = finalProbability * probability.get(i);
		}
		
		return finalProbability;

	}
	
	public static double[] normalize(ArrayList<Double> distribution)
	{
		int distributionLength = distribution.size();
		double sum = 0.0;
		double[] normalizedProbValue = new double[distributionLength];
		
		for (int i = 0; i < distributionLength; i++)
		{
			sum = sum + distribution.get(i);
		}
		if (sum != 0) 
		{
			for (int i = 0; i < distributionLength; i++) 
			{
				normalizedProbValue[i] = distribution.get(i) / sum;
			}
		}
		return normalizedProbValue;
	}
	
	
	public static double[] normalize(double[] distribution) 
	{
		int distributionLength = distribution.length;
		double sum = 0.0;		
		double[] normalizedProbValue = new double[distributionLength];
		
		for (int i = 0; i < distribution.length; i++) 
		{
			sum = sum + distribution[i];
		}
		if (sum != 0) 
		{
			for (int i = 0; i < distribution.length; i++) 
			{
				normalizedProbValue[i] = distribution[i] / sum;
			}
		}
		return normalizedProbValue;
	}
	
	public static double askGivenNoEvidence(ProbabilityQuery query, BayesianNetwork net) 
	{
		double finalProbability = 1;
		ArrayList<Double> probability = new ArrayList<Double>();		
		String[] queryNames = query.getQueryNames();		
		Hashtable<String, Boolean> queryInfoTable = query.getqueryInfoTable();		
		Hashtable<String, Boolean> evidenceInfoTable=query.getEvidenceInfoTable();	
		
		for (int i = 0; i < queryNames.length; i++) 
		{
			evidenceInfoTable.put(queryNames[i], new Boolean(queryInfoTable.get(queryNames[i])));
		}
		
		for (int i = 0; i < queryNames.length; i++) 
		{
			if (evidenceInfoTable.containsKey(queryNames[i])) 
			{
				double[] prob = new double[2];
				boolean status = queryInfoTable.get(queryNames[i]);
				evidenceInfoTable.put(queryNames[i], new Boolean(true));
				prob[0] = enumerateAll(net, net.getNamesOfFollowingNodes(), evidenceInfoTable);
				evidenceInfoTable.put(queryNames[i], new Boolean(false));
				prob[1] = enumerateAll(net, net.getNamesOfFollowingNodes(), evidenceInfoTable);				
				double normalized[] = normalize(prob);
				if (status == true) 
				{
					probability.add(new Double(normalized[0]));
				} 
				else if (status == false) 
				{
					probability.add(new Double(normalized[1]));
				}
				evidenceInfoTable.remove(queryNames[i]);
			}
		}
		
		
		for (int i = 0; i < probability.size(); i++) {
			finalProbability = finalProbability * probability.get(i);
		}
		
		return finalProbability;

	
	}
	
	public static List<String> remainingList(List<String> fullList) 
	{
		List<String> remaining = new ArrayList<String>();
		for (String element : fullList) 
		{
			remaining.add(element);
		}
		remaining.remove(0);
		return remaining;
	}
	
	
	
	public static Hashtable<String, Boolean> tempCopy(Hashtable<String, Boolean> conditions) 
	{
		Iterator<String> it = conditions.keySet().iterator();
		Hashtable<String, Boolean> copy = new Hashtable<String, Boolean>();
		
		while (it.hasNext()) 
		{
			String name = it.next();
			Boolean bool = conditions.get(name);
			if (bool.equals(true)) 
			{
				copy.put(name, true);
			} 
			else 
			{
				copy.put(name, false);
			}
		}
		return copy;
	}
	
	
	
	
	
	
}
