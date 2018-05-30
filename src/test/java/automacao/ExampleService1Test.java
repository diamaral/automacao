package automacao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExampleService1Test {

	SoapClient soap = new SoapClient();
	SoapWebService webService = new SoapWebService();

	@Test
	public void testService() {
		String response = soap.sendRequestByString("http://ws-investimentos.hom.sicredi.net:80/PosicaoConsolidadaBean/PosicaoConsolidadaService?WSDL", 
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pos=\"http://sicredi.com.br/investimentos/posicaoConsolidada/ws/v1/PosicaoConsolidada/\">\r\n" + 
						"   <soapenv:Header/>\r\n" + 
						"   <soapenv:Body>\r\n" + 
						"      <pos:buscarPosicaoConsolidada>\r\n" + 
						"         <!--Optional:-->\r\n" + 
						"         <pos:InBuscarPosicaoConsolidada>\r\n" + 
						"            <!--Optional:-->\r\n" + 
						"            <codigoCanal>002</codigoCanal>\r\n" + 
						"            <!--Optional:-->\r\n" + 
						"            <numeroAgencia>0136</numeroAgencia>\r\n" + 
						"            <!--Optional:-->\r\n" + 
						"            <numeroConta>530948</numeroConta>\r\n" + 
						"            <!--Optional:-->\r\n" + 
						"            <tipoOrigemConsulta>CONSULTA</tipoOrigemConsulta>\r\n" + 
						"         </pos:InBuscarPosicaoConsolidada>\r\n" + 
						"      </pos:buscarPosicaoConsolidada>\r\n" + 
						"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>");

		String value = soap.getValuesByTag(response, "nomeProduto").get(0);
		
		value = value.substring(value.indexOf(">") + 1);
		value = value.substring(0, value.indexOf("</"));

		assertEquals("POUPANCA TRADICIONAL", value);
	}

}
