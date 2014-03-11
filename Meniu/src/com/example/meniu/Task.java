package com.example.meniu;

import Context.Context;
import Context.RealizationContext;
import Context.RequiredContext;


/**
 * 
 * 
 * @author ${Vlad Herescu}
 *
 */
public class Task {

	
	/**
	 * the conditions the user needede to execute the task
	 */
	private Context internContext;
	
	/**
	 * the conditions the environment offers to execute the task
	 */
	private Context externContext;
	
	/**
	 * 
	 */
	private Context scheduledContext;
	
	
	Task(){
		
		internContext = new RequiredContext();
		externContext = new RealizationContext();
		scheduledContext = new RequiredContext();
		
		
	}

	

	public Context getInternContext() {
		return internContext;
	}

	public void setInternContext(Context internContext) {
		this.internContext = internContext;
	}

	public Context getExternContext() {
		return externContext;
	}

	public void setExternContext(Context externContext) {
		this.externContext = externContext;
	}

	public Context getScheduledContext() {
		return scheduledContext;
	}

	public void setScheduledContext(Context scheduledContext) {
		this.scheduledContext = scheduledContext;
	}
	
}