package Body;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableModel;

public class History extends Frame implements ActionListener,MouseListener {
    static String user ="",name="",gia="";
    Button exit;
    Panel pn,pn1,pn2;
    JTable tab;
    static Vector<String> columnNames ;
    static Vector<Vector<Object>> data;
    public void GUI() {
        exit = new Button("Thoát");

        sqlrun(user);
        tab = new JTable( data, columnNames);
        JScrollPane scrollPane = new JScrollPane(tab);
        tab.setFillsViewportHeight(true);

        tab.setSelectionMode(0);
        tab.addMouseListener(this);
        exit.addActionListener(this);

        pn = new Panel(new BorderLayout());
        pn1 = new Panel(new BorderLayout());
        pn2 = new Panel(new FlowLayout());

        pn1.add(scrollPane,BorderLayout.CENTER);
        pn2.add(exit);
        pn.add(pn2,BorderLayout.SOUTH);
        pn.add(pn1,BorderLayout.CENTER);
        add(pn);
        setSize(600,400);
        show();
    }
    public void info(String tk){
        user = tk;
    }
    public void showinfo(String a,String b) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url, "TestLogin", "minhhieu0312");
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            String sql = "SELECT * FROM BOOK where bid like '" + a + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                name = rs.getString("title");
            }
            String sql1 = "SELECT * FROM LICHSUGD";
            ResultSet rs1 = stmt1.executeQuery(sql1);
            while (rs1.next()) {
                String d = rs1.getDate("time")+" "+rs1.getTime("time");
                if(d.equals(b)) gia = rs1.getString("price");
            }
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void sqlrun(String a){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
            Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM LICHSUGD WHERE login like '"+a+"'";
            ResultSet rs = stmt.executeQuery(sql);
            data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> dr = new Vector<Object>();
                dr = new Vector<Object>();
                dr.add(rs.getDate("time")+" "+rs.getTime("time"));
                dr.add(rs.getString("bid"));
                data.add(dr);
            }
            columnNames = new Vector<String>();
            columnNames.add("Thời Gian");
            columnNames.add("ID Sách");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
    public History(String at) {
        super("Lịch Sử Giao Dịch");
        info(at);
        GUI();
    }
    public static void main(String[] args) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        int index = tab.getSelectedRow();
        TableModel model = tab.getModel();
        if(index!=-1) {
            String val = model.getValueAt(index, 0).toString();
            String val1 = model.getValueAt(index, 1).toString();
            showinfo(val1, val);
            JOptionPane.showMessageDialog(this, "Thời gian: " + val + "\nSách: " + name + "\nGiá: " + gia);
        }
    }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}