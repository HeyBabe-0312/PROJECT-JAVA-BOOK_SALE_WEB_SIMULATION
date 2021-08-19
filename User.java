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

public class User extends Frame implements ActionListener,ItemListener,MouseListener{
		static String user = "";
		static int check_sale;
		String v = "";
		Label lb,lb1;
		TextField txt;
		Button mk,sale,dxuat,history,search,exit;
		Panel pn,pn1,pn2,pn3,pn4,pn5,pn6,pn7;
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
			lb1 = new Label("Tài khoản: "+user);
			         
			txt = new TextField();

			mk = new Button("Đổi mật khẩu");
			sale = new Button("Giảm Giá");
			dxuat = new Button("Đăng Xuất");
			history = new Button("Lịch Sử");
			search = new Button("Tìm Kiếm");
			exit = new Button("Thoát");

			sqlrun("","");
			tab = new JTable(data,columnNames);
			update_table();
			JScrollPane scrollPane = new JScrollPane(tab);
			tab.setFillsViewportHeight(true);

			tab.setSelectionMode(0);
			mk.addActionListener(this);
			tab.addMouseListener(this);
			dxuat.addActionListener(this);
			history.addActionListener(this);
			sale.addActionListener(this);
			search.addActionListener(this);
			exit.addActionListener(this);
			tl.addItemListener(this);
			
			pn = new Panel(new BorderLayout());
			pn1 = new Panel(new GridLayout(1,2));
			pn2 = new Panel(new BorderLayout());
			pn3 = new Panel(new FlowLayout());
			pn4 = new Panel(new FlowLayout());
			pn5 = new Panel(new GridLayout(1,2));
			pn6 = new Panel(new GridLayout(1,3));
			pn7 = new Panel(new GridLayout(1,2));
			
			pn1.add(pn6);
			pn1.add(pn7);
			pn2.add(scrollPane,BorderLayout.CENTER);
			pn3.add(lb1);
			pn3.add(history);
			pn3.add(mk);
			pn4.add(dxuat);
			pn4.add(exit);
			pn5.add(pn3);
			pn5.add(pn4);
			pn6.add(sale);
			pn6.add(lb);
			pn6.add(tl);
			pn7.add(txt);
			pn7.add(search);
			pn.add(pn1,BorderLayout.NORTH);
			pn.add(pn2,BorderLayout.CENTER);
			pn.add(pn5,BorderLayout.SOUTH);
			add(pn);
			setSize(800,600);
			show();
		}
		public void info(String tk){
			user = tk;
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
		public void mouseClicked(MouseEvent e) {
			int index = tab.getSelectedRow();
			TableModel model = tab.getModel();
			if(index != -1) {
				String val = model.getValueAt(index, 0).toString();
				Mua_sach.setCheck_sale(val);
				new Mua_sach(val, user);
			}
		}
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

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
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==sale) new choose(1);
			if(e.getSource()==search) {
				sqlrun(txt.getText(),v);
				update_table();
			}
			if(e.getSource()==history){
				new History(user);
			}
			if(e.getSource()==dxuat){
				new APP("Hệ Thống Bán Sách Trực Tuyến");
				this.dispose();
			}
			if(e.getSource()==mk) new Change_pass(user);
			if(e.getSource()==exit) System.exit(0);
		}
		public User(String at) {
			super("Hệ Thống Bán Sách Trực Tuyến");
			info(at);
			GUI();
		}
		public static void main(String[] args) {
			new User("...");
		}
}