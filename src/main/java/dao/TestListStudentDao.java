package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	private String baseSql =
			"select "
			+ "sub.name as subject_name, "
			+ "sub.cd as subject_cd, "
			+ "t.no as test_no, "
			+ "t.point "
			+ "from test t "
			+ "inner join subject sub "
			+ "on t.subject_cd = sub.cd "
			+ "and t.school_cd = sub.school_cd "
			+ "where t.student_no = ? "
			+ "and t.school_cd = ? "
			+ "order by sub.cd asc, t.no asc";
	
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		
		List<TestListStudent> list = new ArrayList<>();
		
		while (rSet.next()) {
			TestListStudent testListStudent = new TestListStudent();
			
			testListStudent.setSubjectName(rSet.getString("subject_name"));
			testListStudent.setSubjectCd(rSet.getString("subject_cd"));
			testListStudent.setNum(rSet.getInt("test_no"));
			testListStudent.setPoint(rSet.getInt("point"));
			
			list.add(testListStudent);
		}
		
		return list;
	}

	/**
	 * 学生を指定して学生別成績一覧を取得する
	 */
	public List<TestListStudent> filter(Student student) throws Exception {
		
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		
		try {
			statement = connection.prepareStatement(baseSql);
			statement.setString(1, student.getNo());
			statement.setString(2, student.getSchool().getCd());
			
			rSet = statement.executeQuery();
			
			list = postFilter(rSet);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			if (rSet != null) {
				try {
					rSet.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
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
}