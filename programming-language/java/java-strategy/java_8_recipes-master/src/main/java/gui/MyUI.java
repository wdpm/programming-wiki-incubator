package gui;

import javax.swing.*;
import java.awt.*;

public class MyUI extends JPanel {
    private JTextField source = new JTextField("Type text here");
    private JTextField destination = new JTextField("Result will be here");

    public MyUI() {
        super(new BorderLayout());
        JPanel north = new JPanel();
        north.add(source);
        north.add(destination);
        JPanel south = new JPanel();
        JButton go = new JButton("Go!");
        south.add(go);

        String greeting = "Hello, %s!";

        go.addActionListener(e -> destination.setText(
                String.format(greeting, source.getText())));

        this.add(north, BorderLayout.NORTH);
        this.add(south, BorderLayout.SOUTH);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JComponent newContentPane = new MyUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MyUI");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyUI::createAndShowGUI);
    }
}
