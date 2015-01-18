/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.*;

/**
 *
 * @author agnaldoa
 */
@ManagedBean
public class ClienteDAO {

    public Cliente objCliente = new Cliente();
    
    public Cidade objCidade = new Cidade();
    
    public TipoPessoa objTp = new TipoPessoa();

    
     
    
    
    
    /**
     * @return the objCliente
     */
    public Cliente getObjCliente() {
        return objCliente;
    }

    /**
     * @param objCliente the objCliente to set
     */
    public void setObjCliente(Cliente objCliente) {
        this.objCliente = objCliente;
    }

    /**
     * @return the objCidade
     */
    public Cidade getObjCidade() {
        return objCidade;
    }

    /**
     * @param objCidade the objCidade to set
     */
    public void setObjCidade(Cidade objCidade) {
        this.objCidade = objCidade;
    }

    /**
     * @return the objTp
     */
    public TipoPessoa getObjTp() {
        return objTp;
    }

    /**
     * @param objTp the objTp to set
     */
    public void setObjTp(TipoPessoa objTp) {
        this.objTp = objTp;
    }

    
    private List<Cidade> L_cidade;

    public List<Cidade> getCidade() {
        if (this.L_cidade == null) {
            EntityManager em = JPAUtil.getEntityManager();
            Query q = em.createQuery("select a from Cidade a", Cidade.class);
            this.L_cidade = q.getResultList();
            em.close();

        }

        return L_cidade;

    }
    
    private List<TipoPessoa> L_tp;

    public List<TipoPessoa> getTipoPessoas() {
        if (this.L_tp == null) {
            EntityManager em = JPAUtil.getEntityManager();
            Query q = em.createQuery("select a from TipoPessoa a", TipoPessoa.class);
            this.L_tp= q.getResultList();
            em.close();

        }

        return L_tp;

    }
    
    
    
    public  void salvar (){
        
        
        System.out.println("-->"+objCliente.getNomeCliente());
      /*  System.out.println("-->"+objCliente.getIdCidade());
        System.out.println("-->"+objCliente.getIdTipoPessoa());
        
       /*objCidade.setIdCidade(Integer.parseInt("1"));
   
      objCliente.setIdCidade(objCidade);*/
       
    
        
        /*EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("pc_help2");

        EntityManager manager = JPAUtil.getEntityManager();

        manager.getTransaction().begin();
        manager.persist(cc);

        manager.getTransaction().commit();
        manager.close();

        factory.close();*/

        
    }

}
