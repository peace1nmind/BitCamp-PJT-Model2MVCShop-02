<%-- ���Ż���ȸ ���� �� ȭ�� --%>
<%-- getPurchase.jsp include�Ұ� --%>

<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%
	System.out.println("\n:: updatePurchase.jsp");
	
	PurchaseVO purchaseVO = (PurchaseVO) request.getAttribute("purchaseVO");
	request.setAttribute("purchaseVO", purchaseVO);
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>������ ��������</title>
	</head>
	
	<body>
		
		<jsp:include page="/purchase/getPurchase.jsp"></jsp:include>
		
	</body>
</html>