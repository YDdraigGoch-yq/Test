package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		int entYear = 0;
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
		TestListSubjectDao tDao = new TestListSubjectDao();
		
		// 現在年取得
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = cDao.filter(teacher.getSchool());
		List<Subject> subjectSet = suDao.filter(teacher.getSchool());
		
		// パラメーター取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		
		// 検索条件不足
		if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
				|| classNum == null || classNum.equals("") || classNum.equals("0")
				|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")) {
			req.setAttribute("subject_error", "入学年度とクラスと科目を選択してください");
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumSet);
			req.setAttribute("subject_set", subjectSet);
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			
			// 成績管理画面へ
			url = "test_list.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		entYear = Integer.parseInt(entYearStr);
		
		Subject subject = suDao.get(subjectCd, teacher.getSchool());
		List<TestListSubject> students = tDao.filter(entYear, classNum, subject, teacher.getSchool());
		
		// 可変列用に回数一覧を作成
		Set<Integer> numSet = new TreeSet<>();
		for (TestListSubject student : students) {
			numSet.addAll(student.getPoints().keySet());
		}
		
		// リクエストへセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("subject", subject);
		req.setAttribute("students", students);
		req.setAttribute("num_set", numSet);
		
		if (students.size() == 0) {
			req.setAttribute("message", "学生情報が存在しませんでした");
		}
		
		// 科目別成績一覧画面へ
		url = "test_list_subject.jsp";
		req.getRequestDispatcher(url)
		.forward(req, res);
	}
}