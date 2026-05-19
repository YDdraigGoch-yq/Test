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
				クラス「${class_num}」の学生一覧
			</h2>
			
			<c:choose>
			
				<c:when test="${students.size() > 0}">
					<table class="table table-hover">
					
						<tr>
							<th>学生番号</th>
							<th>氏名</th>
							<th>クラス変更</th>
						</tr>
						
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.no}</td>
								<td>${student.name}</td>
								<td>
									<a href="ClassUpdate.action?no=${student.no}">
										変更
									</a>
								</td>
							</tr>
						</c:forEach>
						
					</table>
				</c:when>
				
				<c:otherwise>
					<div>
						学生情報が存在しませんでした。
					</div>
				</c:otherwise>
				
			</c:choose>
			
			<div class="mt-3">
				<a href="ClassList.action">
					戻る
				</a>
			</div>
			
		</section>
	</c:param>
</c:import>