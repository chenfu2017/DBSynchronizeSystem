package com.chenfu.dome;

import org.junit.Test;
import com.chenfu.service.OracleTableDBSynchronizer;
import com.chenfu.service.OracleViewDBSynchronizer;
import com.chenfu.utils.Dom4jUtil;

public class Dome {
	
	@Test
	public void fun(){
		OracleViewDBSynchronizer oracleViewDBSynchronizer = Dom4jUtil.getBean("oracleViewDBSynchronizer",OracleViewDBSynchronizer.class);
		OracleTableDBSynchronizer oracleTableDBSynchronizer =Dom4jUtil.getBean("oracleTableDBSynchronizer", OracleTableDBSynchronizer.class);
		System.out.println(oracleTableDBSynchronizer);
		System.out.println(oracleViewDBSynchronizer);
	}

}
