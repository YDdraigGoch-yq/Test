package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

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
		String cd = req.getParameter("cd");
		
		// Dao
		SubjectDao suDao = new SubjectDao();
		// 科目取得
		Subject subject = suDao.get(cd, teacher.getSchool());
		
		// 科目存在チェック
		if (subject == null) {
			subject = new Subject();
			subject.setCd(cd);
			
			req.setAttribute("subject", subject);
			req.setAttribute("error", "科目が存在していません。");
			
			url = "subject_delete.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		try {
			// DBから削除
			boolean result = suDao.delete(subject);
			
			if (result) {
				url = "subject_delete_done.jsp";
			} else {
				req.setAttribute(
					"error",
					"科目を削除できませんでした。"
				);
				req.setAttribute("subject", subject);
				
				// 科目情報削除画面へ
				url = "subject_delete.jsp";
			}
			
		} catch (Exception e) {
			req.setAttribute(
				"error",
				"この科目は成績情報で使用されているため削除できません。"
			);
			req.setAttribute("subject", subject);
			
			// 科目情報削除画面へ
			url = "subject_delete.jsp";
			
			e.printStackTrace();
		}
		
		// 科目情報削除完了画面へ
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
