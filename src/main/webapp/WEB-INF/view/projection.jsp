<%@page import="fr.michael.randrianarisona.boulangerie.page.ProjectionPage"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.michael.randrianarisona.boulangerie.model.ProduitFini" %>
<% ProjectionPage pageModel = (ProjectionPage) request.getAttribute("projection"); %>
<% List<ProduitFini> produitsFini = pageModel.getProduitsFini(); %>
<form action="" method="post">
    <label for="produitFini">Produit à fabriquer</label>
    <select name="produitFini">
        <%  for(ProduitFini produit: produitsFini) { %>
            <option value="<%= produit.getNom() %>"><%= produit.getNom() %></option>
        <%
            } 
        %>
    </select> 
    <label for="date_fabrication">Date de fabrication</label>
    <input type="date" name="date_fabrication" />
    <label for="quantite">Quantité</label>
    <input type="text" name="quantite" value="<%= pageModel.getQuantiteVoulu() %>" />
    <button type="submit" name="action" value="simuler">Simuler</button>
    <button type="submit" name="action" value="enregistrer">Enregistrer</button>
</form>
