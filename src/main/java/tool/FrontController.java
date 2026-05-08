package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest req, HttpServletResponse res
	) throws ServletException, IOException {
		try {
			// パスを取得
			String path = req.getServletPath().substring(1);
			// ファイル名を取得しクラス名に変換
			String name = path.replace(".a", "A").replace('/', '.');
			
			System.out.println("★ servlet path ->" + req.getServletPath());
			System.out.println("★ class name ->" + name);
			
			// Action.instanceを返却
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// 遷移先URLを取得
			action.execute(req, res);

		} catch (Exception e) {
			e.printStackTrace();
			// エラーページへリダイレクト
			req.getRequestDispatcher("/error.jsp")
				.forward(req, res);
		}
	}

	@Override
	protected void doPost(
		HttpServletRequest req, HttpServletResponse res
	) throws ServletException, IOException {

		doGet(req,res);
	}
}
