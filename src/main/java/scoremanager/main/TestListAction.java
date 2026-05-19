package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		// セッション取得
		HttpSession session = request.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		List<Integer> entYearSet = new ArrayList<>();

		// リクエストパラメータの取得
		// 初期表示なのでなし
		// DBからデータ取得
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		var subjectSet = subjectDao.filter(teacher.getSchool());

		// ビジネスロジック
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		// DBへデータ保存
		// レスポンス値をセット
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("subject_set", subjectSet);

		// JSPへフォワード
		request.getRequestDispatcher("test_list.jsp")
			.forward(request, response);
	}
}