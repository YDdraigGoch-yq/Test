<%-- ログアウトJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
				ログアウト
			</h2>
			
			<p class="text-center py-1"
			   style="background-color:#66CC99; margin-bottom:70px">
			   ログアウトしました
			</p>
			
			<div class="mx-3">
				<a href="../Login.action">ログイン</a>
			</div>
		</div>
	</c:param>
</c:import>