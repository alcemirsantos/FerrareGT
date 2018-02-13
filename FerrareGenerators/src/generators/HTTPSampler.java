package generators;

import java.util.HashMap;
import java.util.LinkedList;

import functionalTestRepresentation.Field;
import functionalTestRepresentation.Input;

/**
 * Classe correspondente a um HTTPSampler do JMeter
 * @author Ismayle de Sousa Santos
 */
public class HTTPSampler {
	private String URL, path, method, expectedResponse;
	private int limitTime, port;
	private HashMap<Field, Input> hmpFieldInput = new HashMap<Field, Input>();
	private int capture = 0; //0 ->normal; 1-> captura de url e campos hidden
    private LinkedList<String> hiddenFieldsList = new LinkedList<String>(); 

	/**
	 * Construtor Completo
	 * @param url URL da p�gina
	 * @param path Path da p�gina
	 * @param port Porta da p�gina
	 * @param method M�todo de enviar dados da p�gina
	 * @param limitTime tempo de resposta m�ximo da p�gina
	 * @param expectedResponse resposta esperada da p�gina
	 */
    public LinkedList<String> getHiddenFieldsList(){
       return this.hiddenFieldsList;	
    }
    
    public void setHiddenFieldsList(LinkedList<String> hiddenFieldsList){
        this.hiddenFieldsList = hiddenFieldsList;	
     }
    
    public int getCapture(){
    	return this.capture;
    }
    
    /*-1 -> a p�gina usar� os campos hidden da p�gina anterior e ter� um regex para os campos hidden (da pr�x p�gina)
     * 0 -> a pagina usar� os campos hidden da p�gina anterior e n�o ter� regex
     * 1 -> a pagina tem q ter um regex para pegar os campos hidden e para pegar a pr�xima url
     * 2 -> a pagina tem q ter um regex para pegar os campos hidden mas N�O tem regex para pr�xima url
     * */
    public void setCapture(int capture){
    	this.capture = capture;
    }
    
	public HTTPSampler(String url, String path, int port, String method,
			int expectedResponse, String response,int captura) {
		super();
		URL = url;
		this.path = path;
		this.method = method;
		this.port = port;
		this.limitTime = expectedResponse;
		this.expectedResponse = response;
		this.capture = captura;
	}

	public HTTPSampler() {
		this("", "", 80, "GET", 0, "",0);
	}

	/**
	 * M�todo para pegar o tempo de resposta esperado para o HTTPSAMPLER
	 * @return
	 */
	public int getLimitTime() {
		return limitTime;
	}

	/**
	 * M�todo para alterar o tempo de resposta de um HTTPSAMPLER
	 * @param limitTime novo tempo de resposta
	 */
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}

	/**
	 * M�todo para pegar o m�todo de envio de dados usado no HTTPSAMPLER
	 * @return m�todo usado
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * M�todo para alterar o m�todo de envio de dados do HTTPSAMPLER
	 * @param method novo m�todo 
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * M�todo para pegar a path usada no HTTPSAMPLER
	 * @return path usada no HTTPSAMPLER
	 */
	public String getPath() {
		return path;
	}

	/**
	 * M�todo para alterar a path usada no HTTPSAMLER
	 * @param path nova path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * M�todo para pegar a porta usada no HTTPSAMPELR
	 * @return porta usada
	 */
	public int getPort() {
		return port;
	}

	/**
	 * M�todo para alterar a porta usada no HTTPSAMPLER
	 * @param port nova porta
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * M�todo para pegar a resposta de um HTTPSAMPLER
	 * @return resposta esperada
	 */
	public String getExpectedResponse() {
		return expectedResponse;
	}

	/**
	 * M�todo para alterar a resposta esperada de um HTTPSAMPLER
	 * @param expectedResponse nova resposta esperada
	 */
	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}

	/**
	 * M�todo para pegar a url usada no HTTPSAMLER
	 * @return url usada no HTTPSAMPLER
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * M�todo para alterar a url do HTTPSAMPLER
	 * @param url nova url
	 */
	public void setURL(String url) {
		URL = url;
	}
	
	public String getUrlFull() {
		return (URL + "/" + path);
	}

	public void addFieldAndValue(Field field, Input input) {
		hmpFieldInput.put(field, input);
	}
	
	public HashMap<Field, Input> getHmpFieldInput(){
		return hmpFieldInput;
	}
	
	public void setHmpFieldInput(HashMap<Field, Input> hmpFieldInput){
		this.hmpFieldInput = hmpFieldInput;
	}
}
