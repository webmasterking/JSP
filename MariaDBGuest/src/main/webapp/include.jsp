<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 한글 깨짐 방지
	request.setCharacterEncoding("UTF-8");

	// 3개의 값을 받는 코드를 여기에 쓰자!!!
	int currentPage = 1;
	try{
		currentPage = Integer.parseInt(request.getParameter("p"));
	}catch(Exception e){
		;
	}

	int pageSize = 5;
	try{
		pageSize = Integer.parseInt(request.getParameter("s"));
	}catch(Exception e){
		;
	}

	int blockSize = 5;
	try{
		blockSize = Integer.parseInt(request.getParameter("b"));
	}catch(Exception e){
		;
	}
	
	int idx = 0;
	try{
		idx = Integer.parseInt(request.getParameter("idx"));
	}catch(Exception e){
		;
	}
%>
