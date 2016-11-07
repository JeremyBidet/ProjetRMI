import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ParkImpl extends UnicastRemoteObject implements Park {

	private final List<User> users;
	private final List<Vehicle> vehicles;
	
	protected ParkImpl() throws RemoteException {
		super();
		this.users = new ArrayList<User>();
		this.vehicles = new ArrayList<Vehicle>();
	}

	@Override
	public boolean addVehicle(Vehicle vehicle, User user) throws RemoteException {
		
		return false;
	}

	@Override
	public boolean removeVehicle(Vehicle vehicle, User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable(Vehicle vehicle) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rentVehicle(Vehicle vehicle, User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean returnVehicle(Vehicle vehicle, User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addToWishList(Vehicle book, User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vehicle[] searchByModel(String model) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle[] searchByYear(int year) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBuyable(Vehicle vehicle) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buy(Vehicle vehicle, User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getPrice(Vehicle vehicle) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vehicle[] getVehicles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User connect(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsValidUser(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnect(User user) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
