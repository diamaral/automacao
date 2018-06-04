package automacao.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import automacao.SoapClient;
import automacao.SoapWebService;

public class ExampleServiceTest1 {
	SoapClient soap = new SoapClient();
	SoapWebService webService = new SoapWebService();

	@Test
	public void testService() {
		String response = soap.sendRequestByString("http://localhost:7001/HelloWorldBean/HelloWorldService?WSDL", 
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://sicredi.com.br/devops/poc/ws/v1/helloWorld/\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <hel:echo>\r\n" + 
						"         <hel:InEcho>\r\n" + 
						"            <hel:msg>Hello-World</hel:msg>\r\n" + 
						"         </hel:InEcho>\r\n" + 
						"      </hel:echo>\r\n" + 
						"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>");

		String value = soap.getValuesByTag(response, "dspcws:msg").get(0);

		value = value.substring(value.indexOf(">") + 1);
		value = value.substring(0, value.indexOf("</"));

		assertEquals("Hello-World >", value);
	}
}
