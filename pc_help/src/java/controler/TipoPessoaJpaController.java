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
import model.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.TipoPessoa;

/**
 *
 * @author agnaldoa
 */
public class TipoPessoaJpaController implements Serializable {

    public TipoPessoaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPessoa tipoPessoa) throws RollbackFailureException, Exception {
        if (tipoPessoa.getClienteCollection() == null) {
            tipoPessoa.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : tipoPessoa.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getCodCli());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            tipoPessoa.setClienteCollection(attachedClienteCollection);
            em.persist(tipoPessoa);
            for (Cliente clienteCollectionCliente : tipoPessoa.getClienteCollection()) {
                TipoPessoa oldIdTipoPessoaOfClienteCollectionCliente = clienteCollectionCliente.getIdTipoPessoa();
                clienteCollectionCliente.setIdTipoPessoa(tipoPessoa);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldIdTipoPessoaOfClienteCollectionCliente != null) {
                    oldIdTipoPessoaOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldIdTipoPessoaOfClienteCollectionCliente = em.merge(oldIdTipoPessoaOfClienteCollectionCliente);
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

    public void edit(TipoPessoa tipoPessoa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoPessoa persistentTipoPessoa = em.find(TipoPessoa.class, tipoPessoa.getIdTipoPessoa());
            Collection<Cliente> clienteCollectionOld = persistentTipoPessoa.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = tipoPessoa.getClienteCollection();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteCollectionOldCliente + " since its idTipoPessoa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getCodCli());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            tipoPessoa.setClienteCollection(clienteCollectionNew);
            tipoPessoa = em.merge(tipoPessoa);
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    TipoPessoa oldIdTipoPessoaOfClienteCollectionNewCliente = clienteCollectionNewCliente.getIdTipoPessoa();
                    clienteCollectionNewCliente.setIdTipoPessoa(tipoPessoa);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldIdTipoPessoaOfClienteCollectionNewCliente != null && !oldIdTipoPessoaOfClienteCollectionNewCliente.equals(tipoPessoa)) {
                        oldIdTipoPessoaOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldIdTipoPessoaOfClienteCollectionNewCliente = em.merge(oldIdTipoPessoaOfClienteCollectionNewCliente);
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
                Integer id = tipoPessoa.getIdTipoPessoa();
                if (findTipoPessoa(id) == null) {
                    throw new NonexistentEntityException("The tipoPessoa with id " + id + " no longer exists.");
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
            TipoPessoa tipoPessoa;
            try {
                tipoPessoa = em.getReference(TipoPessoa.class, id);
                tipoPessoa.getIdTipoPessoa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cliente> clienteCollectionOrphanCheck = tipoPessoa.getClienteCollection();
            for (Cliente clienteCollectionOrphanCheckCliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPessoa (" + tipoPessoa + ") cannot be destroyed since the Cliente " + clienteCollectionOrphanCheckCliente + " in its clienteCollection field has a non-nullable idTipoPessoa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPessoa);
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

    public List<TipoPessoa> findTipoPessoaEntities() {
        return findTipoPessoaEntities(true, -1, -1);
    }

    public List<TipoPessoa> findTipoPessoaEntities(int maxResults, int firstResult) {
        return findTipoPessoaEntities(false, maxResults, firstResult);
    }

    private List<TipoPessoa> findTipoPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPessoa.class));
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

    public TipoPessoa findTipoPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPessoa> rt = cq.from(TipoPessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
