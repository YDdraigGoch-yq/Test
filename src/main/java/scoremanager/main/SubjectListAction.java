package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// DAO
		SubjectDao suDao = new SubjectDao();
		
		// 科目一覧取得
		List<Subject> subjects = suDao.filter(teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("subjects", subjects);
		
		// 科目一覧画面へ
		url = "subject_list.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}