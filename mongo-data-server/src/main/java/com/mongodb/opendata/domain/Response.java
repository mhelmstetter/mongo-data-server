package com.mongodb.opendata.domain;

public class Response {
	
	private int total;
	private String slice;
	private String dataset;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getSlice() {
		return slice;
	}
	public void setSlice(String slice) {
		this.slice = slice;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

}
