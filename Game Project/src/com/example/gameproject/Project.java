package com.example.gameproject;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

class Project {
  public static final int HEIGHT = 768;
  public static final int WIDTH = 1024;

  public static void main(String args[]) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    JFrame frame = new JFrame();
    frame.getContentPane().add(new Game());
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(1024, 768);
    frame.setLocation(dimension.width / 2 - frame.getSize().width / 2,
        dimension.height / 2 - frame.getSize().height / 2);
    frame.setTitle("Multiplication Catcher");
    frame.setVisible(true);
  }
}
