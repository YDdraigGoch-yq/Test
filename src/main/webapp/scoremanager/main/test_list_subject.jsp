<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				成績一覧（科目）
			</h2>
			
			<div class="mx-3 border rounded p-3 mb-3">
			
				<form action="TestListSubjectExecute.action" method="post" class="mb-3">
					<div class="row align-items-end g-3">
						<div class="col-auto">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-auto">
							<label class="form-label">クラス</label>
							<select class="form-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-auto">
							<label class="form-label">科目</label>
							<select class="form-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="sub" items="${subject_set}">
									<option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>
										${sub.name}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-auto">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
				
				<hr>
				
				<form action="TestListStudentExecute.action" method="post">
					<div class="row align-items-end g-3">
						<div class="col-auto">
							<label class="form-label">学生番号</label>
							<input type="text" class="form-control" name="f4"
								placeholder="学生番号を入力してください">
						</div>
						
						<div class="col-auto">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
			</div>
			
			<c:if test="${not empty subject}">
				<div class="mx-3 mb-2">科目：${subject.name}</div>
			</c:if>
			
			<c:if test="${not empty message}">
				<div class="mx-3">${message}</div>
			</c:if>
			
			<c:if test="${students.size() > 0}">
				<div class="mx-3">
					<table class="table">
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<c:forEach var="num" items="${num_set}">
									<th>${num}回</th>
								</c:forEach>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="student" items="${students}">
								<tr>
									<td>${student.entYear}</td>
									<td>${student.classNum}</td>
									<td>${student.studentNo}</td>
									<td>${student.studentName}</td>
									
									<c:forEach var="num" items="${num_set}">
										<td>
											<c:choose>
												<c:when test="${not empty student.points[num]}">
													${student.points[num]}
												</c:when>
												<c:otherwise>-</c:otherwise>
											</c:choose>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
						
					</table>
				</div>
			</c:if>
			
		</section>
	</c:param>
</c:import>