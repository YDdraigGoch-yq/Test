<%-- 学生情報登録 --%>
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
				学生情報変更
			</h2>
			
			<%-- フォーム --%>
			<form action="StudentUpdateExecute.action" method="post">
				<div class="mx-3">
				
					<%-- 入学年度 --%>
					<div class="mb-3">
						<label class="form-label">
							入学年度
						</label>
						
						<input class="form-control-plaintext ps-3"
							   type="text"
							   name="ent_year"
							   value="${student.entYear}"
							   readonly>
					</div>
					
					<!-- 学生番号 -->
					<div class="mb-3">
					
						<label class="form-label">
							学生番号
						</label>
						
						<input type="text"
							   name="no"
							   class="form-control-plaintext ps-3"
							   value="${student.no}"
							   readonly>
						
					</div>
					
					<!-- 氏名 -->
					<div class="mb-3">
					
						<label class="form-label">
							氏名
						</label>
						
						<input class="form-control"
							   type="text"
							   name="name"
							   value="${student.name}">
							
					</div>
					
					<!-- クラス -->
					<div class="mb-3">
					
						<label class="form-label">
							クラス
						</label>
						
						<select
							name="class_num"
							class="form-select">
							
							<c:forEach
								var="num"
								items="${class_num_set}">
								
								<option
									value="${num}"
									
									<c:if test="${num == student.classNum}">
										selected
									</c:if>>
									
									${num}
									
								</option>
								
							</c:forEach>
							
						</select>
						
					</div>
					
					<div class="col-2 text-start mb-4">
						<label for="student-f3-check">
							在学中
						</label>
						
						<input class="form-check-input"
							   type="checkbox"
							   id="student-f3-check"
							   name="is_attend"
							   value="true"
							<c:if test="${student.attend}">
								checked
							</c:if>>
					</div>
					
					<div class="text-start">
					
						<%-- 変更ボタン --%>
						<button type="submit" class="btn btn-primary">
							変更
						</button>
						
						<%-- 学生管理画面に遷移する --%>
						<div class="mt-2">
							<a href="StudentList.action">
								戻る
							</a>
						</div>
						
					</div>
					
				</div>
			</form>
			
		</section>
	</c:param>
</c:import>