package performanceTestGenerator;

import performanceTestRepresentation.TestPlan;
import performanceTestRepresentation.URLAccess;
import functionalTestRepresentation.Field;
import functionalTestRepresentation.TestBattery;
import functionalTestRepresentation.TestCase;
import functionalTestRepresentation.TestProcedure;

/**
 * Classe que gera a representa��o orientada a Objetos do Teste de Desempenho e Estresse - TestPlan
 ** @author Ismayle de Sousa Santos
 */
public class SimpleGenerator extends BaseGenerator {

	public SimpleGenerator(TestBattery battery) {
		this.battery = battery;
	}

	/**
	 * M�todo que gera o Plano de Teste com os requisitos de Desempenho padr�es {@link TestPlan}
	 */
	public TestPlan generateDefaultTestPlan() {
		TestPlan performanceTest = new TestPlan();

		int posAtual = 0 ;
		for (TestCase testCase : battery.getTestCases()) {
			TestProcedure tp = testCase.getTestProcedure();
			URLAccess access = new URLAccess();
			access.setServer(tp.getURL());
			access.setPath(tp.getPath());
			access.setMethod(tp.getMethod());
			access.setPageTimeout(8000);
			access.setPort(tp.getPort());		

			if ((testCase.getExpectedOutput() != null)
					&& (testCase.getExpectedOutput().getExpectedOutput() != null)) {
				access.setResponse(testCase.getExpectedOutput()
						.getExpectedOutput());
			} else
				access.setResponse(" ");

			for (Field f : tp.getFields()) {
				if (!f.isOutputField()){					
					access.addField(f);
				}									
			}
			
			if(access.getPath().contains("regex") && (posAtual != 0)){
				performanceTest.getPages().get(posAtual-1).setCaptura(1);
			}
			performanceTest.addPage(access);
			posAtual++;
		}
		
		return performanceTest;

	}

	/**
	 * M�todo que gera o TestPlan com os requisitos de Desempenho
	 */
	public TestPlan generateTestPlan(int concurrentUsers, int rampUpTime,
			int loopCount, int timeout, int constantTimer, int randomTimer,
			boolean infiniteLoop) {

		TestPlan tp = generateDefaultTestPlan();
		tp.setConcurrentUsers(concurrentUsers);
		tp.setRampUpTime(rampUpTime);
		tp.setLoopCount(loopCount);
		tp.setTimeout(timeout);
		tp.setInfiniteLoop(infiniteLoop);
		tp.setConstantTimer(constantTimer);
		tp.setRandomTimer(randomTimer);

		return tp;

	}

}
