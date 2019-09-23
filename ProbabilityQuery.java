import java.util.Hashtable;

public class ProbabilityQuery 
{
	
	String[] queryNames= null;
	String[] conditionNames = null;
	Hashtable<String, Boolean> queryInfoTable=null;
	Hashtable<String, Boolean> EvidenceInfoTable=null;	
	
	// when no given conditions
	public ProbabilityQuery(String[] query, boolean[] queryTruthValue) 
	{
		queryInfoTable = new Hashtable<String, Boolean>();
		for (int i = 0; i < query.length; i++) 
		{
			queryInfoTable.put(query[i], new Boolean (queryTruthValue[i]));
		}
		queryNames = query;
		
		
		EvidenceInfoTable=new Hashtable<String, Boolean>();	
		queryInfoTable.put("temp", new Boolean (true));
	}
	
	// when given conditions
	public ProbabilityQuery (String[] query,  boolean[] queryTruthValue, String[] evidence, boolean[] evidenceTruthValue) 
	{
		queryInfoTable = new Hashtable<String, Boolean>();
		EvidenceInfoTable = new Hashtable<String, Boolean>();
		for (int i = 0; i < query.length; i++) 
		{
			queryInfoTable.put(query[i], new Boolean (queryTruthValue[i]));
		}
		for (int i = 0; i < evidence.length; i++) 
		{
			EvidenceInfoTable.put(evidence[i], new Boolean (evidenceTruthValue[i]));
		}
		queryNames = query;
		conditionNames = evidence;
	}
	
	
	String[] getQueryNames() 
	{
		return queryNames;
	}
	
	Hashtable<String, Boolean> getqueryInfoTable() 
	{
		return queryInfoTable;
	}
		
	
	Hashtable<String, Boolean> getEvidenceInfoTable() 
	{
		return EvidenceInfoTable;
	}
	
}
