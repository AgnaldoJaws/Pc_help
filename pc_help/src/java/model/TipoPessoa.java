/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import crud.AbstractEntity;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author agnaldoa
 */
@Entity
@Table(name = "tipo_pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPessoa.findAll", query = "SELECT t FROM TipoPessoa t"),
    @NamedQuery(name = "TipoPessoa.findByIdTipoPessoa", query = "SELECT t FROM TipoPessoa t WHERE t.idTipoPessoa = :idTipoPessoa"),
    @NamedQuery(name = "TipoPessoa.findByTipoPessoa", query = "SELECT t FROM TipoPessoa t WHERE t.tipoPessoa = :tipoPessoa")})
public class TipoPessoa extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_pessoa")
    private Integer idTipoPessoa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPessoa")
    private Collection<Cliente> clienteCollection;

    public TipoPessoa() {
    }

    public TipoPessoa(Integer idTipoPessoa) {
        this.idTipoPessoa = idTipoPessoa;
    }

    public TipoPessoa(Integer idTipoPessoa, String tipoPessoa) {
        this.idTipoPessoa = idTipoPessoa;
        this.tipoPessoa = tipoPessoa;
    }

    public Integer getIdTipoPessoa() {
        return idTipoPessoa;
    }

    public void setIdTipoPessoa(Integer idTipoPessoa) {
        this.idTipoPessoa = idTipoPessoa;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPessoa != null ? idTipoPessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPessoa)) {
            return false;
        }
        TipoPessoa other = (TipoPessoa) object;
        if ((this.idTipoPessoa == null && other.idTipoPessoa != null) || (this.idTipoPessoa != null && !this.idTipoPessoa.equals(other.idTipoPessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoPessoa[ idTipoPessoa=" + idTipoPessoa + " ]";
    }
    
}
