package io.github.wdpm.jdk16;

import javax.swing.*;
import java.awt.*;

public class JEP392 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World Java Swing");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lblText = new JLabel("Hello JEP392!", SwingConstants.CENTER);
        frame.getContentPane().add(lblText);
        frame.pack();
        frame.setVisible(true);
    }
}