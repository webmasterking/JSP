<%@page import="kr.human.guest.service.GuestbookService"%>
<%@page import="kr.human.guest.vo.GuestbookVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<%
	// 수정할 글을 가져온다.
	GuestbookVO vo = GuestbookService.getInstance().selectByIdx(idx);
	// 수정할 글이 없다면
	if(vo==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정하기</title>
<link href="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/jquery/3.6.0/jquery.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.servletContext.contextPath }/webjars/bootstrap/5.1.3/js/bootstrap.min.js" ></script>
<style type="text/css">
	table{ width: 800px; margin: auto;}
	.title{font-size: 18pt; padding: 5px; text-align: center; }
</style>
<script type="text/javascript">
	$(function(){
		$("#password").focus();
	});
	function formCheck(){
		var value = $("#password").val();
		if(value!=null && value.trim().length==0){
			alert('비밀번호는 반드시 입력해야 합니다.');
			$("#password").val("");
			$("#password").focus();
			return false;
		}
		var value = $("#content").val();
		if(value!=null && value.trim().length==0){
			alert('내용은 반드시 입력해야 합니다.');
			$("#content").val("");
			$("#content").focus();
			return false;
		}
	}
</script>
</head>
<body>
	<form action="updateOk.jsp" method="post" onsubmit="return formCheck();">
		<table>
			<tr>
				<td colspan="4" class="title"> 허접한 방명록 수정하기</td>
			</tr>
			<tr>
				<td align="right">이름 : </td>
				<td>
					<input type="text" name="name" readonly="readonly" value="<%=vo.getName() %>" size="20" />
					<input type="hidden" name="idx" value="<%=vo.getIdx() %>" />
					<input type="hidden" name="p" value="<%=currentPage %>" />
					<input type="hidden" name="s" value="<%=pageSize %>" />
					<input type="hidden" name="b" value="<%=blockSize %>" />
					<input type="hidden" name="mode" value="update" />
				</td>
				<td align="right">비밀번호 : </td>
				<td>
					<input type="password" name="password" id="password" required="required" size="20" />
				</td>
			</tr>
			<tr>
				<td align="right" valign="top">내용 : </td>
				<td colspan="3">
					<textarea rows="10" cols="81" name="content" id="content" required="required"><%=vo.getContent() %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right" style="padding-right: 120px;">
					<input type="submit" value="수정하기" class="btn btn-outline-success btn-sm"/>		
					<input type="button" value="돌아가기" class="btn btn-outline-success btn-sm" 
						onclick="location.href='index.jsp?p=<%=currentPage %>&s=<%=pageSize %>&b=<%=blockSize%>'" />		
				</td>
			</tr>
		</table>
	</form>
</body>
</html>