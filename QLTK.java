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

public class QLTK extends Frame implements ActionListener, MouseListener{
		TextField txt;
		Button search,xoa,sua,exit;
		Panel pn,pn1,pn2,pn3;
		static String user = "";
		static JTable tab;
		static Vector<String> columnNames ;
		static Vector<Vector<Object>> data;
		public void GUI() {
			txt = new TextField("");

			sua = new Button("Sửa thông tin");
			xoa = new Button("Xóa thông tin");
			exit = new Button("Thoát");
			search = new Button("Tìm kiếm");

			sqlrun("");
			tab = new JTable();
			update_table();
			JScrollPane scrollPane = new JScrollPane(tab);
			tab.setFillsViewportHeight(true);

			tab.setSelectionMode(0);
			tab.addMouseListener(this);
			search.addActionListener(this);
			sua.addActionListener(this);
			xoa.addActionListener(this);
			exit.addActionListener(this);
			
			pn = new Panel(new BorderLayout());
			pn1 = new Panel(new GridLayout(1,2));
			pn2 = new Panel(new BorderLayout());
			pn3 = new Panel(new FlowLayout());
			
			pn1.add(txt);
			pn1.add(search);

			pn2.add(scrollPane,BorderLayout.CENTER);

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
		}
		public static void sqlrun(String s){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql = "SELECT * FROM ACCOUNT where login like '%"+s+"%'";
				ResultSet rs = stmt.executeQuery(sql);
				data = new Vector<Vector<Object>>();
				while (rs.next()) {
					Vector<Object> dr = new Vector<Object>();
					dr = new Vector<Object>();
					dr.add(rs.getString("login"));
					dr.add(rs.getString("password"));
					dr.add(rs.getInt("solan"));
					data.add(dr);
				}
				columnNames = new Vector<String>();
				columnNames.add("Tài khoản");
				columnNames.add("Mật khẩu");
				columnNames.add("Số lần GD");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		public void Xoatk(String a, int b){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://LAPTOP-9Q7B6LVA\\SQLEXPRESS:1433;databaseName=PROJECT";
				Connection con = DriverManager.getConnection(url,"TestLogin","minhhieu0312");
				Statement stmt = con.createStatement();
				String sql1="DELETE FROM ACCOUNT WHERE login = '"+a+"'";
				stmt.executeUpdate(sql1);
				if(b == 1){
				String sql2="DELETE FROM LICHSUGD WHERE login = '"+a+"'";
				stmt.executeUpdate(sql2);
				}
				stmt.close();
			} catch(Exception e) {System.out.println("Error "+e);}
		}
		public void mouseClicked(MouseEvent e) {
			int index = tab.getSelectedRow();
			TableModel model = tab.getModel();
			if(index != -1)
				user = model.getValueAt(index,0).toString();
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==search){
				sqlrun(txt.getText());
				update_table();
			}
			if(e.getSource()==xoa) {
				if(user=="") JOptionPane.showMessageDialog(this, "Chọn tài khoản cần xóa");
				else {
					int result = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa ?", "Xóa Thông Tin Tài Khoản",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công");
						Xoatk(user,0);
						sqlrun("");
						int result1 = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa lịch sử liên quan ?", "Xóa Lich Sử Tài Khoản",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if (result1 == JOptionPane.YES_OPTION) {
							Xoatk(user,1);
							Admin.sqlrun();
							Admin.update_table();
						}
						else  if (result1 == JOptionPane.NO_OPTION) { JOptionPane.showMessageDialog(this, "Lịch sử giao dịch được giữ lại");}
					} else if (result == JOptionPane.NO_OPTION) { user = "";}
					update_table();
				}
			}
			if(e.getSource()==sua) {
				if(user=="") JOptionPane.showMessageDialog(this, "Chọn tài khoản cần sửa");
				else new SuaTk(user);
			}
			if(e.getSource()==exit) this.dispose();
		}
		public QLTK(String at) {
			super(at);
			GUI();
		}
		public static void main(String[] args) {
			new QLTK("Quản lí tài khoản");
		}
}
