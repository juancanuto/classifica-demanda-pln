package br.com.juancanuto.model;

import java.io.File;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import br.com.juancanuto.util.ConfigParameters;
import br.com.juancanuto.util.ProductCommonProcessor;
import br.com.juancanuto.util.Timer;

public class DeepModelUtils {
		
	private LabelAwareIterator iterator = null;
	private TokenizerFactory tokenizerFactory=null;
	private ParagraphVectors paragraphVectors=null;
	private Timer timer=null;	
	private File trainingData=null;
	
	private String modelPath=null;
	private String modelName=null;
	private String trainindData=null;
	private String trainingEpochs=null;
	
	
	/**
	 * Inicia classe respons�vel pela gera��o do modelo
	 * 
	 * @param pathModel : Caminho referente ao local onde o modelo ser� salvo
	 * @param nameModel : Nome do arquivo de modelo
	 * @param trnData : Caminho para local onde se localizam os arquivos de treinamento
	 * @param nbEpochs : N�mero de �pocas de treinamento
	 * 
	 * @throws Exception
	 */
	public DeepModelUtils(String pathModel ,String nameModel ,String trnData ,String nbEpochs ) throws Exception{
		
		/**
		 * Se um dos par�metros n�o forem enviados, considero os valores default disponiveis no 
		 * Enum contendo par�metros de configura��o
		 */
		if(pathModel == null || nameModel == null || trnData == null || nbEpochs == null ) {
			modelPath = ConfigParameters.MODEL_PATH.getValor();
			modelName = ConfigParameters.MODEL_NAME.getValor();
			trainindData = ConfigParameters.TRAIN_DATA_PATH.getValor();
			trainingEpochs = String.valueOf(100);
		}else {
			modelPath = pathModel;
			modelName = nameModel;
			trainindData = trnData;
			trainingEpochs = nbEpochs;
		}
	}
	
	/**
	 * Treina modelo com base nos par�metros enviados
	 * @throws Exception
	 */
	public void trainModel() throws Exception{
		
		tokenizerFactory = new DefaultTokenizerFactory();		
		System.out.println("Iniciando processo de gera��o do modelo....");
		timer = new Timer();
		try {
		
			//Verifica se ja existe modelo no disco, se existir, carrega, sen�o cria
			if(!(new File(modelPath + modelName  ).exists())) {
				
				//N�o encontrou modelo treinado, realiza treinamento de um novo modelo baseado na base de treinamento definida
				System.out.println("Iniciando gera��o do modelo de acordo com dados de treino definidos em --> " + modelPath); 
									
				//Efetuando leitura da base de dados a ser utilizada para treinamento			
				trainingData = new File(trainindData);
				
				/**
				 * Criando um iterator para nosso dataset a ser utilizado no treinamento.
				 * Lembre-se que os dados de treinamento devem ser nomeados exatamente como ocorre aqui em :
				 * "/poc-dl4j-nlp/resources/celulares".
				 * Temos uma pasta para cada marca de celular, cada uma com sua respectiva base de treinamento 
				 **/
			    iterator = new FileLabelAwareIterator.Builder()
			    		.addSourceFolder(trainingData)
			    		.build();
				
			    /**
			     * Um tokenizer � respons�vel por efetuar um pre-tratamento nos dados de treinamento.
			     * Neste caso estamos deixando somente strings contendo letras e\ou n�meros
			     **/
			    tokenizerFactory.setTokenPreProcessor(new ProductCommonProcessor());
			    			    		        
			    /**
			     * Definindo o modelo aser utilizado no treinamento
			     **/
			    System.out.println("Definindo modelo de rede neural utilizado no treinamento do modelo...");
			    paragraphVectors = new ParagraphVectors.Builder()
			              .learningRate(0.025)//Tamanho da etapa para cada atualiza��o dos coeficientes utilizados na rede neural
			              .minLearningRate(0.001)//� o minimo da taxa de aprendizado. A taxa de aprendizado decai � medida que o n�mero de palavras que voc� treina diminui. Se a taxa de aprendizado diminuir muito, o aprendizado da rede n�o ser� mais eficiente. Isso mant�m os coeficientes em movimento
			              .batchSize(20)//Tamanho do batch individual de processamento
			              .epochs(Integer.valueOf(trainingEpochs))//Numero de �pocas de treinamento
			              .minWordFrequency(4)//N�mero m�nimo de ocorrencias de uma palavra para a mesma compor o modelo
			              .layerSize(1000)//N�mero de dimens�es do vetor de saida
			              .iterate(iterator)//Iterator contendo dados de treinamento			              
			              .trainWordVectors(true)
			              .useAdaGrad(true)
			              .tokenizerFactory(tokenizerFactory)
			              .build();
			    			    			   
			    System.out.println("Iniciando treinamento do modelo....");			    
			    paragraphVectors.fit();
			    System.out.println("Modelo treinado --> " + modelPath + modelName);
			    System.out.println("Tempo decorrido para a gera��o do modelo --> " + timer.toString());
			    
			    System.out.println("Iniciando processo de grava��o do modelo --> " + modelPath + modelName);
			    WordVectorSerializer.writeParagraphVectors(paragraphVectors,  modelPath + modelName);
			    System.out.println("Tempo decorrido ap�s a grava��o do modelo --> " + timer.toString());
			    
			}else {				
				System.out.println("O referido modelo ja existe no diret�rio indicado --> " + ConfigParameters.MODEL_PATH.getValor()+ConfigParameters.MODEL_NAME.getValor());
			}			
		}catch (Exception e) {
			System.out.println("Problemas na gera��o no modelo de dados!");
			throw e;
		}	
	}
}
