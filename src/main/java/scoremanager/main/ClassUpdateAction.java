package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数
		String url = "";
		//　セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得(学生番号取得)
		String no = req.getParameter("no");
		// Dao
		StudentDao sDao = new StudentDao();
		// 学生取得
		Student student = sDao.get(no);
		// クラス一覧取得
		ClassNumDao cDao = new ClassNumDao();
		List<String> classNums = cDao.filter(teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("student", student);
		req.setAttribute("classNums", classNums);
		
		// 学生クラス番号変更画面へ
		url = "class_update.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}