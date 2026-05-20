package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数の宣言
		String url = "";
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String numStr = "";
		int entYear = 0;
		int num = 0;
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 現在年取得
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		// Dao
		ClassNumDao cDao = new ClassNumDao();
		SubjectDao suDao = new SubjectDao();
		TestDao teDao = new TestDao();
		
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = null;
		List<Subject> subjectSet = null;
		List<Test> tests = null;
		
		// パラメーター取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		numStr = req.getParameter("f4");
		
		// DBからデータ取得
		classNumSet = cDao.filter(teacher.getSchool());
		subjectSet = suDao.filter(teacher.getSchool());
		
		// ビジネスロジック
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		
		boolean isSearch =
				entYearStr != null
				|| classNum != null
				|| subjectCd != null
				|| numStr != null;
		
		if (isSearch) {
			
			if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
					|| classNum == null || classNum.equals("") || classNum.equals("0")
					|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")
					|| numStr == null || numStr.equals("") || numStr.equals("0")) {
				
				errors.put("f1", "入学年度とクラスと科目と回数を選択してください");
				
			} else {
				// 型変換
				entYear = Integer.parseInt(entYearStr);
				num = Integer.parseInt(numStr);
				
				Subject subject = suDao.get(subjectCd, teacher.getSchool());
				tests = teDao.filter(entYear, classNum, subject, num, teacher.getSchool());
				
				if (tests.size() == 0) {
					req.setAttribute("message", "学生情報が存在しませんでした。");
				}
				
				req.setAttribute("subject", subject);
				req.setAttribute("tests", tests);
				
				if (tests.size() == 0) {
					req.setAttribute("message", "学生情報が存在しませんでした");
				}
			}
		}
		
		// DBへデータ保存
		// なし
		// リクエストへセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);
		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", numStr);
		req.setAttribute("errors", errors);
		// 前回JSPの属性名にも対応
		req.setAttribute("search_error", errors.get("f1"));

		// 成績登録画面へ
		url = "test_regist.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}