<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts">
		<style>
			.grmr-search-box {
				margin-left: 1rem;
				margin-right: 1rem;
				padding: 0.35rem 1rem 0.45rem 1rem;
			}
			
			.grmr-subject-row {
				display: grid;
				grid-template-columns: 10rem 8rem 8rem 18rem 3.8rem;
				column-gap: 0.9rem;
				align-items: center;
			}
			
			.grmr-student-row {
				display: grid;
				grid-template-columns: 10rem 19rem 3.8rem;
				column-gap: 0.9rem;
				align-items: center;
			}
			
			.grmr-left-label {
				margin-left: 1rem;
				padding-bottom: 0.25rem;
			}
			
			.grmr-item-label {
				margin-top: 0.3em;
				margin-bottom: 0.3rem;
				align-items: center;
			}
			
			.grmr-select-year {
				margin-bottom: 0.2rem;
				width: 7rem;
			}
			
			.grmr-select-class {
				margin-bottom: 0.2rem;
				width: 7rem;
			}
			
			.grmr-select-subject {
				margin-bottom: 0.2rem;
				width: 15rem;
			}
			
			.grmr-student-no {
				font-size: 0.9rem;
				height: 2rem;
				width: 16rem;
			}
			
			.grmr-button-cell {
				padding-bottom: 0.12rem;
			}
			
			.grmr-search-btn {
				width: 3.5rem;
			}
			
			.grmr-separator {
				margin-top: 0.35rem;
				margin-bottom: 0.35rem;
			}
			
			.grmr-guide {
				margin-top: 0.45rem;
				margin-bottom: 0;
			}
		</style>
	</c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			
			<div class="mx-3 border rounded grmr-search-box">
			
				<%-- 科目情報検索フォーム --%>
				<form action="TestListSubjectExecute.action" method="post" class="mb-0">
				
					<%-- 15 科目情報識別コード --%>
					<input type="hidden" name="f" value="sj">
					
					<div class="grmr-subject-row">
					
						<%-- 2 科目情報 --%>
						<div class="grmr-left-label pt-1">
							科目情報
						</div>
						
						<%-- 3 / 6 入学年度 --%>
						<div>
							<label class="form-label grmr-item-label">入学年度</label>
							<select class="form-select form-select-sm grmr-select-year" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- 4 / 7 クラス --%>
						<div>
							<label class="form-label grmr-item-label">クラス</label>
							<select class="form-select form-select-sm grmr-select-class" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- 5 / 8 科目 --%>
						<div>
							<label class="form-label grmr-item-label">科目</label>
							<select class="form-select form-select-sm grmr-select-subject" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>
						
						<%-- 9 検索ボタン --%>
						<div class="grmr-button-cell">
							<button type="submit" class="btn btn-secondary btn-sm grmr-search-btn">
								検索
							</button>
						</div>
					</div>
				</form>
				
				<c:if test="${not empty subject_error}">
					<div class="text-warning mt-2">${subject_error}</div>
				</c:if>
				
				<hr class="grmr-separator">
				
				<%-- 学生情報検索フォーム --%>
				<form action="TestListStudentExecute.action" method="post" class="mb-0">
				
					<%-- 16 学生情報識別コード --%>
					<input type="hidden" name="f" value="st">
					
					<div class="grmr-student-row">
					
						<%-- 10 学生情報 --%>
						<div class="grmr-left-label pt-2">
							学生情報
						</div>
						
						<%-- 11 / 12 学生番号 --%>
						<div class="pt-1">
							<label class="form-label grmr-item-label">学生番号</label>
							<input type="text" class="form-control form-control-sm grmr-student-no"
								name="f4"
								placeholder="学生番号を入力してください"
								value="${f4}"
								maxlength="10"
								required>
						</div>
						
						<%-- 13 検索ボタン --%>
						<div class="grmr-button-cell pt-3 pr-3">
							<button type="submit" class="btn btn-secondary btn-sm grmr-search-btn">
								検索
							</button>
						</div>
					</div>
				</form>
				
			</div>

			<%-- 14 利用方法案内メッセージ --%>
			<p class="text-info grmr-guide">
				科目情報を選択または学生情報を入力し検索ボタンをクリックしてください
			</p>
		</section>
	</c:param>
</c:import>