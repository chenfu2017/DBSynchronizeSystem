package com.chenfu.service;

public class MacroCommand extends TempTableSynCommand{
	
	private TempTableSynCommand lastCommand;

	public void addCommand(TempTableSynCommand lastCommand) {
		this.lastCommand = lastCommand;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
	
	

}
