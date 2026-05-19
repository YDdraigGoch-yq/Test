<%-- クラス情報変更 --%>
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
				進級変更
			</h2>
			
			<%-- フォーム --%>
			<form action="ClassPromoteExecute.action" method="post">
				<div class="mx-3">
				
					<%-- 現在のクラス番号 --%>
					<div class="mb-3">
						<label class="form-label">
							現在のクラス番号
						</label>
						<input type="text"
							   class="form-control-plaintext ps-3"
							   value="${oldClassNum}"
							   readonly>
						
						<input type="hidden"
							   name="oldClassNum"
							   value="${oldClassNum}">
					</div>
					
					<%-- 新しいクラス番号 --%>
					<div class="mb-3">
						<label class="form-label">
							新しいクラス番号
						</label>
						<select name="newClassNum"
								class="form-select">
							<c:forEach var="cn" items="${classNums}">
								<option value="${cn}">
									${cn}
								</option>
							</c:forEach>
						</select>
					
						<%-- クラス番号のエラーメッセージ --%>
						<div class="text-warning mt-2">
							${errors.get("class_num")}
						</div>
					</div>
						
					<div class="text-start">
					
						<%-- 変更ボタン --%>
						<button type="submit" class="btn btn-primary">
							変更
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