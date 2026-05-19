<%-- クラス情報登録 --%>
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
				クラス情報登録
			</h2>
			
			<%-- フォーム --%>
			<form action="ClassCreateExecute.action" method="post">
				<div class="mx-3">
				
					<%-- クラス番号 --%>
					<div class="mb-3">
						<label class="form-label">
							クラス番号
						</label>
						<input type="text"
							   name="class_num"
							   class="form-control"
							   placeholder="クラス番号を入力してください"
							   value="${class_num}"
							   required>
							   
						<%--　クラス番号のエラーメッセージ --%>
						<div class="text-warning mt-2">
							${errors.get("class_num")}
						</div>
					</div>
					
					<div class="text-start">
					
						<%-- 登録ボタン --%>
						<button type="submit" class="btn btn-primary">
							登録
						</button>
						
						<%-- クラス管理画面に遷移する --%>
						<div class="mt-3">
							<a href="ClassList.action">
								戻る
							</a>
						</div>
						
					</div>
					
				</div>
			</form>
			
		</section>
	</c:param>
</c:import>