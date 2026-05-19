package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class LoginAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数の宣言
		String url = "";
		// パラメーター取得
		// DBからデータ取得
		// ビジネスロジック
		// DBへデータ保存
		// レスポンス値をセット
		
		// ログイン画面へ
		url = "login.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}