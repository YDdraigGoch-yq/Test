package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteDoneAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		// パラメーター取得
		String cd = req.getParameter("cd");
		
		Subject subject = new Subject();
		subject.setCd(cd);
		//　学校をセット
		subject.setSchool(teacher.getSchool());
		
		// 科目取得
		SubjectDao suDao = new SubjectDao();
		// DBから削除
		suDao.delete(subject);
		
		// 科目情報削除完了画面へ
		req.getRequestDispatcher("subject_delete_done.jsp")
			.forward(req, res);
	}
}