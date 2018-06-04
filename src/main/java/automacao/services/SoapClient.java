package automacao.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapClient {
	private SoapWebService soap = new SoapWebService();
	private HttpURLConnection connection;

	public String sendRequestByPath(String webServiceUrl, String XmlPathRequest) {
		connection = soap.getConnection(webServiceUrl);
		byte[] requestBytes = soap.getByPathServiceRequest(XmlPathRequest);
		OutputStream requestStream = soap.setRequestService(connection, requestBytes);
		return soap.setResponseService(connection, requestStream);
	}

	public String sendRequestByString(String webServiceUrl, String XmlString) {
		connection = soap.getConnection(webServiceUrl);
		byte[] requestBytes = soap.getServiceRequest(XmlString);
		OutputStream requestStream = soap.setRequestService(connection, requestBytes);
		return soap.setResponseService(connection, requestStream);
	}

	public void saveResponseInFile(String xmlPathResponse, String response) {
		try {
			PrintWriter writer = new PrintWriter(xmlPathResponse);
			writer.print(response);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean valueExists(String response, String value) {
		return response.contains(value);
	}

	public boolean tagExists(String response, String tagName) {
		return response.contains("<" + tagName + ">");
	}

	public String getResponseMessageCode(String urlWebservice){
		try {
			URL url = new URL(urlWebservice);
			connection = (HttpURLConnection) url.openConnection();                   

			switch(connection.getResponseCode()) {
			case 200:
				return connection.getResponseCode() + " - OK.";
			case 201:
				return connection.getResponseCode() + " - Criado.";
			case 202:
				return connection.getResponseCode() + " - Aceito.";
			case 203:
				return connection.getResponseCode() + " - Não autorizado.";
			case 204:
				return connection.getResponseCode() + " - Nenhum conteúdo.";
			case 205:
				return connection.getResponseCode() + " - Reset.";
			case 206:
				return connection.getResponseCode() + " - Conteúdo parcial.";
			case 207:
				return connection.getResponseCode() + " - Status Múltiplo.";
			case 300:
				return connection.getResponseCode() + " - Múltipla escolha.";
			case 301:
				return connection.getResponseCode() + " - Movido.";
			case 302:
				return connection.getResponseCode() + " - Não encontrado.";
			case 304:
				return connection.getResponseCode() + " - Modificado.";
			case 305:
				return connection.getResponseCode() + " - Use proxy.";
			case 306:
				return connection.getResponseCode() + " - Proxy switch.";
			case 307:
				return connection.getResponseCode() + " - Redirecionamento temporário.";
			case 400:
				return connection.getResponseCode() + " - Requisição inválida.";
			case 401:
				return connection.getResponseCode() + " - Não autorizado.";
			case 402:
				return connection.getResponseCode() + " - Pagamento necessário.";
			case 403:
				return connection.getResponseCode() + " - Proibido.";
			case 404:
				return connection.getResponseCode() + " - Não encontrado.";
			case 405:
				return connection.getResponseCode() + " - Método não permitido.";
			case 406:
				return connection.getResponseCode() + " - Não Aceitável.";
			case 407:
				return connection.getResponseCode() + " - Autenticação de proxy necessária.";
			case 408:
				return connection.getResponseCode() + " - Tempo de requisição esgotou (Timeout).";
			case 409:
				return connection.getResponseCode() + " - Conflito.";
			case 410:
				return connection.getResponseCode() + " - Gone (recurso solicitado não está mais disponível e não estará disponível novamente).";
			case 411:
				return connection.getResponseCode() + " - Comprimento necessário.";
			case 412:
				return connection.getResponseCode() + " - Pré-condição falhou.";
			case 413:
				return connection.getResponseCode() + " - Entidade de solicitação muito grande.";
			case 414:
				return connection.getResponseCode() + " - Pedido-URI Too Long.";
			case 415:
				return connection.getResponseCode() + " - Tipo de mídia não suportado.";
			case 416:
				return connection.getResponseCode() + " - Solicitada de Faixa Não Satisfatória.";
			case 417:
				return connection.getResponseCode() + " - Falha na expectativa.";
			case 418:
				return connection.getResponseCode() + " - Eu sou um bule de chá!";
			case 422:
				return connection.getResponseCode() + " - Entidade improcessável.";
			case 423:
				return connection.getResponseCode() + " - Fechado.";
			case 424:
				return connection.getResponseCode() + " - Falha de Dependência.";
			case 425:
				return connection.getResponseCode() + " - Coleção não ordenada.";
			case 426:
				return connection.getResponseCode() + " - Upgrade Obrigatório.";
			case 450:
				return connection.getResponseCode() + " - Bloqueados pelo Controle de Pais do Windows.";
			case 499:
				return connection.getResponseCode() + " - Cliente fechou Pedido.";
			case 500:
				return connection.getResponseCode() + " - Erro interno do servidor.";
			case 501:
				return connection.getResponseCode() + " - Não implementado.";
			case 502:
				return connection.getResponseCode() + " - Bad Gateway.";
			case 503:
				return connection.getResponseCode() + " - Serviço indisponível.";
			case 504:
				return connection.getResponseCode() + " - Gateway Time-Out.";
			case 505:
				return connection.getResponseCode() + " - HTTP Version not supported.";
			default:
				return connection.getResponseCode() + " - Código informativo.";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Falha na conexão com o servidor. Verifique seu firewall.";
		} finally {
			connection.disconnect();
		}
	}

	public ArrayList<String> getValuesByRegex(String response, String regex) {
		ArrayList<String> values = new ArrayList<String>();
		Pattern regexPattern = Pattern.compile(regex);
		Matcher regexMatcher = regexPattern.matcher(response);
		while(regexMatcher.find()) {
			values.add(regexMatcher.group());
		}
		return values;
	}

	public ArrayList<String> getValuesByTag(String response, String tagName){
		return getValuesByRegex(response, "(\\<" + tagName + "\\>)(.*?)(\\</" + tagName + "\\>)");
	}

	public boolean checkFormatXmlByPath(String xmlPath) throws ParserConfigurationException, SAXException {
		try {
			InputSource input = new InputSource(xmlPath);
			DocumentBuilderFactory factory = soap.getFactory();
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.parse(input);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkFormatXmlByString(String xmlString) throws ParserConfigurationException, SAXException {
		try {
			InputSource input = new InputSource(new StringReader(xmlString));
			DocumentBuilderFactory factory = soap.getFactory();
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.parse(input);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
