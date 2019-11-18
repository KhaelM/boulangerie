package fr.michael.randrianarisona.boulangerie.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.michael.randrianarisona.boulangerie.model.AchatComposante;
import fr.michael.randrianarisona.boulangerie.model.BonDeCommande;
import fr.michael.randrianarisona.boulangerie.model.ComposanteSimulation;

/**
 * DAOBonDeCommande
 */
public class DAOBonDeCommande extends DAOBase {
    private static final String SQL_MOST_RECENT_FOURNISSEUR = "SELECT  fournisseur.nom as fournisseur_nom, fournisseur_id, prix_unitaire, quantite, date_achat, quantite_restante FROM achat_composante JOIN fournisseur ON fournisseur.id = achat_composante.fournisseur_id WHERE achat_composante.composante_id = ? ORDER BY date_achat DESC LIMIT 1";
    private static final String SQL_INSERT_INTO_BON_DE_COMMANDE = "INSERT INTO bon_de_commande (fournisseur_id, date_commande, etat) VALUES(?,?,?)";
    private static final String SQL_INSERT_INTO_BON_DE_COMMANDE_COMPOSANTE = "INSERT INTO bon_de_commande_composante (bon_de_commande_id, composante_id, prix_unitaire, quantite) VALUES (?,?,?,?)";

    public DAOBonDeCommande(DAOFactory daoFactory) {
        super(daoFactory);
    }

    public List<BonDeCommande> getAllBonsDeCommandes() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<BonDeCommande> bonsDeCommandes = new ArrayList<BonDeCommande>();
        

        return bonsDeCommandes;
    }

    private static AchatComposante map(ResultSet resultSet) throws SQLException {
        AchatComposante achat = new AchatComposante();

        achat.setDateAchat(resultSet.getDate("date_achat"));
        achat.setIdFournisseur(resultSet.getShort("fournisseur_id"));
        achat.setNomFournisseur(resultSet.getString("fournisseur_nom"));
        achat.setPrixUnitaire(resultSet.getFloat("prix_unitaire"));
        achat.setQuantite(resultSet.getFloat("quantite"));
        achat.setQuantiteRestante(resultSet.getFloat("quantite_restante"));

        return achat;
    }

    public void insererBonsDeCommande(List<BonDeCommande> bonsDeCommande, Date date_commande) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKey = null;

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);

            for (BonDeCommande bonDeCommande : bonsDeCommande) {
                preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_INSERT_INTO_BON_DE_COMMANDE, true, bonDeCommande.getIdFournisseur(), date_commande, 0);
                int rowAffected = preparedStatement.executeUpdate();
    
                generatedKey = preparedStatement.getGeneratedKeys();
                if(rowAffected == 0) {
                    throw new DAOException("Echec lors de la création d'un bon de commande. Aucune ligne ajoutée à la table.");
                }
                
                if(generatedKey.next()) {
                    long bonDeComnmandeId = generatedKey.getLong(1);
                        for (AchatComposante achat : bonDeCommande.getCommandes()) {
                            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_INSERT_INTO_BON_DE_COMMANDE_COMPOSANTE, false, bonDeComnmandeId, achat.getComposante().getId(), achat.getPrixUnitaire(), achat.getComposante().getQuantiteCommande()); 
                            preparedStatement.executeUpdate();       
                        }
                    connection.commit();
                } else {
                    throw new DAOException("Echec lors de la crétion d'un bon de commande. Aucun next trouvé.");
                }
            }


        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                throw new DAOException(ex);
            }
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(generatedKey, preparedStatement, connection);
        }
    }

    public AchatComposante getFournisseurLePlusRecent(ComposanteSimulation composante) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        AchatComposante achatComposante = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_MOST_RECENT_FOURNISSEUR, false, composante.getId());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                achatComposante = map(resultSet);
                achatComposante.setComposante(composante);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(resultSet, preparedStatement, connection);
        }
        
        return achatComposante;
    }
}