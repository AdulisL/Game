package com.example.gameproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
class Game extends JPanel implements ActionListener, KeyListener {
  private boolean answer = false;
  private int firstNumber = 0;
  private int secondNumber = 0;
  private int paddleX = 0;
  private int paddleSizeX = 0;
  private int paddleSizeY = 0;
  private int paddleY = 0;
  private int score = 0;
  private int tempFirst = 0;
  private int tempSecond = 0;
  private BufferedImage image = null;
  private Color color = new Color(ThreadLocalRandom.current().nextFloat(),
      ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat());
  private Timer timer = new Timer(45, this);
  private Object object = null;
  private Object objectTwo = null;

  public Game() {
    this.addKeyListener(this);
    this.setSize(Project.WIDTH, Project.HEIGHT);
    this.setFocusable(true);
    this.timer.start();
    this.paddleX = this.getWidth() / 3;
    this.firstNumber = ThreadLocalRandom.current().nextInt(1, 10);
    this.object = new Object(this.getWidth(), this.getHeight(), this.firstNumber);
    this.objectTwo =
        new Object(this.getWidth(), this.getHeight(), ThreadLocalRandom.current().nextInt(1, 101));
    try {
      image = ImageIO.read(this.getClass().getResourceAsStream("/background.png"));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  private void collision() {
    if (this.object.getPositionY() >= this.getHeight() - this.paddleSizeY) {
      if (this.object.getPositionX() >= this.paddleX
          && this.object.getPositionX() <= this.paddleX + this.paddleSizeX) {
        this.score++;
        if (this.firstNumber == 0) {
          this.firstNumber = ThreadLocalRandom.current().nextInt(1, 10);
          this.answer = false;
          this.object.reset(this.firstNumber);
          this.objectTwo.reset(ThreadLocalRandom.current().nextInt(1, 101));
        } else {
          if (this.secondNumber == 0) {
            this.secondNumber = ThreadLocalRandom.current().nextInt(1, 10);
            this.object.reset(this.secondNumber);
          } else {
            this.tempFirst = this.firstNumber;
            this.tempSecond = this.secondNumber;
            this.answer = true;
            this.object.reset(this.firstNumber * this.secondNumber);
            this.firstNumber = 0;
            this.secondNumber = 0;
          }
        }
      } else {
        this.end();
      }
    }

    if (this.objectTwo.getPositionY() >= this.getHeight()) {
      this.objectTwo.reset(0);
    }
  }

  private void end() {
    File file = new File("score.txt");
    String name =
        JOptionPane.showInputDialog(null, "New score! Enter your name to record (cancel to skip)",
            "New Score", JOptionPane.QUESTION_MESSAGE);
    if (name != null) {
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(name + " " + Integer.toString(this.score));
        bw.newLine();
        bw.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.timer.stop();
    int result = JOptionPane.showConfirmDialog(null, "Play again?", "Play again?",
        JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.NO_OPTION) {
      System.exit(0);
    } else if (result == JOptionPane.YES_OPTION) {
      this.resetGame();
    } else {
      System.exit(0);
    }
  }

  private void resetGame() {
    this.firstNumber = ThreadLocalRandom.current().nextInt(1, 10);
    this.secondNumber = 0;
    this.object.reset(firstNumber);
    this.objectTwo.reset(ThreadLocalRandom.current().nextInt(1, 101));
    this.paddleX = this.getWidth() / 3;
    this.score = 0;
    this.timer.start();
  }

  @Override
  public void paintComponent(Graphics g) {
    this.paddleSizeX = this.getWidth() / 4;
    this.paddleSizeY = this.getHeight() / 60;
    this.paddleY = this.getHeight() - this.paddleSizeY;
    super.paintComponent(g);
    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    g.setColor(color);
    g.fillRect(this.paddleX, this.paddleY, this.paddleSizeX, this.paddleSizeY);
    g.setColor(Color.RED);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, this.getWidth() / 32));
    g.drawString("SCORE " + this.score, this.getWidth() / 32, this.getWidth() / 32);
    if (this.firstNumber != 0) {
      if (this.secondNumber != 0) {
        g.drawString(
            "What is " + Integer.toString(this.firstNumber) + " \u00D7 "
                + Integer.toString(this.secondNumber) + "?",
            this.getWidth() / 3, this.getWidth() / 32);
      } else {
        g.drawString("Quick, catch that " + Integer.toString(this.firstNumber) + "!",
            this.getWidth() / 3, this.getWidth() / 32);
      }
    } else {
      g.drawString(
          Integer.toString(tempFirst) + " \u00D7 " + Integer.toString(tempSecond) + " is "
              + Integer.toString(this.tempFirst * this.tempSecond) + "!",
          this.getWidth() / 3, this.getWidth() / 32);
    }
    this.object.draw(g);
    this.objectTwo.draw(g);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.object.drop();
    if (this.answer == true) {
      this.objectTwo.drop();
    }
    this.repaint();
    this.collision();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (this.paddleX < this.getWidth() - this.paddleSizeX) {
        this.paddleX += this.getWidth() / 16;
        this.color = new Color(ThreadLocalRandom.current().nextFloat(),
            ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat());
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      if (this.paddleX > 0) {
        this.paddleX -= this.getWidth() / 16;
        this.color = new Color(ThreadLocalRandom.current().nextFloat(),
            ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat());
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
