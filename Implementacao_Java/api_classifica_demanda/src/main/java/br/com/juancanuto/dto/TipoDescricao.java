package br.com.juancanuto.dto;

import java.io.Serializable;

public class TipoDescricao implements Serializable{

	private static final long serialVersionUID = -5085477343007043839L;
	
	private String Descricao;
	private String Classificao;
	private Double Probabilidade;
	
	
	
	public Double getProbabilidade() {
		return Probabilidade;
	}
	public void setProbabilidade(Double Probabilidade) {
		this.Probabilidade = Probabilidade;
	}
	public String getClassificao() {
		return Classificao;
	}
	public void setClassificao(String Classificao) {
		this.Classificao = Classificao;
	}
	public String getDescricao() {
		return Descricao;
	}
	public void setDescricao(String Descricao) {
		this.Descricao = Descricao;
	}
	@Override
	public String toString() {
		return "Descricao [Descricao=" + Descricao + ", Classificao=" + Classificao + ", Probabilidade=" + Probabilidade
				+ "]";
	}
	
	
	
	
		

}
