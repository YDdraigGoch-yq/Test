package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
	
	private String baseSql = "select * from student where school_cd = ?";
	
	public Student get(String no) throws Exception {
		// 学生インスタンスを初期化
		Student student = new Student();
		// DataBaseのConnectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(
				"select * from student where no = ?");
			// PreparedStatementに学生番号をバインド(bind)
			statement.setString(1, no);
			// PreparedStatementを実行
			ResultSet rSet = statement.executeQuery();
			
			// 学校Daoを初期化
			SchoolDao scDao = new SchoolDao();
			
			if (rSet.next()) {
				// ResultSetが存在する場合
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(scDao.get(rSet.getString("school_cd")));
				
			} else {
				// ResultSetが存在しない場合
				// 学生インスタンスにnullをセット
				student = null;
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
		
		return student;
	}
	
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		// Listを初期化
		List<Student> list = new ArrayList<>();
		try {
			// ResultSetを全権走査
			while (rSet.next()) {
				// 学生インスタンスを初期化
				Student student = new Student();
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				
				// Listに追加
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend
	) throws Exception {
		// Listを初期化
		List<Student> list = new ArrayList<>();
		// Connectionを確立
		Connection connection = getConnection();
		//　PreparedStatement
		PreparedStatement statement = null;
		// ResultSet
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year = ? and class_num = ?";
		// SQL文のソート
		String order = "order by no asc";
		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend = true";
		}
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(
				baseSql + condition + conditionIsAttend + order);
			// PreparedStatementに学校コードをバインド(bind)
			statement.setString(1, school.getCd());
			// PreparedStatementに入学年度をバインド(bind)
			statement.setInt(2, entYear);
			// PreparedStatementにクラス番号をバインド(bind)
			statement.setString(3, classNum);
			// PreparedStatementを実行
			rSet = statement.executeQuery();
			
			// Listへの格納処理を実行
			list = postFilter(rSet, school);
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
	
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		// Listを初期化
		List<Student> list = new ArrayList<>();
		// Connectionを確立
		Connection connection = getConnection();
		//　PreparedStatement
		PreparedStatement statement = null;
		// ResultSet
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year = ?";
		// SQL文のソート
		String order = "order by no asc";
		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend = true";
		}
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(
				baseSql + condition + conditionIsAttend + order);
			// PreparedStatementに学校コードをバインド(bind)
			statement.setString(1, school.getCd());
			// PreparedStatementに入学年度をバインド(bind)
			statement.setInt(2, entYear);
			// PreparedStatementを実行
			rSet = statement.executeQuery();
			
			// Listへの格納処理を実行
			list = postFilter(rSet, school);
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
		
		return list;
	}
	
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		// Listを初期化
		List<Student> list = new ArrayList<>();
		// Connectionを確立
		Connection connection = getConnection();
		//　PreparedStatement
		PreparedStatement statement = null;
		// ResultSet
		ResultSet rSet = null;
		// SQL文のソート
		String order = "order by no asc";
		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend = true";
		}
		
		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			// PreparedStatementに学校コードをバインド(bind)
			statement.setString(1, school.getCd());
			// PreparedStatementを実行
			rSet = statement.executeQuery();
			
			// Listへの格納処理を実行
			list = postFilter(rSet, school);
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
		
		return list;
	}
	
	public boolean save(Student student) throws Exception {
		// Connectionを確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// DataBaseから学生を取得
			Student old = get(student.getNo());
			if (old == null) {
				// 学生番号が存在しなかった場合
				// PreparedStatementにInsert文をセット
				statement = connection.prepareStatement(
					"insert into student(no, name, ent_year, class_num, is_attend, school_cd)"
					+ "values(?, ?, ?, ?, ?, ?)");
				// PreparedStatementに値をバインド(bind)
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
				
			} else {
				// 学生番号が存在した場合
				// PreparedStatementにUpdate文をセット
				statement = connection.prepareStatement(
					"update student set name = ?, ent_year = ?, class_num = ?,"
					+ "is_attend = ? where no = ?");
				// PreparedStatementに値をバインド(bind)
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				// Update操作のWhere条件(在哪里进行"Update"操作)
				statement.setString(5, student.getNo());
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