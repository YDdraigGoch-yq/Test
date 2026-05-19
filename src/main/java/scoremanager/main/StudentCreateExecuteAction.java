package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

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
		
		// パラメーター取得
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String entYearStr = req.getParameter("ent_year");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// 入学年度チェック
		int entYear = 0;
		if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0")) {
			errors.put(
				"ent_year",
				"入学年度を選択してください"
			);
			
		} else {
			// 型変換
			entYear = Integer.parseInt(entYearStr);
		}
		
		// 学生取得
		StudentDao sDao = new StudentDao();
		// 学生番号重複チェック
		if (sDao.get(no) != null) {
			errors.put(
				"no",
				"学生番号が重複しています"
			);
		}
		
		// エラーがある場合
		if (!errors.isEmpty()) {
			
			// クラス一覧取得
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());
			
			// 入学年度一覧
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			List<Integer> entYearSet = new ArrayList<>();
			
			for (int i = year - 10; i <= year; i++) {
				entYearSet.add(i);
			}
			
			// リクエストへセット
			req.setAttribute("class_num_set",classNumSet);
			req.setAttribute("ent_year_set",entYearSet);
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("errors", errors);
			
			// 学生情報登録画面へ
			url = "student_create.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// 学生インスタンス生成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(true);
		student.setSchool(teacher.getSchool());
		
		// DBに保存
		sDao.save(student);
		
		// 学生情報登録完了画面へ
		url = "student_create_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}