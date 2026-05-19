package scoremanager;

import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;


public class LoginExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {

		// ローカル変数の宣言
		String url = "";
		String id = "";
		String password = "";
		TeacherDao tDao = new TeacherDao();
		Teacher teacher = null;

		// パラメーターの取得
		// 教員ID
		id = req.getParameter("id");
		// パスワード
		password = req.getParameter("password");

		// DBからデータ取得
		// 教員データ取得
		teacher = tDao.login(id, password);

		// ビジネスロジック
		// DBへデータ保存
		// レスポンス値をセット
		// フォワード
		
		// 条件で手順の内容が分岐
		// 認証成功の場合
		if (teacher != null) {
			// セッション情報を取得
			HttpSession session = req.getSession(true);
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			// セッションにログイン情報を保存
			session.setAttribute("user", teacher);

			// メニューへ
			url = "main/Menu.action";
			res.sendRedirect(url);
			
		} else {
			// 認証失敗の場合
			// エラーメッセージをセット
			List<String> errors = new ArrayList<>();
			errors.add("IDまたはパスワードが確認できませんでした");
			req.setAttribute("errors", errors);
			// 入力された教員IDをセット
			req.setAttribute("id", id);

			// ログイン画面へ
			url = "login.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
		}
	}
}