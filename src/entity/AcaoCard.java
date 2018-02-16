package entity;

public class AcaoCard {
	private String idAcao;
	private String idCartao;
	private String idCriador;
	private String idListaAntes;
	private String idListaDepois;
	
	public String getIdAcao() {
		return idAcao;
	}
	public void setIdAcao(String idAcao) {
		this.idAcao = idAcao;
	}
	public String getIdCartao() {
		return idCartao;
	}
	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}
	public String getIdCriador() {
		return idCriador;
	}
	public void setIdCriador(String idCriador) {
		this.idCriador = idCriador;
	}
	public String getIdListaAntes() {
		return idListaAntes;
	}
	public void setIdListaAntes(String idListaAntes) {
		this.idListaAntes = idListaAntes;
	}
	public String getIdListaDepois() {
		return idListaDepois;
	}
	public void setIdListaDepois(String idListaDepois) {
		this.idListaDepois = idListaDepois;
	}
	
	@Override
	public String toString() {
		return "AcaoCard [idAcao=" + idAcao + ", idCartao=" + idCartao + ", idCriador=" + idCriador + ", idListaAntes="
				+ idListaAntes + ", idListaDepois=" + idListaDepois + "]";
	}
	
	
}
