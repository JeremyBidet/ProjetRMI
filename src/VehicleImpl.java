
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleImpl extends UnicastRemoteObject implements Vehicle {

	private static final long serialVersionUID = -1457399854779835892L;

	private final String matricul;
	private final int year;
	private final String model;
	private int rented;
	private double price;
	private final List<Comment> comments;

	public VehicleImpl(String matricul) throws RemoteException {
		this.matricul = matricul;
		this.year = 0;
		this.model = null;
		this.price = 0;
		this.rented = 0;
		this.comments = null;
	}
	
	public VehicleImpl(String matricul, int year, String model, double price) throws RemoteException {
		this.matricul = matricul;
		this.year = year;
		this.model = Objects.requireNonNull(model, "Invalide model name");
		this.price = price;
		this.rented = 0;
		this.comments = new ArrayList<Comment>();
	}

	@Override
	public String getMatricul() {
		return this.matricul;
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
	public void rent() throws RemoteException {
		this.rented++;
	}

	@Override
	public Comment[] getComment() throws RemoteException {
		return (Comment[]) this.comments.toArray();
	}

	@Override
	public String toString() {
		return "Vehicule : " + model + " " + year;
	}

	@Override
	public boolean equals(Object o) {
		return ((VehicleImpl) o).matricul.equals(this.matricul);
	}

	@Override
	public int hashCode() {
		return this.matricul.hashCode();
	}

}
