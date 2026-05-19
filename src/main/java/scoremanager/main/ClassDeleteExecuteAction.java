package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassDeleteExecuteAction extends Action {

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
		
		// パラメータ取得
		String class_num = req.getParameter("class_num");
		// Bean
		ClassNum classNum = new ClassNum();
		classNum.setClass_num(class_num);
		classNum.setSchool(teacher.getSchool());
		// DAO
		ClassNumDao cDao = new ClassNumDao();
		
		try {
			// 削除
			cDao.delete(classNum);
			
			// クラス番号削除完了画面へ
			url = "class_delete_done.jsp";
			
		} catch (Exception e) {
			
			// エラーメッセージ
			req.setAttribute(
				"error",
				"このクラスには学生情報が存在するため削除できません。"
			);
			
			req.setAttribute("classNum", classNum);
			
			// クラス番号削除画面へ
			url = "class_delete.jsp";
		}
		
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}