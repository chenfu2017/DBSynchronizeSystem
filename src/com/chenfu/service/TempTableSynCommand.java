package com.chenfu.service;

public abstract class  TempTableSynCommand {
	
		protected TempTableSynCommand previousCommand;
		
		protected TempTableSynCommand nextCommand;

		public void setPreviousCommand(TempTableSynCommand previousCommand) {
			this.previousCommand = previousCommand;
		}
		
		public abstract void execute();
		
		public abstract void undo();

}
