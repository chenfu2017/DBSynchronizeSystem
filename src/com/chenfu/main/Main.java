package com.chenfu.main;
import com.chenfu.domain.SynchronizeParamInfo;
import com.chenfu.service.DBSynchronizeManagerBuilder;
import com.chenfu.service.DBSynchronizeManagerDirector;

public class Main {

	public static void main(String[] args) {
		
		DBSynchronizeManagerDirector dbSynchronizeManagerDirector = new DBSynchronizeManagerDirector();
		DBSynchronizeManagerBuilder dbSynchronizeManagerBuilder = new DBSynchronizeManagerBuilder();
		SynchronizeParamInfo synchronizeParamInfo = new SynchronizeParamInfo();
		System.out.println(dbSynchronizeManagerDirector.construct(synchronizeParamInfo));
	}

}
