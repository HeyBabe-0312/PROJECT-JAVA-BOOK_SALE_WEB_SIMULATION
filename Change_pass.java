package Body;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Change_pass extends Frame implements ActionListener {
    static String user="",pass="";
    Label lb1,lb2,lb3,lb;
    TextField txta,txtb,txtc,txtd;
    Button ok,exit;
    Panel pn,pn1,pn2;
    public void GUI1() {
        lb = new Label("Tài khoản");
        lb1 = new Label("Mật Khẩu hiện tại:  ");
        lb2 = new Label("Mật Khẩu mới: ");
        lb3 = new Label("Nhập lại mật khẩu: ");

        txta = new TextField(user);
        txtb = new TextField();
        txtc = new TextField();
        txtd = new TextField();

        ok = new Button("Ok");
        exit = new Button("Thoát");
        txta.disable();

        ok.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn1 = new Panel(new GridLayout(4,2));
        pn2 = new Panel(new FlowLayout());

        pn1.add(lb);
        pn1.add(txta);
        pn1.add(lb1);
        pn1.add(txtb);
        pn1.add(lb2);
        pn1.add(txtc);
        pn1.add(lb3);
        pn1.add(txtd);

        pn2.add(ok);
        pn2.add(exit);

        pn.add(pn1, BorderLayout.CENTER);
        pn.add(pn2, BorderLayout.SOUTH);
        add(pn);
        setSize(400,300);
        show();
    }
    public void info(String tk){
        user = tk;
    }
    public void sqlrun(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT password FROM ACCOUNT where login like '"+user+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                pass = rs.getString("password");
            }
            rs.close();
            stmt.close();
        } catch(Exception e) {System.out.println("Error "+e);};
    }
    public void actionPerformed(ActionEvent e) {
        sqlrun();
        if(e.getSource()==ok){
            if(txtb.getText().isEmpty()==false){
                if(txtb.getText().equals(pass)){
                    if(txtc.getText().isEmpty()==false) {
                        if (txtc.getText().matches("^[a-z A-Z 1-9]{1,50}$")) {
                            if (txtc.getText().equals(txtd.getText())) {
                                try {
                                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
                                    Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
                                    Statement stmt = con.createStatement();
                                    String sql121 = "UPDATE ACCOUNT SET password = '" + txtc.getText() + "' where login like '" + user + "'";
                                    stmt.executeUpdate(sql121);
                                    stmt.close();
                                } catch (Exception a) {
                                    System.out.println("Error " + a);
                                }
                                JOptionPane.showMessageDialog(this, "Thay đổi mật khẩu thành công");
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp");
                                txtc.setText("");
                                txtd.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Nhập sai kí tự !!!");
                            txtc.setText("");
                            txtd.setText("");
                        }
                    }else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
                    }
                    }else {
                        JOptionPane.showMessageDialog(this, "Sai mật khẩu");
                        txtb.setText("");
                    }
            }else{
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
            }
        }
        if(e.getSource()==exit) this.dispose();
    }
    public Change_pass(String at) {
        super("Thay đổi mật khẩu");
        info(at);
        GUI1();
    }
    public static void main(String[] args) {}
}
