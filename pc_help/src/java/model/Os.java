/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author agnaldoa
 */
@Entity
@Table(name = "OS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Os.findAll", query = "SELECT o FROM Os o"),
    @NamedQuery(name = "Os.findByCodOs", query = "SELECT o FROM Os o WHERE o.codOs = :codOs"),
    @NamedQuery(name = "Os.findByDataAbertura", query = "SELECT o FROM Os o WHERE o.dataAbertura = :dataAbertura"),
    @NamedQuery(name = "Os.findByProblema", query = "SELECT o FROM Os o WHERE o.problema = :problema"),
    @NamedQuery(name = "Os.findByObs", query = "SELECT o FROM Os o WHERE o.obs = :obs")})
public class Os implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_os")
    private Integer codOs;
    @Column(name = "data_abertura")
    @Temporal(TemporalType.DATE)
    private Date dataAbertura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "problema")
    private String problema;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "obs")
    private String obs;
    @JoinColumn(name = "cod_cli", referencedColumnName = "cod_cli")
    @ManyToOne(optional = false)
    private Cliente codCli;
    @JoinColumn(name = "id_status", referencedColumnName = "id_status")
    @ManyToOne(optional = false)
    private Status idStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codOs")
    private Collection<Solucao> solucaoCollection;

    public Os() {
    }

    public Os(Integer codOs) {
        this.codOs = codOs;
    }

    public Os(Integer codOs, String problema, String obs) {
        this.codOs = codOs;
        this.problema = problema;
        this.obs = obs;
    }

    public Integer getCodOs() {
        return codOs;
    }

    public void setCodOs(Integer codOs) {
        this.codOs = codOs;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Cliente getCodCli() {
        return codCli;
    }

    public void setCodCli(Cliente codCli) {
        this.codCli = codCli;
    }

    public Status getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Status idStatus) {
        this.idStatus = idStatus;
    }

    @XmlTransient
    public Collection<Solucao> getSolucaoCollection() {
        return solucaoCollection;
    }

    public void setSolucaoCollection(Collection<Solucao> solucaoCollection) {
        this.solucaoCollection = solucaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOs != null ? codOs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Os)) {
            return false;
        }
        Os other = (Os) object;
        if ((this.codOs == null && other.codOs != null) || (this.codOs != null && !this.codOs.equals(other.codOs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Os[ codOs=" + codOs + " ]";
    }
    
}
