package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.*;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメーター取得
		String cd = req.getParameter("cd");
		
		// Dao
		SubjectDao suDao = new SubjectDao();
		
		// 科目取得
		Subject subject = suDao.get(cd, teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("subject", subject);
		
		// エラーを初期値
		req.setAttribute(
			"errors",
			new java.util.HashMap<String, String>()
		);
		
		// 科目情報変更画面へ
		url = "subject_update.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}