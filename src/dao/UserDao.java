package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tools.DatabaseConnection;
import vo.Page;
import vo.User;

public class UserDao {
	public static Connection getConn(){//得到连接
		
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/excise1?useUnicode=true&characterEncoding=utf-8","root","123456");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public User get(String userName) throws Exception {
		// TODO Auto-generated method stub
		
		//JdbcUtil jdbc=new JdbcUtil();
		Connection con=getConn();
		String sql2="select * from t_user";
		
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		
		ResultSet rs=ps2.executeQuery();
		while(rs.next()) {
			String name=rs.getString("userName");
			
			if(name.equals(userName)) {
				String word=rs.getString("password");
				String chrName=rs.getString("chrName");
				User user1=new User(name,chrName,word);
				return user1;
			}
		}
		ps2.close();
		rs.close();
		con.close();
		return null;
	}
	
	public boolean insert(User user) {
		boolean success = false;

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();

		try {

			
			String sql = "insert into t_user(userName,password,chrName,mail,provinceCode,cityCode)";
			sql += " values(?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, user.getUserName());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getChrName());
			pst.setString(4, user.getMail());
			pst.setString(5, user.getProvinceCode());
			pst.setString(6, user.getCityCode());
			
			int i = pst.executeUpdate();
			
			if (i > 0) {
				success = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			dbc.close();
		}

		return success;

	}

	
	public boolean update(User user) {
		boolean success = false;

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {

			
			String sql = "update t_user set password=?,chrName = ?,mail=?,provinceCode=?,cityCode = ?";
			sql += " where userName =? ";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, user.getPassword());
			pst.setString(2, user.getChrName());
			pst.setString(3, user.getMail());
			pst.setString(4, user.getProvinceCode());
			pst.setString(5, user.getCityCode());
			pst.setString(6, user.getUserName());
			
			int i = pst.executeUpdate();
			
			if (i > 0) {
				success = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			dbc.close();
		}

		return success;

	}
	
	public User getByField(String field, String param) {
		User user = null;

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();

		try {
			
			String sql = "select * from t_user where " + field + "=?";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, param);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				user = new User(rs.getString("userName"),
						rs.getString("password"), rs.getString("chrName"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			dbc.close();
		}

		return user;
	}
	
	public ArrayList<User> query(User user, Page page) {
		ArrayList<User> list = new ArrayList<User>(); 
		StringBuffer condition = new StringBuffer();
		if (user.getUserName() != null && !"".equals(user.getUserName())) { 
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) { //
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getMail() != null && !"".equals(user.getMail())) { //
			condition.append(" and mail like '%").append(user.getMail())
					.append("%'");
		}
		if (user.getProvinceName() != null
				&& !"".equals(user.getProvinceName())) { //
			condition.append(" and provinceName like '%")
					.append(user.getProvinceName()).append("%'");
		}
		if (user.getCityName() != null && !"".equals(user.getCityName())) { //
			condition.append(" and cityName like '%")
					.append(user.getCityName()).append("%'");
		}

		int begin = page.getPageSize() * (page.getPageNumber() - 1);
		String sql = "select userName,password,chrName,mail,A.provinceCode provinceCode,";
		sql = sql
				+ " B.provinceName provinceName,A.cityCode cityCode,C.cityName cityName ";
		sql = sql + " from t_user A left join t_province B ";
		sql = sql
				+ " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
		sql = sql + " where  1=1 ";
		sql = sql + condition + " order by " + page.getSort() + " "
				+ page.getSortOrder() + " limit " + begin + ","
				+ page.getPageSize();

		System.out.println(sql);
		
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				User userResult = new User();
				userResult.setUserName(rs.getString("userName"));
				userResult.setPassword(rs.getString("password"));
				userResult.setChrName(rs.getString("chrName"));
				userResult.setMail(rs.getString("mail"));
				userResult.setProvinceCode(rs.getString("provinceCode"));
				userResult.setProvinceName(rs.getString("provinceName"));
				userResult.setCityCode(rs.getString("cityCode"));
				userResult.setCityName(rs.getString("cityName"));
				list.add(userResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}

		return list;
	}
	
	public int count(User user, Page page) {
		int total = 0;

		StringBuffer condition = new StringBuffer();
		if (user.getUserName() != null && !"".equals(user.getUserName())) { //
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) { //
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getMail() != null && !"".equals(user.getMail())) { //
			condition.append(" and mail like '%").append(user.getMail())
					.append("%'");
		}
		if (user.getProvinceName() != null
				&& !"".equals(user.getProvinceName())) { //
			condition.append(" and provinceName like '%")
					.append(user.getProvinceName()).append("%'");
		}
		if (user.getCityName() != null && !"".equals(user.getCityName())) { //
			condition.append(" and cityName like '%")
					.append(user.getCityName()).append("%'");
		}

		String sql = "select count(*) from t_user A left join t_province B";
		sql = sql
				+ " on A.provinceCode = B.provinceCode left join t_city C on A.cityCode = C.cityCode ";
		sql = sql + " where  1=1 ";
		sql = sql + condition;
		System.out.println(sql);
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//
			dbc.close();
		}

		return total;
	}
	
	public boolean delete(String ids) {
		boolean success = false;
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();

		String[] array = ids.split(",");
		try {

			// 3
			String sql = "delete from t_user where userName in(";
			StringBuffer sb = new StringBuffer("?");
			for (int i = 0; i < array.length - 1; i++) {
				sb.append(",?");
			}
			sql = sql + sb.toString() + ")";
			System.out.println(sql);
			PreparedStatement pst = con.prepareStatement(sql);
			for (int i = 0; i < array.length; i++) {
				pst.setString(i + 1, array[i]);
			}

			// 4
			int i = pst.executeUpdate();
			// 5
			if (i > 0) {
				success = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 6
			dbc.close();
		}

		return success;

	}
}
