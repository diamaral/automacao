package automacao.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

import javax.xml.parsers.DocumentBuilderFactory;

public class SoapWebService {

	private HttpURLConnection connection;
	//private static final Logger LOGGER = Logger.getLogger(SoapWebService.class);

	public URL getUrl(String webServiceUrl){
		try {
			URL url = new URL(webServiceUrl);
			return url;
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public HttpURLConnection getConnection(String urlWebservice) {
		try {
			if(isOnline(urlWebservice)) {
				URL url = getUrl(urlWebservice);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				return connection;
			} else throw new MalformedURLException("Connection: URL do Web Service inválida ou offline.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new InvalidParameterException(e.getMessage());
		}
	}

	public byte[] getByPathServiceRequest(String xmlPathRequest) {
		try {
			String request = new String(Files.readAllBytes(Paths.get(xmlPathRequest)));
			return getServiceRequest(request);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public boolean isOnline(String urlWebservice) {
		try {
			URL url = new URL(urlWebservice);
			connection = (HttpURLConnection) url.openConnection();                   

			return (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			connection.disconnect();
		}
	}

	public byte[] getServiceRequest(String request) {
		try {
			int requestLength = (int) request.length();
			byte[] requestBytes = new byte[requestLength];
			InputStream inputStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
			inputStream.read(requestBytes);
			inputStream.close();
			return requestBytes;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public OutputStream setRequestService(HttpURLConnection connection, byte[] requestBytes) {
		try {
			OutputStream requestStream = connection.getOutputStream();
			requestStream.write(requestBytes);
			requestStream.flush();
			return requestStream;	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String setResponseService(HttpURLConnection connection, OutputStream requestStream) {
		try {
			int count;
			byte[] byteBuffer = new byte[16384];
			InputStream responseStream = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			while ((count = responseStream.read(byteBuffer, 0, byteBuffer.length)) > -1) {
				buffer.write(byteBuffer, 0, count);
			}
			buffer.flush();
			requestStream.close();
			responseStream.close();
			return buffer.toString("UTF-8");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connection.disconnect();
		}
		return null;
	}

	public DocumentBuilderFactory getFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		return factory;
	}
}
