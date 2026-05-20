package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassPromoteExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数
		String url = "";
		// セッション取得
		HttpSession session =req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメータ取得
		String oldClassNum = req.getParameter("oldClassNum");
		String newClassNum = req.getParameter("newClassNum");

		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();

		// 新クラス未選択チェック
		if (newClassNum == null || newClassNum.equals("") || newClassNum.equals("0")) {
			errors.put(
				"class_num",
				"新しいクラス番号を選択してください"
			);
		}
		
		// エラーがある場合
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.setAttribute("oldClassNum", oldClassNum);
			
			// クラス一覧取得
			ClassNumDao cDao = new ClassNumDao();
			req.setAttribute("classNums", cDao.filter(teacher.getSchool()));
			
			// クラス進級変更画面へ
			url = "class_promote.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// Dao
		StudentDao sDao = new StudentDao();
		// 在学中学生一覧取得
		List<Student> students = sDao.filter(teacher.getSchool(), true);
		// クラス変更
		for (Student student : students) {
			
			// 古いクラス番号と一致する場合
			if (student.getClassNum().equals(oldClassNum)) {
				
				// 新しいクラス番号をセット
				student.setClassNum(newClassNum);
				
				// DBに保存
				sDao.save(student);
			}
		}
		
		// リクエストへセット
		req.setAttribute("oldClassNum", oldClassNum);
		req.setAttribute("newClassNum", newClassNum);
		
		// クラス進級変更完了画面へ
		url = "class_promote_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
