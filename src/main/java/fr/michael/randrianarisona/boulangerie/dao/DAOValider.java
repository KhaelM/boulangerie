/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.dao;

import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author miker
 */
public class DAOValider extends DAOBase {
    private static final String SQL_INSERT = "INSERT INTO fabrication (produit_fini_id, date_fabrication, quantite) VALUES (?, ?, ?)";
    
    public DAOValider(DAOFactory daoFactory) {
        super(daoFactory);
    }
    
    public void insererFabrication(ProduitFini produitFini, Date dateFabrication, float quantite) {
        Connection connection = null;
        ResultSet generatedKeys = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_INSERT, false, produitFini.getId(), dateFabrication, quantite);
            int rowsCreated = preparedStatement.executeUpdate();
            
            if(rowsCreated == 0) {
                throw new DAOException("Echec lors de la fabrication. Aucune ligne ajoutée à la table.");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(generatedKeys, preparedStatement, connection);
        }
    }
}
