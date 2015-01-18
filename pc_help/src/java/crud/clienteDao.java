/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Cliente;
import model.TipoPessoa;
import model.*;

/**
 *
 * @author agnaldoa
 */
@ManagedBean
public class clienteDao {

    public static void main(String arg[]) {

        Cliente aa = new Cliente();
        Cidade cc = new Cidade();
        TipoPessoa tt = new TipoPessoa();
        
        
  
        
        tt.setIdTipoPessoa(Integer.parseInt("1"));
        cc.setIdCidade(Integer.parseInt("1"));
        
       cc.setNomeCidade("agnaldo");
       
       System.out.println("-->"+cc.getNomeCidade());
        
        

       /* aa.setBairro("agnalldojjjjjjjjjjjjjjjjjjjjj");
        aa.setCelular("agnaldo");
        aa.setCep("agnaldo");
        aa.setCnpj("agnaldo");
        aa.setComplemento("agnaldo");
        aa.setCpf("agnaldo");
        aa.setEmail("agnaldo");
        aa.setEndereco("agnaldo");
        aa.setEstado("agnaldo");
        aa.setIdCidade(cc);
        aa.setIdTipoPessoa(tt);
        aa.setIe("agaldo");
        aa.setLogradouro("agnaldo");
        aa.setNomeCliente("agnaldo");
        aa.setNomeEmpresa("agnaldo");
        aa.setRazaoSocial("agnaldoooooooooo");
        aa.setRg("agnaldo");
        aa.setSkype("agnaldo");
        aa.setTelefone("agnaldo");

        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("pc_help2");

        EntityManager manager = JPAUtil.getEntityManager();

        manager.getTransaction().begin();
        manager.persist(aa);

        manager.getTransaction().commit();
        manager.close();

        factory.close();*/

    }

}
