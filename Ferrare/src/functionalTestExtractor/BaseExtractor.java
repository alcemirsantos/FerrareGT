package functionalTestExtractor;

import java.io.File;
import java.io.FileNotFoundException;
import util.FileHandler;
import functionalTestRepresentation.*;

/**
 * Classe abstrata respons�vel pela cria��o da representa��o abstrata de um script de um teste funcional
 * @author Ismayle de Sousa Santos
 */

public abstract class BaseExtractor {
	
	protected FileHandler fileHandler;
	protected String urlBase = null;	
	protected int port = 80;

	/**
	 * M�todo que instancia o FileHandler a partir de um arquivo
	 * @param file Arquivo com o script de um teste funcional
	 * @return TestBattery - Bateria de Testes
	 */
	
	public TestBattery extract(File file) {
		try {
			fileHandler = new FileHandler(file);
		} catch (FileNotFoundException e) {
			System.out.println("N�o achou o arquivo !!" + e.getMessage());
			return null;
		}
		return extract();
	}

	/**
	 * M�todo que instancia o FileHandler a partir do endere�o de um arquivo
	 * @param fileName Endere�o do arquivo com o script de teste funcional
	 * @return TestBattery - Bateria de Testes
	 */
	public TestBattery extract(String fileName) {
		try {
			fileHandler = new FileHandler(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("N�o achou o arquivo !!" + e.getMessage());
			return null;
		}
		return extract();
	}

	/**
	 * M�todo que extrai os casos de teste do script de teste funcional
	 * @return TestBattery - Bateria de Testes
	 */
	public TestBattery extract() {

		TestBattery testBattery = new TestBattery();
		TestCase testCase = new TestCase();
		testCase = getNextTest();
		do {
			if(testCase.getTestProcedure() != null)				
				testBattery.addTestCase(testCase);
			testCase = getNextTest();			
		} while (!fileHandler.endOfFile());
		if(testCase.getTestProcedure() != null)				
			testBattery.addTestCase(testCase);		
		fileHandler.close();
		return testBattery;	
	}

	/**
	 * M�todo abstrato a ser implementado pelos Extratores
	 * @return TestCase - Caso de Teste
	 */
	public abstract TestCase getNextTest();

}
