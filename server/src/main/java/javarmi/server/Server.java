package javarmi.server;

import javarmi.core.Service;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server extends DefaultService {

    protected Server() throws RemoteException {
    }

    public static void main(String[] args) throws Exception {
        // Call rmiregistry on ~/projects/git/java-rmi/out/production/java-rmi first.
        // rmiregistry -J-classpath -J/Users/i851309/projects/git/java-rmi/out/production/java-rmi
        Service service = new DefaultService();
        Naming.rebind("rmiServiceServer", service);
        //UnicastRemoteObject.unexportObject(service, true);
    }

}
