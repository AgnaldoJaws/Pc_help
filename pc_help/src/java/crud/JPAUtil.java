package crud;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author agnaldoa
 */
public class JPAUtil {
    
    private static final EntityManagerFactory emf=
            Persistence.createEntityManagerFactory("pc_help2");
    
    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
    
    
