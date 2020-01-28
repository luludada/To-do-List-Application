package ui;

import model.Item;
import model.RegularItem;
import model.TodoList;
import model.UrgentItem;
import model.exceptions.TooManyItemsException;
import model.exceptions.TooManyUrgentItemException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddNewItemWindow extends JFrame implements ActionListener {
    JTextField itemText;
    JTextField itemDueDate;
    ViewTodoListWindow viewTodoListWindow;
    private JComboBox<String> itemTypeList;

    public AddNewItemWindow(ViewTodoListWindow viewTodoListWindow) {
        super("Add an Item");
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

        JLabel enterText = new JLabel("Enter the Item Text: ");
        enterText.setBounds(48, 40, 400, 20);
        add(enterText);
        enterText.setForeground(Color.white);

        itemText = new JTextField(30);
        itemText.setUI(new TransparentText(" Item Description ", Color.gray));
        itemText.setFont(new Font("Ubuntu", Font.PLAIN,15));
        itemText.setBounds(50, 70, 300, 20);
        add(itemText);


        JLabel enterDueDate = new JLabel("Enter the Item Due Date ");
        enterDueDate.setBounds(50, 100, 500, 20);
        add(enterDueDate);
        enterDueDate.setForeground(Color.white);

        itemDueDate = new JTextField(30);
        itemDueDate.setUI(new TransparentText(" In Format of [MM/dd/yy] ", Color.gray));
        itemDueDate.setFont(new Font("Ubuntu", Font.PLAIN,15));
        itemDueDate.setBounds(50, 130,300,20);
        add(itemDueDate);

        JLabel typeSelection = new JLabel("Please select the type of Item: ");
        typeSelection.setBounds(50, 160, 600, 20);
        add(typeSelection);
        typeSelection.setForeground(Color.white);

        String[] typeList = { "Urgent", "Regular", "Academic" };
        //Create the combo box
        itemTypeList = new JComboBox<String>(typeList);
        itemTypeList.addActionListener(this);
        itemTypeList.setBounds(60, 190, 200, 20);
        add(itemTypeList);


        JButton addType = new JButton("Add Event");
        addType.setActionCommand("Add Event");
        addType.addActionListener(this);
        addType.setBounds(250,300, 100,30);
        add(addType);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        TodoList todoList = new TodoList();
        load(todoList);

        String text = itemText.getText();
        String dueDate = itemDueDate.getText();
        String viewSelect = (String)itemTypeList.getSelectedItem();

        if (e.getActionCommand().equals("Add Event")) {
            if (viewSelect.equals("Urgent")) {
                Item item = new UrgentItem(text, dueDate);
                urgent(todoList, item);
            } else if (viewSelect.equals("Regular")) {
                Item item = new RegularItem(text, dueDate);
                regular(todoList, item);
            } else if (viewSelect.equals("Academic")) {
                Item item = new RegularItem(text, dueDate);
                academic(todoList, item);
            }
            close();
        }
    }

    public void load(TodoList todoList) {
        try {
            todoList.load("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("Please create file before running program.");
        }
    }

    public void urgent(TodoList todoList, Item item) {
        try {
            todoList.addItem(item);
            JOptionPane.showMessageDialog(null,"Your Item has been successfully added.");
        } catch (TooManyUrgentItemException e1) {
            JOptionPane.showMessageDialog(null,"Too many Urgent item in the list!");
        } catch (TooManyItemsException e1) {
            JOptionPane.showMessageDialog(null,"Too many undo item in the list!");
        }

        try {
            todoList.save("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("This should not happen since file exists!");
        }
    }

    public void regular(TodoList todoList, Item item) {
        try {
            todoList.addItem(item);
            JOptionPane.showMessageDialog(null,"Your Item has been successfully added.");
        } catch (TooManyUrgentItemException e1) {
            System.out.print("Regular Unexpected");
        } catch (TooManyItemsException e2) {
            JOptionPane.showMessageDialog(null,"Too many undo item in the list!");
        }

        try {
            todoList.save("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("This should not happen since file exists!");
        }
    }

    public void academic(TodoList todoList, Item item) {
        try {
            todoList.addItem(item);
            JOptionPane.showMessageDialog(null,"Your Item has been successfully added.");
        } catch (TooManyUrgentItemException e1) {
            System.out.print("Academic Unexpected");
        } catch (TooManyItemsException e2) {
            JOptionPane.showMessageDialog(null,"Too many undo item in the list!");
        }

        try {
            todoList.save("src/saveItemFile.txt");
        } catch (IOException ex) {
            System.out.println("This should not happen since file exists!");
        }
    }

    public void close() {
        viewTodoListWindow.dispose();
        new ViewTodoListWindow();
        dispose();
    }

}
