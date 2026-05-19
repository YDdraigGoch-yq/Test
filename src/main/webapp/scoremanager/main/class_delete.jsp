<%-- クラス情報削除 --%>
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
				クラス情報削除
			</h2>
			
			<div class="mx-3">
				<p style="margin-top:35px">
					クラス「${classNum.class_num}」を削除してよろしいですか？
				</p>
				
				<c:if test="${not empty error}">
					<%-- 「d-inline-block」エラーメッセージの長さによって枠を調整する --%>
					<div class="alert alert-danger mb-3 d-inline-block">
						${error}
					</div>
				</c:if>
				
				<%-- フォーム --%>
				<form action="ClassDeleteExecute.action" method="post">
					<input type="hidden" name="class_num" value="${classNum.class_num}">
					
					<%-- 削除ボタン --%>
					<button type="submit"
							class="btn btn-danger">
						削除
					</button>
					
					<div style="margin-top:60px">
						<%-- クラス管理画面に遷移する --%>
						<a href="ClassList.action">
							戻る
						</a>
					</div>
				</form>
			</div>
			
		</section>
	</c:param>
</c:import>