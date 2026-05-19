package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.*;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

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
		String name = req.getParameter("name");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// 科目コード文字数チェック
		if (cd.length() !=3) {
			errors.put(
				"cd",
				"科目コードは3文字で入力してください"
			);
		}
		
		// 科目取得
		SubjectDao suDao = new SubjectDao();
		// 科目コード重複チェック
		Subject old = suDao.get(cd, teacher.getSchool());
		if (old != null) {
			errors.put(
				"cd",
				"科目コードが重複しています"
			);
		}
		
		// エラーがある場合
		if (!errors.isEmpty()) {
			
			// レスポンス値をセット
			// エラーをセット
			req.setAttribute("errors", errors);
			// 科目コードと科目名をセット
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			
			// 科目情報登録画面へ
			url = "subject_create.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// Bean
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// DBに保存
		suDao.save(subject);
		
		// 科目情報登録完了画面へ
		url = "subject_create_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}