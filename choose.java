package Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class choose extends Frame implements ActionListener {
    Label lb;
    Button sale1,sale2,exit;
    Panel pn,pn1,pn2,pn3;
    int check = 0;
    boolean checksale;
    public void GUI1() {
        lb = new Label("Chọn phiếu giảm giá");

        sale1 = new Button("10%");
        sale2 = new Button("20%");
        exit = new Button("Quay lại");

        sale1.addActionListener(this);
        sale2.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new GridLayout(3,1));
        pn1 = new Panel(new FlowLayout());
        pn2 = new Panel(new GridLayout(1,2));
        pn3 = new Panel(new FlowLayout());

        pn1.add(lb);
        pn2.add(sale1);
        pn2.add(sale2);
        pn3.add(exit);
        pn.add(pn1);
        pn.add(pn2);
        pn.add(pn3);
        add(pn);
        setSize(300,200);
        show();
    }
    public void returnapp(int a){
        check = a;
    }
    public void check_sql(){
        int i = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            String sql,sql1;
            sql = "SELECT * FROM BOOK ORDER BY sale DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next() && i < 1) {
                int a = rs.getInt("sale");
                if(a!=0) checksale = false;
                else checksale = true;
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sale1){
            check_sql();
            if(checksale) JOptionPane.showMessageDialog(this, "Hiện không có chương trình giảm giá");
            else {
                if (check == 0) {
                    APP.showsale(1);
                    APP.update_table();
                } else {
                    User.showsale(1);
                    User.update_table();
                }
            }
            this.dispose();
        }
        if(e.getSource()==sale2){
            check_sql();
            if(checksale) JOptionPane.showMessageDialog(this, "Hiện không có chương trình giảm giá");
            else {
                check_sql();
                if (check == 0) {
                    APP.showsale(2);
                    APP.update_table();
                } else {
                    User.showsale(2);
                    User.update_table();
                }
            }
            this.dispose();
        }
        if(e.getSource()==exit) this.dispose();
    }
    public choose(int at) {
        super("Phiếu Giảm Giá");
        returnapp(at);
        GUI1();
    }
    public static void main(String[] args) {}
}