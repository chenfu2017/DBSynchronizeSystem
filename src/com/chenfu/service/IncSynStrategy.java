package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class IncSynStrategy extends DataSynStrategy{

	public IncSynStrategy(String tableName, DataBase srcDB, DataBase destDB) {
		super(tableName, srcDB, destDB);
	}

	@Override
	public String processSyn() {
		
		return null;
	}

}
