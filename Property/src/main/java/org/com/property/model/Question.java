package org.com.property.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable{

	private Integer id;
	private String question;
	private String questionName;
	private List<OfferedAnswer> offeredAnswers = new ArrayList<OfferedAnswer>();
	private String answer;
	

	public Question() {
	}

	public Question(Integer id, String question) {
		this.id = id;
		this.question = question;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public List<OfferedAnswer> getOfferedAnswers() {
		return offeredAnswers;
	}

	public void setOfferedAnswers(List<OfferedAnswer> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
