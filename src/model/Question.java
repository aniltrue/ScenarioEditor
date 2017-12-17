package model;

import org.json.simple.*;

import javax.swing.*;
import java.util.ArrayList;

public class Question {
    private String text;
    private ArrayList<Answer> answers;

    public Question(JSONObject question) {
        answers = new ArrayList<Answer>();
        try {
            text = question.get("Text").toString();
            JSONArray answers = (JSONArray) question.get("Answers");

            if (answers == null)
                return;

            for (Object item : answers) {
                JSONObject answer = (JSONObject) item;
                this.answers.add(new Answer(answer.get("AnswerText").toString(), (int) (long) answer.get("NextQuestionID")));
            }

        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Casting Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Question(String text) {
        this.text = text;
        answers = new ArrayList<Answer>();
    }

    public void addAnswer(String text) {
        answers.add(new Answer(text, -1));
    }

    public void addAnswer(String text, int nextQuestionID) {
        answers.add(new Answer(text, nextQuestionID));
    }

    public void setNextQuestionID(int id) {
        answers.get(id).setNextQuestionID(id);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public int getAnswerCount() {
        return answers.size();
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Text", text);

        JSONArray answers = new JSONArray();

        for (Answer answer : this.answers)
            answers.add(answer.toJSONObject());

        jsonObject.put("Answers", answers);

        return jsonObject;
    }
}
