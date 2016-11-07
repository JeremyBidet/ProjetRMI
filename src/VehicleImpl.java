
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class VehicleImpl extends UnicastRemoteObject implements Vehicle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int year;
	private final String model;
	private int rented;
	private double price;
	private final List<Commentary> com;
	private final PriorityQueue<User> wishlist;
	
	public  VehicleImpl(int year, String model, double price) throws RemoteException{
		this.year = year;
		this.model = Objects.requireNonNull(model, "Invalide model name");
		this.price = price;
		this.rented = 0;
		this.com = new ArrayList<Commentary>();
		this.wishlist = new PriorityQueue<User>();
	}
	
	@Override
	public double getPrice() throws RemoteException {
		return price;
	}
	@Override
	public int getYear() throws RemoteException {
		return year;
	}
	@Override
	public String getModel() throws RemoteException {
		return model;
	}
	@Override
	public int getNbRented() throws RemoteException {
		return rented;
	}
	@Override
	public Commentary[] getComment() throws RemoteException {
		Commentary[] comments = new Commentary[com.size()];
		for(int i=0; i < com.size(); i++){
			comments[i] = com.get(i);
		}
		return comments;
	}
	
	@Override
	public String toString(){
		return "Vehicule : " + model + " " + year;
	}
	
}
