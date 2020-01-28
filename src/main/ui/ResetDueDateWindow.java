package ui;

import model.Item;
import model.TodoList;
import model.exceptions.IncorrectDateFormatException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResetDueDateWindow extends JFrame implements ActionListener {
    JTextField dateEntered;
    int index;
    ViewTodoListWindow viewTodoListWindow;

    public ResetDueDateWindow(int index, ViewTodoListWindow viewTodoListWindow) {

        super("Reset Item Due Date");
        this.index = index;
        this.viewTodoListWindow = viewTodoListWindow;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/main/ui/image/Background.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel enterDate = new JLabel("Please Enter New Date for the Item in MM/dd/yy Format");
        add(enterDate);
        enterDate.setBounds(120, 30, 300, 20);
        enterDate.setForeground(Color.black);

        dateEntered = new JTextField(30);
        add(dateEntered);
        dateEntered.setBounds(175,80,150,20);

        JButton enter = new JButton("Enter");
        add(enter);
        enter.setBounds(210, 150,80,20);
        enter.setForeground(Color.black);

        enter.setActionCommand("Enter");
        enter.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TodoList todoList = new TodoList();
        load(todoList);

        Item i = todoList.getTodoList().get(index);

        if (e.getActionCommand().equals("Enter")) {
            String date = dateEntered.getText();
            i.setDueDate(date);
            JOptionPane.showMessageDialog(null,"Item due date has been reset");
            checkInProgress(todoList);
            check(todoList);
            viewTodoListWindow.dispose();
            dispose();
            try {
                todoList.save("src/saveItemFile.txt");
            } catch (IOException ex) {
                System.out.println("This should not happen since file exists");
            }
            new ViewTodoListWindow();
        }
    }

    public void checkInProgress(TodoList todoList) {
        try {
            todoList.checkInProgress();
        } catch (IncorrectDateFormatException e) {
            System.out.println("Date Format Incorrect");
        }
    }

    public void check(TodoList todoList) {
        try {
            todoList.checkOverDue();
        } catch (IncorrectDateFormatException e) {
            System.out.println("Date Format Incorrect");
        }
    }

    public void load(TodoList todoList) {
        try {
            todoList.load("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("Please create the file before running!");
        }
    }
}
