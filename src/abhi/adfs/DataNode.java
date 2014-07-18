/**
 * 
 */
package abhi.adfs;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Douglas Rew 
 *
 */ 
public interface DataNode extends Remote {
	
	public boolean submit(String filename, String data) throws RemoteException;
	public boolean submitJar(String filename, byte data[], int length) throws RemoteException;
	public boolean remove(String filename) throws RemoteException;
	public String retrieve(String filename) throws RemoteException;
	public boolean isExist(String filename) throws RemoteException;
	public List<String> getFileList() throws RemoteException;
	public boolean registrFileName(String filename) throws RemoteException;
	public boolean ping() throws RemoteException;
	
	// This is for testing
	public void print() throws RemoteException;
}
