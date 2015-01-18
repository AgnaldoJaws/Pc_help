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
@Table(name = "SOLUCAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solucao.findAll", query = "SELECT s FROM Solucao s"),
    @NamedQuery(name = "Solucao.findByCodSol", query = "SELECT s FROM Solucao s WHERE s.codSol = :codSol"),
    @NamedQuery(name = "Solucao.findByDataBertura", query = "SELECT s FROM Solucao s WHERE s.dataBertura = :dataBertura"),
    @NamedQuery(name = "Solucao.findBySolucao", query = "SELECT s FROM Solucao s WHERE s.solucao = :solucao"),
    @NamedQuery(name = "Solucao.findByPecaTrocada", query = "SELECT s FROM Solucao s WHERE s.pecaTrocada = :pecaTrocada"),
    @NamedQuery(name = "Solucao.findByModelo", query = "SELECT s FROM Solucao s WHERE s.modelo = :modelo"),
    @NamedQuery(name = "Solucao.findByMarca", query = "SELECT s FROM Solucao s WHERE s.marca = :marca"),
    @NamedQuery(name = "Solucao.findByRam", query = "SELECT s FROM Solucao s WHERE s.ram = :ram"),
    @NamedQuery(name = "Solucao.findByHd", query = "SELECT s FROM Solucao s WHERE s.hd = :hd"),
    @NamedQuery(name = "Solucao.findByDataGarantia", query = "SELECT s FROM Solucao s WHERE s.dataGarantia = :dataGarantia"),
    @NamedQuery(name = "Solucao.findByTempGarantia", query = "SELECT s FROM Solucao s WHERE s.tempGarantia = :tempGarantia")})
public class Solucao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_sol")
    private Integer codSol;
    @Column(name = "data_bertura")
    @Temporal(TemporalType.DATE)
    private Date dataBertura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "solucao")
    private String solucao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "peca_trocada")
    private String pecaTrocada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ram")
    private String ram;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "hd")
    private String hd;
    @Column(name = "data_garantia")
    @Temporal(TemporalType.DATE)
    private Date dataGarantia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "temp_garantia")
    private String tempGarantia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codSol")
    private Collection<Pagamento> pagamentoCollection;
    @JoinColumn(name = "cod_os", referencedColumnName = "cod_os")
    @ManyToOne(optional = false)
    private Os codOs;
    @JoinColumn(name = "id_status", referencedColumnName = "id_status")
    @ManyToOne(optional = false)
    private Status idStatus;

    public Solucao() {
    }

    public Solucao(Integer codSol) {
        this.codSol = codSol;
    }

    public Solucao(Integer codSol, String solucao, String pecaTrocada, String modelo, String marca, String ram, String hd, String tempGarantia) {
        this.codSol = codSol;
        this.solucao = solucao;
        this.pecaTrocada = pecaTrocada;
        this.modelo = modelo;
        this.marca = marca;
        this.ram = ram;
        this.hd = hd;
        this.tempGarantia = tempGarantia;
    }

    public Integer getCodSol() {
        return codSol;
    }

    public void setCodSol(Integer codSol) {
        this.codSol = codSol;
    }

    public Date getDataBertura() {
        return dataBertura;
    }

    public void setDataBertura(Date dataBertura) {
        this.dataBertura = dataBertura;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getPecaTrocada() {
        return pecaTrocada;
    }

    public void setPecaTrocada(String pecaTrocada) {
        this.pecaTrocada = pecaTrocada;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public Date getDataGarantia() {
        return dataGarantia;
    }

    public void setDataGarantia(Date dataGarantia) {
        this.dataGarantia = dataGarantia;
    }

    public String getTempGarantia() {
        return tempGarantia;
    }

    public void setTempGarantia(String tempGarantia) {
        this.tempGarantia = tempGarantia;
    }

    @XmlTransient
    public Collection<Pagamento> getPagamentoCollection() {
        return pagamentoCollection;
    }

    public void setPagamentoCollection(Collection<Pagamento> pagamentoCollection) {
        this.pagamentoCollection = pagamentoCollection;
    }

    public Os getCodOs() {
        return codOs;
    }

    public void setCodOs(Os codOs) {
        this.codOs = codOs;
    }

    public Status getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Status idStatus) {
        this.idStatus = idStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSol != null ? codSol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solucao)) {
            return false;
        }
        Solucao other = (Solucao) object;
        if ((this.codSol == null && other.codSol != null) || (this.codSol != null && !this.codSol.equals(other.codSol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Solucao[ codSol=" + codSol + " ]";
    }
    
}
