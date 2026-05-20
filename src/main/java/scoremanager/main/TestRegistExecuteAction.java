package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
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

public class TestRegistExecuteAction extends Action {

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
		Map<String, String> pointErrors = new HashMap<>();
		
		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = null;
		List<Subject> subjectSet = null;
		
		// パラメーター取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		numStr = req.getParameter("f4");
		
		String[] studentNoArray = req.getParameterValues("student_no");
		String[] classNumArray = req.getParameterValues("class_num");
		String[] pointArray = req.getParameterValues("point");
		
		// DBからデータ取得
		classNumSet = cDao.filter(teacher.getSchool());
		subjectSet = suDao.filter(teacher.getSchool());
		
		// ビジネスロジック
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		
		if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
				|| classNum == null || classNum.equals("") || classNum.equals("0")
				|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")
				|| numStr == null || numStr.equals("") || numStr.equals("0")
				|| studentNoArray == null
				|| classNumArray == null
				|| pointArray == null) {
			
			errors.put("f1", "入学年度とクラスと科目と回数を選択してください");
			
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumSet);
			req.setAttribute("subject_set", subjectSet);
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("f4", numStr);
			req.setAttribute("errors", errors);
			req.setAttribute("search_error", errors.get("f1"));
			
			// 成績登録画面へ
			url = "test_regist.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// 型変換
		entYear = Integer.parseInt(entYearStr);
		num = Integer.parseInt(numStr);
		
		Subject subject = suDao.get(subjectCd, teacher.getSchool());
		
		List<Test> registList = new ArrayList<>();
		
		for (int i = 0; i < studentNoArray.length; i++) {
			
			String studentNo = studentNoArray[i];
			String rowClassNum = classNumArray[i];
			String pointStr = pointArray[i];
			int point = 0;
			boolean isPointOk = true;
			
			if (pointStr == null || pointStr.equals("")) {
				pointErrors.put(studentNo, "0～100の範囲で入力してください");
				errors.put(studentNo, "0～100の範囲で入力してください");
				isPointOk = false;
				
			} else {
				try {
					point = Integer.parseInt(pointStr);
					
					if (point < 0 || point > 100) {
						pointErrors.put(studentNo, "0～100の範囲で入力してください");
						errors.put(studentNo, "0～100の範囲で入力してください");
						isPointOk = false;
					}
					
				} catch (NumberFormatException e) {
					pointErrors.put(studentNo, "0～100の範囲で入力してください");
					errors.put(studentNo, "0～100の範囲で入力してください");
					isPointOk = false;
				}
			}
			
			if (isPointOk) {
				Student student = new Student();
				student.setNo(studentNo);
				student.setSchool(teacher.getSchool());
				
				Test test = new Test();
				test.setStudent(student);
				test.setClassNum(rowClassNum);
				test.setSubject(subject);
				test.setSchool(teacher.getSchool());
				test.setNo(num);
				test.setPoint(point);
				
				registList.add(test);
			}
		}
		
		if (pointErrors.size() > 0) {
			
			List<Test> tests = teDao.filter(entYear, classNum, subject, num, teacher.getSchool());
			
			// 入力エラー時、入力された値をできるだけ画面に戻す
			for (Test test : tests) {
				String studentNo = test.getStudent().getNo();
				
				for (int i = 0; i < studentNoArray.length; i++) {
					if (studentNo.equals(studentNoArray[i])) {
						try {
							if (pointArray[i] == null || pointArray[i].equals("")) {
								test.setPoint(-1);
							} else {
								test.setPoint(Integer.parseInt(pointArray[i]));
							}
						} catch (NumberFormatException e) {
							test.setPoint(-1);
						}
					}
				}
			}
			
			// リクエストへセット
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumSet);
			req.setAttribute("subject_set", subjectSet);
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("f4", numStr);
			req.setAttribute("subject", subject);
			req.setAttribute("tests", tests);
			req.setAttribute("errors", errors);
			req.setAttribute("point_errors", pointErrors);
			
			// 成績登録画面へ
			url = "test_regist.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			
			return;
		}
		
		// DBへデータ保存
		teDao.save(registList);
		
		// レスポンス値をセット
		// 完了画面は固定メッセージのため、特になし
		// 成績登録完了画面へ
		url = "test_regist_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}