package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメータ取得
		String class_num = req.getParameter("class_num");
		// DAO
		ClassNumDao cDao = new ClassNumDao();
		// クラス番号取得
		ClassNum classNum = cDao.get(class_num, teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("classNum", classNum);
		
		// クラス番号削除画面へ
		url = "class_delete.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}