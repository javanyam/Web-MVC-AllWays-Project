package com.jsplec.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jsplec.manager.dto.goodsDto;
import com.jsplec.manager.dto.ordersReviewDto;

public class CManagerOrdersReviewDao {
	
	DataSource dataSource;

	public CManagerOrdersReviewDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mvc");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ordersReviewDto> showOrdersReviewOnMain(){
		ArrayList<ordersReviewDto> dtos=new ArrayList<ordersReviewDto>();
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			String query = "select or_customerId, oreviewStarrating, oreviewInitdate from ordersreview where oreviewDeletedate is null ";
			String query2 = "order by oreviewInitdate desc ;";

			ps = connection.prepareStatement(query + query2);
			rs = ps.executeQuery();

			while (rs.next()) {
				String wkor_customerId = rs.getString(1);
				int wkoreviewStarrating = rs.getInt(2);
				Timestamp wkoreviewInitdate=rs.getTimestamp(3);

				ordersReviewDto dto = new ordersReviewDto(wkor_customerId, wkoreviewStarrating, wkoreviewInitdate);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	public ArrayList<ordersReviewDto> showOrdersReviewList(){
		ArrayList<ordersReviewDto> dtos=new ArrayList<ordersReviewDto>();
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			String query = "select oreviewId, or_customerId, cakeName, goodsName, oreviewStarrating, oreviewInitdate ";
			String query2 = "from cake, goods, ordersreview where or_o_cakeId=cakeId and or_o_goodsId=goodsId and oreviewDeletedate is null;";

			ps = connection.prepareStatement(query + query2);
			rs = ps.executeQuery();

			while (rs.next()) {
				int wkoreviewId=rs.getInt(1);
				String wkor_customerId = rs.getString(2);
				String wkcakeName=rs.getString(3);
				String wkgoodsName=rs.getString(4);
				int wkoreviewStarrating=rs.getInt(5);
				Timestamp wkoreviewInitdate=rs.getTimestamp(6);

				ordersReviewDto dto = new ordersReviewDto(wkoreviewId, wkor_customerId, wkcakeName, wkgoodsName, wkoreviewStarrating, wkoreviewInitdate);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}
}
