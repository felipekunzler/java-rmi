package javarmi.server;

import javarmi.core.Service;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server extends DefaultService {

    protected Server() throws RemoteException {
    }

    public static void main(String[] args) throws Exception {
        // Call rmiregistry with the Service interface on the classpath first.
        // rmiregistry -J-classpath -J/Users/i851309/projects/git/java-rmi/core/out/production/classes
        Service service = new DefaultService();
        Naming.rebind("rmiServiceServer", service);
        // Stops all RMI non-daemon threads
        //UnicastRemoteObject.unexportObject(service, true);
    }

}
