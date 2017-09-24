package javarmi.client.core;

import javarmi.server.core.Service;

import java.rmi.Naming;

public class Client {

    public static void main(String[] args) throws Exception {
        Service service = (Service) Naming.lookup("rmiServiceServer");
        System.out.println(service.getLastNews(""));
    }

}
