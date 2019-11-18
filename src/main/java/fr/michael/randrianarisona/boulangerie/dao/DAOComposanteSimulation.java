/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.dao;

import fr.michael.randrianarisona.boulangerie.model.AchatComposante;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;
import fr.michael.randrianarisona.boulangerie.model.exception.NegatifException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miker
 */
public class DAOComposanteSimulation extends DAOBase {

    private static final String SQL_SELECT_BY_PRODUIT_FINI = "SELECT * FROM recette WHERE produit_fini = ?";
    private static final String SQL_SELECT_ACHAT_COMPOSANTE_BY_COMPOSANTE_ID = "SELECT fournisseur.nom as fournisseur_nom, fournisseur_id, prix_unitaire, quantite, date_achat, quantite_restante FROM achat_composante JOIN fournisseur ON fournisseur.id = achat_composante.fournisseur_id WHERE composante_id = ? AND quantite_restante > 0 ORDER BY date_achat ASC";

    public DAOComposanteSimulation(DAOFactory daoFactory) {
        super(daoFactory);
    }

    private static ComposanteSimulation map(ResultSet resultSet, float quantiteProduitFiniVoulu) throws SQLException, NegatifException {
        long id = resultSet.getLong("composante_id");
        String nom = resultSet.getString("composante");
        float quantiteDisponible = resultSet.getFloat("quantite_disponible");
        float quantiteUnitaire = resultSet.getFloat("quantite_unitaire");
        String uniteDeMesure = resultSet.getString("unite_de_mesure");
        String abreviationUniteDeMesure = resultSet.getString("abreviation_unite_de_mesure");
        ComposanteSimulation composanteSimulation = new ComposanteSimulation(id, nom, quantiteDisponible, quantiteUnitaire, quantiteProduitFiniVoulu, uniteDeMesure, abreviationUniteDeMesure);

        return composanteSimulation;
    }

    public List<ComposanteSimulation> getComposantes(String produitFini, float quantiteProduitFiniVoulu) throws NegatifException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ComposanteSimulation> composantes = new ArrayList<ComposanteSimulation>();
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_SELECT_BY_PRODUIT_FINI, false, produitFini);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ComposanteSimulation composante = map(resultSet, quantiteProduitFiniVoulu);
                float quantiteRequise = composante.getQuantiteRequise();

                preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_SELECT_ACHAT_COMPOSANTE_BY_COMPOSANTE_ID, false, composante.getId());
                ResultSet temp = preparedStatement.executeQuery();
                List<AchatComposante> achats = new ArrayList<AchatComposante>();
    
                while (temp.next()) {
                    AchatComposante achat = new AchatComposante();
                    achat.setDateAchat(temp.getDate("date_achat"));
                    achat.setIdFournisseur(temp.getShort("fournisseur_id"));
                    achat.setNomFournisseur(temp.getString("fournisseur_nom"));
                    achat.setPrixUnitaire(temp.getFloat("prix_unitaire"));
                    achat.setQuantite(temp.getFloat("quantite"));
                    achat.setQuantiteRestante(temp.getFloat("quantite_restante"));
                    achat.setQuantiteUtilise(quantiteRequise);
                    quantiteRequise -= achat.getQuantiteUtilise();
                    achats.add(achat);
                    if(quantiteRequise <= 0) {
                        break;
                    }
                }
                composante.setAchats(achats);
                temp.close();

                composantes.add(composante);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(resultSet, preparedStatement, connection);
        }

        return composantes;
    }
}
