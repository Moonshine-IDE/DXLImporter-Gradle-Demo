package net.prominic.demo;
import lotus.domino.*;
public class DemoJavaAgent extends AgentBase {
	public void NotesMain() {
		try {
			Session session = getSession();
			AgentContext agentContext = 
			session.getAgentContext();
			
			Database db = agentContext.getCurrentDatabase();
			System.out.println("Title:\t\t" + db.getTitle());
			System.out.println("File name:\t" + db.getFileName());
			String msg = "closed";
			if (db.isOpen()) msg = "open";
			System.out.println("Database is " + msg);
			
			// test the script library
			//System.out.println("Test Script Library:  '" + DemoScriptLibrary.getExampleText() + "'.");

			System.out.println("Your custom message here!");

			// Here is an example of how to  write to the browser output instead.
			//getAgentOutput().println("<H1>Hello, World!</H1>");
			//getAgentOutput().println("<P>Running in database '" + db.getTitle() + "'.</P>");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}