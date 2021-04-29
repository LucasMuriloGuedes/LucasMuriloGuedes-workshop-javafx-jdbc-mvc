/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import DB.DB;
import DB.DbException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Lucas Murilo
 */
public class SellerDaoJDBC implements SellerDao{
    
    Connection conn;
    
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                "INSERT INTO seller "
                +"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                +"VALUES "
                +"(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartament().getId());
            
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);                 
                }
                DB.closeResultSet(rs);
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ?");
            
           st.setString(1, seller.getName());
           st.setString(2, seller.getEmail());
           st.setDate(3, new Date(seller.getBirthDate().getTime()));
           st.setDouble(4, seller.getBaseSalary());
           st.setInt(5, seller.getDepartament().getId());
           st.setInt(6, seller.getId());
           st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());  
        }
        finally{
            DB.closePreparedStatement(st);
        }
        
        
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
            "DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());            
        }
        finally{
            DB.closePreparedStatement(st);  
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");
            
            st.setInt(1, id);
            rs = st.executeQuery();
            if(st.execute()){
                Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
                Seller seller = instatiationSeller(rs, dep);
                return seller;
            }
            return null;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name ");
            
           rs = st.executeQuery();
           List<Seller> list = new ArrayList<>();
           Map<Integer, Department> map = new HashMap<>();
           
           while(rs.next()){
               Department dep = map.get(rs.getInt("DepartamentId"));
               if(dep == null){
                   dep = new Department(rs.getInt("DepartamentId"), rs.getString("DepName"));
                   map.put(rs.getInt("DepartamentId"), dep);
               }
               Seller seller = instatiationSeller(rs, dep);
               list.add(seller);              
           }
           return null;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }   
    }

    @Override
    public List<Seller> findByDepartament(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name ");
                st.setInt(1, id);
                rs = st.executeQuery();  
                List<Seller> list = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();
                while(rs.next()){
                    Department dep = map.get(rs.getInt("DepartamentId"));
                    if(dep == null){
                        dep = new Department(rs.getInt("DepartamentId"), rs.getString("DepName"));
                        map.put(rs.getInt("DepartamentId"), dep);    
                    }
                    Seller seller = instatiationSeller(rs, dep);
                    list.add(seller);
                }
                return list;
                
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }    
    }

    private Seller instatiationSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setDepartament(dep);
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        return seller;
        
    }
    
}
