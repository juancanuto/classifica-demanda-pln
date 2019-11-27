import java.util.Date;

import br.com.juancanuto.model.DeepModelUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase{

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName ){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite(AppTest.class);
    }

    public void testaGeracaoModelo() {
    	DeepModelUtils deepModel = null;

    	String modelPath="C:\\Users\\jhassam\\Documents\\project\\poc-dl4j-nlp-master\\classes";
    	String modelName= String.valueOf(new Date().getTime()) + ".zip";
    	String trainingFiles="C:\\Users\\jhassam\\Documents\\project\\poc-dl4j-nlp-master\\resources\\classes";
    	String nbrEpochs="5";
    	
		try {
			deepModel = new DeepModelUtils(modelPath,modelName,trainingFiles,nbrEpochs);
			deepModel.trainModel();
			System.out.println("Modelo Gerado com sucesso !");
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}
    }
}
