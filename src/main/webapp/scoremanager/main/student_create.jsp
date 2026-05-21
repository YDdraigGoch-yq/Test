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
				学生情報登録
			</h2>
			
			<%-- フォーム --%>
			<form action="StudentCreateExecute.action" method="post">
				<div class="mx-3">
				
					<%-- 入学年度 --%>
					<div class="mb-3">
						<label class="form-label">
							入学年度
						</label>
						
						<select name="ent_year"
								class="form-select">
								
							<option value="0">
								--------
							</option>
							
							<c:forEach var="year"
									   items="${ent_year_set}">
									   
								<option value="${year}"
									<c:if test="${year == ent_year}">
										selected
									</c:if>>
									${year}
								</option>
								
							</c:forEach>
						</select>
						
						<%-- 入学年度のエラーメッセージ --%>
						<div class="text-warning mt-2">
							${errors.get("ent_year")}
						</div>
					</div>
					
					<%-- 学生番号 --%>
					<div class="mb-3">
					    <label class="form-label">
					        学生番号
					    </label>
					    <input class="form-control"
					           type="text"
					           name="no"
					           placeholder="学生番号を入力してください"
					           value="${no}"
					           required>
					
					    <%-- 学生番号のエラーメッセージ --%>
					    <div class="text-warning mt-2">
					        ${errors.get("no")}
					    </div>
					</div>
					
					<%-- 氏名 --%>
					<div class="mb-3">
					    <label class="form-label">
					        氏名
					    </label>
					    <input class="form-control"
					           type="text"
					           name="name"
					           placeholder="氏名を入力してください"
					           value="${name}"
					           required>
					</div>
					
					<!-- クラス -->
					<div class="mb-3">
					
						<label class="form-label">
							クラス
						</label>
						
						<select name="class_num"
								class="form-select">
								
							<c:forEach var="num"
									   items="${class_num_set}">
									   
								<option value="${num}"
									<c:if test="${num == class_num}">
										selected
									</c:if>>
									${num}
								</option>
								
							</c:forEach>
							
						</select>
						
					</div>
					
					<div class="text-start">
					
						<%-- 登録ボタン --%>
						<button type="submit" class="btn btn-secondary">
							登録して終了
						</button>
						
						<%-- 学生管理画面に遷移する --%>
						<div class="mt-3">
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
