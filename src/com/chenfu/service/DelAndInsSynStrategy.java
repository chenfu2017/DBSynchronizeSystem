package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class DelAndInsSynStrategy extends DataSynStrategy{

	public DelAndInsSynStrategy(String tableName, DataBase srcDB,
			DataBase destDB) {
		super(tableName, srcDB, destDB);
	}

	@Override
	public String processSyn() {

		return null;
	}

}
