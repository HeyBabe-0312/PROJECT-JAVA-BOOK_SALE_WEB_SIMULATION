package Body;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class APP extends Frame implements ActionListener,ItemListener,MouseListener{
    String v = "";
    static int check_sale;
    static String name = "", gia = "", tacgia = "", theloai = "", sta ="";
    Label lb;
    TextField txt;
    Button sale,search,dnhap,dki,exit;
    Panel pn,pn1,pn2,pn3,pn4,pn5;
    static JTable tab;
    static Vector<String> columnNames ;
    static Vector<Vector<Object>> data;
    public void GUI() {
        Choice tl=new Choice();
        tl.addItem("All");
        tl.addItem("Novel");
        tl.addItem("Horror");
        tl.addItem("Humor");
        tl.addItem("Anime");

        lb = new Label("Thể Loại: ");

        txt = new TextField();

        sale = new Button("Giảm Giá");
        dnhap = new Button("Đăng Nhập");
        dki = new Button("Đăng Kí");
        search = new Button("Tìm Kiếm");
        exit = new Button("Thoát");

        sqlrun("","");
        tab = new JTable();
        update_table();
        JScrollPane scrollPane = new JScrollPane(tab);
        tab.setFillsViewportHeight(true);

        tab.addMouseListener(this);
        dnhap.addActionListener(this);
        dki.addActionListener(this);
        tab.setSelectionMode(0);
        sale.addActionListener(this);
        search.addActionListener(this);
        exit.addActionListener(this);
        tl.addItemListener(this);

        pn = new Panel(new BorderLayout());
        pn1 = new Panel(new GridLayout(1,2));
        pn2 = new Panel(new BorderLayout());
        pn3 = new Panel(new FlowLayout());
        pn4 = new Panel(new GridLayout(1,3));
        pn5 = new Panel(new GridLayout(1,2));

        pn1.add(pn4);
        pn1.add(pn5);
        pn2.add(scrollPane,BorderLayout.CENTER);
        pn3.add(dnhap);
        pn3.add(dki);
        pn3.add(exit);
        pn4.add(sale);
        pn4.add(lb);
        pn4.add(tl);
        pn5.add(txt);
        pn5.add(search);
        pn.add(pn1,BorderLayout.NORTH);
        pn.add(pn2,BorderLayout.CENTER);
        pn.add(pn3,BorderLayout.SOUTH);
        add(pn);
        setSize(800,600);
        show();
    }
    public static void update_table(){
        tab.setModel(new DefaultTableModel(data, columnNames));
        tab.getColumnModel().getColumn(0).setPreferredWidth(1);
        tab.getColumnModel().getColumn(1).setPreferredWidth(180);
        tab.getColumnModel().getColumn(2).setPreferredWidth(50);
        tab.getColumnModel().getColumn(3).setPreferredWidth(50);
        tab.getColumnModel().getColumn(4).setPreferredWidth(30);
        tab.getColumnModel().getColumn(5).setPreferredWidth(30);
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
                name = rs.getString("title");
                tacgia = rs.getString("author");
                theloai = rs.getString("type");
                gia = rs.getString("price");
                int check =rs.getInt("sl");
                if(check>0) sta ="Còn hàng";
                else sta = "Hết hàng";
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void showsale(int a){
        int c;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt2 = con.createStatement();
            String sql2;
            if(a==1) {
                sql2 = "SELECT * FROM BOOK WHERE sale like '1'";
                ResultSet rs1 = stmt2.executeQuery(sql2);
                data = new Vector<Vector<Object>>();
                while (rs1.next()) {
                    Vector<Object> dr = new Vector<Object>();
                    c = rs1.getInt("bid");
                    setCheck_sale(String.valueOf(c));
                    dr.add(c);
                    if(check_sale==0) dr.add(rs1.getString("title"));
                    else if(check_sale==1) dr.add(rs1.getString("title")+" -10%");
                    else dr.add(rs1.getString("title")+" -20%");
                    dr.add(rs1.getString("author"));
                    dr.add(rs1.getString("type"));
                    dr.add(rs1.getDouble("price"));
                    int check =rs1.getInt("sl");
                    if(check>0) dr.add("Còn hàng");
                    else dr.add("Hết hàng");
                    data.add(dr);
                }
            }
            else if(a==2) {
                sql2 = "SELECT * FROM BOOK WHERE sale like '2'";
                ResultSet rs1 = stmt2.executeQuery(sql2);
                data = new Vector<Vector<Object>>();
                while (rs1.next()) {
                    Vector<Object> dr = new Vector<Object>();
                    c = rs1.getInt("bid");
                    setCheck_sale(String.valueOf(c));
                    dr.add(c);
                    if(check_sale==0) dr.add(rs1.getString("title"));
                    else if(check_sale==1) dr.add(rs1.getString("title")+" -10%");
                    else dr.add(rs1.getString("title")+" -20%");
                    dr.add(rs1.getString("author"));
                    dr.add(rs1.getString("type"));
                    dr.add(rs1.getDouble("price"));
                    int check =rs1.getInt("sl");
                    if(check>0) dr.add("Còn hàng");
                    else dr.add("Hết hàng");
                    data.add(dr);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void sqlrun(String s, String f){
        int c;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BOOK where (title like '%"+s+"%' or author like '%"+s+"%') and type like '%"+f+"%'";
            ResultSet rs = stmt.executeQuery(sql);
            data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> dr = new Vector<Object>();
                c = rs.getInt("bid");
                setCheck_sale(String.valueOf(c));
                dr.add(c);
                if(check_sale==0) dr.add(rs.getString("title"));
                else if(check_sale==1) dr.add(rs.getString("title")+" -10%");
                else dr.add(rs.getString("title")+" -20%");
                dr.add(rs.getString("author"));
                dr.add(rs.getString("type"));
                dr.add(rs.getDouble("price"));
                int check =rs.getInt("sl");
                if(check>0) dr.add("Còn hàng");
                else dr.add("Hết hàng");
                data.add(dr);
            }
            columnNames = new Vector<String>();
            columnNames.add("ID");
            columnNames.add("Tên");
            columnNames.add("Tác Giả");
            columnNames.add("Thể Loại");
            columnNames.add("Giá");
            columnNames.add("Tình Trạng");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==ItemEvent.SELECTED) {
            String itemlabel = (String)e.getItem();
            if(itemlabel=="All") v = "";
            if(itemlabel=="Novel") v = "Novel";
            if(itemlabel=="Horror") v = "Horror";
            if(itemlabel=="Humor") v = "Humor";
            if(itemlabel=="Anime") v = "Anime";
        }
    }
    public void mouseClicked(MouseEvent e) {
        int index = tab.getSelectedRow();
        TableModel model = tab.getModel();
        if(index != -1) {
            String val = model.getValueAt(index, 0).toString();
            showinfo(val);
            setCheck_sale(val);
            if (check_sale == 0) {
                JOptionPane.showMessageDialog(this, "Sách: " + name + "\nThể Loại: " + theloai + "\nTác Giả: " + tacgia + "\nGiá: " + gia + "d\nTình Trạng: " + sta + "\nLưu ý: Đăng nhập để mua sách");
            }
            else if(check_sale == 1){
                double giagoc = Math.round(Double.parseDouble(gia)*100/90*100.0)/100.0;
                JOptionPane.showMessageDialog(this, "Sách: " + name + "\nThể Loại: " + theloai + "\nTác Giả: " + tacgia + "\nGiá: " + gia + "d (Gốc: "+ giagoc +"d)\nTình Trạng: " + sta + "\nLưu ý: Đăng nhập để mua sách");
            }
            else{
                double giagoc = Math.round(Double.parseDouble(gia)*100/80*100.0)/100.0;
                JOptionPane.showMessageDialog(this, "Sách: " + name + "\nThể Loại: " + theloai + "\nTác Giả: " + tacgia + "\nGiá: " + gia + "d (Gốc: "+ giagoc +"d)\nTình Trạng: " + sta + "\nLưu ý: Đăng nhập để mua sách");
            }
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sale) new choose(0);
        if(e.getSource()==dnhap){
            new Dang_nhap("Đăng Nhập");
            this.dispose();
        }
        if(e.getSource()==dki){
            new Dang_ki("Đăng Kí");
            this.dispose();
        }
        if(e.getSource()==search) {
            sqlrun(txt.getText(),v);
            update_table();
        }
        if(e.getSource()==exit) System.exit(0);
    }
    public APP(String at) {
        super(at);
        GUI();
    }
    public static void main(String[] args) {
        new APP("Hệ Thống Bán Sách Trực Tuyến");
    }
}