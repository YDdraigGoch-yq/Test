package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql =
			"select "
			+ "s.ent_year, "
			+ "s.class_num, "
			+ "s.no as student_no, "
			+ "s.name as student_name, "
			+ "sub.cd as subject_cd, "
			+ "sub.name as subject_name, "
			+ "? as test_no, "
			+ "t.point "
			+ "from student s "
			+ "inner join subject sub "
			+ "on sub.school_cd = s.school_cd "
			+ "and sub.cd = ? "
			+ "left join test t "
			+ "on s.no = t.student_no "
			+ "and s.school_cd = t.school_cd "
			+ "and t.subject_cd = sub.cd "
			+ "and t.no = ? ";

	/**
	 * 成績を1件取得する
	 */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		Test test = null;
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement(
					"select "
					+ "s.ent_year, "
					+ "s.class_num, "
					+ "s.no as student_no, "
					+ "s.name as student_name, "
					+ "sub.cd as subject_cd, "
					+ "sub.name as subject_name, "
					+ "t.no as test_no, "
					+ "t.point "
					+ "from test t "
					+ "inner join student s "
					+ "on t.student_no = s.no "
					+ "and t.school_cd = s.school_cd "
					+ "inner join subject sub "
					+ "on t.subject_cd = sub.cd "
					+ "and t.school_cd = sub.school_cd "
					+ "where t.student_no = ? "
					+ "and t.subject_cd = ? "
					+ "and t.school_cd = ? "
					+ "and t.no = ?");

			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			rSet = statement.executeQuery();

			List<Test> list = postFilter(rSet, school);

			if (list.size() > 0) {
				test = list.get(0);
			}

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

		return test;
	}

	/**
	 * ResultSetの内容をTestのリストに詰め替える
	 */
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

		List<Test> list = new ArrayList<>();

		try {
			while (rSet.next()) {

				Test test = new Test();

				Student student = new Student();
				student.setNo(rSet.getString("student_no"));
				student.setName(rSet.getString("student_name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(true);
				student.setSchool(school);

				Subject subject = new Subject();
				subject.setCd(rSet.getString("subject_cd"));
				subject.setName(rSet.getString("subject_name"));
				subject.setSchool(school);

				test.setStudent(student);
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(rSet.getInt("test_no"));

				int point = rSet.getInt("point");

				// TEST未登録の場合、left joinによりpointがnullになる
				// Test.pointはint型なので、画面側で空欄判定できるように-1を入れる
				if (rSet.wasNull()) {
					point = -1;
				}

				test.setPoint(point);

				list.add(test);
			}

		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 入学年度、クラス、科目、回数、学校を指定して成績登録対象の一覧を取得する
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		String condition =
				"where s.ent_year = ? "
				+ "and s.class_num = ? "
				+ "and s.school_cd = ? "
				+ "and s.is_attend = true ";

		String order = "order by s.no asc";

		try {
			statement = connection.prepareStatement(baseSql + condition + order);

			statement.setInt(1, num);
			statement.setString(2, subject.getCd());
			statement.setInt(3, num);
			statement.setInt(4, entYear);
			statement.setString(5, classNum);
			statement.setString(6, school.getCd());

			rSet = statement.executeQuery();

			list = postFilter(rSet, school);

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

	/**
	 * 成績リストを保存する
	 */
	public boolean save(List<Test> list) throws Exception {

		Connection connection = getConnection();
		boolean result = true;

		try {
			connection.setAutoCommit(false);

			for (Test test : list) {
				if (!save(test, connection)) {
					result = false;
					break;
				}
			}

			if (result) {
				connection.commit();
			} else {
				connection.rollback();
			}

		} catch (Exception e) {
			connection.rollback();
			throw e;

		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return result;
	}

	/**
	 * 成績を1件保存する
	 * 既存データがある場合はUPDATE、ない場合はINSERT
	 */
	public boolean save(Test test, Connection connection) throws Exception {

		PreparedStatement statement = null;
		ResultSet rSet = null;
		int count = 0;

		try {
			statement = connection.prepareStatement(
					"select count(*) as count "
					+ "from test "
					+ "where student_no = ? "
					+ "and subject_cd = ? "
					+ "and school_cd = ? "
					+ "and no = ?");

			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd());
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());

			rSet = statement.executeQuery();

			if (rSet.next()) {
				count = rSet.getInt("count");
			}

			rSet.close();
			statement.close();

			if (count == 0) {
				statement = connection.prepareStatement(
						"insert into test("
						+ "student_no, subject_cd, school_cd, no, point, class_num"
						+ ") values(?, ?, ?, ?, ?, ?)");

				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());

			} else {
				statement = connection.prepareStatement(
						"update test "
						+ "set point = ?, class_num = ? "
						+ "where student_no = ? "
						+ "and subject_cd = ? "
						+ "and school_cd = ? "
						+ "and no = ?");

				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getClassNum());
				statement.setString(3, test.getStudent().getNo());
				statement.setString(4, test.getSubject().getCd());
				statement.setString(5, test.getSchool().getCd());
				statement.setInt(6, test.getNo());
			}

			count = statement.executeUpdate();

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
		}

		return count > 0;
	}
}