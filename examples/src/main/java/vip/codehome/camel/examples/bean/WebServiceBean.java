package vip.codehome.camel.examples.bean;

import org.apache.camel.Header;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import javax.xml.namespace.QName;
import java.util.UUID;

public class WebServiceBean {
    public String invoke(@Header("allParams")String allParams){
        return allParams;
    }
    public String invoke(@Header("wsdl")String wsdl,@Header("namespace")String namespace,@Header("method")String method){
        JaxWsDynamicClientFactory dcf=JaxWsDynamicClientFactory.newInstance();
        Client client=dcf.createClient(wsdl);
        Endpoint endpoint = client.getEndpoint();
        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), method);
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        if (bindingInfo.getOperation(opName) == null) {
            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                if (method.equals(operationInfo.getName().getLocalPart())) {
                    opName = operationInfo.getName();
                    break;
                }
            }
        }
            try {
            Object[] objects=client.invoke(opName,new Object[]{"beijing",15});
            System.out.println(objects[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return wsdl+","+method;
    }
}
