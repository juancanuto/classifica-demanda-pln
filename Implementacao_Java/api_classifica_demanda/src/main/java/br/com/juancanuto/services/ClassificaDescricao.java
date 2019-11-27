package br.com.juancanuto.services;

import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.juancanuto.dto.TipoDescricao;
import br.com.juancanuto.utils.TrataDados;
import br.com.juancanuto.utils.SystemParams;
import br.com.juancanuto.utils.Timer;



@Service
public class ClassificaDescricao {

	
	private TokenizerFactory tokenizerFactory = null;
	private ParagraphVectors paragraphVectors = null;
	Logger logger = LoggerFactory.getLogger(ClassificaDescricao.class);
	Timer  timer;
	 
	/**
	 * Construtor da classe de serviço  ClassificaDescricao
	 * @throws Exception
	 */
	public ClassificaDescricao() throws Exception{
     timer = new Timer();	 
		 
		 try{
			
			logger.info("Iniciando carga do modelo a ser verificado....");
			logger.info(SystemParams.MODEL_FILE.getValor());						
			
			paragraphVectors = WordVectorSerializer.readParagraphVectors(SystemParams.MODEL_FILE.getValor());
			
			logger.info("Modelo carregado com sucesso!");
			logger.info(timer.toString());
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Error to load Model!");			
			throw new Exception("Error to load Model!");
		}		 
		 tokenizerFactory = new DefaultTokenizerFactory();	
	 }
	 
	 /**
	  *  Infere a marca de um determinado celular levando em consideração o modelo treinado
	  *  
	  * @param description : Descrição do Celular
	  * 
	  * @return : Descrição da marca do celular
	  * 
	  * @throws Exception
	  */
	 public TipoDescricao findByDesc(String description) throws Exception{
		 
		 TipoDescricao marca = new TipoDescricao();
		 
		 try {
			
			//Trata String com descrição do produto a ser pesquisado na base de dados
			tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new TrataDados());
			paragraphVectors.setTokenizerFactory(tokenizerFactory);
			 
			LabelledDocument document =  new LabelledDocument();
			document.setContent(description);		
			
			//Faz predição de acordo com o modelo
			Collection<String> listaResultados =  paragraphVectors.predictSeveral(document,Integer.valueOf(SystemParams.LABELS_NUMBER.getValor()).intValue());
			
			double valorTemp = 0d;
			double valorfinal = -1d;
			
			for (String label : listaResultados) {
				valorTemp = paragraphVectors.similarityToLabel(document, label);				
				if(valorTemp > valorfinal) {
					
					marca = new TipoDescricao();
					valorfinal = valorTemp;					
					marca.setDescricao(description);
					marca.setClassificao(label);
					marca.setProbabilidade(valorfinal);
				}								
			}
			
			logger.info("Descricao a verificar --> " + document.getContent());
			logger.info("Tipo descricao Inferida --> " + marca);
			logger.info("Tipo descricao Score --> " + valorfinal);
			
		} catch (Exception e) {
			logger.error("Erro ao inferir resposta a partir do modelo " + SystemParams.MODEL_FILE.getValor());
			throw e;
		}		 		 
		 return marca;
	 }
	
}
