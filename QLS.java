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

public class QLS extends Frame implements ActionListener, MouseListener{
		TextField txt;
		Button search,exit,add,sua,xoa;
		Panel pn,pn1,pn2,pn3;
		static int check_sale;
		static String val = "";
		static JTable tab;
		static Vector<String> columnNames ;
		static Vector<Vector<Object>> data ;
		public void GUI() {
			txt = new TextField();
			search = new Button("Tìm kiếm");
			exit = new Button("Thoát");
			add = new Button("Thêm TT");
			sua = new Button("Sửa TT");
			xoa = new Button("Xóa TT");

			sqlrun("");
			tab = new JTable();
			update_table();
			JScrollPane scrollPane = new JScrollPane(tab);
			tab.setFillsViewportHeight(true);

			tab.setSelectionMode(0);
			tab.addMouseListener(this);
			search.addActionListener(this);
			exit.addActionListener(this);
			add.addActionListener(this);
			sua.addActionListener(this);
			xoa.addActionListener(this);
			
			pn = new Panel(new BorderLayout());
			pn1 = new Panel(new GridLayout(1,2));
			pn2 = new Panel(new BorderLayout());
			pn3 = new Panel(new FlowLayout());
			
			pn1.add(txt);
			pn1.add(search);
			pn2.add(scrollPane,BorderLayout.CENTER);
			pn3.add(add);
			pn3.add(sua);
			pn3.add(xoa);
			pn3.add(exit);
			pn.add(pn1,BorderLayout.NORTH);
			pn.add(pn2,BorderLayout.CENTER);
			pn.add(pn3,BorderLayout.SOUTH);
			add(pn);
			setSize(600,400);
			show();
		}
		public static void update_table(){
			tab.setModel(new DefaultTableModel(data, columnNames));
			tab.getColumnModel().getColumn(0).setPreferredWidth(5);
			tab.getColumnModel().getColumn(1).setPreferredWidth(130);
			tab.getColumnModel().getColumn(2).setPreferredWidth(50);
			tab.getColumnModel().getColumn(3).setPreferredWidth(40);
			tab.getColumnModel().getColumn(4).setPreferredWidth(40);
			tab.getColumnModel().getColumn(5).setPreferredWidth(35);
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
		public static void sqlrun(String s){
			int c;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM BOOK where title like '%"+s+"%' or type like '%"+s+"%' or author like '%"+s+"%' or bid like '%"+s+"%'";
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
					dr.add(rs.getInt("sl"));
					data.add(dr);
				}
				columnNames = new Vector<String>();
				columnNames.add("ID");
				columnNames.add("Tên");
				columnNames.add("Tác Giả");
				columnNames.add("Thể Loại");
				columnNames.add("Giá");
				columnNames.add("Số Lượng");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		public void Xoasach(String a){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql1="DELETE FROM BOOK WHERE bid = '"+a+"'";
				stmt.executeUpdate(sql1);
				String sql2="DELETE FROM MUASACH WHERE bid = '"+a+"'";
				stmt.executeUpdate(sql2);
				String sql3="DELETE FROM LICHSUGD WHERE bid = '"+a+"'";
				stmt.executeUpdate(sql3);
				stmt.close();
			} catch(Exception e) {System.out.println("Error "+e);};
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			int index = tab.getSelectedRow();
			TableModel model = tab.getModel();
			if(index != -1) val = model.getValueAt(index,0).toString();
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==add){
				new TTSach("Thêm Sách");
				this.dispose();
			}
			if(e.getSource()==sua){
				if(val=="") JOptionPane.showMessageDialog(this, "Chọn sách cần sửa");
				else new SuaSach(val);
			}
			if(e.getSource()==xoa){
				if(val=="") JOptionPane.showMessageDialog(this, "Chọn sách cần xóa");
				else {
					int result = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa ?\nDữ liệu mua bán liên quan sẽ bị xóa ?", "Xóa Thông Tin Sách",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(this, "Xóa thành công");
						Xoasach(val);
						sqlrun("");
						Admin.sqlrun();
						Admin.update_table();
					} else if (result == JOptionPane.NO_OPTION) { val = "";}
					update_table();
				}
			}
			if(e.getSource()==search) {
				sqlrun(txt.getText());
				update_table();
			}
			if(e.getSource()==exit) this.dispose();
		}
		public QLS(String at) {
			super(at);
			GUI();
		}
		public static void main(String[] args) {
			new QLS("Quản lí sách");
		}
}
