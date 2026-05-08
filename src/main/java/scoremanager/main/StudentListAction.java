package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentListAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// Session
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// Loginチェック
		if (teacher == null) {
			res.sendRedirect("Login.action");
			return;
		}
		
		// Local変数の宣言
		String entYearStr = "";
		String classNum = "";	
		String isAttendStr = "";
		
		int entYear = 0;
		boolean isAttend = false;
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();
		
		// Parameter取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");
			
		// 在学フラグが送信されていた場合
		if (isAttendStr != null) {
			// 在学フラグを立てる
			isAttend = true;
			// requestに在学フラグをセット
			req.setAttribute("f3", isAttendStr);
		}

		// Business.logic
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		
		// Listを初期化
		List<Integer> entYearSet = new ArrayList<>();
		
		// 10年前から1年後まで年をlistに追加（２０１１～２０２６）
		for (int i = year - 15; i < year + 1; i++) {
			entYearSet.add(i);
		}
		
		// DBからdataを取得
		// Login.userの学校コードをもとに、クラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
			
		} else if (entYear != 0 && classNum.equals("0")) {
			// 入学年度のみ指定
			students = sDao.filter(teacher.getSchool(), entYear, isAttend);
			
		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			// 指定なしの場合
			// 全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
			
		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
			// 全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		}
		
		// Response値をセット
		// Requestに「入学年度」と「クラス番号」をセット
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		
		// Requestに学生リストをセット
		req.setAttribute("students", students);
		// Requestにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		
		// 学生一覧画面へ
		req.getRequestDispatcher("student_list.jsp")
			.forward(req, res);
	}
}