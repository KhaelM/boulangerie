/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.dao;

import fr.michael.randrianarisona.boulangerie.model.ProduitFini;
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
public class DAOProduitFini extends DAOBase {
    private static final String SQL_SELECT_ALL = "SELECT * FROM produit_fini";
    private static final String SQL_SELECT_BY_NOM = "SELECT * FROM produit_fini where nom = ?";

    public DAOProduitFini(DAOFactory daoFactory) {
        super(daoFactory);
    }

    private static ProduitFini map(ResultSet resultSet) throws SQLException {
        ProduitFini produitFini = new ProduitFini();
        produitFini.setId(resultSet.getShort("produit_fini_id"));
        produitFini.setNom(resultSet.getString("nom"));
        produitFini.setTempsDeFabrication(resultSet.getFloat("temps_de_fabrication"));

        return produitFini;
    }
    
    public List<ProduitFini> getAllProduitFini() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ProduitFini> allProduitFini = new ArrayList<ProduitFini>();
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_SELECT_ALL , false);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                allProduitFini.add(map(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(resultSet, preparedStatement, connection);
        }
        
        return allProduitFini;
    }
    
    public ProduitFini getProduitFini(String nomProduit) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ProduitFini produit = null;
        ResultSet resultSet = null;
        
        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_SELECT_BY_NOM, false, nomProduit);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                produit = map(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(resultSet, preparedStatement, connection);
        }
        
        return produit;
    }
}
