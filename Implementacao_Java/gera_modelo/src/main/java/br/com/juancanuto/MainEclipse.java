package br.com.juancanuto;

import br.com.juancanuto.model.DeepModelUtils;

public class MainEclipse {

	public static void main(String[] args) {
		DeepModelUtils deepModel = null;
		
		String modelPath="C:\\Users\\jhassam\\Documents\\project\\poc-dl4j-nlp-master\\resources\\";
		String modelName= "MODELOS.ZIP";
		String trainingFiles="C:\\Users\\jhassam\\Documents\\project\\poc-dl4j-nlp-master\\resources\\classes";
		String nbrEpochs="100";
		
		try {
			deepModel = new DeepModelUtils(modelPath,modelName,trainingFiles,nbrEpochs);
			deepModel.trainModel();
			System.out.println("Modelo Gerado com sucesso !");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
