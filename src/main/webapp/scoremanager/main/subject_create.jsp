<%-- 科目情報登録 --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-4 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
				科目情報登録
			</h2>
			
			<%-- フォーム --%>
			<form action="SubjectCreateExecute.action" method="post">
				<div class="mx-3">
				
					<%-- 科目コード --%>
					<div class="mb-3">
						<label class="form-label">
							科目コード
						</label>
						<input class="form-control"
							   type="text"
							   name="cd"
							   placeholder="科目コードを入力してください"
							   value="${cd}"
							   required>
							   
						<%-- 科目コードのエラーメッセージ --%>
						<div class="text-warning mt-2">
							${errors.get("cd")}
						</div>
					</div>
					
					<%-- 科目名 --%>
					<div class="mb-3">
						<label class="form-label">
							科目名
						</label>
						<input class="form-control"
							   type="text"
							   name="name"
							   placeholder="科目名を入力してください"
							   value="${name}"
							   required>
					</div>
					
					<div class="text-start">
					
						<%-- 登録ボタン --%>
						<button type="submit" class="btn btn-primary">
							登録
						</button>
						
						<%-- 科目管理画面に遷移する --%>
						<div class="mt-3">
							<a href="SubjectList.action">
								戻る
							</a>
						</div>
						
					</div>
					
				</div>
			</form>
			
		</section>
	</c:param>
</c:import>