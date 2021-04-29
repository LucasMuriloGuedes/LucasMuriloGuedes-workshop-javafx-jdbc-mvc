/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author Lucas Murilo
 */
public class DbException extends RuntimeException{
    public DbException(String msg){
        super(msg);
    }
    
}
