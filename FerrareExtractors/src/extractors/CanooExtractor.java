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
 * Extrator para testes feitos com o Canoo Web Test através de arquivos XML
 * @author Ismayle de Sousa Santos 
 */

public class CanooExtractor extends BaseExtractor{

		private LinkedList<Command> storedCommands = new LinkedList<Command>();

		private boolean returnInputs;
		
		private LinkedList<String> pathList = new LinkedList<String>(); 
		
		private String urlPath;
		
		private String previousLine = "";
		
		private boolean incomplete = false;
		
		private TestCase testCase = new TestCase();
		
		
		public TestCase getNextTest() {
			TestCase returnedTestCase = new TestCase();
			Command command = getNextCommand();				
			do {
				
				if (command == null)
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
				if(storedCommands.size() == 0){
					returnInputs = false;
				}else{				
					return storedCommands.removeFirst();
				}						
			}	
			if (fileHandler.endOfFile()) {
				return null;
			} else {
				String entry;
				do {	
					if(previousLine.length() > 0){
						entry = previousLine + fileHandler.readLine();						
						if(incomplete)
							break;						
					}
					entry = fileHandler.readLine();
					if (entry == null) {
						return null;
					}
				} while (!(entry.contains("<")));
				int start = entry.indexOf("<");				
				if(start < 0)
					return getNextCommand();
				start++;
				int end = entry.indexOf(">",start);				
				if(end < 0){
					previousLine = entry;
					incomplete = true;					
					return getNextCommand();
				}else{
					incomplete = false;
					previousLine = "";
				}
				int currentPosition = entry.indexOf(" ",start);				
				if(currentPosition < 0){
					return getNextCommand();
				}					
				String param1 = entry.substring(start, currentPosition);
				String param2 = "";
				String param3 = "";
				CommandType tipo = CommandType.UNKNOWN;
				String target = "";
				String value = "";				
				if(param1.equals("invoke")){
					int posUrl = entry.indexOf("url=");
					start = entry.indexOf("\"", posUrl);
					start++;
					end = entry.indexOf("\"", start);				
					param2 = entry.substring(start,end);
					returnInputs = true;
					return createOPEN(param2);					
				}else if (param1.equals("setInputField")){
					currentPosition = entry.indexOf("name=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					param2 = entry.substring(start,end);
					currentPosition = entry.indexOf("value=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					if(start > 0 && end > 0 )
						param3 = entry.substring(start,end);	
					storedCommands.addLast(createINPUT(param2, param3));
					return getNextCommand();
				}else if(param1.equals("verifyText")){
					currentPosition = entry.indexOf("text=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					param2 = entry.substring(start,end);					
					currentPosition = entry.indexOf("description=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					param3 = entry.substring(start,end);	
					if (param1.contains("Title")) {						
						storedCommands.addLast(createASSERT("title", param2));
						return getNextCommand();
					} else {
						return createASSERT("conteúdo", param2);
					}	
				}else if (param1.equals("clickButton")){
					currentPosition = entry.indexOf("label=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					param2 = entry.substring(start,end);
					returnInputs = true;				
					return createOPEN("http://"+urlBase+"/regex");
				}else if (param1.equals("alert")){
					currentPosition = entry.indexOf("description=");
					start = entry.indexOf("\"", currentPosition);
					start++;
					end = entry.indexOf("\"", start);
					param2 = entry.substring(start,end);	
					if (param1.endsWith("Title")) {
						storedCommands.addLast(createASSERT("title", param2));
						return getNextCommand();
					} else {
						return createASSERT("conteúdo", param2);
					}					
				}else if (param1.equals("clickLink")){
					start = end = 0;
					currentPosition = entry.indexOf("label=");
					if(currentPosition > 0){
						start = entry.indexOf("\"", currentPosition);
						start++;
						end = entry.indexOf("\"", start);
					}
					if(start > 0 && end > 0 ){
						param2 = entry.substring(start,end);
					}else{						
						currentPosition = entry.indexOf("href=");						
						start = entry.indexOf("\"", currentPosition);
						start++;
						end = entry.indexOf("\"", start);
						if(start > 0 && end > 0 ){
							param2 = entry.substring(start,end);
							returnInputs = true;							
							return createOPEN("href='"+param2+"'");
						}
					}					
					returnInputs = true;				
					return createOPEN("http://"+urlBase+"/regex");					
				}
				return new Command(tipo, target, value);
			}
		}
		
		/**
		 * Método que extrai a UrlBase a partir de uma url fornecida
		 * @param url Url acessada 
		 * @return String - Url base da página em questão
		 */
		private String extractUrlBase(String url) {
			String urlBase;
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
			}else if(url.contains("href")){				
				start = url.indexOf("'");
				start++;
				String path = url.substring(start, url.indexOf("'",start));				
				pathList.add(urlPath+"/"+path);
				return urlPath+"/"+path;
			}else {
				start = 0;
			}
			int end = url.indexOf('?');
			if (start >= 0) {
				if (end > 0) {
					String inputs = url.substring(end + 1, url.length());
					String[] entries = inputs.split("&");
					for (String entrada : entries) {
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
				}
				if (start + 1 < end)  {				
					if(url.charAt(end-1) == '/')
						end--;
					else if (url.charAt(end-2) == ')'){
						end = end - 3;
						pathList.add(urlPath+"/"+url.substring(start+1,end));
						return urlPath+"/"+url.substring(start+1,end);
					}
					else if (url.charAt(end-1) == ']'){
						end = end - 2;			
						pathList.add(urlPath+"/"+url.substring(start+1,end));
						return urlPath+"/"+url.substring(start+1,end);
					}
				
				}
				String returnedUrl = url.substring(start + 1, end);
				
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


