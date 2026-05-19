package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	
	private String baseSql = "select * from subject where school_cd = ?";
	
	 // 単体取得
	public Subject get(String cd, School school) throws Exception {
		// 科目インスタンスを初期化
		Subject subject = new Subject();
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// SQL文をセット(準備)
			statement = connection.prepareStatement(
				"select * from subject where cd = ? and school_cd = ?"
			);
			// 科目コードと学校コードをバインド(bind)
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			// 実行
			ResultSet rSet = statement.executeQuery();
			
			if (rSet.next()) {
				// ResultSetが存在する場合
				// 科目Instanceに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				
			} else {
				// ResultSetが存在しない場合
				// 科目Instanceにnullをセット
				subject = null;
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
		
		return subject;
	}
	
	// 科目一覧取得
	public List<Subject> filter(School school) throws Exception {
		// Listを初期化
		List<Subject> list = new ArrayList<>();
		// Connection確立
		Connection connection = getConnection();
		//　PreparedStatement
		PreparedStatement statement = null;
		// ResultSet
		ResultSet rSet = null;
		// SQL文のソート
		String order = " order by cd asc";	
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				baseSql + order
			);
			// 学校コードをバインド(bind)
			statement.setString(1, school.getCd());
			// 実行
			rSet = statement.executeQuery();
			
			// ResultSet全件走査
			while (rSet.next()) {
				
				// 科目生成
				Subject subject = new Subject();
				
				// 検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				
				// Listに追加
				list.add(subject);
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
	
	// 保存機能（save）
	public boolean save(Subject subject) throws Exception {
		// Connectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// DataBaseから科目を取得
			Subject old = get(subject.getCd(), subject.getSchool());
			
			if (old == null) {
				// 科目コードが存在しなかった場合
				// Insert文をセット
				statement = connection.prepareStatement(
					"insert into subject(cd, name, school_cd) values(?, ?, ?)"
				);
				// 値をバインド(bind)
				statement.setString(1, subject.getCd());
				statement.setString(2, subject.getName());
				statement.setString(3, subject.getSchool().getCd());
			
			} else {
				// 科目コードが存在した場合
				// Update文をセット
				statement = connection.prepareStatement(
					"update subject set name = ? where cd = ? and school_cd = ?"
				);
				// 科目名をバインド(bind)
				statement.setString(1, subject.getName());
				// Update操作のWhere条件(在哪里进行"Update"操作)
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getSchool().getCd());
			}
			
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
	
	// 削除機能(delete)
	public boolean delete(Subject subject) throws Exception {
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"delete from subject where cd = ? and school_cd = ?"
			);
			// 値をバインド(bind)
			statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getSchool().getCd());
			
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