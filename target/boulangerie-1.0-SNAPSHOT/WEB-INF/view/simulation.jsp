<%@page import="fr.michael.randrianarisona.boulangerie.model.ProduitFini"%>
<%@page import="fr.michael.randrianarisona.utility.NumberFormatter"%>
<%@page import="fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation"%>
<%@page import="fr.michael.randrianarisona.boulangerie.page.SimulationPage"%>
<%@ page pageEncoding="UTF-8" %>
<% SimulationPage simulation = (SimulationPage) request.getAttribute("simulation"); %>
<% ProduitFini produitFini = simulation.getProduitFini(); %>
<div>Produit à réaliser: <%= produitFini.getNom() %></div>
<div>Temps de fabrication: <%= NumberFormatter.formatNumber(produitFini.getTempsDeFabrication(), " ", ",", 2) %> heures</div>
<div>Quantité à produire: <%= NumberFormatter.formatNumber(simulation.getQuantiteVoulu(), " ", ",", 2)  %></div>
<div>Date de fabrication: <%= simulation.getDateFabrication() != null ? simulation.getDateFabrication() : "Non renseignée"  %></div>
<table border="1" style="border-collapse: collapse">
    <thead>
        <tr>
            <th rowspan="2">Composante</th>
            <th colspan="2">Quantité</th>
        </tr>
        <tr>
            <th>Disponible</th>
            <th>Requise</th>
        </tr>
    </thead>
    <tbody>
        <% for(ComposanteSimulation composante : simulation.getComposantes()) { %>
            <tr style="background-color: <%= composante.getCouleurDeFond() %>;color: white">
                <td><%= composante.getNom() %></td>
                <td style="text-align: right"><%= NumberFormatter.formatNumber(composante.getQuantiteDisponible(), " ", ",", 2) + composante.getAbreviationUniteDeMesure() %></td>
                <td style="text-align: right"><%= NumberFormatter.formatNumber(composante.getQuantiteRequise(), " ", ",", 2) + composante.getAbreviationUniteDeMesure() %></td>
            </tr>
        <% } %>
    </tbody>
</table>
<form method="post">
    <input type="hidden" name="produitFini" value="<%= request.getParameter("produitFini") %>">
    <input type="hidden" name="quantite" value="<%= request.getParameter("quantite") %>">
    <input type="hidden" name="date_fabrication" value="<%= request.getParameter("date_fabrication") %>">
    <button type="submit" name="action" value="possible">Possible ?</button>
    <button type="submit" name="action" value="enregistrer">Enregistrer</button>
    <button type="submit" name="action" value="generer">Commander composantes insuffisantes</button>
</form>