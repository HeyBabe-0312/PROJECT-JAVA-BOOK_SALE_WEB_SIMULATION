package Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QL_Sale extends Frame implements ActionListener {
    Label lb;
    Button open,close,exit;
    Panel pn,pn1,pn2,pn3;
    boolean check;
    String id[] = new String[10];
    public void GUI1() {
        check_sql();
        if(!check) lb = new Label("Chương trình giảm giá hiện đang được áp dụng");
        else lb = new Label("Chương trình giảm giá hiện đang đóng");

        open = new Button("Mở giảm giá");
        close = new Button("Đóng giảm giá");
        exit = new Button("Quay lại");

        open.addActionListener(this);
        close.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new GridLayout(3,1));
        pn1 = new Panel(new FlowLayout());
        pn2 = new Panel(new GridLayout(1,2));
        pn3 = new Panel(new FlowLayout());

        pn1.add(lb);
        pn2.add(open);
        pn2.add(close);
        pn3.add(exit);
        pn.add(pn1);
        pn.add(pn2);
        pn.add(pn3);
        add(pn);
        setSize(300,200);
        show();
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
                if(a!=0) check = false;
                else check = true;
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void opensale(){
        int i = 0, j = 0;
        if(check) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
                Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
                Statement stmt = con.createStatement();
                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();
                String sql, sql1, sql2;
                sql = "SELECT * FROM MUASACH ORDER BY luotmua DESC";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next() && i < 10) {
                    id[i] = rs.getString("bid");
                    sql1 = "UPDATE BOOK SET sale = 1 where bid like '" + id[i] + "'";
                    stmt1.executeUpdate(sql1);
                    sql2 = "UPDATE BOOK SET price = price*90/100 where bid like '" + id[i] + "'";
                    stmt2.executeUpdate(sql2);
                    i++;
                }
                sql = "SELECT * FROM MUASACH ORDER BY luotmua ASC";
                ResultSet rs1 = stmt.executeQuery(sql);
                while (rs1.next() && j < 10) {
                    id[j] = rs1.getString("bid");
                    sql1 = "UPDATE BOOK SET sale = 2 where bid like '" + id[j] + "'";
                    stmt1.executeUpdate(sql1);
                    sql2 = "UPDATE BOOK SET price = price*80/100 where bid like '" + id[j] + "'";
                    stmt2.executeUpdate(sql2);
                    j++;
                }
                stmt.close();
                stmt1.close();
                stmt2.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    public void closesale(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            String sql;
            sql = "UPDATE BOOK SET price = price*100/90 WHERE sale = 1";
            stmt.executeUpdate(sql);
            sql = "UPDATE BOOK SET price = price*100/80 WHERE sale = 2";
            stmt.executeUpdate(sql);
            sql = "UPDATE BOOK SET sale = 0";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==open){
            JOptionPane.showMessageDialog(this, "Đã tạo danh sách sách giảm giá");
            check_sql();
            opensale();
            this.dispose();
        }
        if(e.getSource()==close){
            JOptionPane.showMessageDialog(this, "Đã đóng chương trình giảm giá");
            closesale();
            this.dispose();
        }
        if(e.getSource()==exit) this.dispose();
    }
    public QL_Sale(String at) {
        super(at);
        GUI1();
    }
    public static void main(String[] args) {}
}