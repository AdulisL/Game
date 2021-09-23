package com.example.gameproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

class Object {
  private int height = 0;
  private int positionX = 0;
  private int positionY = 0;
  private int width = 0;
  private String character = null;

  public Object(int width, int height, int digit) {
    this.height = height;
    this.width = width;
    this.reset(digit);
  }

  public void draw(Graphics g) {
    g.setColor(new Color(ThreadLocalRandom.current().nextFloat(),
        ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat()));
    g.setFont(new Font("Comic Sans MS", Font.BOLD, this.width / 32));
    g.drawString(this.character, this.positionX, this.positionY);
  }

  public int getPositionX() {
    return this.positionX;
  }

  public int getPositionY() {
    return this.positionY;
  }

  public void drop() {
    this.positionY += this.height / 64;
  }

  public void reset(int digit) {
    this.character = Integer.toString(digit);
    this.positionX = ThreadLocalRandom.current().nextInt(this.width);
    this.positionY = 0;
  }
}
