package com.internousdev.magenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.magenda.dto.PurchaseHistoryInfoDTO;
import com.internousdev.magenda.util.DBConnector;

public class PurchaseHistoryInfoDAO {

	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryList(String loginId)throws SQLException{
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList = new ArrayList<PurchaseHistoryInfoDTO>();
		String sql = "select"
				+ " phi.id as id,"
				+ " phi.user_id as user_id,"
				+ " phi.product_count as product_count,"
				+ " pi.product_id as product_id,"
				+ " pi.product_name as product_name,"
				+ " pi.product_name_kana as product_name_kana,"
				+ " pi.product_description as product_description,"
				+ " pi.category_id as category_id,"
				+ " pi.price,"
				+ " pi.image_file_path as image_file_path,"
				+ " pi.image_file_name as image_file_name,"
				+ " pi.release_company,"
				+ " pi.release_date,"
				+ " phi.price as subtotal,"
				+ " phi.regist_date as regist_date,"
				+ " phi.update_date as update_date,"
				+ " di.family_name as family_name,"
				+ " di.first_name as first_name,"
				+ " di.family_name_kana as family_name_kana,"
				+ " di.first_name_kana as first_name_kana,"
				+ " di.email as email,"
				+ " di.tel_number as tel_number,"
				+ " di.user_address as user_address"
				+ " FROM purchase_history_info as phi"
				+ " LEFT JOIN product_info as pi"
				+ " ON phi.product_id = pi.product_id"
				+ " LEFT JOIN destination_info as di"
				+ " ON phi.destination_id = di.id"
				+ " WHERE phi.user_id = ?"
				+ " ORDER BY regist_date DESC";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();
				purchaseHistoryInfoDTO.setId(rs.getInt("id"));
				purchaseHistoryInfoDTO.setLoginId(rs.getString("user_id"));
				purchaseHistoryInfoDTO.setProductCount(rs.getInt("product_count"));
				purchaseHistoryInfoDTO.setProductId(rs.getInt("product_id"));
				purchaseHistoryInfoDTO.setProductName(rs.getString("product_name"));
				purchaseHistoryInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				purchaseHistoryInfoDTO.setProductDescription(rs.getString("product_description"));
				purchaseHistoryInfoDTO.setCategoryId(rs.getInt("category_id"));
				purchaseHistoryInfoDTO.setPrice(rs.getInt("price"));
				purchaseHistoryInfoDTO.setProductImageFilePath(rs.getString("image_file_path"));
				purchaseHistoryInfoDTO.setProductImageFileName(rs.getString("image_file_name"));
				purchaseHistoryInfoDTO.setProductReleaseCompany(rs.getString("release_company"));
				purchaseHistoryInfoDTO.setProductReleaseDate(rs.getDate("release_date"));
				purchaseHistoryInfoDTO.setSubtotal(rs.getInt("subtotal"));
				purchaseHistoryInfoDTO.setFamilyName(rs.getString("family_name"));
				purchaseHistoryInfoDTO.setFirstName(rs.getString("first_name"));
				purchaseHistoryInfoDTO.setFamilyNameKana(rs.getString("family_name_kana"));
				purchaseHistoryInfoDTO.setFirstNameKana(rs.getString("first_name_kana"));
				purchaseHistoryInfoDTO.setEmail(rs.getString("email"));
				purchaseHistoryInfoDTO.setTelNumber(rs.getString("tel_number"));
				purchaseHistoryInfoDTO.setUserAddress(rs.getString("user_address"));
				purchaseHistoryInfoDTOList.add(purchaseHistoryInfoDTO);

			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			con.close();
		}

		return purchaseHistoryInfoDTOList;
	}

	public int regist(String loginId, int productId, int productCount, int destinationId, int price) throws SQLException{
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "insert into purchase_history_info(user_id, product_id, product_count, destination_id, price, regist_date, update_date) value (?, ?, ?, ?, ?, now(), now())";
		int count = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			ps.setInt(4, destinationId);
			ps.setInt(5, price);
			count = ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			con.close();
		}
		return count;
	}

	public int deleteAll(String loginId)throws SQLException{
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "delete from purchase_history_info where user_id=?";
		int count = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginId);
			count = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			con.close();
		}
		return count;
	}

}
