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
import model.Os;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Solucao;
import model.Status;

/**
 *
 * @author agnaldoa
 */
public class StatusJpaController implements Serializable {

    public StatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Status status) throws RollbackFailureException, Exception {
        if (status.getOsCollection() == null) {
            status.setOsCollection(new ArrayList<Os>());
        }
        if (status.getSolucaoCollection() == null) {
            status.setSolucaoCollection(new ArrayList<Solucao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Os> attachedOsCollection = new ArrayList<Os>();
            for (Os osCollectionOsToAttach : status.getOsCollection()) {
                osCollectionOsToAttach = em.getReference(osCollectionOsToAttach.getClass(), osCollectionOsToAttach.getCodOs());
                attachedOsCollection.add(osCollectionOsToAttach);
            }
            status.setOsCollection(attachedOsCollection);
            Collection<Solucao> attachedSolucaoCollection = new ArrayList<Solucao>();
            for (Solucao solucaoCollectionSolucaoToAttach : status.getSolucaoCollection()) {
                solucaoCollectionSolucaoToAttach = em.getReference(solucaoCollectionSolucaoToAttach.getClass(), solucaoCollectionSolucaoToAttach.getCodSol());
                attachedSolucaoCollection.add(solucaoCollectionSolucaoToAttach);
            }
            status.setSolucaoCollection(attachedSolucaoCollection);
            em.persist(status);
            for (Os osCollectionOs : status.getOsCollection()) {
                Status oldIdStatusOfOsCollectionOs = osCollectionOs.getIdStatus();
                osCollectionOs.setIdStatus(status);
                osCollectionOs = em.merge(osCollectionOs);
                if (oldIdStatusOfOsCollectionOs != null) {
                    oldIdStatusOfOsCollectionOs.getOsCollection().remove(osCollectionOs);
                    oldIdStatusOfOsCollectionOs = em.merge(oldIdStatusOfOsCollectionOs);
                }
            }
            for (Solucao solucaoCollectionSolucao : status.getSolucaoCollection()) {
                Status oldIdStatusOfSolucaoCollectionSolucao = solucaoCollectionSolucao.getIdStatus();
                solucaoCollectionSolucao.setIdStatus(status);
                solucaoCollectionSolucao = em.merge(solucaoCollectionSolucao);
                if (oldIdStatusOfSolucaoCollectionSolucao != null) {
                    oldIdStatusOfSolucaoCollectionSolucao.getSolucaoCollection().remove(solucaoCollectionSolucao);
                    oldIdStatusOfSolucaoCollectionSolucao = em.merge(oldIdStatusOfSolucaoCollectionSolucao);
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

    public void edit(Status status) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status persistentStatus = em.find(Status.class, status.getIdStatus());
            Collection<Os> osCollectionOld = persistentStatus.getOsCollection();
            Collection<Os> osCollectionNew = status.getOsCollection();
            Collection<Solucao> solucaoCollectionOld = persistentStatus.getSolucaoCollection();
            Collection<Solucao> solucaoCollectionNew = status.getSolucaoCollection();
            List<String> illegalOrphanMessages = null;
            for (Os osCollectionOldOs : osCollectionOld) {
                if (!osCollectionNew.contains(osCollectionOldOs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Os " + osCollectionOldOs + " since its idStatus field is not nullable.");
                }
            }
            for (Solucao solucaoCollectionOldSolucao : solucaoCollectionOld) {
                if (!solucaoCollectionNew.contains(solucaoCollectionOldSolucao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solucao " + solucaoCollectionOldSolucao + " since its idStatus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Os> attachedOsCollectionNew = new ArrayList<Os>();
            for (Os osCollectionNewOsToAttach : osCollectionNew) {
                osCollectionNewOsToAttach = em.getReference(osCollectionNewOsToAttach.getClass(), osCollectionNewOsToAttach.getCodOs());
                attachedOsCollectionNew.add(osCollectionNewOsToAttach);
            }
            osCollectionNew = attachedOsCollectionNew;
            status.setOsCollection(osCollectionNew);
            Collection<Solucao> attachedSolucaoCollectionNew = new ArrayList<Solucao>();
            for (Solucao solucaoCollectionNewSolucaoToAttach : solucaoCollectionNew) {
                solucaoCollectionNewSolucaoToAttach = em.getReference(solucaoCollectionNewSolucaoToAttach.getClass(), solucaoCollectionNewSolucaoToAttach.getCodSol());
                attachedSolucaoCollectionNew.add(solucaoCollectionNewSolucaoToAttach);
            }
            solucaoCollectionNew = attachedSolucaoCollectionNew;
            status.setSolucaoCollection(solucaoCollectionNew);
            status = em.merge(status);
            for (Os osCollectionNewOs : osCollectionNew) {
                if (!osCollectionOld.contains(osCollectionNewOs)) {
                    Status oldIdStatusOfOsCollectionNewOs = osCollectionNewOs.getIdStatus();
                    osCollectionNewOs.setIdStatus(status);
                    osCollectionNewOs = em.merge(osCollectionNewOs);
                    if (oldIdStatusOfOsCollectionNewOs != null && !oldIdStatusOfOsCollectionNewOs.equals(status)) {
                        oldIdStatusOfOsCollectionNewOs.getOsCollection().remove(osCollectionNewOs);
                        oldIdStatusOfOsCollectionNewOs = em.merge(oldIdStatusOfOsCollectionNewOs);
                    }
                }
            }
            for (Solucao solucaoCollectionNewSolucao : solucaoCollectionNew) {
                if (!solucaoCollectionOld.contains(solucaoCollectionNewSolucao)) {
                    Status oldIdStatusOfSolucaoCollectionNewSolucao = solucaoCollectionNewSolucao.getIdStatus();
                    solucaoCollectionNewSolucao.setIdStatus(status);
                    solucaoCollectionNewSolucao = em.merge(solucaoCollectionNewSolucao);
                    if (oldIdStatusOfSolucaoCollectionNewSolucao != null && !oldIdStatusOfSolucaoCollectionNewSolucao.equals(status)) {
                        oldIdStatusOfSolucaoCollectionNewSolucao.getSolucaoCollection().remove(solucaoCollectionNewSolucao);
                        oldIdStatusOfSolucaoCollectionNewSolucao = em.merge(oldIdStatusOfSolucaoCollectionNewSolucao);
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
                Integer id = status.getIdStatus();
                if (findStatus(id) == null) {
                    throw new NonexistentEntityException("The status with id " + id + " no longer exists.");
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
            Status status;
            try {
                status = em.getReference(Status.class, id);
                status.getIdStatus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The status with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Os> osCollectionOrphanCheck = status.getOsCollection();
            for (Os osCollectionOrphanCheckOs : osCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Os " + osCollectionOrphanCheckOs + " in its osCollection field has a non-nullable idStatus field.");
            }
            Collection<Solucao> solucaoCollectionOrphanCheck = status.getSolucaoCollection();
            for (Solucao solucaoCollectionOrphanCheckSolucao : solucaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Solucao " + solucaoCollectionOrphanCheckSolucao + " in its solucaoCollection field has a non-nullable idStatus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(status);
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

    public List<Status> findStatusEntities() {
        return findStatusEntities(true, -1, -1);
    }

    public List<Status> findStatusEntities(int maxResults, int firstResult) {
        return findStatusEntities(false, maxResults, firstResult);
    }

    private List<Status> findStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Status.class));
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

    public Status findStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Status.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Status> rt = cq.from(Status.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
