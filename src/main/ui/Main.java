package ui;

import model.Password;
import model.User;
import model.UserSystem;
import ui.parser.WeatherReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    JTextField username;
    JTextField userPassword;

    public Main() throws IOException {
        super("TodoList Application");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        try {
            BufferedImage myImage = ImageIO.read(new File("src/main/ui/image/Background.png"));
            setContentPane(new BackgroundPanel(myImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel welcome = new JLabel("Welcome to TodoList Application", JLabel.CENTER);
        welcome.setFont(new Font("Ubuntu", Font.BOLD, 22));
        welcome.setBounds(60,20, 480,30);
        add(welcome);
        welcome.setForeground(Color.white);

        JLabel weatherReport = new JLabel(weatherMessage());
        weatherReport.setFont(new Font("Ubuntu", Font.PLAIN,15));
        weatherReport.setBounds(60, 100, 550, 20);
        add(weatherReport);
        weatherReport.setForeground(Color.white);

        username = new JTextField(30);
        username.setUI(new TransparentText(" Enter Your User Name", Color.gray));
        username.setFont(new Font("Ubuntu", Font.PLAIN,15));
        username.setBounds(200,150, 200,30);
        add(username);


        userPassword = new JPasswordField();
        userPassword.setUI(new TransparentText(" Enter Your Password", Color.gray));
        userPassword.setFont(new Font("Ubuntu", Font.PLAIN,15));
        userPassword.setBounds(200,220, 200,30);
        add(userPassword);

        JButton newUser = new JButton("Add A New User to the System");
        newUser.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        newUser.setBounds(350, 400, 200, 30);
        add(newUser);
        newUser.setActionCommand("newUser");
        newUser.addActionListener(this);
        newUser.setForeground(Color.black);

        JButton icon = new JButton();
        try {
            BufferedImage iconImg = ImageIO.read(new File("src/main/ui/image/Login.png"));
            icon.setIcon(new ImageIcon(iconImg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        icon.setBounds(280, 280, 40,40);
        add(icon);

        icon.setActionCommand("Enter");
        icon.addActionListener(this);

        JButton addUser = new JButton("New User?");
        addUser.setFont(new Font("Ubuntu", Font.PLAIN, 15));
        addUser.setBounds(420, 320, 100, 30);
        add(addUser);
        addUser.setActionCommand("newUser");
        addUser.addActionListener(this);
        addUser.setForeground(Color.black);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserSystem userSystem = new UserSystem();
        save(userSystem);
        if (e.getActionCommand().equals("Enter")) {
            String name = username.getText();
            int password =  Integer.parseInt(userPassword.getText());
            Boolean isVerified = userSystem.verification(password, name);
            if (isVerified) {
                this.dispose();
                new ShowOptionWindow(new User(name, new Password(password)));
            } else {
                JOptionPane.showMessageDialog(null,"Invalid username or password");
            }
        }
        if (e.getActionCommand().equals("newUser")) {
            new AddUserWindow();
        }
    }


    public static void main(String[] args) throws IOException {
        new Main();
    }

    public void save(UserSystem userSystem) {
        try {
            userSystem.load("src/userFile");
        } catch (IOException exce) {
            System.out.println("This should not happen since UserFile exists!");
        }
    }

    private static String weatherMessage() throws IOException {
        WeatherReader webReader = new WeatherReader();
        return webReader.getWeather();
    }

}
