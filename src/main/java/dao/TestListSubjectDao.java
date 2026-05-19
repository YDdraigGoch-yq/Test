package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql =
			"select "
			+ "s.ent_year, "
			+ "s.class_num, "
			+ "s.no as student_no, "
			+ "s.name as student_name, "
			+ "t.no as test_no, "
			+ "t.point "
			+ "from student s "
			+ "left join test t "
			+ "on s.no = t.student_no "
			+ "and s.school_cd = t.school_cd "
			+ "and t.subject_cd = ? "
			+ "where s.ent_year = ? "
			+ "and s.class_num = ? "
			+ "and s.school_cd = ? "
			+ "and s.is_attend = true "
			+ "order by s.no asc, t.no asc";

	/**
	 * ResultSet を一覧表示用リストに詰め替える
	 */
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		
		List<TestListSubject> list = new ArrayList<>();
		Map<String, TestListSubject> map = new LinkedHashMap<>();
		
		while (rSet.next()) {
			
			String studentNo = rSet.getString("student_no");
			TestListSubject testListSubject = map.get(studentNo);
			
			// まだその学生が map にない場合は新規作成
			if (testListSubject == null) {
				testListSubject = new TestListSubject();
				testListSubject.setEntYear(rSet.getInt("ent_year"));
				testListSubject.setClassNum(rSet.getString("class_num"));
				testListSubject.setStudentNo(studentNo);
				testListSubject.setStudentName(rSet.getString("student_name"));
				
				map.put(studentNo, testListSubject);
			}
			
			// 得点データがある場合のみセット
			int testNo = rSet.getInt("test_no");
			if (!rSet.wasNull()) {
				testListSubject.putPoint(testNo, rSet.getInt("point"));
			}
		}
		
		list.addAll(map.values());
		return list;
	}

	/**
	 * 入学年度、クラス、科目、学校を指定して科目別成績一覧を取得する
	 */
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		
		List<TestListSubject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		
		try {
			statement = connection.prepareStatement(baseSql);
			statement.setString(1, subject.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, school.getCd());
			
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