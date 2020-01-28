package ui;

import model.UserSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddUserWindow extends JFrame implements ActionListener {

    JTextField userName;
    JTextField userPassword;

    public AddUserWindow() {
        super("Add New User");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage myImage = ImageIO.read(new File("src/main/ui/image/Background.png"));
            setContentPane(new BackgroundPanel(myImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel enterNewUser = new JLabel("Create a New User: ");
        enterNewUser.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        enterNewUser.setBounds(120,40, 300,30);
        add(enterNewUser);
        enterNewUser.setForeground(Color.white);

        userName = new JTextField(10);
        userName.setUI(new TransparentText(" Create User Name", Color.gray));
        userName.setFont(new Font("Ubuntu", Font.PLAIN,15));
        userName.setBounds(125,90, 200,30);
        add(userName);

        JLabel enterUserPassword = new JLabel("Please Enter New User Password: ", JLabel.CENTER);
        enterUserPassword.setBounds(65,140, 365,30);
        enterUserPassword.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        add(enterUserPassword);
        enterUserPassword.setForeground(Color.white);

        userPassword = new JTextField(10);
        userPassword.setUI(new TransparentText(" Number Only", Color.gray));
        userPassword.setFont(new Font("Ubuntu", Font.PLAIN,15));
        userPassword.setBounds(125,180, 200,30);
        add(userPassword);

        JButton addUser = new JButton("Add User");
        addUser.setBounds(250,240, 100,30);
        addUser.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        add(addUser);

        addUser.setActionCommand("Add User");
        addUser.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserSystem userSystem = new UserSystem();
        loadUserFile(userSystem);

        if (e.getActionCommand().equals("Add User")) {
            String nm = userName.getText();
            int pw =  Integer.parseInt(userPassword.getText());
            userSystem.addUser(nm,pw);
            saveUserFile(userSystem);
            JOptionPane.showMessageDialog(null, "New user has been added");
            dispose();
        }


    }

    public void loadUserFile(UserSystem userSystem) {
        try {
            userSystem.load("src/userfile");
        } catch (IOException e1) {
            System.out.println("Please create before running the program!");
        }
    }

    public void saveUserFile(UserSystem userSystem) {
        try {
            userSystem.save("src/userfile");
        } catch (IOException e2) {
            System.out.println("This should not happen since file exists");
        }
    }
}
