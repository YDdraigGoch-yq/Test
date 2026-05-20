package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		String studentNo = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		if (teacher == null) {
			res.sendRedirect("../login.jsp");
			
			return;
		}
		
		// Dao
		ClassNumDao cDao = new ClassNumDao();
		SubjectDao suDao = new SubjectDao();
		StudentDao sDao = new StudentDao();
		TestListStudentDao tDao = new TestListStudentDao();
		
		// 現在年取得
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = cDao.filter(teacher.getSchool());
		List<Subject> subjectSet = suDao.filter(teacher.getSchool());
		
		// パラメーター取得
		studentNo = req.getParameter("f4");
		
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		
		if (studentNo == null || studentNo.trim().equals("")) {
			req.setAttribute("student_error", "このフィールドを入力してください。");
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumSet);
			req.setAttribute("subject_set", subjectSet);
			
			// 成績管理画面へ
			req.getRequestDispatcher("test_list.jsp")
				.forward(req, res);
			
			return;
		}
		
		//　
		Student student = sDao.get(studentNo);
		List<TestListStudent> scores = new ArrayList<>();
		
		if (student != null) {
			scores = tDao.filter(student);
		}
		
		// リクエストへセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);
		req.setAttribute("f4", studentNo);
		req.setAttribute("student", student);
		req.setAttribute("scores", scores);
		
		if (student == null || scores.size() == 0) {
			req.setAttribute(
				"message",
				"成績情報が存在しませんでした");
		}
		
		// 学生別成績一覧画面へ
		url = "test_list_student.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}