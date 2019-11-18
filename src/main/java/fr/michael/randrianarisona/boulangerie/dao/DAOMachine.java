/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.michael.randrianarisona.boulangerie.dao;

import fr.michael.randrianarisona.boulangerie.model.Machine;
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
public class DAOMachine extends DAOBase {
    private static final String SQL_SELECT_BY_PRODUIT_FINI_NOM = "SELECT * FROM machines_par_produit where produit_fini_nom = ?";
    
    public DAOMachine(DAOFactory daoFactory) {
        super(daoFactory);
    }
    
    private static Machine map(ResultSet resultSet) throws SQLException {
        Machine machine = new Machine();
        machine.setId(resultSet.getShort("machine_id"));
        machine.setNom(resultSet.getString("machine_nom"));
        machine.setCoutHoraire(resultSet.getFloat("machine_cout_horaire"));

        return machine;
    }
    
    public List<Machine> getMachines(String nomProduitFini) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Machine> machines = new ArrayList<Machine>();
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = DAOUtility.getInitialisedPreparedStatement(connection, SQL_SELECT_BY_PRODUIT_FINI_NOM, false, nomProduitFini);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                machines.add(map(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtility.closeQuietly(resultSet, preparedStatement, connection);
        }
        
        return machines;
    }
}
