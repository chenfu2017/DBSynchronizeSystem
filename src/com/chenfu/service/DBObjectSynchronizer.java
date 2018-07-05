package com.chenfu.service;


public abstract class DBObjectSynchronizer {
	
	protected String objectTypeCode;
	
	protected String objectTypeLable;
	
	public String getObjectTypeCode() {
		return objectTypeCode;
	}

	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}

	public String getObjectTypeLable() {
		return objectTypeLable;
	}

	public void setObjectTypeLable(String objectTypeLable) {
		this.objectTypeLable = objectTypeLable;
	}

	public abstract void processSyn(DBSynchronizeManager dbSynchronizeManager);
	
	public abstract void compile(DBSynchronizeManager dbSynchronizeManager);

}
