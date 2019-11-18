<%@page import="fr.michael.randrianarisona.boulangerie.model.Machine"%>
<%@page import="fr.michael.randrianarisona.boulangerie.model.ProduitFini"%>
<%@page import="fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation"%>
<%@page import="fr.michael.randrianarisona.boulangerie.model.AchatComposante"%>
<%@page import="fr.michael.randrianarisona.utility.NumberFormatter"%>
<%@page import="fr.michael.randrianarisona.boulangerie.page.AnalysePage"%>
<%@ page pageEncoding="UTF-8" %>
<% AnalysePage analyse = (AnalysePage) request.getAttribute("analyse"); %>
<% ProduitFini produitFini = analyse.getProduitFini();%>

<!-- Modal -->
<div class="modal fade" id="detailCoutHoraire" tabindex="-1" role="dialog" aria-labelledby="detailCoutHoraireTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="detailCoutHoraireTitle">Détails coût horaires</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <table border="1" style="border-collapse: collapse">
              <div><span style="font-style: italic">Machines</span>: <span style="font-weight: bold"><%= NumberFormatter.formatNumber(analyse.getChargesMachines(), " ", ",", 2) %> Ariary</span></div>
              <thead>
                  <tr>
                      <th>Nom</th>
                      <th>Cout horaire(Ariary/heure)</th>
                      <th>Cout de production(Ariary)</th>
                  </tr>
              </thead>
              <tbody>
                  <% for(Machine machine: analyse.getMachines()) { %>
                  <tr>
                      <td><%= machine.getNom() %></td>
                      <td style="text-align: right"><%=  NumberFormatter.formatNumber(machine.getCoutHoraire(), " ", ",", 2) %></td>
                      <td style="text-align: right"><%= NumberFormatter.formatNumber(analyse.getCoutDeProductionMachine(machine, analyse.getProduitFini().getTempsDeFabrication()), " ", ",", 2) %></td>
                  </tr>
                  <% } %>
              </tbody>
          </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
      </div>
    </div>
  </div>
</div>

<% for (ComposanteSimulation composante : analyse.getComposantes()) {%>
  <div class="modal fade" id="detail<%= composante.getNom() %>" tabindex="-1" role="dialog" aria-labelledby="detail<%= composante.getNom() %>Title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="detail<%= composante.getNom() %>Title">Détails <%= composante.getNom() %></h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div>Quantité requise: <%= NumberFormatter.formatNumber(composante.getQuantiteRequise(), " ", ",", 2)  %> <%= composante.getUniteDeMesure() %>(s)</div>
            <table border="1" style="border-collapse: collapse">
                <thead>
                    <tr>
                        <th>Fournisseur</th>
                        <th>Date d'achat</th>
                        <th>Quantité en stock(<%= composante.getUniteDeMesure() %>)</th>
                        <th>Quantite à utiliser(<%= composante.getUniteDeMesure() %>)</th>
                        <th>Prix unitaire(Ariary/<%= composante.getUniteDeMesure() %>)</th>
                        <th>Cout d'achat(Ariary)</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(AchatComposante achat: composante.getAchats()) { %>
                    <tr>
                        <td><%= achat.getNomFournisseur() %></td>
                        <td><%=  achat.getDateAchat() %></td>
                        <td style="text-align: right"><%= NumberFormatter.formatNumber(achat.getQuantiteRestante(), " ", ",", 2)  %></td>
                        <td style="text-align: right"><%= NumberFormatter.formatNumber(achat.getQuantiteUtilise(), " ", ",", 2) %></td>
                        <td style="text-align: right"><%= NumberFormatter.formatNumber(achat.getPrixUnitaire(), " ", ",", 2) %></td>
                        <td style="text-align: right"><%= NumberFormatter.formatNumber(achat.getCoutAchat(), " ", ",", 2) %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
        </div>
      </div>
    </div>
  </div>
<% } %>

<div>Produit à réaliser: <%= produitFini.getNom()%></div>
<div>Temps de fabrication: <%= NumberFormatter.formatNumber(produitFini.getTempsDeFabrication(), " ", ",", 2)%> heures</div>
<div>Quantité à produire: <%= NumberFormatter.formatNumber(analyse.getQuantite(), " ", ",", 2)%></div>
<div>Date de fabrication: <%= analyse.getDateFabrication() != null ? analyse.getDateFabrication() : "Non renseignée"  %></div>
<table border="1" style="border-collapse: collapse">
    <thead>
        <tr>
            <th>Composante</th>
            <th>Quantité par <%= request.getParameter("produitFini")%></th>
            <th>Montant (Ariary)</th>
        </tr>
    </thead>
    <tbody>
        <% for (ComposanteSimulation composante : analyse.getComposantes()) {%>
        <tr style="background-color: <%= composante.getCouleurDeFond()%>;color: white">
            <td><%= composante.getNom()%></td>
            <td style="text-align: right"><%= NumberFormatter.formatNumber(composante.getQuantiteUnitaire(), " ", ",", 2) + composante.getAbreviationUniteDeMesure()%></td>
            <td style="text-align: right"><a style="color: white" href="#" data-toggle="modal" data-target="#detail<%= composante.getNom() %>"><%= NumberFormatter.formatNumber(composante.getMontantParComposante(), " ", ",", 2)%></a></td>
        </tr>
        <% }%>
    </tbody>
</table>
<div><span style="text-decoration: underline">Prix unitaire:</span> <span style="font-weight: bold"><%= NumberFormatter.formatNumber(analyse.getMontantTotalComposantes(), " ", ",", 2) %> Ariary</span></div>
<div><span style="text-decoration: underline">Prix total:</span> <span style="font-weight: bold"><%= NumberFormatter.formatNumber(analyse.getMontantTotal(), " ", ",", 2) %> Ariary</span></div>
<div><span style="text-decoration: underline">Coût horaires:</span> <a href="#" data-toggle="modal" data-target="#detailCoutHoraire" style="font-weight: bold"><%= NumberFormatter.formatNumber(analyse.getCoutHoraires(), " ", ",", 2) %> Ariary</a></div>
<div><span style="text-decoration: underline">Prix de fabrication:</span><span style="font-weight: bold"><%= NumberFormatter.formatNumber(analyse.getCoutDeFabrication(), " ", ",", 2) %> Ariary</span></div>
<form method="post">
    <input type="hidden" name="produitFini" value="<%= request.getParameter("produitFini") %>">
    <input type="hidden" name="quantite" value="<%= request.getParameter("quantite") %>">
    <input type="hidden" name="date_fabrication" value="<%= request.getParameter("date_fabrication") %>">
    <button type="submit" name="action" value="valider_fabrication">Valider fabrication</button>
</form>