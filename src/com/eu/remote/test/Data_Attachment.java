package com.eu.remote.test;

public class Data_Attachment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String fileName;
	public String filePath;

	public Data_Attachment() {
		super();

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Data_Attachment [fileName=");
		builder.append(fileName);
		builder.append(", filePath=");
		builder.append(filePath);
		builder.append("]");
		return builder.toString();
	}

}
