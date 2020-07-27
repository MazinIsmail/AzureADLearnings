package com.azureAD.learnings.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class AzureADObject {
	
	@JsonProperty("@odata.context")
	@SerializedName("@odata.context")
	private String context;
	
	@JsonProperty("@odata.nextLink")
	@SerializedName("@odata.nextLink")
	private String nextLink;
	
	@JsonProperty("value")
	private List<UserDetails> value;

	public synchronized String getContext() {
		return context;
	}

	public synchronized void setContext(String context) {
		this.context = context;
	}

	public synchronized String getNextLink() {
		return nextLink;
	}

	public synchronized void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public synchronized List<UserDetails> getValue() {
		return value;
	}

	public synchronized void setValue(List<UserDetails> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AzureADObject [context=" + context + ", nextLink=" + nextLink + ", value=" + value + "]";
	}

	
}
