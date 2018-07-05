package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class TempTableSynStrategy extends DataSynStrategy {

	public TempTableSynStrategy(String tableName, DataBase srcDB,
			DataBase destDB) {
		super(tableName, srcDB, destDB);
	}

	@Override
	public String processSyn() {
		
		return null;
	}

}
