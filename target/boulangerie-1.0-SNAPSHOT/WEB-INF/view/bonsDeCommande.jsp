<%@page import="java.util.List"%>
<%@page import="fr.michael.randrianarisona.boulangerie.page.BonsDeCommandePage"%>
<%@ page import="fr.michael.randrianarisona.boulangerie.model.BonDeCommande" %>
<%@ page import="fr.michael.randrianarisona.boulangerie.model.AchatComposante" %>
<%@page import="fr.michael.randrianarisona.utility.NumberFormatter"%>
<%@ page pageEncoding="UTF-8" %>
<% BonsDeCommandePage bons = (BonsDeCommandePage) request.getAttribute("page"); %>
<% for(BonDeCommande bonDeCommande: bons.getBonsDeCommande()) { %>
<fieldset style="border: solid 5px black; padding: 10px; margin-bottom: 10px;">
    <legend>Bon de commande: <%= bonDeCommande.getId() %></legend>
    <div>Fournisseur: <%= bonDeCommande.getNomFournisseur() %></div>
    <label for="">Date de commande</label>
    <input type="date" name="" id="" value="<%= bons.getDateCommande() %>">
    <%  for(AchatComposante achat: bonDeCommande.getCommandes()) { %>
    <hr>
    <fieldset>
        <legend><%= achat.getComposante().getNom() %></legend>
        <label for="">Quantite à commander</label>
        <input type="text" value='<%=  NumberFormatter.formatNumber(achat.getComposante().getQuantiteCommande(), "", ".", 2) %>'>
        <label for="">Prix Unitaire</label>
        <input type="text" value='<%=  NumberFormatter.formatNumber(achat.getPrixUnitaire(), "", ".", 2) %>'>
    </fieldset>
    <hr>
    <%  } %>
    <div><button>Valider</button><button>Livrer</button></div>
</fieldset>
<% } %>