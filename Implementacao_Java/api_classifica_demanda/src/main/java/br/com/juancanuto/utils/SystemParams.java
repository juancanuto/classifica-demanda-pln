package br.com.juancanuto.utils;

public enum SystemParams {
	
	LABELS_NUMBER("3"),
	MODEL_FILE("C:\\Users\\jhassam\\Documents\\Projeto Giuliano\\Implementacao_Java\\api_classifica_demanda\\resources\\MODELOS.ZIP");

	
	private final String valor;
	
	SystemParams(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }
}
