package scoremanager.main;


import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {

	// エラーを初期化
	req.setAttribute(
		"errors",
		new HashMap<String, String>()
	);
	
	// 初期値をセット
	req.setAttribute("cd", "");
	req.setAttribute("name", "");
	
	// 科目情報登録画面へ
	req.getRequestDispatcher("subject_create.jsp")
    	.forward(req, res);
	}
}