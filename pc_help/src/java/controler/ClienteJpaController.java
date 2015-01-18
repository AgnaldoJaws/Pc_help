/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import controler.exceptions.IllegalOrphanException;
import controler.exceptions.NonexistentEntityException;
import controler.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TipoPessoa;
import model.Cidade;
import model.Os;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Cliente;
import model.Maquina;

/**
 *
 * @author agnaldoa
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        if (cliente.getOsCollection() == null) {
            cliente.setOsCollection(new ArrayList<Os>());
        }
        if (cliente.getMaquinaCollection() == null) {
            cliente.setMaquinaCollection(new ArrayList<Maquina>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoPessoa idTipoPessoa = cliente.getIdTipoPessoa();
            if (idTipoPessoa != null) {
                idTipoPessoa = em.getReference(idTipoPessoa.getClass(), idTipoPessoa.getIdTipoPessoa());
                cliente.setIdTipoPessoa(idTipoPessoa);
            }
            Cidade idCidade = cliente.getIdCidade();
            if (idCidade != null) {
                idCidade = em.getReference(idCidade.getClass(), idCidade.getIdCidade());
                cliente.setIdCidade(idCidade);
            }
            Collection<Os> attachedOsCollection = new ArrayList<Os>();
            for (Os osCollectionOsToAttach : cliente.getOsCollection()) {
                osCollectionOsToAttach = em.getReference(osCollectionOsToAttach.getClass(), osCollectionOsToAttach.getCodOs());
                attachedOsCollection.add(osCollectionOsToAttach);
            }
            cliente.setOsCollection(attachedOsCollection);
            Collection<Maquina> attachedMaquinaCollection = new ArrayList<Maquina>();
            for (Maquina maquinaCollectionMaquinaToAttach : cliente.getMaquinaCollection()) {
                maquinaCollectionMaquinaToAttach = em.getReference(maquinaCollectionMaquinaToAttach.getClass(), maquinaCollectionMaquinaToAttach.getCodMaq());
                attachedMaquinaCollection.add(maquinaCollectionMaquinaToAttach);
            }
            cliente.setMaquinaCollection(attachedMaquinaCollection);
            em.persist(cliente);
            if (idTipoPessoa != null) {
                idTipoPessoa.getClienteCollection().add(cliente);
                idTipoPessoa = em.merge(idTipoPessoa);
            }
            if (idCidade != null) {
                idCidade.getClienteCollection().add(cliente);
                idCidade = em.merge(idCidade);
            }
            for (Os osCollectionOs : cliente.getOsCollection()) {
                Cliente oldCodCliOfOsCollectionOs = osCollectionOs.getCodCli();
                osCollectionOs.setCodCli(cliente);
                osCollectionOs = em.merge(osCollectionOs);
                if (oldCodCliOfOsCollectionOs != null) {
                    oldCodCliOfOsCollectionOs.getOsCollection().remove(osCollectionOs);
                    oldCodCliOfOsCollectionOs = em.merge(oldCodCliOfOsCollectionOs);
                }
            }
            for (Maquina maquinaCollectionMaquina : cliente.getMaquinaCollection()) {
                Cliente oldCodCliOfMaquinaCollectionMaquina = maquinaCollectionMaquina.getCodCli();
                maquinaCollectionMaquina.setCodCli(cliente);
                maquinaCollectionMaquina = em.merge(maquinaCollectionMaquina);
                if (oldCodCliOfMaquinaCollectionMaquina != null) {
                    oldCodCliOfMaquinaCollectionMaquina.getMaquinaCollection().remove(maquinaCollectionMaquina);
                    oldCodCliOfMaquinaCollectionMaquina = em.merge(oldCodCliOfMaquinaCollectionMaquina);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCodCli());
            TipoPessoa idTipoPessoaOld = persistentCliente.getIdTipoPessoa();
            TipoPessoa idTipoPessoaNew = cliente.getIdTipoPessoa();
            Cidade idCidadeOld = persistentCliente.getIdCidade();
            Cidade idCidadeNew = cliente.getIdCidade();
            Collection<Os> osCollectionOld = persistentCliente.getOsCollection();
            Collection<Os> osCollectionNew = cliente.getOsCollection();
            Collection<Maquina> maquinaCollectionOld = persistentCliente.getMaquinaCollection();
            Collection<Maquina> maquinaCollectionNew = cliente.getMaquinaCollection();
            List<String> illegalOrphanMessages = null;
            for (Os osCollectionOldOs : osCollectionOld) {
                if (!osCollectionNew.contains(osCollectionOldOs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Os " + osCollectionOldOs + " since its codCli field is not nullable.");
                }
            }
            for (Maquina maquinaCollectionOldMaquina : maquinaCollectionOld) {
                if (!maquinaCollectionNew.contains(maquinaCollectionOldMaquina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maquina " + maquinaCollectionOldMaquina + " since its codCli field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoPessoaNew != null) {
                idTipoPessoaNew = em.getReference(idTipoPessoaNew.getClass(), idTipoPessoaNew.getIdTipoPessoa());
                cliente.setIdTipoPessoa(idTipoPessoaNew);
            }
            if (idCidadeNew != null) {
                idCidadeNew = em.getReference(idCidadeNew.getClass(), idCidadeNew.getIdCidade());
                cliente.setIdCidade(idCidadeNew);
            }
            Collection<Os> attachedOsCollectionNew = new ArrayList<Os>();
            for (Os osCollectionNewOsToAttach : osCollectionNew) {
                osCollectionNewOsToAttach = em.getReference(osCollectionNewOsToAttach.getClass(), osCollectionNewOsToAttach.getCodOs());
                attachedOsCollectionNew.add(osCollectionNewOsToAttach);
            }
            osCollectionNew = attachedOsCollectionNew;
            cliente.setOsCollection(osCollectionNew);
            Collection<Maquina> attachedMaquinaCollectionNew = new ArrayList<Maquina>();
            for (Maquina maquinaCollectionNewMaquinaToAttach : maquinaCollectionNew) {
                maquinaCollectionNewMaquinaToAttach = em.getReference(maquinaCollectionNewMaquinaToAttach.getClass(), maquinaCollectionNewMaquinaToAttach.getCodMaq());
                attachedMaquinaCollectionNew.add(maquinaCollectionNewMaquinaToAttach);
            }
            maquinaCollectionNew = attachedMaquinaCollectionNew;
            cliente.setMaquinaCollection(maquinaCollectionNew);
            cliente = em.merge(cliente);
            if (idTipoPessoaOld != null && !idTipoPessoaOld.equals(idTipoPessoaNew)) {
                idTipoPessoaOld.getClienteCollection().remove(cliente);
                idTipoPessoaOld = em.merge(idTipoPessoaOld);
            }
            if (idTipoPessoaNew != null && !idTipoPessoaNew.equals(idTipoPessoaOld)) {
                idTipoPessoaNew.getClienteCollection().add(cliente);
                idTipoPessoaNew = em.merge(idTipoPessoaNew);
            }
            if (idCidadeOld != null && !idCidadeOld.equals(idCidadeNew)) {
                idCidadeOld.getClienteCollection().remove(cliente);
                idCidadeOld = em.merge(idCidadeOld);
            }
            if (idCidadeNew != null && !idCidadeNew.equals(idCidadeOld)) {
                idCidadeNew.getClienteCollection().add(cliente);
                idCidadeNew = em.merge(idCidadeNew);
            }
            for (Os osCollectionNewOs : osCollectionNew) {
                if (!osCollectionOld.contains(osCollectionNewOs)) {
                    Cliente oldCodCliOfOsCollectionNewOs = osCollectionNewOs.getCodCli();
                    osCollectionNewOs.setCodCli(cliente);
                    osCollectionNewOs = em.merge(osCollectionNewOs);
                    if (oldCodCliOfOsCollectionNewOs != null && !oldCodCliOfOsCollectionNewOs.equals(cliente)) {
                        oldCodCliOfOsCollectionNewOs.getOsCollection().remove(osCollectionNewOs);
                        oldCodCliOfOsCollectionNewOs = em.merge(oldCodCliOfOsCollectionNewOs);
                    }
                }
            }
            for (Maquina maquinaCollectionNewMaquina : maquinaCollectionNew) {
                if (!maquinaCollectionOld.contains(maquinaCollectionNewMaquina)) {
                    Cliente oldCodCliOfMaquinaCollectionNewMaquina = maquinaCollectionNewMaquina.getCodCli();
                    maquinaCollectionNewMaquina.setCodCli(cliente);
                    maquinaCollectionNewMaquina = em.merge(maquinaCollectionNewMaquina);
                    if (oldCodCliOfMaquinaCollectionNewMaquina != null && !oldCodCliOfMaquinaCollectionNewMaquina.equals(cliente)) {
                        oldCodCliOfMaquinaCollectionNewMaquina.getMaquinaCollection().remove(maquinaCollectionNewMaquina);
                        oldCodCliOfMaquinaCollectionNewMaquina = em.merge(oldCodCliOfMaquinaCollectionNewMaquina);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCodCli();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCodCli();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Os> osCollectionOrphanCheck = cliente.getOsCollection();
            for (Os osCollectionOrphanCheckOs : osCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Os " + osCollectionOrphanCheckOs + " in its osCollection field has a non-nullable codCli field.");
            }
            Collection<Maquina> maquinaCollectionOrphanCheck = cliente.getMaquinaCollection();
            for (Maquina maquinaCollectionOrphanCheckMaquina : maquinaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Maquina " + maquinaCollectionOrphanCheckMaquina + " in its maquinaCollection field has a non-nullable codCli field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoPessoa idTipoPessoa = cliente.getIdTipoPessoa();
            if (idTipoPessoa != null) {
                idTipoPessoa.getClienteCollection().remove(cliente);
                idTipoPessoa = em.merge(idTipoPessoa);
            }
            Cidade idCidade = cliente.getIdCidade();
            if (idCidade != null) {
                idCidade.getClienteCollection().remove(cliente);
                idCidade = em.merge(idCidade);
            }
            em.remove(cliente);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
