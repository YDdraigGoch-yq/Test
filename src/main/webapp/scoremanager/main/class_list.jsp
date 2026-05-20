<%-- クラス一覧 --%>
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
				クラス管理
			</h2>
			
			<%-- クラス新規登録 --%>
			<div class="my-2 text-end px-4">
				<a href="ClassCreate.action">新規登録</a>
			</div>
			
			
			<%-- クラス一覧 --%>
			<c:choose>
				<c:when test="${classNums.size() > 0}">
				
					<table class="table table-hover">
					
						<tr>
							<th style="width: 240px">
								クラス番号
							</th>
							<th style="width: 150px"></th>
							<th style="width: 90px">
								進級変更
							</th>
							<th style="width: 90px"></th>
						</tr>
						
						<c:forEach var="cn" items="${classNums}">
							<tr>
								<td>${cn}</td>
								<td>
									<a href="ClassStudentList.action?class_num=${cn}">
										在学中学生一覧
									</a>
								</td>
								<td>
									<c:if test="${!cn.startsWith('3')}">
										<a href="ClassPromote.action?class_num=${cn}">
											変更
										</a>
									</c:if>
								</td>
								<td>
									<a href="ClassDelete.action?class_num=${cn}"
									   class="text-danger">
										削除
									</a>
								</td>
							</tr>
						</c:forEach>
						
					</table>
					
				</c:when>
				
				<c:otherwise>
					<div>クラス情報が存在しませんでした。</div>
				</c:otherwise>
				
			</c:choose>
		</section>
	</c:param>
</c:import>
