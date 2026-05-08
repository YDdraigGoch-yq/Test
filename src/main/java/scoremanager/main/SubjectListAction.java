package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// Session
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// Loginチェック
		if (teacher == null) {
			res.sendRedirect("Login.action");
			return;
		}
		
		// Local変数の宣言
		List<Subject> subjects = null;
		SubjectDao suDao = new SubjectDao();
		
		// Parameter取得
		String cd = req.getParameter("f1"); 
		String name = req.getParameter("f2");
		//　エラーマップ
		Map<String, String> errors = new HashMap<>();
		
		// Business.logic
		if (cd != null && cd.equals("0") &&
			name != null && name.equals("0")) {
			
			// 両方指定
			subjects = suDao.filter(teacher.getSchool())
				.stream()
				.filter(su -> su.getCd().equals(cd))
				.filter(su -> su.getName().equals(name))
				.toList();
			
		} else if ( cd != null && !cd.equals("0")) {
			
			// cdのみ指定
			subjects = suDao.filter(teacher.getSchool())
				.stream()
				.filter(su -> su.getCd().equals(cd))
				.toList();
			
			
		} else if ( name != null && !name.equals("0")) {
			
			// nameのみ指定
			subjects = suDao.filter(teacher.getSchool())
				.stream()
				.filter(su -> su.getName().equals(name))
				.toList();
			
		} else {
			
			 // 指定なし
			subjects = suDao.filter(teacher.getSchool());
		}
		
		// request  一次请求
		// session  多次请求
		// flash    一次提示
		// flash処理
		String flash = (String) session.getAttribute("flash");
		req.setAttribute("flash", flash);
		// 重複を防止
		session.removeAttribute("flash");
		
		// Response値をセット
		req.setAttribute("f1", flash);
		req.setAttribute("f2", name);
		
		req.setAttribute("subjects", subjects);
		// Requestｍに科目リストをセット
		req.setAttribute("subject_list", suDao.filter(teacher.getSchool())
		);
		
		req.setAttribute("errors", errors);
		
		// student_list.jspへforward
		req.getRequestDispatcher("subject_list.jsp")
			.forward(req, res);
	}
}