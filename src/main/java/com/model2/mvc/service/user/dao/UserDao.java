package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.mbeans.DataSourceUserDatabaseMBean;

import java.util.List;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;


public class UserDao {
	
	//Field
	
	
	//Constructor
	public UserDao() {
		Debug.setDaoName("UserDao");
	}
	
	
	//Method
	// 회원가입
	public void insertUser(User user) throws Exception {
		
		Debug.startDaoMethod("insertUser", "user");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO USERS "
					+"VALUES (?, ?, ?, 'user', ?, ?, ?, ?,SYSDATE)";
		
		Debug.printSQL(sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, user.getUserId());
		pStmt.setString(2, user.getUserName());
		pStmt.setString(3, user.getPassword());
		pStmt.setString(4, user.getSsn());
		pStmt.setString(5, user.getPhone());
		pStmt.setString(6, user.getAddr());
		pStmt.setString(7, user.getEmail());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		Debug.endDaoMethod();
	}

	
	// 회원정보조회
	public User findUser(String userId) throws Exception {
		
		Debug.startDaoMethod("findUser", "userId");
		
		Connection con = DBUtil.getConnection();
			
		String sql = "SELECT user_id , "
							+"user_name , "
							+"password , "
							+"role, "
							+"cell_phone, "
							+"addr, "
							+"email, "
							+"reg_date "
					+ "FROM users "
					+ "WHERE user_id = ?";
		
		Debug.printSQL(sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, userId);

		ResultSet rs = pStmt.executeQuery();

		User user = null;

		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));
		}
		
		Debug.printDataInDao("user", user);
		
		rs.close();
		pStmt.close();
		con.close();
		
		Debug.endDaoMethod();
		
		return user;
	}

	
	// 회원전체 리스트 조회
	public Map<String , Object> getUserList(Search search) 
			throws Exception {
		
		Debug.startDaoMethod("getUserList", "search");
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "SELECT user_id, user_name ,email FROM users ";
		
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE user_id = '" + search.getSearchKeyword()+"'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE user_name ='" + search.getSearchKeyword()+"'";
			}
		}
		sql += " ORDER BY user_id";
		
		System.out.println("\t\tUserDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("\t\tUserDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		Debug.printDataInDao("search", search);

		List<User> list = new ArrayList<User>();
		
		while(rs.next()){
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setEmail(rs.getString("email"));
			list.add(user);
		}
		
		Debug.printDataInDao("totalCount", totalCount);
		Debug.printDataInDao("lit", list);
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();
		
		Debug.endDaoMethod();

		return map;
	}

	public void updateUser(User user) throws Exception {
		
		Debug.startDaoMethod("updateUser", "user");

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE users "+
					 "SET user_name = ?, cell_phone = ? , addr = ? , email = ? "+ 
					 "WHERE user_id = ?";
		
		Debug.printSQL(sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, user.getUserName());
		pStmt.setString(2, user.getPhone());
		pStmt.setString(3, user.getAddr());
		pStmt.setString(4, user.getEmail());
		pStmt.setString(5, user.getUserId());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		Debug.endDaoMethod();
		
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		Debug.startDaoMethod("getTotalCount", "sql");
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Debug.printSQL(sql);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		Debug.endDaoMethod();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	// sql문을 넣어서 리스트를 줄때 해당 page에 필요한 내용을 줄 수 있는 sql문을 만들어줌
	private String makeCurrentPageSql(String sql , Search search){
		
		Debug.startDaoMethod("makeCurrentPageSql", "sql, search");
		Debug.printDataInDao("sql", sql);
		Debug.printDataInDao("search", search);
		
		sql = 	"SELECT * "+ 
				"FROM (	SELECT inner_table.* , ROWNUM AS row_seq " +
						"FROM (	"+sql+" ) inner_table "+
						"WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("\t\tUserDAO :: make SQL :: "+ sql);	
		
		Debug.endDaoMethod();
		
		return sql;
	}
}