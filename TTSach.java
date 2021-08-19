package Body;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;


public class TTSach extends Frame implements ActionListener {
    String id[] = new String[150];
    Label lb,lb0,lb1,lb2,lb3,lb4,lb5;
    TextField txt,txt1,txt2,txt3,txt4,txt5;
    Button ok,exit;
    Panel pn,pn1,pn2,pn3;
    public void GUI() {
        lb = new Label("Thông Tin Sách");
        lb0 = new Label("Số ID");
        lb1 = new Label("Tên");
        lb2 = new Label("Thể Loại");
        lb3 = new Label("Tác Giả");
        lb4 = new Label("Giá Tiền");
        lb5 = new Label("Số Lượng");

        txt = new TextField("");
        txt1 = new TextField("");
        txt2 = new TextField("");
        txt3 = new TextField("");
        txt4 = new TextField("");
        txt5 = new TextField("");

        ok = new Button("Chấp Nhận");
        exit = new Button("Huỷ");

        ok.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn1 = new Panel(new FlowLayout());
        pn2 = new Panel(new GridLayout(6,2));
        pn3 = new Panel(new FlowLayout());

        pn1.add(lb);

        pn2.add(lb0);
        pn2.add(txt);
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
    public void sqlrun(){
        int i = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BOOK";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                id[i] = rs.getString("bid");
                i++;
            }
            rs.close();
            stmt.close();
        } catch(Exception e) {System.out.println("Error "+e);};
    }
    public void actionPerformed(ActionEvent e) {
        sqlrun();
        if(e.getSource()==ok) {
            boolean check = true;
            String a = txt.getText(), b = txt1.getText(), c = txt2.getText(), d = txt3.getText(), f = txt4.getText(), g = txt5.getText();
            for (int i = 0; id[i] != null; i++) {
                if (id[i].equals(txt.getText())) check = false;
            }
            if (check) {
                if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || f.isEmpty() || g.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ");
                }else {
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
                        Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
                        Statement stmt = con.createStatement();
                        String sql = "INSERT INTO BOOK(bid,title,type,author,price,sl,sale) Values(" + a + ",'" + b + "','" + c + "','" + d + "'," + f + "," + g + ",0)";
                        stmt.executeUpdate(sql);
                        String sql1 = "INSERT INTO MUASACH(bid,luotmua) Values(" + a + ",0)";
                        stmt.executeUpdate(sql1);
                        stmt.close();
                    } catch (Exception ex) {System.out.println(ex);}
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    new QLS("Quản Lí Sách");
                    this.dispose();
                }
            }else {
                JOptionPane.showMessageDialog(this, "Trùng mã ID sách");
                txt.setText("");
            }
        }
        if(e.getSource()==exit) {
            new QLS("Quản Lí Sách");
            this.dispose();
        }
    }
    public TTSach(String at) {
        super(at);
        GUI();
    }
    public static void main(String[] args) {
        new TTSach("Thông tin sách");
    }
}