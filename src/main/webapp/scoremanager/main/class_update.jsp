<%-- 学生クラス変更 --%>
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
				クラス変更
			</h2>
			
			<%-- フォーム --%>
			<form action="ClassUpdateExecute.action" method="post">
				<div class="mx-3">
				
					<input type="hidden" name="no" value="${student.no}">
					<div class="mb-3">
						<label class="form-label">
							学生番号
						</label>
						<input type="text"
							   class="form-control-plaintext ps-3"
							   value="${student.no}"
							   readonly>
					</div>
					
					<div class="mb-3">
						<label class="form-label">
							氏名
						</label>
						<input type="text"
							   class="form-control-plaintext ps-3"
							   value="${student.name}"
							   readonly>
					</div>
					
					<div class="mb-3">
						<label class="form-label">
							変更後クラス
						</label>
						
						<select name="newClassNum"
								class="form-select">
								
							<c:forEach var="cn"
									   items="${classNums}">
								<option value="${cn}"
									<c:if test="${cn == student.classNum}">disabled</c:if>>
									${cn}</option>
							</c:forEach>
							
						</select>
						
					</div>
					
					<button type="submit"
							class="btn btn-primary">
						変更
					</button>
					
					<div class="mt-3">
						<a href="ClassList.action">
							戻る
						</a>
					</div>
					
				</div>
			</form>
			
		</section>
	</c:param>
</c:import>