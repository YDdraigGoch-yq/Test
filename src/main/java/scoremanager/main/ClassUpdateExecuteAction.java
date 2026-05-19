package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassUpdateExecuteAction
	extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数
		String url = "";
		
		// パラメーター取得
		String no = req.getParameter("no");
		String newClassNum = req.getParameter("newClassNum");
		
		// Dao
		StudentDao sDao = new StudentDao();
		// 学生取得
		Student student = sDao.get(no);
		student.setClassNum(newClassNum);
		
		// DBに保存
		sDao.save(student);
		
		// リクエストへセット
		req.setAttribute("student", student);
		req.setAttribute("newClassNum", newClassNum);
		
		// 学生クラス番号変更完了画面へ
		url ="class_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}