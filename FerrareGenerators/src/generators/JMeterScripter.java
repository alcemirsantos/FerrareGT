package generators;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import performanceTestGenerator.BaseScripter;
import performanceTestRepresentation.TestPlan;
import performanceTestRepresentation.URLAccess;
import functionalTestRepresentation.Field;
import functionalTestRepresentation.Input;
import functionalTestRepresentation.requirements.*;

/**
 * Classe para montar os Samplers de uma bateria de testes
 * @author Ismayle de Sousa Santos
 */
public class JMeterScripter extends BaseScripter {
	static final int INT = 1,DAT = 2, STR = 3 ;

	JMeterGenerator gen;

	public JMeterScripter() {
		gen = new JMeterGenerator();
	}

	/**
	 * Método que retorna um número aleatório entre um número mínimo e um número máximo
	 * @param min número mínimo
	 * @param max número máximo
	 * @return número aleatório entre o mínimo e o máximo
	 */
	private String randomNumber(int min, int max,int type) {
		long minValue, maxValue;	
		if(type == 1){
			minValue = min;
			maxValue = max;
		}else{
			
			if (min != 0){			
					minValue = (long) (Math.pow(10, (min - 1)));
			}
			else{
					minValue = 0;
			}
			if (max != 0 ){					
					maxValue = (long) (Math.pow(10, (max)) - 1);					
			}
			else{
					maxValue = 10;
			}
		}		
		return ("${__Random( " + minValue + " , " + maxValue + " , rdm)}");
	}

	/**
	 * Classe que cria os HTTPSAMPLER para formar a bateria de Testes de Desempenho no JMeter
	 * @param testPlan Plano de Teste
	 * @param path caminho da saída do Script gerado
	 */
	public boolean parse(TestPlan testPlan, String path) {
		int minSize = 0, maxSize = 10, type = 3;
		
		gen.setUsers(testPlan.getConcurrentUsers());
		gen.setLoopCount(testPlan.getLoopCount());
		gen.setMaxTimeout(testPlan.getMaxTimeout());
		gen.setRampUpTime(testPlan.getRampUpTime());
		gen.setConstantTimer(testPlan.getConstantTimer());
		gen.setRandomTimer(testPlan.getRandomTimer());
		gen.setinfiniteLoop(testPlan.isInfiniteLoop());

		Vector<HTTPSampler> pages = new Vector<HTTPSampler>();
		int posAtual = 0;
		if(testPlan.hasCSV()){
			gen.setCsvFileName(testPlan.getCsvFileName());
			gen.setCsvVariablesNames(testPlan.getCsvVariablesNames());
		}		
		
		for (URLAccess ac : testPlan.getPages()) {
			HTTPSampler page = new HTTPSampler();			
			boolean unique = false;			
			Iterator<?> fieldsIt = ac.getFields().iterator();
			
			while (fieldsIt.hasNext()) {		
				
				Field field = (Field) fieldsIt.next();
				//
				System.out.println(field.getName()+"-:"+field.getReferenceToCSV());
				minSize = 0;
				maxSize = 10;
				for (Requirement<?> rq : field.getRequirements()) {									
					if (rq.getName().equals(Constants.UNIQUE)) {
						unique = (Boolean) (rq.getValue());
					}
					if (rq.getName().equals(Constants.MAX_SIZE)) {
						maxSize = (Integer) (rq.getValue());						
					}
					if (rq.getName().equals(Constants.MIN_SIZE)) {
						minSize = (Integer) (rq.getValue());
					}
					if (rq.getName().equals(Constants.TYPE)) {						
						if (rq.getValue().equals(TypeRequirement.INTEGER))
							type = INT;
						if (rq.getValue().equals(TypeRequirement.STRING))
							type = STR;
						if (rq.getValue().equals(TypeRequirement.DATE))
							type = DAT;						
					}

				}
				int a = field.getInputs().size();
				Input input = new Input();
				input = field.getInputs().get(a-1);
				//TRATAMENTO AOS REQUISITOS
				if (unique) {
					switch(type){
						case INT:
							input.setValue(input.getValue()+randomNumber(minSize, maxSize,1));
							break;
						case STR:							
							input.setValue(input.getValue()+"t"+randomNumber(minSize, maxSize,2));
							break;
						case DAT:
							input.setValue(input.getValue()+
									"${__Random( 1 , 28 , rdm)}/"
									+ "${__Random( 1 , 12 , rdm)}/"
									+ "${__Random( 1900 , 3000 , rdm)}");
							break;						
					}					
					unique = false;
				}				
				page.addFieldAndValue(field, input);
			}			
						
			
			page.setURL(ac.getServer());			
			page.setPath(ac.getPath());
			page.setExpectedResponse(ac.getResponse());
			page.setLimitTime(ac.getLimitTime());
			page.setPort(ac.getPort());
			page.setMethod(ac.getMethod());
			page.setCapture(ac.getCaptura());
			page.setHiddenFieldsList(ac.getListHidden());
			pages.add(page);
			
			
			if(page.getPath().contains("regex") && (posAtual !=0)){
				pages.get(posAtual-1).setCapture(1);				
			}else if (page.getHmpFieldInput().size() > 0){				
				if(pages.get(posAtual-1).getPath().contains("regex"))
					pages.get(posAtual-1).setCapture(-1);
				else
					pages.get(posAtual-1).setCapture(2);				
			}
			posAtual++;
		}
		gen.setPages(pages);
		
		try {
			gen.parse(path+".jmx");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
