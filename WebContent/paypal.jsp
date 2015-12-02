<%@page import="com.paypal.api.payments.RedirectUrls"%>
<%@page import="com.paypal.api.payments.Payment"%>
<%@page import="com.paypal.api.payments.Payer"%>
<%@page import="com.paypal.api.payments.Transaction"%>
<%@page import="com.paypal.api.payments.Amount"%>
<%@page import="com.paypal.base.rest.APIContext"%>
<%@page import="com.paypal.base.rest.OAuthTokenCredential"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%
	final String CLIENT_ID = "AW9_pbpQgV9Vltcn_V4uM2D5A3PMmS-edajKYe4OWtTr0N0JAfNkS0qfOsCH0pwgKR-PlosjzgMb8Sh_";
	final String CLIENT_SECRET = "EJvbMEBxbntbx4DCYbsemiYSkNZC_DFPQqQjLDVzv9tGKTGutTanzJ09mIS5N5Me_3ER5UI_fcWuUl8j";
	
	Map<String, String> sdkConfig = new HashMap<String, String>();
	sdkConfig.put("mode", "sandbox");
	
	String accessToken = new OAuthTokenCredential(CLIENT_ID,CLIENT_SECRET, sdkConfig).getAccessToken();
	APIContext apiContext = new APIContext(accessToken);
	apiContext.setConfigurationMap(sdkConfig);
	
	Amount amount = new Amount();
	amount.setCurrency("EUR");
	amount.setTotal(request.getParameter("euro"));
	
	Transaction transaction = new Transaction();
	transaction.setDescription("creating a payment");
	transaction.setAmount(amount);
	
	List<Transaction> transactions = new ArrayList<Transaction>();
	transactions.add(transaction);
	
	Payer payer = new Payer();
	payer.setPaymentMethod("paypal");
	
	Payment payment = new Payment();
	payment.setIntent("sale");
	payment.setPayer(payer);
	payment.setTransactions(transactions);
	RedirectUrls redirectUrls = new RedirectUrls();
	redirectUrls.setCancelUrl("http://localhost:8080/E-Commerce/payment.jsp");
	redirectUrls.setReturnUrl("http://localhost:8080/E-Commerce/payment.jsp");
	payment.setRedirectUrls(redirectUrls);
	
	Payment createdPayment = payment.create(apiContext);
	session.setAttribute("executeURL", createdPayment.getLinks().get(2).getHref());
	response.sendRedirect(createdPayment.getLinks().get(1).getHref());
%>
