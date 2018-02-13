package extractors;

import java.util.LinkedList;

import functionalTestExtractor.BaseExtractor;
import functionalTestExtractor.command.Command;
import functionalTestExtractor.command.CommandType;
import functionalTestRepresentation.Field;
import functionalTestRepresentation.Input;
import functionalTestRepresentation.Output;
import functionalTestRepresentation.TestCase;
import functionalTestRepresentation.TestProcedure;

/**
 * Extrator para testes feitos com o Selenium IDE
 * @author Ismayle de Sousa Santos
 */
public class SeleniumExtractor extends BaseExtractor {

	private LinkedList<Command> storedCommands = new LinkedList<Command>();

	private boolean returnInputs;
	
	private LinkedList<String> pathList = new LinkedList<String>(); 
	
	private String urlPath;
	
	private TestCase testCase = new TestCase();
	
	public TestCase getNextTest() {
		TestCase returnedTestCase = new TestCase();
		Command command = getNextCommand();

		do {
			if(command == null)
				break;
			CommandType type = command.getType();

			if (type.equals(CommandType.OPEN)) {
				if(testCase.getTestProcedure() != null)
					returnedTestCase = testCase;
				testCase = new TestCase();
				TestProcedure tp = new TestProcedure(urlBase,
						command.getTarget(), TestProcedure.GET, port);
				testCase.setTestProcedure(tp);	
				return returnedTestCase;
			} else if (type.equals(CommandType.INPUT)) {
				testCase.getTestProcedure().setMethod(TestProcedure.POST);
				Field field = new Field(command.getTarget());
				if (testCase.getTestProcedure().containFieldName(
						field.getName()) == -1){
					testCase.getTestProcedure().addField(field);
				}
				Input input = new Input(command.getValue(), field);
				field.addInput(input);
				testCase.addInput(input);
			} else if (type.equals(CommandType.ASSERT)) {
				Output output = new Output(command.getValue());
				testCase.setExpectedOutput(output);							
			}
			command = getNextCommand();
		} while (!fileHandler.endOfFile());	
		if(testCase.getTestProcedure() != null){
			returnedTestCase = testCase;
			testCase = new TestCase();
			return returnedTestCase;			
		}			
		return null;
		
	}
	
	/**
	 * Método que extrai um comando do script de teste funcional
	 * @return Command - Comando
	 */
	public Command getNextCommand() {		
		if (returnInputs) {
			if (storedCommands.isEmpty()) {
				returnInputs = false;
			} else {
				return storedCommands.removeFirst();
			}
		}
		if (fileHandler.endOfFile()) {			
			return null;
		} else {
			String entry;
			do {
				entry = fileHandler.readLine();
				if (entry == null) {
					return null;
				}
			} while (!entry.equals("<tr>"));

			String param1 = fileHandler.readLine();
			param1 = param1.substring(5, param1.length() - 5);

			String param2 = fileHandler.readLine();
			param2 = param2.substring(5, param2.length() - 5);

			String param3 = fileHandler.readLine();
			param3 = param3.substring(5, param3.length() - 5);


			CommandType type = CommandType.UNKNOWN;
			String target = "";
			String value = "";
 			
			//1° teste -> submit
			if(( param1.equals("click") || param1.equals("clickAndWait") ) && ( (param2.contains("Submit")) || (param2.contains("submit") ) )){
				Command nextCommand = getNextCommand();				
				if(nextCommand.getType().equals(CommandType.OPEN)){					
					returnInputs = true;
					return createOPEN("http://"+urlBase+"/"+nextCommand.getTarget());
				}else if (nextCommand.getType().equals(CommandType.ASSERT)){
					storedCommands.add(nextCommand);
				}
				returnInputs = true;
				return createOPEN("http://"+urlBase+"/regex");
			}
			//2º teste -> inputs
			if(( param1.equals("click") || param1.equals("clickAndWait") ) && (param2.contains("//input")) ){									
				storedCommands.addLast(inputsFromClick(param2));
				return getNextCommand();
			}
			
			//3° teste -> alvo não válido 
			if(( param1.equals("click") || param1.equals("clickAndWait") ) && (!param2.contains("href")) ){									
				return new Command(CommandType.UNKNOWN,"","");
			}
		    //4° teste
			if(( param1.equals("click") || param1.equals("clickAndWait") ) && (param2.contains("href") ) ){									
				returnInputs = true;
				return createOPEN(param2);
			}				
 
			if (param1.equals("open")) {
				returnInputs = true;
				return createOPEN(param2);
			} else if (param1.equals("type")) {
				storedCommands.addLast(createINPUT(param2, param3));
				return getNextCommand();
			} else if (param1.equals("select")) {
				storedCommands.addLast(createINPUT(param2, param3.substring(6,
						param3.length())));
				return getNextCommand();
			} else if (param1.contains("assert") || param1.contains("verify")) {
				if (param1.endsWith("Title")) {					
					storedCommands.addLast(createASSERT("title", param2));
					return getNextCommand();
				} else if (param1.endsWith("TextPresent")) {
					return createASSERT("conteúdo", param2);
				} 
			}	
			
			return new Command(type, target, value);
		}
	}

	private Command inputsFromClick(String target){
		String name = "";
		String value = "";
		int start = 0;
		int end = 0;
		if(target.contains("@id='") && target.contains("@value='")){
			start = target.indexOf("@id='")+5;
			end  = target.indexOf("'",start+1);
			name = target.substring(start, end);
			
			start = target.indexOf("@value='")+8;
			end  = target.indexOf("'",start+1);
			value = target.substring(start, end);
			System.out.println(name);
			return new Command(CommandType.INPUT, name, value);			
		}
		if(target.contains("@name='") && target.contains("@value='")){
			start = target.indexOf("@name='")+7;
			end  = target.indexOf("'",start+1);
			name = target.substring(start, end);
			
			start = target.indexOf("@value='")+8;
			end  = target.indexOf("'",start+1);
			value = target.substring(start, end);
			System.out.println(name);
			return new Command(CommandType.INPUT, name, value);			
		}
		return new Command(CommandType.UNKNOWN,"","");
	}
	
	/**
	 * Método que extrai a UrlBase a partir de uma url fornecida
	 * @param url Url acessada 
	 * @return String - Url da página em questão
	 */
	private String extractUrlBase(String url) {
		if (url.startsWith("http")) {
			url = url.substring(7, url.length());
		}	
		if(url.contains("/")){
			urlBase = url.substring(0, url.indexOf("/"));						
			if(url.length() == urlBase.length()+1)
				urlPath = "";
			else{
				if(url.indexOf("/", urlBase.length()+1) > 0)
					urlPath = url.substring(urlBase.length()+1,url.lastIndexOf("/"));
				else
					urlPath = url.substring(urlBase.length()+1,url.length());
			}								
		}else{
			urlBase = url;
			urlPath = "";
		}
		int portStart = url.indexOf(":", 7);
		int portEnd = url.indexOf("/", portStart);
		if (portStart >= 0) {
			String portNumber = urlBase.substring(portStart + 1, portEnd);
			port = Integer.parseInt(portNumber);
			return url.substring(0, portStart);
		} else {			
			return urlBase;
		}
	}

	/**
	 * Método que extrai o Path a partir de uma url fornecida
	 * @param url Url acessada
	 * @return String - Path da página em questão
	 */		
	private String extractPath(String url) {
		
		if (url.equals("/")) {
			return null;
		}
		int start;
		if (url.startsWith("http")) {			
			start = url.indexOf("/", 7);
		}else if(url.startsWith("//a[contains")){
			start = url.indexOf("'");
		}
		else if(url.contains("href")){
			start = url.indexOf("'");
		}else {
			start = 0;
		}
		int end = url.indexOf('?');		
		if (start >= 0) {
			//retira os campos da url
			/*if (end > 0) {
				String inputs = url.substring(end + 1, url.length());
				String[] entradas = inputs.split("&");
				for (String entrada : entradas) {
					String[] split = entrada.split("=");					
					Command c;
					if (split.length == 2) {
						if(split[0].startsWith("amp;")){
							split[0] = split[0].substring(split[0].indexOf(";")+1);
						}
						if(split[1].indexOf("'") > 0){
							c = createINPUT(split[0], split[1].substring(0,split[1].indexOf("'")));
						}else{
							c = createINPUT(split[0], split[1]);
						}						
					} else {
						c = createINPUT(split[0], "");
					}
					storedCommands.add(c);
				}
			} else {
				end = url.length();
			}*/
			//para nao retirar os campos da url
			end = url.length(); // modificado
			
			if(url.substring(start+1,end).startsWith("/"))
				start = start+1;
			
			if (start + 1 < end)  {				
				if(url.charAt(end-1) == '/')
					end--;
				else if (url.charAt(end-2) == ')'){
					end = end - 3;		
					if(url.substring(start+1,end).startsWith(urlPath)){
						pathList.add(url.substring(start+1,end));
						return url.substring(start+1,end);
					}else{
						pathList.add(urlPath+"/"+url.substring(start+1,end));
						return urlPath+"/"+url.substring(start+1,end);
					}					
				}
				else if (url.charAt(end-1) == ']'){
					end = end - 2;					
					pathList.add(urlPath+"/"+url.substring(start+1,end));
					return urlPath+"/"+url.substring(start+1,end);
				}			
			}			
			
			String returnedUrl = url.substring(start + 1, end);
			System.out.println(returnedUrl +"::"+urlPath);
			if (returnedUrl.equals("regex")){				
				returnedUrl = returnedUrl+"["+pathList.getLast()+"]";
				pathList.add(returnedUrl);				
				return returnedUrl;
			}				
			else if(returnedUrl.startsWith(urlPath)){
				pathList.add(returnedUrl);
				return returnedUrl;
			}
			else{
				pathList.add(urlPath+"/"+returnedUrl);
				return urlPath+"/"+returnedUrl;
			}
		} else {
			pathList.add(""); //MODIFICADO EM 28.07.09
			return "";
		}
	}
	
	/** Método que cria um comando do tipo OPEN com base no alvo
	 * @param target Url invocada por este comando
	 * @return Command - Comando do tipo OPEN
	 */
	private Command createOPEN(String target) {
		if (urlBase == null) {
			urlBase = extractUrlBase(target);
		}		
		return new Command(CommandType.OPEN, extractPath(target), "");
	}
	
	/**
	 * Método que cria um comando do tipo INPUT com base no alvo e no valor
	 * @param target Campo da página acessada
	 * @param value Valor que deve ser colocado no campo
	 * @return Command - Comando do tipo INPUT
	 */
	private Command createINPUT(String target, String value) {
		return new Command(CommandType.INPUT, target, value);
	}

	/**
	 * Método que cria um comando do tipo ASSERT com base no alvo e valor
	 * @param target Local onde deve ser feita a verificação
	 * @param value Texto que deve ser verificado
	 * @return Command - Comando do tipo ASSERT
	 */
	private Command createASSERT(String target, String value) {
		return new Command(CommandType.ASSERT, target, value);
	}

	
}

