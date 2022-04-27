
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Login {
    private JPanel MainLogin;
    private JButton btnOK;
    private JButton btnCancel;
    private JComboBox combousertype;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().MainLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            var con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mmsdb", "root", "admin");
            System.out.println("Successfully connected");
            return con;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public Login() {
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = connect();
                    Statement st;
                    st = conn.createStatement();

                    String username, password, usertype;

                    usertype = combousertype.getSelectedItem().toString();
                    username = txtUsername.getText();
                    password = String.valueOf(txtPassword.getPassword());

                    var query1 = "SELECT * FROM mmsdb.receptionist WHERE name = '" + username + "' AND password='" + password + "'";

                    ResultSet resultSet = st.executeQuery(query1);
                    if (resultSet.next()) {

                        JFrame lf = new JFrame("Login");
                        lf.setVisible(false);
                        lf.dispose();

                        JFrame frame = new JFrame("Receptionist");
                        frame.setContentPane(new Receptionist().Recept);
                        frame.pack();
                        frame.setVisible(true);


                    } else
                        JOptionPane.showMessageDialog(null, "Invalid user name or password", "Alter", JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception ex) {
                    throw new RuntimeException(ex);

                }
            }
        });
    }
}
