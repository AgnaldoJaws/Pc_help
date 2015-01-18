/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Cidade;
import model.Cliente;
import model.Status;

/**
 *
 * @author agnaldoa
 */

@ManagedBean
public class testeDAO {
    
    public String horario;
    
    private Status aa = new Status();
    
    
    public String getHorario() {
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    return "Atualizado em " + sdf.format(new Date());
  }
        
    
    
    
    
    public  void inserir (Status oo){
        
       
       
       EntityManagerFactory factory = Persistence.createEntityManagerFactory("pc_help");
       
        
    
         EntityManager manager = JPAUtil.getEntityManager();

         manager.getTransaction().begin();
         manager.persist(aa);
         manager.getTransaction().commit();
         manager.close();

         factory.close();
        
        
        
    }

    /**
     * @return the aa
     */
    public Status getAa() {
        return aa;
    }

    /**
     * @param aa the aa to set
     */
    public void setAa(Status aa) {
        this.aa = aa;
    }
        
    private List<Status> s1;
     public List<Status> getStatus(){
         
         if(this.s1 == null){
         EntityManager em = JPAUtil.getEntityManager();
         Query q = em.createQuery("select a from Status a",Status.class);
         
         this.s1 = q.getResultList();
         em.close();
         }
         return s1;
    
    
    
    }
    
}
    
    
 
       
       
        
       
    
    

