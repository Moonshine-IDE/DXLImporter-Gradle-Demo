package net.prominic.demo;

import lotus.domino.*;

/**
 * An example of an agent run from the WebQuerySave option on a form.
 * This returns a generic XML response.
 */
public class WQS_Placeholder extends AgentBase 
{
	public void NotesMain() {
		getAgentOutput().println("content-type:application/xml");
		//getAgentOutput().println("Pragma:cache");
		//getAgentOutput().println("Cache-control:public");
		getAgentOutput().println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		getAgentOutput().println("<root>");
		getAgentOutput().println("<note>This is not yet fully implemented.</note>");
		getAgentOutput().println("</root>");
		getAgentOutput().flush();
	}
}