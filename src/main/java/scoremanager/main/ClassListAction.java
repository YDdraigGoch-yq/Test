package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

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
		ClassNumDao cDao = new ClassNumDao();
		// クラス一覧取得
		List<String> classNums = cDao.filter(teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("classNums", classNums);
		
		// クラス一覧画面へ
		url = "class_list.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}