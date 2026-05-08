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
		
		// Local変数の宣言 1
		String url = "";
		HttpSession session=req.getSession();

		// Parameter取得 2
		// DBからデータ取得 3
		// Business.logic 4
		if (session.getAttribute("user") != null) {
			session.invalidate();
		}

		// DBへデータ保存 5
		// Response値をセット 6
		// logout.jspへforward 7
		url = "logout.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
