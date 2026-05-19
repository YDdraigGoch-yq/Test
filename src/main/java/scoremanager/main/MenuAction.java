package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class MenuAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		
		// パラメーターの取得
		// DBからデータ取得
		// ビジネスロジック
		// DBへデータ保存
		// レスポンス値をセット
		
		// メニュー画面へ
		url = "menu.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
