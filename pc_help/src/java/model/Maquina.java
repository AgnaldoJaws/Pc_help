/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author agnaldoa
 */
@Entity
@Table(name = "maquina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maquina.findAll", query = "SELECT m FROM Maquina m"),
    @NamedQuery(name = "Maquina.findByCodMaq", query = "SELECT m FROM Maquina m WHERE m.codMaq = :codMaq"),
    @NamedQuery(name = "Maquina.findByTipo", query = "SELECT m FROM Maquina m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Maquina.findByModelo", query = "SELECT m FROM Maquina m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "Maquina.findByMarca", query = "SELECT m FROM Maquina m WHERE m.marca = :marca"),
    @NamedQuery(name = "Maquina.findByCor", query = "SELECT m FROM Maquina m WHERE m.cor = :cor"),
    @NamedQuery(name = "Maquina.findByProcessador", query = "SELECT m FROM Maquina m WHERE m.processador = :processador"),
    @NamedQuery(name = "Maquina.findByTela", query = "SELECT m FROM Maquina m WHERE m.tela = :tela"),
    @NamedQuery(name = "Maquina.findByRam", query = "SELECT m FROM Maquina m WHERE m.ram = :ram"),
    @NamedQuery(name = "Maquina.findByHd", query = "SELECT m FROM Maquina m WHERE m.hd = :hd"),
    @NamedQuery(name = "Maquina.findByDataCadastro", query = "SELECT m FROM Maquina m WHERE m.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "Maquina.findByObs", query = "SELECT m FROM Maquina m WHERE m.obs = :obs")})
public class Maquina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_maq")
    private Integer codMaq;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipo")
    private String tipo;
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
    @Column(name = "cor")
    private String cor;
    @Size(max = 50)
    @Column(name = "processador")
    private String processador;
    @Size(max = 50)
    @Column(name = "tela")
    private String tela;
    @Size(max = 50)
    @Column(name = "ram")
    private String ram;
    @Size(max = 50)
    @Column(name = "hd")
    private String hd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "data_cadastro")
    private String dataCadastro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "obs")
    private String obs;
    @JoinColumn(name = "cod_cli", referencedColumnName = "cod_cli")
    @ManyToOne(optional = false)
    private Cliente codCli;

    public Maquina() {
    }

    public Maquina(Integer codMaq) {
        this.codMaq = codMaq;
    }

    public Maquina(Integer codMaq, String tipo, String modelo, String marca, String cor, String dataCadastro, String obs) {
        this.codMaq = codMaq;
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.dataCadastro = dataCadastro;
        this.obs = obs;
    }

    public Integer getCodMaq() {
        return codMaq;
    }

    public void setCodMaq(Integer codMaq) {
        this.codMaq = codMaq;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
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

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMaq != null ? codMaq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maquina)) {
            return false;
        }
        Maquina other = (Maquina) object;
        if ((this.codMaq == null && other.codMaq != null) || (this.codMaq != null && !this.codMaq.equals(other.codMaq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Maquina[ codMaq=" + codMaq + " ]";
    }
    
}
