package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateDoneAction extends Action {

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
		String name = req.getParameter("name");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// エラーメッセージ
		if (!errors.isEmpty()) {
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			
			req.setAttribute("subject", subject);
			req.setAttribute("errors", errors);
			
			req.getRequestDispatcher("subject_update.jsp")
				.forward(req, res);
			
			return;
		}
		
		// Bean
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// 科目取得
		SubjectDao suDao = new SubjectDao();
		// DBに保存
		suDao.save(subject);
			
		// 科目情報変更完了画面へ
		req.getRequestDispatcher("subject_update_done.jsp")
			.forward(req, res);
	}
}