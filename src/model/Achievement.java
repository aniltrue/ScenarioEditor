package model;

public class Achievement {
  private String text;
  private int point;
  
  public Achievement(String text, int point) {
    this.text = text;
    this.point = point;
  }
  
  public String getText() {
    return text;
  }
  
  public int getPoint() {
    return point;
  }
  
  
}
