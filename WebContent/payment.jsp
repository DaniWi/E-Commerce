<%@page import="com.paypal.api.payments.PaymentExecution"%>
<%@page import="com.paypal.api.payments.Payment"%>
<%@page import="com.paypal.base.rest.OAuthTokenCredential"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%
	// paymentId=PAY-64016039VN168984KKZPPVNA & token=EC-849483546W131325A & PayerID=2TAZWQMQB595G
%>
<%
	
	final String CLIENT_ID = "AW9_pbpQgV9Vltcn_V4uM2D5A3PMmS-edajKYe4OWtTr0N0JAfNkS0qfOsCH0pwgKR-PlosjzgMb8Sh_";
	final String CLIENT_SECRET = "EJvbMEBxbntbx4DCYbsemiYSkNZC_DFPQqQjLDVzv9tGKTGutTanzJ09mIS5N5Me_3ER5UI_fcWuUl8j";
	
	Map<String, String> sdkConfig = new HashMap<String, String>();
	sdkConfig.put("mode", "sandbox");
	String accessToken = new OAuthTokenCredential(CLIENT_ID,CLIENT_SECRET, sdkConfig).getAccessToken();
	
	// Payment payment = Payment.get(request.getParameter("token"), request.getParameter("paymentId"));
	Payment payment = Payment.get(accessToken, request.getParameter("paymentId"));
	
	PaymentExecution paymentExecution = new PaymentExecution();
	paymentExecution.setPayerId(request.getParameter("PayerID"));
	
	// Payment newPayment = payment.execute(request.getParameter("token"), paymentExecution);
	Payment newPayment = payment.execute(accessToken, paymentExecution);
	 
%>
<!-- 
paymentID : <%= request.getParameter("paymentId") %>
token     : <%= request.getParameter("token") %>
payerID   : <%= request.getParameter("PayerID") %>
 -->