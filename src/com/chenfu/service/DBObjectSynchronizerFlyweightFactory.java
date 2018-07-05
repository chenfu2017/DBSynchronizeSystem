package com.chenfu.service;

import java.util.HashMap;
import java.util.Map;

public class DBObjectSynchronizerFlyweightFactory {
	
	private static DBObjectSynchronizerFlyweightFactory instance = new DBObjectSynchronizerFlyweightFactory();
	
	private DBObjectSynchronizerFlyweightFactory(){};
	
	private Map<String,DBObjectSynchronizer> map = new HashMap<String,DBObjectSynchronizer>();
	
	public static DBObjectSynchronizerFlyweightFactory getinstance(){
		return instance;
	}
	
	public DBObjectSynchronizer factory(DBObjectSynchronizerMeta dbObjectSynchronizerMeta){
		String synchronizerName = dbObjectSynchronizerMeta.getSynchronizerName();
		DBObjectSynchronizer dbObjectSynchronizer = map.get(synchronizerName);
		        if(dbObjectSynchronizer==null){
		            dbObjectSynchronizer=buildDBObjectSynchronizer(dbObjectSynchronizerMeta);
		            map.put(dbObjectSynchronizerMeta.getSynchronizerName(),dbObjectSynchronizer);
		        }
		        return dbObjectSynchronizer;
		    }

	private DBObjectSynchronizer buildDBObjectSynchronizer(DBObjectSynchronizerMeta dbObjectSynchronizerMeta) {	
		String synchronizerName = dbObjectSynchronizerMeta.getSynchronizerName();
		return null ;
	}
}
