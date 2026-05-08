	package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class MenuAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {

		// Local変数の宣言 1
		// Request.parameterの取得 2
		// DBからデータ取得 3
		// Business.logic 4
		// DBへデータ保存 5
		// レスポンス値をセット 6
		
		// menu.jspへforward 7
		req.getRequestDispatcher("menu.jsp")
			.forward(req, res);
	}
}
