<%-- 科目情報削除 --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
				科目情報削除
			</h2>
			
			<div class="mx-3">
			
				<p style="margin-top:35px">
					「${subject.name}」を削除してよろしいですか？
				</p>
				<c:if test="${not empty error}">
					<div class="alert alert-danger mb-3 d-inline-block">
						${error}
					</div>
				</c:if>
				<%-- フォーム --%>
				<form action="SubjectDeleteExecute.action" method="post">
					<input type="hidden" name="cd" value="${subject.cd}">
					
					<%-- 削除ボタン --%>
					<button type="submit"
							class="btn btn-danger">
						削除
					</button>
					
					<div style="margin-top:60px">
						<%-- 科目管理画面に遷移する --%>
						<a href="SubjectList.action">
							戻る
						</a>
					</div>
				</form>
			</div>
			
		</section>
	</c:param>
</c:import>
