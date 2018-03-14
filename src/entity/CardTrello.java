package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class CardTrello {

	private String id;
	private String idList;
	private String name;
	private String shortLink;
	private DateTime dataEntrega;
	private boolean cardDataFinalizada;
	private DateTime dataUltimaAtividade;
	private String descricao;
	private Map<String, String> descricoes;
	
	private ArrayList<AcaoCard> listaAcoes;
	
	public CardTrello() {
		listaAcoes = new ArrayList<AcaoCard>();
		descricoes = new HashMap<String, String>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public DateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(DateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public boolean isCardDataFinalizada() {
		return cardDataFinalizada;
	}

	public void setCardDataFinalizada(boolean cardDataFinalizada) {
		this.cardDataFinalizada = cardDataFinalizada;
	}

	public DateTime getDataUltimaAtividade() {
		return dataUltimaAtividade;
	}

	public void setDataUltimaAtividade(DateTime dataUltimaAtividade) {
		this.dataUltimaAtividade = dataUltimaAtividade;
	}

	public void addAcao(AcaoCard acao) {
		listaAcoes.add(acao);
	}
	
	public ArrayList<AcaoCard> getAllAcoes() {
		return listaAcoes;
	}
	
	@Override
	public String toString() {
		return "CardTrello [id=" + id + ", idList=" + idList + ", name=" + name + ", shortLink=" + shortLink
				+ ", dataEntrega=" + dataEntrega + ", cardDataFinalizada=" + cardDataFinalizada
				+ ", dataUltimaAtividade=" + dataUltimaAtividade + ", descricoes =" + descricoes + "]";
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Map<String, String> getDescricoes() {
		return descricoes;
	}

	public void setDescricoes(Map<String, String> descricoes) {
		this.descricoes = descricoes;
	}

}
