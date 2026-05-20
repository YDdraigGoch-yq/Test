package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req,
		HttpServletResponse res
	) throws Exception {

		String url = "";

		HttpSession session = req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		Subject subject = null;

		try {

			String cd =
				req.getParameter("cd");

			SubjectDao suDao =
				new SubjectDao();

			// DBから元データ取得
			subject =
				suDao.get(
					cd,
					teacher.getSchool()
				);

			boolean result =
				suDao.delete(subject);

			if(result){

				url =
					"subject_delete_done.jsp";

			}else{

				req.setAttribute(
					"error",
					"科目を削除できませんでした。"
				);

				req.setAttribute(
					"subject",
					subject
				);

				url =
					"subject_delete.jsp";
			}

		}catch(Exception e){

			req.setAttribute(
				"error",
				"この科目は成績情報で使用されているため削除できません。"
			);

			req.setAttribute(
				"subject",
				subject
			);

			url =
				"subject_delete.jsp";

			e.printStackTrace();
		}

		req.getRequestDispatcher(url)
			.forward(req,res);
	}
}
