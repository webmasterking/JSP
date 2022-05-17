<%@page import="kr.human.guest.service.GuestbookService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<%
	if(!request.getMethod().equals("POST")){
		response.sendRedirect("index.jsp");
		return;
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="vo" class="kr.human.guest.vo.GuestbookVO"/>
	<jsp:setProperty property="*" name="vo"/>
	<jsp:setProperty property="ip" name="vo" value="<%=request.getRemoteAddr() %>"/>
	<%
		switch(vo.getMode()){
		case "insert":
			// 서비스를 호출하여 저장을 수행하고
			int count = GuestbookService.getInstance().insert(vo);
			if(count==0){
				out.println("<script>");
				out.println("alert('저장 실패!!!');");
				out.println("location.href='insertForm.jsp?p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}else{
				out.println("<script>");
				out.println("alert('저장 성공!!!');");
				out.println("location.href='index.jsp?p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}
			break;
		case "update":
			// 서비스를 호출하여 수정을 수행하고
			int count2 = GuestbookService.getInstance().update(vo);
			if(count2==0){
				out.println("<script>");
				out.println("alert('수정 실패!!!');");
				out.println("location.href='updateForm.jsp?idx="+idx+"&p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}else{
				out.println("<script>");
				out.println("alert('수정 성공!!!');");
				out.println("location.href='index.jsp?p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}
			break;
		case "delete":
			// 서비스를 호출하여 삭제를 수행하고
			int count3 = GuestbookService.getInstance().delete(vo);
			if(count3==0){
				out.println("<script>");
				out.println("alert('삭제 실패!!!');");
				out.println("location.href='deleteForm.jsp?idx="+idx+"&p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}else{
				out.println("<script>");
				out.println("alert('삭제 성공!!!');");
				out.println("location.href='index.jsp?p="+currentPage+"&s="+pageSize+"&b="+blockSize+"'");
				out.println("</script>");
			}

			break;
		}
		// 목록으로 간다.
		// response.sendRedirect("index.jsp?p=" + currentPage + "&s=" + pageSize + "&b=" + blockSize);		
	%> 
</body>
</html>