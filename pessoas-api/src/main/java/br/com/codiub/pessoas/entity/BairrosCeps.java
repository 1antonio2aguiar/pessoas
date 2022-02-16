package br.com.codiub.pessoas.entity;
// Generated 3 de mai. de 2021 14:41:28 by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BairrosCeps generated by hbm2java
 */
@Entity
@Table(name = "BAIRROS_CEPS", schema = "DBO_CC_PESSOAS")
public class BairrosCeps implements java.io.Serializable {

	private BairrosCepsId id;

	public BairrosCeps() {
	}

	public BairrosCeps(BairrosCepsId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)),
			@AttributeOverride(name = "cidade", column = @Column(name = "CIDADE", precision = 10, scale = 0)),
			@AttributeOverride(name = "distrito", column = @Column(name = "DISTRITO", precision = 10, scale = 0)),
			@AttributeOverride(name = "bairro", column = @Column(name = "BAIRRO", precision = 10, scale = 0)),
			@AttributeOverride(name = "logradouro", column = @Column(name = "LOGRADOURO", precision = 10, scale = 0)),
			@AttributeOverride(name = "cdCcmLogradouro", column = @Column(name = "CD_CCM_LOGRADOURO", precision = 10, scale = 0)),
			@AttributeOverride(name = "cdCcmBairro", column = @Column(name = "CD_CCM_BAIRRO", precision = 10, scale = 0)),
			@AttributeOverride(name = "cdCcmCep", column = @Column(name = "CD_CCM_CEP", precision = 10, scale = 0)),
			@AttributeOverride(name = "identificacao", column = @Column(name = "IDENTIFICACAO", length = 1)),
			@AttributeOverride(name = "banco", column = @Column(name = "BANCO", length = 30)) })
	public BairrosCepsId getId() {
		return this.id;
	}

	public void setId(BairrosCepsId id) {
		this.id = id;
	}

}