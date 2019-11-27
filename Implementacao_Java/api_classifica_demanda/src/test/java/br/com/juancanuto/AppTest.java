package br.com.juancanuto;

import br.com.juancanuto.dto.TipoDescricao;
import br.com.juancanuto.services.ClassificaDescricao;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	

	ClassificaDescricao cellPhoneClassService ;
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName){
        super(testName);
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
        return new TestSuite(AppTest.class);
    }

    public void testApp(){        
    	
    	TipoDescricao marca = null;
    	 try {
    		 cellPhoneClassService = new ClassificaDescricao();
			 marca = cellPhoneClassService.findByDesc("Engenharia da Computacao");
			 if(marca == null) {
				 assertFalse(true);
			 }else {
				 assertTrue(true);
			 }
		} catch (Exception e) {			
			e.printStackTrace();
			assertFalse(true);
		}    	    	
    }
}
