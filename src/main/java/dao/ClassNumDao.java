package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {
	
	public ClassNum get(String class_num, School school) throws Exception {
		// クラス番号インスタンスを初期化
		ClassNum classNum = new ClassNum();
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"select * from class_num where class_num = ? and school_cd = ?"
			);
			// バインド(bind)
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			// 実行
			ResultSet rSet = statement.executeQuery();
			
			if (rSet.next()) {
				// ResultSetが存在する場合
				// クラス番号インスタンスに検索結果をセット
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(school);
				
			} else {
				// ResultSetが存在しない場合
				// クラス番号インスタンスにnullをセット
				classNum = null;
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
		
		return classNum;
	}
	
	public List<String> filter(School school) throws Exception {
		// Listを初期化
		List<String> list = new ArrayList<>();
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"select class_num from class_num where school_cd = ? order by class_num"
			);
			// 学生コードをバインド(bind)
			statement.setString(1, school.getCd());
			// 実行
			ResultSet rSet = statement.executeQuery();
			
			// ResultSetを全件走査
			while (rSet.next()) {
				// Listにクラス番号を追加
				list.add(rSet.getString("class_num"));
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
		
		return list;
	}
	
	public boolean save(ClassNum classNum) throws Exception {
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// Insert文をセット
			statement = connection.prepareStatement(
				"insert into class_num(school_cd, class_num) values(?, ?)"
			);
			// バインド(bind)
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClass_num());
			// 実行
			count = statement.executeUpdate();
			
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
		
		return count > 0;
	}
	
	// 削除機能(delete)
	public boolean delete(ClassNum classNum) throws Exception {
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			
			// SQL文をセット
			statement = connection.prepareStatement(
				"delete from class_num where class_num = ? and school_cd = ?"
			);
			// バインド(bind)
			statement.setString(1,classNum.getClass_num());
			statement.setString(2,classNum.getSchool().getCd());
			// 実行
			count = statement.executeUpdate();
			
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
			
			// connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return count > 0;
	}
}