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
		// DataBaseへのConnectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(
				"select * from class_num where class_num = ? and school_cd = ?");
			// PreparedStatementに値をバインド(bind)
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			// PreparedStatementを実行
			ResultSet rSet = statement.executeQuery();
			
			// 学校Daoを初期化
			SchoolDao scDao = new SchoolDao();
			
			if (rSet.next()) {
				// ResultSetが存在する場合
				// クラス番号インスタンスに検索結果をセット
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(scDao.get(rSet.getString("school_cd")));
				
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
		// Connectionを確立
		Connection connection = getConnection();
		//　PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(
					"select class_num from class_num where school_cd = ? order by class_num");
			// PreparedStatementに学生コードをバインド(bind)
			statement.setString(1, school.getCd());
			// PreparedStatementを実行
			ResultSet rSet = statement.executeQuery();
			
			// ResultSetを全権走査
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
		// Connectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// PreparedStatementにInsert文をセット
			statement = connection.prepareStatement(
				"insert into class_num(school_cd, class_num) values(?, ?)");
			// PreparedStatementに値をバインド(bind)
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClass_num());
			
			// PreparedStatementを実行
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
		
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
			
		}  else {
			// 実行件数が０件の場合
			return false;
		}
	}
	
	public boolean save(ClassNum classNum, String newClassNum) throws Exception {
		// Connectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// DataBaseからクラス番号を取得
			ClassNum old = get(classNum.getClass_num(), classNum.getSchool());
			
			if (old == null) {
				// クラス番号が存在しなかった場合
				// PreparedStatementにInsert文をセット
				statement = connection.prepareStatement(
					"insert into class_num(school_cd, class_num) values(?, ?)");
				// PreparedStatementに値をバインド(bind)				
				statement.setString(1, classNum.getSchool().getCd());
				statement.setString(2, classNum.getClass_num());
				
			} else {
				// クラス番号が存在した場合
				// PreparedStatementにUpdate文をセット
				statement = connection.prepareStatement(
					"update class_num set school_cd = ? where class_num = ?");
				// PreparedStatementに値をバインド(bind)
				statement.setString(1, classNum.getSchool().getCd());
				statement.setString(2, classNum.getClass_num());
			}
			
			// PreparedStatementを実行
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
		
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
			
		}  else {
			// 実行件数が０件の場合
			return false;
		}
	}
}