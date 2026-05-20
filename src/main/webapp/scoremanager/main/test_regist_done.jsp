<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>


<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>
	
	<c:param name="scripts">
		<style>
			.test-regist-done-message {
				height: 22px;
				background-color: #8ec9ac;
				display: flex;
				align-items: center;
				justify-content: center;
			}
			
			.test-regist-done-links {
				margin-top: 70px;
				margin-left: 8px;
			}
		</style>
	</c:param>
	
	<c:param name="content">
		<section class="me-4">
		
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			
			<div class="text-center py-1"
				 style="background-color:#66CC99; margin-bottom:130px">
				登録が完了しました
			</div>
			
			<div class="d-flex gap-5 test-regist-done-links">
				<a href="TestRegist.action">戻る</a>
				<a href="TestList.action">成績参照</a>
			</div>
			
		</section>
	</c:param>
</c:import>