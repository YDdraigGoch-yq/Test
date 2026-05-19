package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得(学生番号取得)
		String no = req.getParameter("no");
		
		// 学生情報取得
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(no);
		
		// 現在年取得
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();
		
		for (int i = year - 15; i <= year; i++) {
			entYearSet.add(i);
		}
		
		// クラス一覧取得
		ClassNumDao cDao = new ClassNumDao();
		List<String> classNumSet = cDao.filter(teacher.getSchool());
		
		// リクエストにセット
		req.setAttribute("student",student);
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classNumSet);
		
		// 学生情報変更画面へ
		url = "student_update.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}