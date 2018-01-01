package model;

import org.json.simple.JSONObject;

public class Answer {
    private String text;
    private int nextQuestionID;
	private Achievement achievement;

    public Answer(String text, int nextQuestionID) {
        this.text = text;
        this.nextQuestionID = nextQuestionID;
		achievement = null;
    }
	
	public Answer(JSONObject answer) {
		this(answer.get("AnswerText").toString(), (int) (long) answer.get("NextQuestionID"));
		boolean hasAchievement = (boolean) answer.get("HasAchievement");
		if (!hasAchievement)
			return;
		
		this.achievement = new Achievement(answer.get("AchievementText").toString(), (int) (long) answer.get("AchievementPoint"));
	}

    public String getText() {
        return text;
    }

    public int getNextQuestionID() {
        return nextQuestionID;
    }
	
	public Achievement getAchievement() {
		return achievement;
	}
	
	public boolean hasAchievement() {
		return achievement != null;
	}

    public void setText(String text) {
        this.text = text;
    }

    public void setNextQuestionID(int questionID) {
        this.nextQuestionID = questionID;
    }
	
	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	
	public void setAchievement(String text, int point) {
		this.achievement = new Achievement(text, point);
	}
	
	public void removeAchievement() {
		this.achievement = null;
	}

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AnswerText", text);
        jsonObject.put("NextQuestionID", nextQuestionID);
		if (!hasAchievement())
			jsonObject.put("HasAchievement", false);
		else {
			jsonObject.put("HasAchievement", true);
			jsonObject.put("AchievementText", achievement.getText());
			jsonObject.put("AchievementPoint", achievement.getPoint());
		}

		return jsonObject;
    }
}
