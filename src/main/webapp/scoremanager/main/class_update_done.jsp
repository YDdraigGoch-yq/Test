<%-- 学生クラス変更完了 --%>
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
				クラス変更
			</h2>
			
			<p class="text-center py-1"
			   style="background-color:#66CC99; margin-bottom:30px">
				変更が完了しました
			</p>
			
			<p class="mx-3">
				「${student.name}」
				のクラスを
				「${newClassNum}」
				に変更しました。
			</p>
			
			<div class="mx-3">
				<%-- クラス一覧画面に遷移する --%>
				<a href="ClassList.action">
					クラス一覧
				</a>
			</div>
			
		</section>
	</c:param>
</c:import>
