package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ScenarioEditor {
    private String name;
    private String description;
    private ArrayList<Question> questions;

    public ScenarioEditor(String name, String description) {
        this.name = name;
        this.description = description;
        questions = new ArrayList<Question>();
    }

    public ScenarioEditor(JSONObject scenario) {
        name = scenario.get("ScenarioName").toString();
        description = scenario.get("ScenarioDescription").toString();
        questions = new ArrayList<Question>();

        JSONArray questions = (JSONArray) scenario.get("Questions");

        for (Object question : questions)
            this.questions.add(new Question((JSONObject) question));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion(int id) {
        return questions.get(id);
    }

    public void removeQuestion(int id) {
        questions.remove(id);
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public void addAnswer(int questionID, String text) {
        questions.get(questionID).addAnswer(text);
    }

    public void addAnswer(int questionID, String text, int nextQuestionID) {
        questions.get(questionID).addAnswer(text, nextQuestionID);
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ScenarioName", name);
        jsonObject.put("ScenarioDescription", description);

        JSONArray questions = new JSONArray();

        for (Question question : this.questions)
            questions.add(question.toJSONObject());

        jsonObject.put("Questions", questions);

        return jsonObject;
    }
}
