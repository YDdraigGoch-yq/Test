package scoremanager.main;

import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassCreateAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数
		String url = "";
		
		// エラー初期化
		req.setAttribute(
			"errors",
			new HashMap<String, String>()
		);
		// 初期値
		req.setAttribute("class_num", "");
		
		// クラス番号登録画面へ
		url = "class_create.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}