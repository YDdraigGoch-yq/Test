package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");
		
		// 型変換
		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = isAttendStr != null;
		
		// 学生インスタンス生成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());
		
		// 学生取得
		StudentDao sdao = new StudentDao();
		// DBに保存
		sdao.save(student);
		
		// 学生情報変更完了画面へ
		url = "student_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}