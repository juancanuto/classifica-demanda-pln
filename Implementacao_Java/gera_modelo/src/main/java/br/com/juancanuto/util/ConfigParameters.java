package br.com.juancanuto.util;

public enum ConfigParameters {

	MODEL_PATH("C:\\Users\\jhassam\\Documents\\Projeto Giuliano\\Implementacao_Java\\gera_modelo\\resources\\ "),
	MODEL_NAME("MODELOS.ZIP"),
	MODEL_LABELS_NUMBER("3"),
	TRAIN_DATA_PATH("C:\\Users\\jhassam\\Documents\\Projeto Giuliano\\Implementacao_Java\\gera_modelo\\resources\\classes");
	private final String valor;
	
	ConfigParameters(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }
}
