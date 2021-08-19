package Body;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mua_sach extends Frame implements ActionListener {
    String price;
    static double gia;
    static int check_sale = 0;
    static String bid = "", user = "";
    Label lb,lb1,lb2,lb3,lb4,lb5;
    TextField txt1,txt2,txt3,txt4,txt5;
    Button ok,exit;
    Panel pn,pn1,pn2,pn3;
    public void GUI() {
        lb = new Label("Thông Tin Sách");
        lb1 = new Label("Tên");
        lb2 = new Label("Thể Loại");
        lb3 = new Label("Tác Giả");
        lb4 = new Label("Giá Tiền");
        lb5 = new Label("Tình Trạng");

        showinfo(bid);
        txt1.disable();
        txt2.disable();
        txt3.disable();
        txt4.disable();
        txt5.disable();

        ok = new Button("Mua");
        exit = new Button("Huỷ");
        ok.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn1 = new Panel(new FlowLayout());
        pn2 = new Panel(new GridLayout(5,2));
        pn3 = new Panel(new FlowLayout());

        pn1.add(lb);
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
        pn.add(pn1,BorderLayout.NORTH);
        pn.add(pn2,BorderLayout.CENTER);
        pn.add(pn3,BorderLayout.SOUTH);
        add(pn);
        setSize(500,300);
        show();
    }
    public void info(String sach, String tk){
        bid = sach;
        user = tk;
    }
    public static void setCheck_sale(String a){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BOOK where bid like '" + a + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                check_sale = rs.getInt("sale");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
                if(check_sale==0) {
                    gia = rs.getDouble("price");
                    txt4 = new TextField(gia+"");
                }
                else if(check_sale==1) {
                    gia = rs.getDouble("price");
                    txt4 = new TextField(gia+"d (Gốc: "+Math.round(gia*100/90*100.0)/100.0+"d)");
                }
                else{
                    gia = rs.getDouble("price");
                    txt4 = new TextField(gia+"d (Gốc: "+Math.round(gia*100/80*100.0)/100.0+"d)");
                }
                int check =rs.getInt("sl");
                if(check>0) txt5 = new TextField("Còn hàng");
                else txt5 = new TextField("Hết hàng");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void sqlrun(String a, String b, String c, String d){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO LICHSUGD(login,time,bid,price) Values('" + a + "','" + b + "'," + c + "," + d + ")";
            stmt.executeUpdate(sql);
            String sql1="UPDATE BOOK SET sl = sl - 1 WHERE bid = '"+c+"'";
            stmt.executeUpdate(sql1);
            String sql2="UPDATE ACCOUNT SET solan = solan + 1 WHERE login = '"+a+"'";
            stmt.executeUpdate(sql2);
            String sql3="UPDATE MUASACH SET luotmua = luotmua + 1 WHERE bid = '"+c+"'";
            stmt.executeUpdate(sql3);
            stmt.close();
        } catch(Exception e) {System.out.println("Error "+e);};
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok) {
            if(txt5.getText().equals("Còn hàng")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime d = LocalDateTime.now();
                setCheck_sale(bid);
                price = String.valueOf(gia);
                sqlrun(user, dtf.format(d), bid,price);
                JOptionPane.showMessageDialog(this, "Bạn đã mua thành công\nCó thể xem lại lịch sử");
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, "Sản phẩm hiện đã hết hàng");
            }
        }
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
    public Mua_sach(String at, String user) {
        super("Mua sách");
        info(at,user);
        GUI();
    }
    public static void main(String[] args) {
    }
}