<%-- ���Ÿ�� ȭ�� --%>
<%-- listPurchase.do --%>
<%@page import="com.model2.mvc.service.Paging"%>
<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@page import="com.model2.mvc.service.user.vo.UserVO"%>
<%@page import="com.model2.mvc.service.purchase.TranCodeMapper"%>
<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@page import="java.util.List"%>
<%@page import="com.model2.mvc.common.SearchVO"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%
	System.out.println("\n:: listPurchase.jsp");
	
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
	SearchVO searchVO = (SearchVO) request.getAttribute("searchVO");
	List<PurchaseVO> purchaseList = null;
	int total = 0;
	
	if (map != null) {
		total = (Integer) map.get("count");
		purchaseList = (List<PurchaseVO>) map.get("list");
	}
	
	int currentPage = searchVO.getPage();
	int totalPage = 0;
	
	if (total > 0) {
		totalPage = (int) Math.ceil(total*1.0 / searchVO.getPageUnit()); 
	}
	
	Map<String, String> tranCodeMap = TranCodeMapper.getInstance().getMap();
	
	Paging paging = (Paging) request.getAttribute("paging");
	paging.calculatePage(totalPage, currentPage);
	
	request.setAttribute("searchVO", searchVO);
	request.setAttribute("paging", paging);
	request.setAttribute("historyMap", (Map<String, Object>) request.getAttribute("historyMap"));
	
%>
    
<!DOCTYPE html>

<html>

	<head>
	
		<title>���Ÿ�� ��ȸ</title>
	
		<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
		<script type="text/javascript">
			function fncGetUserList() {
				document.detailForm.submit();
			}
			
		</script>
		
	</head>
	
	<body bgcolor="#ffffff" text="#000000">
	
		<div style="width: 98%; margin-left: 10px;">
		
			<form name="detailForm" action="/listPurchase.do" method="post">
			
				<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
					<tr>
						<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
						<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="93%" class="ct_ttl01">
										<%= ((UserVO) session.getAttribute("user")).getUserId() %>���� ���Ÿ�� ��ȸ
									</td>
								</tr>
							</table>
						</td>
						<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
					</tr>
				</table>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
				
					<tr>
						<td colspan="11">��ü <%= total %> �Ǽ�, ���� <%= currentPage %> ������</td>
					</tr>
					<tr>
						<td class="ct_list_b" width="50">No</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="100">��ǰ��</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="100">�޴»��</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">��ȭ��ȣ</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">��������</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">�����Ȳ</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">��������</td>
					</tr>
					<tr>
						<td colspan="11" bgcolor="808285" height="1"></td>
					</tr>
					
					<%
						int no = purchaseList.size();
					
						for (PurchaseVO purchaseVO : purchaseList) {
							ProductVO productVO = purchaseVO.getPurchaseProd();
					%>
						<tr class="ct_list_pop">
							<td align="center">
								<%= no-- %>
							</td>
							
							<td></td>
							<%-- ��ǰ�� --%>
							<td align="left">
								<a href="/getProduct.do?prodNo=<%= productVO.getProdNo() %>">
									<%= productVO.getProdName() %>
								</a>
							</td>
							
							<td></td>
							
							<%-- �޴»�� --%>
							<td align="left">
								<%= purchaseVO.getReceiverName() %>
							</td>
							
							<td></td>
							
							<%-- ��ȭ��ȣ --%>
							<td align="left">
								<%= purchaseVO.getReceiverPhone() %>
							</td>
							
							<td></td>
							
							<%-- �������� --%>
							<td align="left">
								<%= purchaseVO.getOrderDate() %>
							</td>
							
							<td></td>
							
							<%-- �����Ȳ --%>
							<td align="left">
								<%= tranCodeMap.get(purchaseVO.getTranCode().trim()) %>
							</td>
							
							<td></td>
							
							<%-- ��������(���Ȯ�ι�ư) --%>
							<td align="left">
							
						<%  	int tranCode = Integer.parseInt(purchaseVO.getTranCode().trim());
								
								if (tranCode == 3) { %>
						
									<a href="/updateTranCode.do?tranNo=<%= purchaseVO.getTranNo() %>
																&tranCode=3
																&page=<%= currentPage %>">
										���ǵ���
									</a>
								
							<%-- �������� : ��ۿϷ����̸� ����� �����ϰԲ� **4=��ۿϷ� --%>
						<%		} else if (tranCode > 1 && tranCode < 4) { %>
						
									<a href="/getPurchase.do?tranNo=<%= purchaseVO.getTranNo() %>">
										������� Ȯ��
									</a>

						<%		} %>
						
							</td>
						</tr>
						
					<%	} %>
					
					<%	if (total==0) { %>
						<tr class="ct_list_pop">
							<table>
								<tr>
									<td></td>
									<td align="center">
										<h3>�����̷��� �����ϴ�.</h3>
									</td>
								</tr>
							</table>
						</tr>
					<%	} %>

					
				</table>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
					
					<tr>
						<td align="center">
						
					<%	if (paging.isLeft()) { %>
					
							<a href="/listPurchase.do?page=1">
								<span>��</span>
							</a>
							
							&nbsp;
							
							<a href="/listPurchase.do?page=<%= paging.getStart()-1 %>">
								<span>����</span>
							</a>
							
					<%	} %>
					
							&nbsp;&nbsp;
						
					<%	for (int i=paging.getStart(); i<=paging.getEnd(); i++) { %>
					
							<a href="/listPurchase.do?page=<%= i %>" 
							<%= (currentPage==i)? "style='font-weight: bold; font-size: 15px'" : ""%>>
								<%= i %>
							</a> 
							
					<%	} %>
							
							&nbsp;&nbsp;
							
					<%	if (paging.isRight()) { %>
							
							<a href="/listPurchase.do?page=<%= paging.getEnd()+1 %>">
								<span>����</span>
							</a>
							
							&nbsp;
							
							<a href="/listPurchase.do?page=<%= totalPage %>">
								<span>��</span>
							</a>
							
					<%	} %>
					
						</td>
					</tr>
					
				</table>
			
			<!--  ������ Navigator �� -->
			</form>
		
		</div>
		
		<br><br>
		
		<%	System.out.println("\tinclude �߻�: listPurchaseHistory.jsp"); %>
		<jsp:include page="listPurchaseHistory.jsp"></jsp:include>
		<%--<%@ include file="listPurchaseHistory.jsp" %>--%>
		
		
		
	</body>
</html>