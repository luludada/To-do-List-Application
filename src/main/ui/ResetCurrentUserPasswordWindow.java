package ui;

import model.User;
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

public class ResetCurrentUserPasswordWindow extends JFrame implements ActionListener {

    private User currentUser;
    private JTextField passwordEntered;

    public ResetCurrentUserPasswordWindow(User currentUser) {
        super("Reset User Password");
        this.currentUser = currentUser;


        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage myImage = ImageIO.read(new File("src/main/ui/image/Background.png"));
            setContentPane(new BackgroundPanel(myImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel enterPassword = new JLabel("Please Enter New Passwords");
        add(enterPassword);
        enterPassword.setBounds(50, 40, 300, 20);
        enterPassword.setForeground(Color.white);

        passwordEntered = new JTextField(20);
        passwordEntered.setUI(new TransparentText("Number Only", Color.gray));
        passwordEntered.setFont(new Font("Ubuntu", Font.PLAIN,15));
        passwordEntered.setBounds(50, 80, 100, 20);
        add(passwordEntered);

        JButton btn = new JButton("Enter");
        add(btn);
        btn.setBounds(210, 120, 80, 20);
        btn.setActionCommand("Enter");
        btn.addActionListener(this);
        btn.setForeground(Color.black);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Enter")) {
            UserSystem userSystem = new UserSystem();
            try {
                userSystem.load("src/userFile");
            } catch (IOException ex) {
                System.out.println("Please create the file before run the program!");
            }

            int userPassword = Integer.parseInt(passwordEntered.getText());

            userSystem.resetPasswords(currentUser, userPassword);

            try {
                userSystem.save("src/userFile");
            } catch (IOException ex) {
                System.out.println("This should not happen since file exists");
            }

            JOptionPane.showMessageDialog(null, "User password has been reset");
            dispose();
        }

    }
}
