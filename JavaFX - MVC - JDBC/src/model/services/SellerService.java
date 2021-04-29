/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.services;

import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

/**
 *
 * @author Lucas Murilo
 */
public class SellerService {
    
    private SellerDao dao = DaoFactory.createSellerDao();
    
    public List<Seller> findAll(){
        return dao.findAll();     
    }
    
    public void saveOrUpdate(Seller dep){
        if(dep.getId() == null){
            dao.insert(dep);
        }else{
            dao.update(dep);
        }     
    }
    
    public void remove(Seller dep){
        dao.deleteById(dep.getId());
    }
    
}
