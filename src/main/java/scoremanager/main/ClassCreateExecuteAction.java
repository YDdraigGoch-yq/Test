package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得
		String class_num = req.getParameter("class_num");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();

		// 文字数チェック
		if (class_num == null || class_num.trim().equals("")) {
			errors.put(
				"class_num",
				"クラス番号を入力してください"
			);
		} else if (class_num.length() > 3) {
			errors.put(
				"class_num",
				"クラス番号は3文字以内で入力してください"
			);
		}
		
		// DAO
		ClassNumDao cDao = new ClassNumDao();
		
		// クラス番号取得
		ClassNum old = cDao.get(class_num, teacher.getSchool());
		// クラス番号重複チェック
		if (old != null) {
			errors.put(
				"class_num",
				"クラス番号が重複しています"
			);
		}
		
		// エラーがある場合
		if (!errors.isEmpty()) {
			
			// レスポンス値をセット
			// エラーをセット
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", class_num);
			
			// クラス情報登録画面へ
			url = "class_create.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// クラス番号インスタンス生成
		ClassNum classNum = new ClassNum();
		classNum.setClass_num(class_num);
		classNum.setSchool(teacher.getSchool());
		
		// DBに保存
		cDao.save(classNum);
		
		// クラス番号登録完了画面へ
		url = "class_create_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
