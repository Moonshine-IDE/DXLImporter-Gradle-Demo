package net.prominic.demo;

import lotus.domino.*;

/**
 * An example of an agent run from the WebQuerySave option on a form.
 * This returns a generic XML response.
 */
public class TestWeb extends AgentBase 
{
	public void NotesMain() {

                String message = "This is not yet fully implemented.";
		getAgentOutput().println("content-type:application/xml");
		//getAgentOutput().println("Pragma:cache");
		//getAgentOutput().println("Cache-control:public");
		getAgentOutput().println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		getAgentOutput().println("<root>");
                // note that this should be encoded in a real agent
		getAgentOutput().println("<note>" + message +  "</note>");
		getAgentOutput().println("</root>");
		getAgentOutput().flush();
	}
}
