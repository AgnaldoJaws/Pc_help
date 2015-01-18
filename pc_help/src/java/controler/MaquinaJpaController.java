/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import controler.exceptions.NonexistentEntityException;
import controler.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Cliente;
import model.Maquina;

/**
 *
 * @author agnaldoa
 */
public class MaquinaJpaController implements Serializable {

    public MaquinaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maquina maquina) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente codCli = maquina.getCodCli();
            if (codCli != null) {
                codCli = em.getReference(codCli.getClass(), codCli.getCodCli());
                maquina.setCodCli(codCli);
            }
            em.persist(maquina);
            if (codCli != null) {
                codCli.getMaquinaCollection().add(maquina);
                codCli = em.merge(codCli);
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

    public void edit(Maquina maquina) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Maquina persistentMaquina = em.find(Maquina.class, maquina.getCodMaq());
            Cliente codCliOld = persistentMaquina.getCodCli();
            Cliente codCliNew = maquina.getCodCli();
            if (codCliNew != null) {
                codCliNew = em.getReference(codCliNew.getClass(), codCliNew.getCodCli());
                maquina.setCodCli(codCliNew);
            }
            maquina = em.merge(maquina);
            if (codCliOld != null && !codCliOld.equals(codCliNew)) {
                codCliOld.getMaquinaCollection().remove(maquina);
                codCliOld = em.merge(codCliOld);
            }
            if (codCliNew != null && !codCliNew.equals(codCliOld)) {
                codCliNew.getMaquinaCollection().add(maquina);
                codCliNew = em.merge(codCliNew);
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
                Integer id = maquina.getCodMaq();
                if (findMaquina(id) == null) {
                    throw new NonexistentEntityException("The maquina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Maquina maquina;
            try {
                maquina = em.getReference(Maquina.class, id);
                maquina.getCodMaq();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maquina with id " + id + " no longer exists.", enfe);
            }
            Cliente codCli = maquina.getCodCli();
            if (codCli != null) {
                codCli.getMaquinaCollection().remove(maquina);
                codCli = em.merge(codCli);
            }
            em.remove(maquina);
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

    public List<Maquina> findMaquinaEntities() {
        return findMaquinaEntities(true, -1, -1);
    }

    public List<Maquina> findMaquinaEntities(int maxResults, int firstResult) {
        return findMaquinaEntities(false, maxResults, firstResult);
    }

    private List<Maquina> findMaquinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maquina.class));
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

    public Maquina findMaquina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maquina.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaquinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maquina> rt = cq.from(Maquina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
