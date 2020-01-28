package ui;

import model.TodoList;
import model.User;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShowOptionWindow extends JFrame implements ActionListener {
    private int setXposition1 = 40;
    private int setXposition2 = 330;
    private int buttonHeight = 40;
    private int buttonWidth = 250;

    Clip clip;
    private User currentUser;


    public ShowOptionWindow(User currentUser) {
        super("TodoList Application");
        this.currentUser = currentUser;
        setPreferredSize(new Dimension(600, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/main/ui/image/Background.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel option = new JLabel("Please select following option: ", JLabel.CENTER);
        option.setBounds(40, 20, 300, 20);
        option.setFont(new Font("Ubuntu", Font.BOLD, 20));
        add(option);
        option.setForeground(Color.white);

        JButton viewTodoList = new JButton("View Current Todolist");
        viewTodoList.setBounds(setXposition1, 100, buttonWidth, buttonHeight);
        viewTodoList.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        add(viewTodoList);
        viewTodoList.setActionCommand("view");
        viewTodoList.addActionListener(this);
        viewTodoList.setForeground(Color.black);

        JButton empty = new JButton("Empty Todolist");
        empty.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        empty.setBounds(setXposition1, 200, buttonWidth, buttonHeight);
        add(empty);
        empty.setActionCommand("empty");
        empty.addActionListener(this);
        empty.setForeground(Color.black);

        this.currentUser = currentUser;
        JButton resetPassword = new JButton("Reset Passwords");
        resetPassword.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        resetPassword.setBounds(setXposition2, 100, buttonWidth, buttonHeight);
        add(resetPassword);
        resetPassword.setActionCommand("resetPassword");
        resetPassword.addActionListener(this);
        resetPassword.setForeground(Color.black);


        JButton logOut = new JButton("Log out");
        logOut.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        logOut.setBounds(setXposition2, 200, buttonWidth, buttonHeight);
        add(logOut);
        logOut.setActionCommand("logOut");
        logOut.addActionListener(this);
        logOut.setForeground(Color.black);

        JButton quit = new JButton("Quit TodoList Application");
        quit.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        quit.setBounds(175, 300, buttonWidth, buttonHeight);
        add(quit);
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        quit.setForeground(Color.black);

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/main/ui/music/Log.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            new ViewTodoListWindow();
        } else if (e.getActionCommand().equals("resetPassword")) {
            new ResetCurrentUserPasswordWindow(currentUser);
        } else if (e.getActionCommand().equals("empty")) {
            empty();
        } else if (e.getActionCommand().equals("logOut")) {
            logOut();
        } else if (e.getActionCommand().equals("quit")) {
            quit();
        }
    }


    private void empty() {
        TodoList todoList = new TodoList();
        try {
            todoList.emptyFile("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("This should not happen since the file exists!");
        }
        JOptionPane.showMessageDialog(null,"Your TodoList has been emptied!");
    }

    private void logOut() {
        JOptionPane.showMessageDialog(null,"You have been logged out.");
        clip.close();
        try {
            new Main();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        dispose();
    }

    private void quit() {

        try {
            AudioInputStream end = AudioSystem.getAudioInputStream(new File("src/main/ui/music/End.wav"));
            clip = AudioSystem.getClip();
            clip.open(end);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null,"Thanks for using TodoList!");
        clip.close();
        dispose();
        System.exit(0);
    }


}
