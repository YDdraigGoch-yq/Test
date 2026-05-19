package scoremanager.main;

import java.util.List;
import java.util.ArrayList;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassPromoteAction extends Action {

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
		String classNum = req.getParameter("class_num");
		// Dao
		ClassNumDao cDao = new ClassNumDao();
		// クラス一覧取得
		List<String> classNums = cDao.filter(teacher.getSchool());
		
		// 次年度クラス一覧
		List<String> promoteClassNums = new ArrayList<>();
		// 現在年次取得
		int currentGrade = Integer.parseInt(classNum.substring(0, 1));
		
		// 次年次
		int nextGrade = currentGrade + 1;
		
		// 次年度クラスのみ取得
		for (String cn : classNums) {
			int grade = Integer.parseInt(cn.substring(0, 1));
			
			// 次年度のみ
			if (grade == nextGrade) {
				promoteClassNums.add(cn);
			}
		}
		
		// リクエストへセット
		req.setAttribute("oldClassNum", classNum);
		req.setAttribute("classNums", promoteClassNums);
		
		// クラス番号変更画面へ
		url = "class_promote.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}