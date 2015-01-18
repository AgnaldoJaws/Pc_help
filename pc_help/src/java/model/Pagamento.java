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
@Table(name = "PAGAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagamento.findAll", query = "SELECT p FROM Pagamento p"),
    @NamedQuery(name = "Pagamento.findByCodPag", query = "SELECT p FROM Pagamento p WHERE p.codPag = :codPag"),
    @NamedQuery(name = "Pagamento.findByFormPag", query = "SELECT p FROM Pagamento p WHERE p.formPag = :formPag"),
    @NamedQuery(name = "Pagamento.findByNumVezes", query = "SELECT p FROM Pagamento p WHERE p.numVezes = :numVezes"),
    @NamedQuery(name = "Pagamento.findByNomeCartao", query = "SELECT p FROM Pagamento p WHERE p.nomeCartao = :nomeCartao")})
public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_pag")
    private Integer codPag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "form_pag")
    private String formPag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "num_vezes")
    private String numVezes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_cartao")
    private String nomeCartao;
    @JoinColumn(name = "cod_sol", referencedColumnName = "cod_sol")
    @ManyToOne(optional = false)
    private Solucao codSol;

    public Pagamento() {
    }

    public Pagamento(Integer codPag) {
        this.codPag = codPag;
    }

    public Pagamento(Integer codPag, String formPag, String numVezes, String nomeCartao) {
        this.codPag = codPag;
        this.formPag = formPag;
        this.numVezes = numVezes;
        this.nomeCartao = nomeCartao;
    }

    public Integer getCodPag() {
        return codPag;
    }

    public void setCodPag(Integer codPag) {
        this.codPag = codPag;
    }

    public String getFormPag() {
        return formPag;
    }

    public void setFormPag(String formPag) {
        this.formPag = formPag;
    }

    public String getNumVezes() {
        return numVezes;
    }

    public void setNumVezes(String numVezes) {
        this.numVezes = numVezes;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public Solucao getCodSol() {
        return codSol;
    }

    public void setCodSol(Solucao codSol) {
        this.codSol = codSol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPag != null ? codPag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagamento)) {
            return false;
        }
        Pagamento other = (Pagamento) object;
        if ((this.codPag == null && other.codPag != null) || (this.codPag != null && !this.codPag.equals(other.codPag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pagamento[ codPag=" + codPag + " ]";
    }
    
}
