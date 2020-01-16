
package org.com.property.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.com.property.PropertyTableBean;

public class PropertyDAO {

	public static void storeProperty(String title, String description, String type, InputStream imageBlob) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into property.propertydata (`title`,\n" + "`description`,\n" + "`type`,\n"
					+ "`file`)\n" + "VALUES (?,?,?,?)");
			ps.setString(1, title);
			ps.setString(2, description);
			ps.setString(3, type);
			ps.setBinaryStream(4, imageBlob);

			ps.executeUpdate();
			con.commit();

		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
	}

	public static List<PropertyTableBean> getProperties() {
		
		List<PropertyTableBean> propertyList = new ArrayList<PropertyTableBean>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from property.propertydata order by id");
			rs = ps.executeQuery();
			while (rs.next()) {
				PropertyTableBean tbl = new PropertyTableBean();
				tbl.setPropertyId(rs.getInt("id"));
				tbl.setPropertyTitle(rs.getString("title"));
				tbl.setDescription(rs.getString("description"));
				tbl.setPropertyType(rs.getString("type"));
				propertyList.add(tbl);
			}

		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}

		return propertyList;
	}

	public static byte[] getPropertyImage(String imageId) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		byte[] imageBytes = null;
		
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from property.propertydata where id=" + imageId);
			rs = ps.executeQuery();
			while (rs.next()) {
				imageBytes = rs.getBytes("file");
			}

		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}

		return imageBytes;
	}
}
