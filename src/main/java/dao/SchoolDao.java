package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao {
	
	public School get(String cd) throws Exception {
		// 学校インスタンスを初期化
		School school = new School();
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;

		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"select * from school where cd = ?"
			);
			// 学校コードをバインド(bind)
			statement.setString(1, cd);
			// 実行
			ResultSet rSet = statement.executeQuery();
			
			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに学校コードと学校名をセット
				school.setCd(rSet.getString("cd"));
				school.setName(rSet.getString("name"));
			} else {
				// 存在しない場合
				// 学校インスタンスにnullをセット
				school = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// Connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return school;
	}
}
