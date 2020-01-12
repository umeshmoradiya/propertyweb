
package org.com.property;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpServletRequest;

import org.com.property.model.OfferedAnswer;
import org.com.property.model.Question;

@ManagedBean
@ViewScoped
public class QuestionBean implements Serializable {

	private static final long serialVersionUID = 6081417964063918994L;
	private Map<Integer, String> mapQestionsWithAnswers;

	String geschlecht, alter, question3, question4, question5, question6, question7, question8;
	List<Question> questions;

	private String paramName;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Loads the questions from the data base process the survey parameter provided
	 * in the url
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void check() throws IOException, ClassNotFoundException, SQLException {

		try {
			if (paramName == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("missing_survey_id.xhtml");
			} else if (paramName.equals("123")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("done_survey.xhtml");
			} else {
				questions = getQuestionsFromDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().getExternalContext().redirect("missing_survey_id.xhtml");
		}
	}

	/**
	 * Load the questions and the related answers for the survey from the database
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Question> getQuestionsFromDB() throws ClassNotFoundException, SQLException {

		Connection connect = null;

		// MySql connection string
		String url = "jdbc:mysql://localhost:3306/survey?rewriteBatchedStatements=true";

		String username = "root";
		String password = "Helloroot123";

		try {

			// Driver name for the mysql connection
			Class.forName("com.mysql.jdbc.Driver");

			// connect to the database
			connect = DriverManager.getConnection(url, username, password);

			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}

		List<Question> questions = new ArrayList<Question>();

		// Query to load questions
		PreparedStatement pstmt = connect.prepareStatement(
				"SELECT q.*, o.* FROM survey.questions q JOIN survey.question_offered_answer r ON q.question_id=r.question_id JOIN survey.offered_answer o ON r.offered_answer_id=o.offered_answer_id");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			boolean isStored = false;

			OfferedAnswer offeredAnswer = new OfferedAnswer();
			offeredAnswer.setId(rs.getInt("offered_answer_id"));
			offeredAnswer.setOfferedAnswer(rs.getString("text"));
			offeredAnswer.setType(rs.getString("type"));

			for (Question question : questions) {
				if (question.getId() == rs.getInt("question_id")) {
					isStored = true;
					question.getOfferedAnswers().add(offeredAnswer);
				}
			}

			if (!isStored) {
				Question question = new Question();
				question.setId(rs.getInt("question_id"));
				question.setQuestion(rs.getString("question"));
				question.setQuestionName(rs.getString("question_name"));
				question.getOfferedAnswers().add(offeredAnswer);
				questions.add(question);
			}

		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();
		return questions;

	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	// Upon submit button click this method will call and also it store the answers
	// to the database
	public String submit() throws ClassNotFoundException, SQLException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		mapQestionsWithAnswers = new HashMap<Integer, String>();

		setGeschlecht(request.getParameter("geschlecht"));
		mapQestionsWithAnswers.put(1, getGeschlecht());

		setAlter(request.getParameter("alter"));
		mapQestionsWithAnswers.put(2, getAlter());

		setQuestion3(request.getParameter("question3") + " " + request.getParameter("question3_text"));
		mapQestionsWithAnswers.put(3, getQuestion3());

		setQuestion4(request.getParameter("question4") + " " + request.getParameter("question4_text"));
		mapQestionsWithAnswers.put(4, getQuestion4());

		setQuestion5(request.getParameter("question5") + " " + request.getParameter("question5_text"));
		mapQestionsWithAnswers.put(5, getQuestion5());

		setQuestion6(request.getParameter("question6") + " " + request.getParameter("question6_text"));
		mapQestionsWithAnswers.put(6, getQuestion6());

		setQuestion7(request.getParameter("question7") + " " + request.getParameter("question7_text"));
		mapQestionsWithAnswers.put(7, getQuestion7());

		setQuestion8(request.getParameter("question8") + " " + request.getParameter("question8_text"));
		mapQestionsWithAnswers.put(8, getQuestion8());

		storeResult();
		return "response.xhtml";
	}

	public void storeResult() throws ClassNotFoundException, SQLException {
		Connection connect = null;

		String url = "jdbc:mysql://localhost:3306/survey";

		String username = "root";
		String password = "root";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, username, password);

			connect.setAutoCommit(false);
		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
		PreparedStatement ps = null;
		String sql = "INSERT INTO `survey`.`answer`\r\n" + "(`question_id`,\r\n" + "`answer_text`,\r\n"
				+ "`surveys_id`)\r\n" + "VALUES\r\n" + "(?,?,?);";
		ps = connect.prepareStatement(sql);

		for (Map.Entry<Integer, String> entry : mapQestionsWithAnswers.entrySet()) {
			ps.setInt(1, entry.getKey());
			ps.setString(2, entry.getValue());
			ps.setInt(3, 123);
			ps.addBatch();

		}

		ps.executeBatch();
		connect.commit();
		// close resources
		ps.close();
		connect.close();

	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public String getAlter() {
		return alter;
	}

	public void setAlter(String alter) {
		this.alter = alter;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(String question4) {
		this.question4 = question4;
	}

	public String getQuestion5() {
		return question5;
	}

	public void setQuestion5(String question5) {
		this.question5 = question5;
	}

	public String getQuestion6() {
		return question6;
	}

	public void setQuestion6(String question6) {
		this.question6 = question6;
	}

	public String getQuestion7() {
		return question7;
	}

	public void setQuestion7(String question7) {
		this.question7 = question7;
	}

	public String getQuestion8() {
		return question8;
	}

	public void setQuestion8(String question8) {
		this.question8 = question8;
	}
}
