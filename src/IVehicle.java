
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVehicle extends Remote {

	String getMatricul() throws RemoteException;

	double getPrice() throws RemoteException;

	int getYear() throws RemoteException;

	String getModel() throws RemoteException;
	
	//ajouter une liste des IUsers
	//ajouter subscribe (ajouter ustilisateur dans la liste)
	//unsubscribe (suppression de l'utilisateur)
	// onChangeValue(Affecter la nouvelle valeur de la disponibilité puis parcouris la liste des users puis 
	//appel à la méthode définie dans la classe user "newValue" )

}
