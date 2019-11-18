<%-- 
    Document   : index
    Created on : Oct 24, 2019, 11:34:59 AM
    Author     : miker
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel='stylesheet' href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
        <title><%= (String) request.getAttribute("title")%></title>
    </head>
    <body>
        <jsp:include page='<%= (String) request.getAttribute("contenu")%>' />
        
        <% List<String> erreurs = (List) request.getAttribute("erreur"); %>
        <% List<String> succes = (List) request.getAttribute("succes"); %>
        <%  if (erreurs != null && !erreurs.isEmpty()) {
                for(String erreur: erreurs) {
        %>
        <div style="color: red"><%= erreur %></div>
        <%
                }
            }
        %>
        <%  if (succes != null && !succes.isEmpty()) {
                for(String succ: succes) {
        %>
        <div style="color: green"><%= succ %></div>
        <%
                }
            }
        %>
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
