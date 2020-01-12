package org.com.property.model;

import java.io.Serializable;

public class OfferedAnswer implements Serializable{

	private Integer id;
	private String offeredAnswer;
	private String type;
	
	public OfferedAnswer() {
	}

	public OfferedAnswer(Integer id, String offeredAnswer, String type) {
		this.id = id;
		this.offeredAnswer = offeredAnswer;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferedAnswer() {
		return offeredAnswer;
	}

	public void setOfferedAnswer(String offeredAnswer) {
		this.offeredAnswer = offeredAnswer;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
