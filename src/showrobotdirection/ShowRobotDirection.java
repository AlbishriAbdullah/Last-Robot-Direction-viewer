package ShowRobotDirection;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShowRobotDirection extends JFrame implements ActionListener {
    private JButton showLastDirection;

    public ShowRobotDirection() {
        this.setTitle("Last Direction");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 100);

        showLastDirection = new JButton("Show Last Direction");
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(showLastDirection);

        this.add(panel);
        showLastDirection.addActionListener(this);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ShowRobotDirection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showLastDirection) {
            showLastRecord();
        }
    }

    private void showLastRecord() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotdb", "root", "");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM robot_direction ORDER BY id DESC LIMIT 1");

            if (rs.next()) {
                String direction = rs.getString("direction");
                JOptionPane.showMessageDialog(this, "Last direction: " + direction, "Last Direction", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Last Direction: "+direction);
            } else {
                JOptionPane.showMessageDialog(this, "No records found", "No Records", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving last record", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
