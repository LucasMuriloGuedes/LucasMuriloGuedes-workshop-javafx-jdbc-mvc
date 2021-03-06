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
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author Lucas Murilo
 */
public class DepartmentService {
    
    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    
    public List<Department> findAll(){
        return dao.findAll();     
    }
    
    public void saveOrUpdate(Department dep){
        if(dep.getId() == null){
            dao.insert(dep);
        }else{
            dao.update(dep);
        }     
    }
    
    public void remove(Department dep){
        dao.deleteById(dep.getId());
    }
    
}
