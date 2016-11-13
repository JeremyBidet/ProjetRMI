
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class Vehicle extends UnicastRemoteObject implements IVehicle {

	private static final long serialVersionUID = -1457399854779835892L;

	private final String matricul;
	private final int year;
	private final String model;
	private int rented;
	private double price;

	public Vehicle(String matricul) throws RemoteException {
		this.matricul = matricul;
		this.year = 0;
		this.model = null;
		this.price = 0;
		this.rented = 0;
	}
	
	public Vehicle(String matricul, int year, String model, double price) throws RemoteException {
		this.matricul = matricul;
		this.year = year;
		this.model = Objects.requireNonNull(model, "Invalide model name");
		this.price = price;
		this.rented = 0;
	}

	@Override
	public String getMatricul() throws RemoteException {
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
	
	public void incRented() {
		this.rented++;
	}

	@Override
	public String toString() {
		return "Vehicule : " + model + " " + year;
	}

	@Override
	public boolean equals(Object o) {
		return ((Vehicle) o).matricul.equals(this.matricul);
	}

	@Override
	public int hashCode() {
		return this.matricul.hashCode();
	}

}
