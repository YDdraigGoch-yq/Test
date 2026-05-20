<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts">
		<style>
			.test-regist-field {
				width: 125px;
			}
			
			.test-regist-subject-field {
				width: 320px;
			}
			
			.test-regist-search-button-wrap {
				width: 70px;
				margin: auto;
			}
			
			.test-regist-search-button {
				width: 64px;
				height: 38px;
				padding-top: 6px;
				padding-bottom: 6px;
				position: relative;
				top: -2px;
			}
			
			.test-regist-point-input {
				width: 12rem;
			}
		</style>
	</c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			
			<%-- 検索フォーム --%>
			<form action="TestRegist.action" method="post">
				<div class="mx-3 border rounded p-3 mb-3">
					<div class="d-flex align-items-end gap-4 flex-nowrap">
					
						<%-- 入学年度 --%>
						<div class="test-regist-field">
							<label class="form-label mb-1" for="test-regist-f1-select">入学年度</label>
							<select class="form-select" id="test-regist-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- クラス --%>
						<div class="test-regist-field">
							<label class="form-label mb-1" for="test-regist-f2-select">クラス</label>
							<select class="form-select" id="test-regist-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- 科目 --%>
						<div class="test-regist-subject-field">
							<label class="form-label mb-1" for="test-regist-f3-select">科目</label>
							<select class="form-select" id="test-regist-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- 回数 --%>
						<div class="test-regist-field">
							<label class="form-label mb-1" for="test-regist-f4-select">回数</label>
							<select class="form-select" id="test-regist-f4-select" name="f4">
								<option value="0">--------</option>
								<option value="1" <c:if test="${f4 == 1 || f4 == '1'}">selected</c:if>>1</option>
								<option value="2" <c:if test="${f4 == 2 || f4 == '2'}">selected</c:if>>2</option>
							</select>
						</div>
						
						<%-- 検索ボタン --%>
						<div class="test-regist-search-button-wrap">
							<button type="submit" class="btn btn-secondary test-regist-search-button">
								検索
							</button>
						</div>
					</div>
					
					<%-- 検索条件エラー --%>
					<c:if test="${not empty search_error}">
						<div class="text-warning mt-2">${search_error}</div>
					</c:if>
				</div>
			</form>
			
			<%-- 検索結果がない場合、エラーメッセージを表示 --%>
			<c:if test="${not empty message}">
				<div class="mx-3 text-warning">
					${message}
				</div>
			</c:if>
			
			<%-- 検索結果がある場合だけ、成績入力欄を表示 --%>
			<c:if test="${not empty tests}">
			
				<form action="TestRegistExecute.action" method="post">
				
					<%-- 登録処理へ検索条件を渡す --%>
					<input type="hidden" name="f1" value="${f1}">
					<input type="hidden" name="f2" value="${f2}">
					<input type="hidden" name="f3" value="${f3}">
					<input type="hidden" name="f4" value="${f4}">
					
					<div class="mx-3 mb-2">
						科目：${subject.name}（${f4}回）
					</div>
					
					<div class="mx-3">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>入学年度</th>
									<th>クラス</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th>点数</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach var="test" items="${tests}">
									<tr>
										<td>${test.student.entYear}</td>
										<td>${test.classNum}</td>
										<td>${test.student.no}</td>
										<td>${test.student.name}</td>
										<td>
											<input type="hidden" name="student_no" value="${test.student.no}">
											<input type="hidden" name="class_num" value="${test.classNum}">
											
											<input
												type="number"
												class="form-control form-control-sm test-regist-point-input"
												name="point"
												min="0"
												max="100"
												value="${test.point == -1 ? '' : test.point}">
												
											<c:if test="${not empty point_errors[test.student.no]}">
												<div class="text-warning mt-1">
													${point_errors[test.student.no]}
												</div>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<div class="mx-3 mt-3">
						<button type="submit" class="btn btn-secondary">
							登録して終了
						</button>
					</div>
					
				</form>
			</c:if>
			
		</section>
	</c:param>
</c:import>