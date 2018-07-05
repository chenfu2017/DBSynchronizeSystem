package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class DefaultOracleObjectSynchronizer extends DBObjectSynchronizer{
	
	@Override
	public void processSyn(DBSynchronizeManager dbSynchronizeManager) {
		
		System.out.println(getClass().getName()+"dbSynchronizeManager"+dbSynchronizeManager+"processSyn");
	}

	@Override
	public void compile(DBSynchronizeManager dbSynchronizeManager) {
		if (getCompileable()){
			DataBase destDB = dbSynchronizeManager.getDeskDB();
			String[] compileObjs = findAllObjects(destDB);
			int iLen = compileObjs.length;
			for (int i = 0; i < iLen; i++) {
				compileObject(destDB, compileObjs[i]);
			}
		}	
	}
	
	protected boolean getCompileable(){
		
		return true;
	}
	
	protected String[] findAllObjects(DataBase destDB){
		
		return null;	
	}
	
	protected void compileObject(DataBase destDB,String dbObjectName){
		System.out.println(destDB+dbObjectName);
	}

}
