package Body;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SuaSach extends Frame implements ActionListener {
    static String bid = "";
    Label lb1,lb2,lb3,lb4,lb5;
    TextField txt1,txt2,txt3,txt4,txt5;
    Button ok,exit;
    Panel pn,pn2,pn3;
    public void GUI() {
        lb1 = new Label("Tên");
        lb2 = new Label("Tác Giả");
        lb3 = new Label("Thể Loại");
        lb4 = new Label("Giá Tiền");
        lb5 = new Label("Số Lượng");

        showinfo(bid);

        ok = new Button("Lưu");
        exit = new Button("Huỷ");

        ok.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn2 = new Panel(new GridLayout(5,2));
        pn3 = new Panel(new FlowLayout());

        pn2.add(lb1);
        pn2.add(txt1);
        pn2.add(lb2);
        pn2.add(txt2);
        pn2.add(lb3);
        pn2.add(txt3);
        pn2.add(lb4);
        pn2.add(txt4);
        pn2.add(lb5);
        pn2.add(txt5);
        pn3.add(ok);
        pn3.add(exit);
        pn.add(pn2,BorderLayout.CENTER);
        pn.add(pn3,BorderLayout.SOUTH);
        add(pn);
        setSize(500,300);
        show();
    }
    public void info(String sach){
        bid = sach;
    }
    public void showinfo(String a) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BOOK where bid like '" + a + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                txt1 = new TextField(rs.getString("title"));
                txt2 = new TextField(rs.getString("author"));
                txt3 = new TextField(rs.getString("type"));
                txt4 = new TextField(rs.getString("price"));
                txt5 = new TextField(rs.getString("sl"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok) {
            String a = txt1.getText(), b = txt2.getText(), c = txt3.getText(), d = txt4.getText(), f = txt5.getText();
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
                Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
                Statement stmt = con.createStatement();
                String sql = "UPDATE BOOK SET title = '" + a + "', author = '" + b + "', type = '" + c + "', price = " + d + ", sl = " + f + " WHERE bid = "+bid;
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (Exception ex) {System.out.println(ex);}
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            QLS.sqlrun("");
            QLS.update_table();
            this.dispose();
        }
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
    public SuaSach(String at) {
        super("Sửa Thông Tin Sách");
        info(at);
        GUI();
    }
    public static void main(String[] args) {}
}