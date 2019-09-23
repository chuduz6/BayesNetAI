import java.util.ArrayList;
import java.util.List;


public class bnet 
{
	public static void main(String[] arguments) 
	{
		double probs=0.0;
		String[] queryNames;
		String[] conditionNames;
		boolean[] queryInfoTable;
		boolean[] conditionInfoTable;
		boolean Evidence = false; 
		int count = 0;
	
		// check for appropriate length
		if (arguments.length < 1 || arguments.length > 6) 
		{
			System.out.println("\nThe bnet program takes 1 to 6 arguments. No more. No Fewer.\n");
			exit_function(0);
		} 
		
		// check for given evidence
		for (int i = 0; i < arguments.length; i++) 
		{
			if (arguments[i].equalsIgnoreCase("given")) 
			{
				Evidence = true;
				count = i;
				break;
			}
		}
		
		// store information given no evidence
		if (Evidence == false) 
		{
			queryNames = new String[arguments.length];
			queryInfoTable = new boolean[queryNames.length];
			conditionNames = new String[1];
			conditionNames[0] = "Default";
			conditionInfoTable = new boolean[1];
			conditionInfoTable[0]=true;
			storeInformation(queryNames, queryInfoTable, arguments);			
		} 
		
		// store information given evidence
		else 
		{
			queryNames = new String[count];
			queryInfoTable = new boolean[queryNames.length];
			conditionNames = new String[arguments.length - (count + 1)];
			conditionInfoTable = new boolean[conditionNames.length];
			storeInformation(queryNames, queryInfoTable, arguments);
			List<String> list = new ArrayList<String>();
			for (int i = (count + 1); i < arguments.length; i++) 
			{
				list.add(arguments[i]);
			}
			String [] newInput = list.toArray(new String[list.size()]);
			storeInformation(conditionNames, conditionInfoTable, newInput);
		}
		
		// create network
		
		NetworkNode maryCalls = new NetworkNode("MaryCalling");
		NetworkNode johnCalls = new NetworkNode("JohnCalling");
		NetworkNode alarm = new NetworkNode("alarm");
		NetworkNode burglary = new NetworkNode("burglary");
		NetworkNode earthquake = new NetworkNode("earthquake");		
		maryCalls.assignParent(alarm);
		johnCalls.assignParent(alarm);		
		alarm.assignParent(burglary, earthquake);
		maryCalls.assignProbability(true, 0.70);
		maryCalls.assignProbability(false, 0.01);
		johnCalls.assignProbability(true, 0.90);
		johnCalls.assignProbability(false, 0.05);
		alarm.assignProbability(true, true, 0.95);
		alarm.assignProbability(true, false, 0.94);
		alarm.assignProbability(false, true, 0.29);
		alarm.assignProbability(false, false, 0.001);
		earthquake.assignProbability(true, 0.002);
		burglary.assignProbability(true, 0.001);			
		BayesianNetwork bnet = new BayesianNetwork (burglary, earthquake);	
		
		
		System.out.println("------------------------------------------------------------");
		
		// evaluate the probability for the query given NO EVIDENCE
		if (Evidence == false) 
		{
			ProbabilityQuery query = new ProbabilityQuery(queryNames, queryInfoTable);
			probs = EnumerationAsk.askGivenNoEvidence(query, bnet);		
				
		}
		
		// evaluate the probability for the query given EVIDENCE
		else 
		{
			ProbabilityQuery query = new ProbabilityQuery(queryNames,  queryInfoTable, conditionNames, conditionInfoTable);
			probs = EnumerationAsk.askGivenEvidence(query, bnet);						
		}
		
		// display evaluated probability for the query
		
		System.out.print("The Probability for: ");
		for(int i=0; i< arguments.length; i++)
		{
			System.out.print(" " + arguments[i]);
		}
		System.out.println(" is "+ probs);
		System.out.println("------------------------------------------------------------");
		System.out.println("\nThe program ends. Thank you.\n");
	}
	
		
	
	static void storeInformation(String[] event, boolean[] eventStatus, String[] input) 
	{
		
		
			for (int i = 0; i < event.length; i++) 
			{
				if (input[i].equalsIgnoreCase("mt"))
				{
					event[i] = "MaryCalling";
					eventStatus[i] = true;
				}
				else if (input[i].equalsIgnoreCase("mf")) 
				{
					event[i] = "MaryCalling";
					eventStatus[i] = false;
				}
				else if (input[i].equalsIgnoreCase("jt"))
				{
					event[i] = "JohnCalling";
					eventStatus[i] = true;
				}
				else if (input[i].equalsIgnoreCase("jf")) 
				{
					event[i] = "JohnCalling";
					eventStatus[i] = false;
				}
				else if (input[i].equalsIgnoreCase("at"))
				{
					event[i] = "alarm";
					eventStatus[i] = true;
				}
				else if (input[i].equalsIgnoreCase("af")) 
				{
					event[i] = "alarm";
					eventStatus[i] = false;
				}
				else if (input[i].equalsIgnoreCase("bt")) 
				{
					event[i] = "burglary";
					eventStatus[i] = true;
				}
				else if (input[i].equalsIgnoreCase("bf")) 
				{
					event[i] = "burglary";
					eventStatus[i] = false;
				}
				else if (input[i].equalsIgnoreCase("et")) 
				{
					event[i] = "earthquake";
					eventStatus[i] = true;
				}
				else if (input[i].equalsIgnoreCase("ef"))
				{
					event[i] = "earthquake";
					eventStatus[i] = false;
				}				
				else
				{
					System.out.println("Error on Inputing Values");
					exit_function(0);
				}
			
			}
	}
	
	
	static void exit_function(int value) 
	{
		System.out.println("Exiting from bnet program due to invalid parameters!!!!!\n\n");
		System.exit(value);
	}
	

}
	