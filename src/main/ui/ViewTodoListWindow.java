package ui;

import model.Item;
import model.TodoList;
import model.exceptions.IncorrectDateFormatException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ViewTodoListWindow extends JFrame implements ActionListener {

    DefaultTableModel model;
    JTable table;
    TodoList todoList;

    public ViewTodoListWindow() {

        //set the window at the center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage viewListImage = ImageIO.read(new File("src/main/ui/image/ViewList.png"));
            setContentPane(new BackgroundPanel(viewListImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item Index");
        model.addColumn("Type");
        model.addColumn("Status");
        model.addColumn("Name");
        model.addColumn("Due Date");


        //model = new DefaultTableModel(null, columns) {};
        //model.setColumnIdentifiers(columns);

        table = new JTable(model);

        table.setShowGrid(true);


        JScrollPane tableSP = new JScrollPane(table);
        add(tableSP);

        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        table.setBackground(Color.white);
        table.setForeground(Color.black);

        todoList = new TodoList();

        try {
            todoList.load("src/saveItemFile.txt");
        } catch (IOException e) {
            System.out.println("Please create saveItemFile before run the program");
        }

        for (Item i: todoList.getTodoList()) {
            Vector row = new Vector();
            row.add(todoList.getTodoList().indexOf(i));
            row.add(i.getType());
            row.add(i.getStatus());
            row.add(i.getName());
            row.add(i.getDueDate());
            model.addRow(row);
        }

        JButton addItem = new JButton("Add a New Item");
        addItem.setBounds(100,400,150,30);
        add(addItem);
        addItem.setActionCommand("add");
        addItem.addActionListener(this);

        JButton removeSelectedItem = new JButton("Cross-Off Selected Item");
        removeSelectedItem.setBounds(100, 400, 150, 30);
        add(removeSelectedItem);
        removeSelectedItem.setActionCommand("remove");
        removeSelectedItem.addActionListener(this);

        JButton resetDue = new JButton("Reset Item Due date");
        resetDue.setBounds(100, 400, 120, 30);
        add(resetDue);
        resetDue.setActionCommand("resetDueDate");
        resetDue.addActionListener(this);


        setTitle("TodoList");
        setLayout(new FlowLayout(10, 10, 10));
        checkInProgress(todoList);
        check(todoList);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    class SortingColumnModel extends DefaultTableColumnModel {

        public void addColumn(TableColumn tc) {
            super.addColumn(tc);
            int newIndex = sortedIndexOf(tc);
            if (newIndex != tc.getModelIndex()) {
                moveColumn(tc.getModelIndex(), newIndex);
            }
        }

        protected int sortedIndexOf(TableColumn tc) {
            // just do a linear search for now
            int stop = getColumnCount();
            String name = tc.getHeaderValue().toString();

            for (int i = 0; i < stop; i++) {
                if (name.compareTo(getColumn(i).getHeaderValue().toString()) <= 0) {
                    return i;
                }
            }
            return stop;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("remove")) {
            remove();
        }
        if (e.getActionCommand().equals("resetDueDate")) {
            int row = table.getSelectedRow();
            new ResetDueDateWindow(row, this);
        }
        if (e.getActionCommand().equals("add")) {
            new AddNewItemWindow(this);
        }

    }

    public void remove() {
        int row = table.getSelectedRow();
        todoList.crossedOffItem(row);

        JOptionPane.showMessageDialog(null,"Item status has been changed successfully.");

        String done = "Completed";
        table.setValueAt((Object)done,row,4);
        try {
            todoList.save("src/saveItemFile.txt");
        } catch (IOException excp) {
            System.out.println("This should never happen, I know this file exists");
        }
        new ViewTodoListWindow();
        dispose();
    }

    public void checkInProgress(TodoList todoList) {
        try {
            todoList.checkInProgress();
        } catch (IncorrectDateFormatException e1) {
            System.out.println("Incorrect Date Format");
        }
    }

    public void check(TodoList todoList) {
        try {
            todoList.checkOverDue();
        } catch (IncorrectDateFormatException e1) {
            System.out.println("Incorrect Date Format");
        }
    }
}


