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

public class SubjectUpdateExecuteAction extends Action {

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
		String name = req.getParameter("name");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// エラーがある場合
		if (!errors.isEmpty()) {
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			
			req.setAttribute("subject", subject);
			req.setAttribute("errors", errors);
			
			
			// 科目情報変更画面へ
			url = "subject_update.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// 科目インスタンス生成
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// Dao
		SubjectDao suDao = new SubjectDao();
		// DBに保存
		suDao.save(subject);
			
		// 科目情報変更完了画面へ
		url = "subject_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}