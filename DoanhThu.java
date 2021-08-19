package Body;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoanhThu extends Frame implements ActionListener {
    static String bid[] = new String[150];
    int tongtien = 0;
    Label lb,lb1,lb2;
    TextField txt1,txt2;
    Button ngay,thang,nam,exit;
    Panel pn,pn1,pn2,pn3;
    public void GUI() {
        lb = new Label("Thống Kê Doanh Thu");
        lb1 = new Label("Số Lượng:");
        lb2 = new Label("Tổng Tiền:");

        txt1 = new TextField("");
        txt2 = new TextField("");
        txt1.disable();
        txt2.disable();

        ngay = new Button("Hôm Nay");
        thang = new Button("Tháng Nay");
        nam = new Button("Năm Nay");
        exit = new Button("Quay Lại");

        ngay.addActionListener(this);
        thang.addActionListener(this);
        nam.addActionListener(this);
        exit.addActionListener(this);

        pn = new Panel(new GridLayout(3,1));
        pn1 = new Panel(new FlowLayout());
        pn2 = new Panel(new GridLayout(2,2));
        pn3 = new Panel(new FlowLayout());

        pn1.add(lb);
        pn2.add(lb1);
        pn2.add(txt1);
        pn2.add(lb2);
        pn2.add(txt2);
        pn3.add(ngay);
        pn3.add(thang);
        pn3.add(nam);
        pn3.add(exit);

        pn.add(pn1);
        pn.add(pn2);
        pn.add(pn3);
        add(pn);
        setSize(500,300);
        show();
    }
    public void tinhdthu(String a){
        int i = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM LICHSUGD";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(String.valueOf(rs.getDate("time")).contains(a)){
                    bid[i] = rs.getString("bid");
                    tongtien += rs.getDouble("price");
                    i++;
                }
            }
            if(bid[0] == null) {
                txt1.setText("0"); txt2.setText("Không có giao dịch");
            }
            else{
                txt1.setText(String.valueOf(i));
                txt2.setText(String.valueOf(tongtien));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ngay) {
            tongtien = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime d = LocalDateTime.now();
            tinhdthu(dtf.format(d));
        }
        if(e.getSource()==thang) {
            tongtien = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
            LocalDateTime d = LocalDateTime.now();
            tinhdthu(dtf.format(d));
        }
        if(e.getSource()==nam) {
            tongtien = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
            LocalDateTime d = LocalDateTime.now();
            tinhdthu(dtf.format(d));
        }
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
    public DoanhThu(String at) {
        super(at);
        GUI();
    }
    public static void main(String[] args) { }
}