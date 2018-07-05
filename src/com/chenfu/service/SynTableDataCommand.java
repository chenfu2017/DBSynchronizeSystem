package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class SynTableDataCommand extends TempTableSynCommand {

	
	private DataSynHelper dataSynHelper;
	
	private DataBase srcDB;
	
	private DataBase deskDB;
	
	private String fromTableName;
	
	private String toTableName;
	
	public SynTableDataCommand(DataSynHelper dataSynHelper, DataBase srcDB,
			DataBase deskDB, String fromTableName, String toTableName) {
		this.dataSynHelper = dataSynHelper;
		this.srcDB = srcDB;
		this.deskDB = deskDB;
		this.fromTableName = fromTableName;
		this.toTableName = toTableName;
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
