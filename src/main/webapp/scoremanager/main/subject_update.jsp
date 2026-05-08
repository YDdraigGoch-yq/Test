·<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
				科目情報変更
			</h2>
			
			<%-- フォーム --%>
			<form action="SubjectUpdateDone.action" method="post">
				<div class="mx-3">
				
					<%-- 科目コード --%>
					<div class="mb-3">
						<label class="form-label">
							科目コード
						</label>
						<input class="form-control-plaintext ps-3"
							   type="text"
							   id="cd"
							   name="cd" 
							   value="${subject.cd}"
							   readonly>
					</div>
					
					
					<%-- 科目名 --%>
					<div class="mb-3">
						<label class="form-label">
							科目名
						</label>
						<input class="form-control"
							   type="text"
							   id="name"
							   name="name"  
							   value="${subject.name}"
							   required>
							   
						<%--　科目名のエラーメッセージ --%>
						<div class="text-warning mt-2">
							${errors.get("name")}
						</div>
					</div>
							
					<div class="text-start">
					
						<%-- 変更ボタン --%>
						<button type="submit" class="btn btn-primary">
							変更
						</button>
						
						<%-- 科目管理一覧画面に遷移する --%>
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