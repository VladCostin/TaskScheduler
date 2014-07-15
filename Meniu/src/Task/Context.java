package Task;

import java.util.HashMap;

import ContextElements.ContextElementType;
import ContextElements.ContextElement;



/**
 * the context in which the task can be executed : it represents a collection of
 * context Elements that define how,where,when the task can pe executed
 * 
 * @author ${Vlad Herescu}
 *
 */
public class Context {


	
	
	/**
	 * ccontains the data refferint to each context element
	 */
	HashMap<ContextElementType,ContextElement> contextElementsCollection;


	public Context()
	{
		contextElementsCollection = new HashMap<ContextElementType,ContextElement>();
	}

	public HashMap<ContextElementType,ContextElement> getContextElementsCollection() {
		return contextElementsCollection;
	}

	public void setContextElementsCollection(HashMap<ContextElementType,ContextElement> contextElementsCollection) {
		contextElementsCollection = contextElementsCollection;
	}
	
	
	
}
