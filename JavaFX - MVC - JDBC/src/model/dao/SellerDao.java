/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Lucas Murilo
 */
public interface SellerDao {
    
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List <Seller> findAll();
    List<Seller> findByDepartament(Integer id);
    
}
