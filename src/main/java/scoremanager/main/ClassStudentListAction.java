package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassStudentListAction extends Action {

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
		
		// パラメーター取得(クラス番号)
		String class_num = req.getParameter("class_num");
		// Dao
		StudentDao sDao = new StudentDao();
		
		// 在学中の学生一覧取得
		List<Student> allStudents = sDao.filter(teacher.getSchool(), true);
		// クラス別学生一覧取得
		List<Student> students = new ArrayList<>();
		
		// クラス番号一致チェック
		for (Student student : allStudents) {
			if(student.getClassNum().equals(class_num)) {
				students.add(student);
			}
		}
		
		// リクエストへセット
		req.setAttribute("students", students);
		req.setAttribute("class_num", class_num);
		
		// 選んだクラスに在学中の学生一覧画面へ
		url = "class_student_list.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}