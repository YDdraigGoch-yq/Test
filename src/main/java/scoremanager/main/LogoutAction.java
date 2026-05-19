package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		HttpSession session = req.getSession();
		
		// パラメーター取得
		// DBからデータ取得
		// ビジネスロジック
		if (session.getAttribute("user") != null) {
			session.invalidate();
		}
		
		// DBへデータ保存
		// レスポンス値をセット
		// ログアウト画面へ
		url = "logout.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}