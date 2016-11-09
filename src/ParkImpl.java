import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ParkImpl extends UnicastRemoteObject implements Park {

	private static final long serialVersionUID = 2319278071804545900L;

	private final List<User> users = new ArrayList<>();
	private final HashMap<Vehicle, SortedSet<PendingUser>> vehiclesQueue = new HashMap<Vehicle, SortedSet<PendingUser>>();
	private final HashMap<Vehicle, User> rentedVehicles = new HashMap<Vehicle, User>();

	protected ParkImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean addVehicle(String matricul, int year, String model, double price) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if (vehiclesQueue.containsKey(key)) {
			return false;
		}
		vehiclesQueue.put(new VehicleImpl(matricul, year, model, price), new TreeSet<PendingUser>());
		return true;
	}

	@Override
	public boolean removeVehicle(String matricul) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if(vehiclesQueue.containsKey(key)) {
			vehiclesQueue.remove(key);
			return true;
		}
		return false;
	}

	public synchronized boolean rentVehicle(Vehicle vehicle) throws RemoteException {
		if(vehiclesQueue.containsKey(vehicle) && !rentedVehicles.containsKey(vehicle)) {
			if(!vehiclesQueue.get(vehicle).isEmpty()) {
				return false;
			}
			PendingUser p_user = vehiclesQueue.get(vehicle).first();
			rentedVehicles.put(vehicle, p_user.getKey());
			vehicle.rent();
			vehiclesQueue.get(vehicle).remove(p_user);
			return true;
		}
		return false;
	}

	@Override
	public boolean returnVehicle(String matricul) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if(rentedVehicles.containsKey(key)) {
			rentedVehicles.remove(key);
			rentVehicle(key);
			return true;
		}
		return false;
	}

	@Override
	public boolean rentVehicle(String matricul) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if(vehiclesQueue.containsKey(key)) {
			User user = null; // get user session
			vehiclesQueue.get(key).add(new PendingUser(user));
			return rentVehicle(key);
		}
		throw new RemoteException("This vehicle does not exist!");
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
	public boolean isBuyable(String matricul) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buy(String matricul) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getPrice(String matricul) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vehicle[] getVehicles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
