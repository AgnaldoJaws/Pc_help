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
import model.Status;
import model.Pagamento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Solucao;

/**
 *
 * @author agnaldoa
 */
public class SolucaoJpaController implements Serializable {

    public SolucaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solucao solucao) throws RollbackFailureException, Exception {
        if (solucao.getPagamentoCollection() == null) {
            solucao.setPagamentoCollection(new ArrayList<Pagamento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Os codOs = solucao.getCodOs();
            if (codOs != null) {
                codOs = em.getReference(codOs.getClass(), codOs.getCodOs());
                solucao.setCodOs(codOs);
            }
            Status idStatus = solucao.getIdStatus();
            if (idStatus != null) {
                idStatus = em.getReference(idStatus.getClass(), idStatus.getIdStatus());
                solucao.setIdStatus(idStatus);
            }
            Collection<Pagamento> attachedPagamentoCollection = new ArrayList<Pagamento>();
            for (Pagamento pagamentoCollectionPagamentoToAttach : solucao.getPagamentoCollection()) {
                pagamentoCollectionPagamentoToAttach = em.getReference(pagamentoCollectionPagamentoToAttach.getClass(), pagamentoCollectionPagamentoToAttach.getCodPag());
                attachedPagamentoCollection.add(pagamentoCollectionPagamentoToAttach);
            }
            solucao.setPagamentoCollection(attachedPagamentoCollection);
            em.persist(solucao);
            if (codOs != null) {
                codOs.getSolucaoCollection().add(solucao);
                codOs = em.merge(codOs);
            }
            if (idStatus != null) {
                idStatus.getSolucaoCollection().add(solucao);
                idStatus = em.merge(idStatus);
            }
            for (Pagamento pagamentoCollectionPagamento : solucao.getPagamentoCollection()) {
                Solucao oldCodSolOfPagamentoCollectionPagamento = pagamentoCollectionPagamento.getCodSol();
                pagamentoCollectionPagamento.setCodSol(solucao);
                pagamentoCollectionPagamento = em.merge(pagamentoCollectionPagamento);
                if (oldCodSolOfPagamentoCollectionPagamento != null) {
                    oldCodSolOfPagamentoCollectionPagamento.getPagamentoCollection().remove(pagamentoCollectionPagamento);
                    oldCodSolOfPagamentoCollectionPagamento = em.merge(oldCodSolOfPagamentoCollectionPagamento);
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

    public void edit(Solucao solucao) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Solucao persistentSolucao = em.find(Solucao.class, solucao.getCodSol());
            Os codOsOld = persistentSolucao.getCodOs();
            Os codOsNew = solucao.getCodOs();
            Status idStatusOld = persistentSolucao.getIdStatus();
            Status idStatusNew = solucao.getIdStatus();
            Collection<Pagamento> pagamentoCollectionOld = persistentSolucao.getPagamentoCollection();
            Collection<Pagamento> pagamentoCollectionNew = solucao.getPagamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Pagamento pagamentoCollectionOldPagamento : pagamentoCollectionOld) {
                if (!pagamentoCollectionNew.contains(pagamentoCollectionOldPagamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagamento " + pagamentoCollectionOldPagamento + " since its codSol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codOsNew != null) {
                codOsNew = em.getReference(codOsNew.getClass(), codOsNew.getCodOs());
                solucao.setCodOs(codOsNew);
            }
            if (idStatusNew != null) {
                idStatusNew = em.getReference(idStatusNew.getClass(), idStatusNew.getIdStatus());
                solucao.setIdStatus(idStatusNew);
            }
            Collection<Pagamento> attachedPagamentoCollectionNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoCollectionNewPagamentoToAttach : pagamentoCollectionNew) {
                pagamentoCollectionNewPagamentoToAttach = em.getReference(pagamentoCollectionNewPagamentoToAttach.getClass(), pagamentoCollectionNewPagamentoToAttach.getCodPag());
                attachedPagamentoCollectionNew.add(pagamentoCollectionNewPagamentoToAttach);
            }
            pagamentoCollectionNew = attachedPagamentoCollectionNew;
            solucao.setPagamentoCollection(pagamentoCollectionNew);
            solucao = em.merge(solucao);
            if (codOsOld != null && !codOsOld.equals(codOsNew)) {
                codOsOld.getSolucaoCollection().remove(solucao);
                codOsOld = em.merge(codOsOld);
            }
            if (codOsNew != null && !codOsNew.equals(codOsOld)) {
                codOsNew.getSolucaoCollection().add(solucao);
                codOsNew = em.merge(codOsNew);
            }
            if (idStatusOld != null && !idStatusOld.equals(idStatusNew)) {
                idStatusOld.getSolucaoCollection().remove(solucao);
                idStatusOld = em.merge(idStatusOld);
            }
            if (idStatusNew != null && !idStatusNew.equals(idStatusOld)) {
                idStatusNew.getSolucaoCollection().add(solucao);
                idStatusNew = em.merge(idStatusNew);
            }
            for (Pagamento pagamentoCollectionNewPagamento : pagamentoCollectionNew) {
                if (!pagamentoCollectionOld.contains(pagamentoCollectionNewPagamento)) {
                    Solucao oldCodSolOfPagamentoCollectionNewPagamento = pagamentoCollectionNewPagamento.getCodSol();
                    pagamentoCollectionNewPagamento.setCodSol(solucao);
                    pagamentoCollectionNewPagamento = em.merge(pagamentoCollectionNewPagamento);
                    if (oldCodSolOfPagamentoCollectionNewPagamento != null && !oldCodSolOfPagamentoCollectionNewPagamento.equals(solucao)) {
                        oldCodSolOfPagamentoCollectionNewPagamento.getPagamentoCollection().remove(pagamentoCollectionNewPagamento);
                        oldCodSolOfPagamentoCollectionNewPagamento = em.merge(oldCodSolOfPagamentoCollectionNewPagamento);
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
                Integer id = solucao.getCodSol();
                if (findSolucao(id) == null) {
                    throw new NonexistentEntityException("The solucao with id " + id + " no longer exists.");
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
            Solucao solucao;
            try {
                solucao = em.getReference(Solucao.class, id);
                solucao.getCodSol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solucao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pagamento> pagamentoCollectionOrphanCheck = solucao.getPagamentoCollection();
            for (Pagamento pagamentoCollectionOrphanCheckPagamento : pagamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solucao (" + solucao + ") cannot be destroyed since the Pagamento " + pagamentoCollectionOrphanCheckPagamento + " in its pagamentoCollection field has a non-nullable codSol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Os codOs = solucao.getCodOs();
            if (codOs != null) {
                codOs.getSolucaoCollection().remove(solucao);
                codOs = em.merge(codOs);
            }
            Status idStatus = solucao.getIdStatus();
            if (idStatus != null) {
                idStatus.getSolucaoCollection().remove(solucao);
                idStatus = em.merge(idStatus);
            }
            em.remove(solucao);
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

    public List<Solucao> findSolucaoEntities() {
        return findSolucaoEntities(true, -1, -1);
    }

    public List<Solucao> findSolucaoEntities(int maxResults, int firstResult) {
        return findSolucaoEntities(false, maxResults, firstResult);
    }

    private List<Solucao> findSolucaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solucao.class));
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

    public Solucao findSolucao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solucao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolucaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solucao> rt = cq.from(Solucao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
