package com.ceq.app_core.framework;

import java.io.Serializable;

public class Framework_Database implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 705417369748602999L;
	private String databaseName, tableName;
	private int version;

	public Framework_Database() {
		super();
	}

	public Framework_Database(String databaseName, String tableName, int version) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.version = version;
	}

	public final String getDatabaseName() {
		return databaseName;
	}


	public final String getTableName() {
		return tableName;
	}


	public final int getVersion() {
		return version;
	}


	@Override
	public String toString() {
		return "Framework_DB [databaseName=" + databaseName + ", tableName=" + tableName + ", version=" + version + "]";
	}

}
