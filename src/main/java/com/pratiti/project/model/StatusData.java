package com.pratiti.project.model;

import com.pratiti.project.entity.Token.Status;

public class StatusData {
	private int cid;
	private String st;
	private int tokenId;
	
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	

}
