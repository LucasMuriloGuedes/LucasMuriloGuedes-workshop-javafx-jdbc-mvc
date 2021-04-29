/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import DB.DB;
import DB.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Lucas Murilo
 */
public class DepartmentDaoJDBC implements DepartmentDao {
    
    Connection conn;
    
    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
             "INSERT INTO department " +
				"(Name) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1,department.getName());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    department.setId(id);
                    DB.closeResultSet(rs);
                } 
            }
            else{
                throw new DbException("Nao foi possivel inserir o departamento");
                
            }
           
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
            
        }
        finally{
            DB.closePreparedStatement(st);
            
        }
       
    }

    @Override
    public void update(Department department) {
         PreparedStatement st = null;
         try{
             st = conn.prepareStatement(
                     "UPDATE department " +
                     "SET Name = ? " +
                     "WHERE Id = ?");
             
             st.setInt(1, department.getId());
             st.setString(2, department.getName());     
         }
         catch(SQLException e){
             throw new DbException(e.getMessage());
         }
    }

    @Override
    public void deleteById(Integer id) {
       PreparedStatement st = null;
       
       try{
       st = conn.prepareStatement(
       "DELETE FROM department WHERE Id = ?");
       st.setInt(1, id);
       st.executeUpdate();
       }
       catch(SQLException e){
           throw new DbException(e.getMessage());
           
       }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
             "SELECT * FROM department WHERE Id = ?");
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            Department dep;
            
            if(rs.next()){
                dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                return dep;
                
            }
            return null;
            
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closePreparedStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.prepareStatement(
            "SELECT * FROM department ORDER BY Name");
            
            rs = st.executeQuery();
            List<Department> list = new ArrayList<>();
            while(rs.next()){
                Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                list.add(dep);
                
            }
            return list;
            
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closePreparedStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
}
