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
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメーター取得
		String cd = req.getParameter("cd");
		
		// 科目インスタンス生成
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(teacher.getSchool());
		
		// Dao
		SubjectDao suDao = new SubjectDao();
		// DBから削除
		suDao.delete(subject);
		
		// 科目情報削除完了画面へ
		url = "subject_delete_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}