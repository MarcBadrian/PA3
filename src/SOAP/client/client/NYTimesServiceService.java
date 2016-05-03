
package SOAP.client.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import SOAP.endpoints.ReviewServiceInterface;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NYTimesServiceService", targetNamespace = "http://endpoints.SOAP/", wsdlLocation = "http://localhost:8888/PA3/nytimes?wsdl")
public class NYTimesServiceService
    extends Service
{

    private final static URL NYTIMESSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException NYTIMESSERVICESERVICE_EXCEPTION;
    private final static QName NYTIMESSERVICESERVICE_QNAME = new QName("http://endpoints.SOAP/", "NYTimesServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8888/PA3/nytimes?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NYTIMESSERVICESERVICE_WSDL_LOCATION = url;
        NYTIMESSERVICESERVICE_EXCEPTION = e;
    }

    public NYTimesServiceService() {
        super(__getWsdlLocation(), NYTIMESSERVICESERVICE_QNAME);
    }

    public NYTimesServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), NYTIMESSERVICESERVICE_QNAME, features);
    }

    public NYTimesServiceService(URL wsdlLocation) {
        super(wsdlLocation, NYTIMESSERVICESERVICE_QNAME);
    }

    public NYTimesServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NYTIMESSERVICESERVICE_QNAME, features);
    }

    public NYTimesServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NYTimesServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ReviewServiceInterface
     */
    @WebEndpoint(name = "NYTimesServicePort")
    public ReviewServiceInterface getNYTimesServicePort() {
        return super.getPort(new QName("http://endpoints.SOAP/", "NYTimesServicePort"), ReviewServiceInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReviewServiceInterface
     */
    @WebEndpoint(name = "NYTimesServicePort")
    public ReviewServiceInterface getNYTimesServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://endpoints.SOAP/", "NYTimesServicePort"), ReviewServiceInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NYTIMESSERVICESERVICE_EXCEPTION!= null) {
            throw NYTIMESSERVICESERVICE_EXCEPTION;
        }
        return NYTIMESSERVICESERVICE_WSDL_LOCATION;
    }

}
