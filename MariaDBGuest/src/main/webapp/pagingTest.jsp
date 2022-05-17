<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="kr.human.guest.vo.PagingVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>페이징 테스트</title>
</head>
<body>
	<%
		int totalCount = 123; // 전체 개수로 가정
		PagingVO<String> pagingVO = new PagingVO<>(totalCount,currentPage,pageSize,blockSize); // 계산
		List<String> list = new ArrayList<>();
		for(int i=pagingVO.getStartNo();i<=pagingVO.getEndNo();i++){
			list.add(String.format("글이라고 가정하고 ... %04d",i));
		}
		pagingVO.setList(list); // 글 넣어주고
		
		out.println(pagingVO.getPageInfo() + "<br><hr>");
		for(String str : pagingVO.getList()){
			out.println(str + "<br>");
		}
		out.println("<br><hr>" + pagingVO.getPageList());
	%>
</body>
</html>