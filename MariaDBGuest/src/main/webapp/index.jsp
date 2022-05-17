<%@page import="kr.human.guest.service.GuestbookService"%>
<%@page import="kr.human.guest.vo.GuestbookVO"%>
<%@page import="kr.human.guest.vo.PagingVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<%
	// 페이지 정보를 얻어온다.
	PagingVO<GuestbookVO> pv = 
			GuestbookService.getInstance().selectList(currentPage, pageSize, blockSize);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>허접한 방명록</title>
<link href="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/jquery/3.6.0/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/js/bootstrap.min.js" ></script>
<style type="text/css">
	.mainContent {
		width: 80%; margin: auto; border: none;padding: 5px;
	}
	.title{
		width: 100%; margin: auto; border: none; font-size: 18pt; text-align: center;padding: 5px;
	}
	.pageInfo{
		width: 100%; margin: auto; border: none; font-size: 10pt; text-align: right;padding: 5px;
	}
	.pageList{
		width: 100%; margin: auto; border: none; font-size: 10pt; text-align: center;padding: 5px;
	}
</style>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>
<body>
	<div class="mainContent">
		<div class="title">허접한 방명록 Ver 0.009</div> <%-- 제목 --%>
		<div class="pageInfo"><%=pv.getPageInfo() %></div> <%-- 글정보 --%>
		<%-- 글목록 --%>
		<%
			if(pv.getList()!=null && pv.getList().size()>0){
				for(GuestbookVO vo : pv.getList()){
		%>
		<div style="border: 1px solid gray;padding: 5px; margin-bottom: 5px;'">
			<div style="background-color: silver;border: 1px solid gray;padding: 5px;">
				<%=vo.getName() %>님이 <%=vo.getIp() %>에서
				<%=vo.getRegDate() %>에 남긴 흔적
			<input type="button" value="수정" class="btn btn-outline-success btn-sm" 
				onclick="location.href='updateForm.jsp?idx=<%=vo.getIdx() %>&p=<%=pv.getCurrentPage() %>&s=<%=pv.getPageSize() %>&b=<%=pv.getBlockSize() %>'" />		
			<input type="button" value="삭제" class="btn btn-outline-success btn-sm" 
				onclick="location.href='deleteForm.jsp?idx=<%=vo.getIdx() %>&p=<%=pv.getCurrentPage() %>&s=<%=pv.getPageSize() %>&b=<%=pv.getBlockSize() %>'" />		
				
				
			</div>
			<%
				String content = vo.getContent();
				content = content.replaceAll("<", "&lt;");
				content = content.replaceAll("\n", "<br>");
				out.println(content);
			%>
		</div>
		<%
				}
			}else{
		%>
			<div style="border: 1px solid gray;padding: 5px; margin-bottom: 5px;text-align: center;">
				등록된 글이 없습니다.
			</div>
		<%		
			}
		%>
		<div class="pageList"><%=pv.getPageList() %></div> <%-- 페이지 정보 --%>
		
		<%-- 글쓰기 버튼을 달자 --%>
		<div class="pageInfo">
			<input type="button" value="새글쓰기" class="btn btn-outline-success btn-sm" 
				onclick="location.href='insertForm.jsp?p=<%=pv.getCurrentPage() %>&s=<%=pv.getPageSize() %>&b=<%=pv.getBlockSize() %>'" />		
		</div>
	</div>
</body>
</html>