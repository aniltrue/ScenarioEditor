package model;

import org.json.simple.*;

import javax.swing.*;

public class Achievement {
  private String text;
  private int point;
  
  public Achievement(String text, int point) {
    this.text = text;
    this.point = point;
  }
  
  public Achievement(JSONObject achievement) {
    try {
      text = achievement.get("Text").toString();
      point = (int) (long) achievement.get("Point");
      
    } catch (ClassCastException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Casting Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public String getText() {
    return text;
  }
  
  public int getPoint() {
    return point;
  }
  
  
}