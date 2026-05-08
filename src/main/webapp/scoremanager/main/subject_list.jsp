<%-- 科目一覧.JSP --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<%-- 共通箇所（１～９） --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
				科目管理一覧
			</h2>
			
			<%--　科目新規登録 --%>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			
			
			<%-- 科目一覧 --%>
			<c:choose>
				<c:when test="${subjects.size() > 0}">
				
					<table class="table table-hover">
						<tr>
							<th style="width: 120px">
								科目コード
							</th>
							<th style="width: 260px">
								科目名
							</th>
							<th style="width: 90px"></th>
							<th style="width: 90px"></th>
						</tr>
						
						<c:forEach var="su" items="${subjects}">
							<tr>
								<td>${su.cd}</td>
								<td>${su.name}</td>
								<td>
									<a href="SubjectUpdate.action?cd=${su.cd}">
										変更
									</a>
								</td>
								<td>
									<a href="SubjectDelete.action?cd=${su.cd}"
									   class="text-danger">
										削除
									</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					
				</c:when>
				
				<c:otherwise>
					<div>科目情報が存在しませんでした。</div>
				</c:otherwise>
				
			</c:choose>
		</section>
	</c:param>
</c:import>