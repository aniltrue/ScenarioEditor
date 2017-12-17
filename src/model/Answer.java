package model;

import org.json.simple.JSONObject;

public class Answer {
    private String text;
    private int nextQuestionID;

    public Answer(String text, int nextQuestionID) {
        this.text = text;
        this.nextQuestionID = nextQuestionID;
    }

    public String getText() {
        return text;
    }

    public int getNextQuestionID() {
        return nextQuestionID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNextQuestionID(int questionID) {
        this.nextQuestionID = questionID;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AnswerText", text);
        jsonObject.put("NextQuestionID", nextQuestionID);

        return jsonObject;
    }
}
