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
				成績一覧（学生）
			</h2>
			
			<div class="mx-3 border rounded p-3 mb-3">
			
				<form action="TestListSubjectExecute.action" method="post" class="mb-3">
					<div class="row align-items-end g-3">
						<div class="col-auto">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}">${year}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-auto">
							<label class="form-label">クラス</label>
							<select class="form-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}">${num}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-auto">
							<label class="form-label">科目</label>
							<select class="form-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}">${subject.name}</option>
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
								placeholder="学生番号を入力してください" value="${f4}" required>
						</div>
						
						<div class="col-auto">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
			</div>
			
			<c:if test="${not empty student}">
				<div class="mx-3 mb-2">氏名：${student.name}（${student.no}）</div>
			</c:if>
			
			<c:if test="${not empty message}">
				<div class="mx-3">${message}</div>
			</c:if>
			
			<c:if test="${scores.size() > 0}">
				<div class="mx-3">
					<table class="table">
						<thead>
							<tr>
								<th>科目名</th>
								<th>科目コード</th>
								<th>回数</th>
								<th>点数</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="score" items="${scores}">
								<tr>
									<td>${score.subjectName}</td>
									<td>${score.subjectCd}</td>
									<td>${score.num}</td>
									<td>${score.point}</td>
								</tr>
							</c:forEach>
						</tbody>
						
					</table>
				</div>
			</c:if>
			
		</section>
	</c:param>
</c:import>