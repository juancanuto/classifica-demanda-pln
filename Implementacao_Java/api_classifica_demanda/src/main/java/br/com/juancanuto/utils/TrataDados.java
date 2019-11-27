package br.com.juancanuto.utils;

import java.util.regex.Pattern;

import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;

/**
 * Esta classe � respons�vel por limpar a string a ser utilizada para compor a base de treinamento
 **/
public class TrataDados implements TokenPreProcess {

	private static final Pattern punctPattern = Pattern.compile("[\\.:,\"\'\\(\\)\\[\\]|/?!;]+");
	
	public String preProcess(String token) {		
		
		String msgRet  = punctPattern.matcher(token).replaceAll(" ");
		msgRet = msgRet.replaceAll("  "," ").trim().toLowerCase();		
		return msgRet ;
	}

}
