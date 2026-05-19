package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメーター取得
		String cd = req.getParameter("cd");
		
		// 科目取得
		SubjectDao suDao = new SubjectDao();
		
		// Bean
		Subject subject = suDao.get(cd, teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("subject", subject);
		
		// 科目情報削除画面へ
		url = "subject_delete.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res); 
	}
}