package br.com.juancanuto;

import br.com.juancanuto.model.DeepModelUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", description = "Gera modelo de classifica��o de marcas de celulares a partir da descri��o do produto celular.", mixinStandardHelpOptions = true, version = "App 1.0")
public class App implements Runnable{

	@Option(names = { "-mp", "--modelpath" }, required = false, description = "Path para o caminho onde o arquivo contendo o modelo ser� salvo.")
	private String modelPath;
	
	@Option(names = { "-mn", "--modelname" }, required = false, description = "Nome do arquivo que conter� o modelo gerado.")
	private String modelName;
	
	@Option(names = { "-td", "--traininddata" }, required = false, description = "Diret�rio onde se encontram os dados para treinamento do modelo.")
	private String trainindData;
	
	@Option(names = { "-te", "--trainingepochs" }, required = false, description = "N�mero de ciclos de treinamento a seren utilizados no treinamento do modelo.")
	private String trainingEpochs;
	
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		 CommandLine.run(new App(), args);
	}
	
	/**
	 * 
	 */
	public void run() {
		DeepModelUtils deepModel = null;		
		try {
			deepModel = new DeepModelUtils(modelPath,modelName,trainindData,trainingEpochs);
			deepModel.trainModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
