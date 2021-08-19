package Body;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SuaTk extends Frame implements ActionListener {
    static String user = "";
    Label lb1,lb2;
    TextField txt1,txt2;
    Button ok,exit;
    Panel pn,pn2,pn3;
    public void GUI() {
        lb1 = new Label("Tài Khoản");
        lb2 = new Label("Mật Khẩu");

        showinfo(user);
        txt1.disable();
        ok = new Button("Lưu");
        exit = new Button("Huỷ");

        ok.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn2 = new Panel(new GridLayout(2,2));
        pn3 = new Panel(new FlowLayout());

        pn2.add(lb1);
        pn2.add(txt1);
        pn2.add(lb2);
        pn2.add(txt2);
        pn3.add(ok);
        pn3.add(exit);
        pn.add(pn2,BorderLayout.CENTER);
        pn.add(pn3,BorderLayout.SOUTH);
        add(pn);
        setSize(400,200);
        show();
    }
    public void info(String sach){
        user = sach;
    }
    public void showinfo(String a) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM ACCOUNT where login like '" + a + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                txt1 = new TextField(rs.getString("login"));
                txt2 = new TextField(rs.getString("password"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok) {
            String a = txt1.getText(), b = txt2.getText();
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
                Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
                Statement stmt = con.createStatement();
                String sql = "UPDATE ACCOUNT SET password = '" + b + "' WHERE login = '"+a+"'";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (Exception ex) {System.out.println(ex);}
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            QLTK.sqlrun("");
            QLTK.update_table();
            this.dispose();
        }
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
    public SuaTk(String at) {
        super("Sửa Thông Tin Tài Khoản");
        info(at);
        GUI();
    }
    public static void main(String[] args) {}
}