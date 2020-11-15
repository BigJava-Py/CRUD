package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.City;
import vo.Province;

public class CityDao {
	public ArrayList<Province> queryProvince() {
		ArrayList<Province> list = new ArrayList<Province>();
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/excise1?useUnicode=true&characterEncoding=utf-8", "root", "123456");
			String sql = "select * from t_province";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Province province = new Province();
				province.setProvinceCode(rs.getString("provinceCode"));
				province.setProvinceName(rs.getString("provinceName"));
				list.add(province);
			}
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public ArrayList<City> queryCity(String proviceCode) {
		ArrayList<City> list = new ArrayList<City>();

		return list;
	}

}
