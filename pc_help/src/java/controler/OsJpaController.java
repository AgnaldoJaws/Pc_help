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
import model.Status;
import model.Solucao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Os;

/**
 *
 * @author agnaldoa
 */
public class OsJpaController implements Serializable {

    public OsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Os os) throws RollbackFailureException, Exception {
        if (os.getSolucaoCollection() == null) {
            os.setSolucaoCollection(new ArrayList<Solucao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente codCli = os.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodCli());
                os.setCodCli(codCli);
            }
            Status idStatus = os.getIdStatus();
            if (idStatus != null) {
                idStatus = em.getReference(idStatus.getClass(), idStatus.getIdStatus());
                os.setIdStatus(idStatus);
            }
            Collection<Solucao> attachedSolucaoCollection = new ArrayList<Solucao>();
            for (Solucao solucaoCollectionSolucaoToAttach : os.getSolucaoCollection()) {
                solucaoCollectionSolucaoToAttach = em.getReference(solucaoCollectionSolucaoToAttach.getClass(), solucaoCollectionSolucaoToAttach.getCodSol());
                attachedSolucaoCollection.add(solucaoCollectionSolucaoToAttach);
            }
            os.setSolucaoCollection(attachedSolucaoCollection);
            em.persist(os);
            if (codCli != null) {
                codCli.getOsCollection().add(os);
                codCli = em.merge(codCli);
            }
            if (idStatus != null) {
                idStatus.getOsCollection().add(os);
                idStatus = em.merge(idStatus);
            }
            for (Solucao solucaoCollectionSolucao : os.getSolucaoCollection()) {
                Os oldCodOsOfSolucaoCollectionSolucao = solucaoCollectionSolucao.getCodOs();
                solucaoCollectionSolucao.setCodOs(os);
                solucaoCollectionSolucao = em.merge(solucaoCollectionSolucao);
                if (oldCodOsOfSolucaoCollectionSolucao != null) {
                    oldCodOsOfSolucaoCollectionSolucao.getSolucaoCollection().remove(solucaoCollectionSolucao);
                    oldCodOsOfSolucaoCollectionSolucao = em.merge(oldCodOsOfSolucaoCollectionSolucao);
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

    public void edit(Os os) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Os persistentOs = em.find(Os.class, os.getCodOs());
            Cliente codCliOld = persistentOs.getCodCli();
            Cliente codCliNew = os.getCodCli();
            Status idStatusOld = persistentOs.getIdStatus();
            Status idStatusNew = os.getIdStatus();
            Collection<Solucao> solucaoCollectionOld = persistentOs.getSolucaoCollection();
            Collection<Solucao> solucaoCollectionNew = os.getSolucaoCollection();
            List<String> illegalOrphanMessages = null;
            for (Solucao solucaoCollectionOldSolucao : solucaoCollectionOld) {
                if (!solucaoCollectionNew.contains(solucaoCollectionOldSolucao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solucao " + solucaoCollectionOldSolucao + " since its codOs field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodCli());
                os.setCodCli(codCliNew);
            }
            if (idStatusNew != null) {
                idStatusNew = em.getReference(idStatusNew.getClass(), idStatusNew.getIdStatus());
                os.setIdStatus(idStatusNew);
            }
            Collection<Solucao> attachedSolucaoCollectionNew = new ArrayList<Solucao>();
            for (Solucao solucaoCollectionNewSolucaoToAttach : solucaoCollectionNew) {
                solucaoCollectionNewSolucaoToAttach = em.getReference(solucaoCollectionNewSolucaoToAttach.getClass(), solucaoCollectionNewSolucaoToAttach.getCodSol());
                attachedSolucaoCollectionNew.add(solucaoCollectionNewSolucaoToAttach);
            }
            solucaoCollectionNew = attachedSolucaoCollectionNew;
            os.setSolucaoCollection(solucaoCollectionNew);
            os = em.merge(os);
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getOsCollection().remove(os);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getOsCollection().add(os);
                codCliNew = em.merge(codCliNew);
            }
            if (idStatusOld != null && !idStatusOld.equals(idStatusNew)) {
                idStatusOld.getOsCollection().remove(os);
                idStatusOld = em.merge(idStatusOld);
            }
            if (idStatusNew != null && !idStatusNew.equals(idStatusOld)) {
                idStatusNew.getOsCollection().add(os);
                idStatusNew = em.merge(idStatusNew);
            }
            for (Solucao solucaoCollectionNewSolucao : solucaoCollectionNew) {
                if (!solucaoCollectionOld.contains(solucaoCollectionNewSolucao)) {
                    Os oldCodOsOfSolucaoCollectionNewSolucao = solucaoCollectionNewSolucao.getCodOs();
                    solucaoCollectionNewSolucao.setCodOs(os);
                    solucaoCollectionNewSolucao = em.merge(solucaoCollectionNewSolucao);
                    if (oldCodOsOfSolucaoCollectionNewSolucao != null && !oldCodOsOfSolucaoCollectionNewSolucao.equals(os)) {
                        oldCodOsOfSolucaoCollectionNewSolucao.getSolucaoCollection().remove(solucaoCollectionNewSolucao);
                        oldCodOsOfSolucaoCollectionNewSolucao = em.merge(oldCodOsOfSolucaoCollectionNewSolucao);
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
                Integer id = os.getCodOs();
                if (findOs(id) == null) {
                    throw new NonexistentEntityException("The os with id " + id + " no longer exists.");
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
            Os os;
            try {
                os = em.getReference(Os.class, id);
                os.getCodOs();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The os with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solucao> solucaoCollectionOrphanCheck = os.getSolucaoCollection();
            for (Solucao solucaoCollectionOrphanCheckSolucao : solucaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Os (" + os + ") cannot be destroyed since the Solucao " + solucaoCollectionOrphanCheckSolucao + " in its solucaoCollection field has a non-nullable codOs field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente codCli = os.getCodCli();
            if (codCli != null) {
                codCli.getOsCollection().remove(os);
                codCli = em.merge(codCli);
            }
            Status idStatus = os.getIdStatus();
            if (idStatus != null) {
                idStatus.getOsCollection().remove(os);
                idStatus = em.merge(idStatus);
            }
            em.remove(os);
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

    public List<Os> findOsEntities() {
        return findOsEntities(true, -1, -1);
    }

    public List<Os> findOsEntities(int maxResults, int firstResult) {
        return findOsEntities(false, maxResults, firstResult);
    }

    private List<Os> findOsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Os.class));
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

    public Os findOs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Os.class, id);
        } finally {
            em.close();
        }
    }

    public int getOsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Os> rt = cq.from(Os.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
