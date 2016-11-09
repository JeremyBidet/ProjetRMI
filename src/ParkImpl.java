import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ParkImpl extends UnicastRemoteObject implements Park {

	private static final long serialVersionUID = 2319278071804545900L;

	private final List<User> users = new ArrayList<>();
	private final HashMap<Vehicle, SortedSet<PendingUser>> vehicles = new HashMap<Vehicle, SortedSet<PendingUser>>();
	private final HashMap<Vehicle, User> rentedVehicles = new HashMap<Vehicle, User>();
	private final HashMap<Vehicle, List<Comment>> vehicleComments = new HashMap<Vehicle, List<Comment>>();

	protected ParkImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean addVehicle(String matricul, int year, String model, double price) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if (vehicles.containsKey(key)) {
			return false;
		}
		Vehicle v = new VehicleImpl(matricul, year, model, price);
		vehicles.put(v, new TreeSet<PendingUser>());
		vehicleComments.put(v, new ArrayList<Comment>());
		return true;
	}

	@Override
	public boolean removeVehicle(String matricul) throws RemoteException {
		Vehicle key = new VehicleImpl(matricul);
		if(vehicles.containsKey(key)) {
			vehicles.remove(key);
			return true;
		}
		return false;
	}

	public synchronized boolean rentVehicle(Vehicle vehicle) throws RemoteException {
		if(vehicles.containsKey(vehicle) && !rentedVehicles.containsKey(vehicle)) {
			if(!vehicles.get(vehicle).isEmpty()) {
				return false;
			}
			PendingUser p_user = vehicles.get(vehicle).first();
			rentedVehicles.put(vehicle, p_user.getKey());
			vehicle.rent();
			vehicles.get(vehicle).remove(p_user);
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
		if(vehicles.containsKey(key)) {
			User currentUser = null; // get user session
			try {
				User user = users.stream().filter(u -> u.equals(currentUser)).findAny().get();
				vehicles.get(key).add(new PendingUser(user));
				return rentVehicle(key);
			} catch(NoSuchElementException e) {
				throw new RemoteException("You are not logged in!");
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}

	@Override
	public Vehicle[] searchByModel(String model) throws RemoteException {
		return (Vehicle[]) vehicles.keySet().stream().filter(v -> {
			try {
				return v.getModel().equals(model);
			} catch (RemoteException e) {
				return false;
			}
		}).collect(Collectors.toList()).toArray();
	}

	@Override
	public Vehicle[] searchByYear(int year) throws RemoteException {
		return (Vehicle[]) vehicles.keySet().stream().filter(v -> {
			try {
				return v.getYear() == year;
			} catch (RemoteException e) {
				return false;
			}
		}).collect(Collectors.toList()).toArray();
	}

	private boolean isBuyable(Vehicle vehicle) throws RemoteException {
		if(rentedVehicles.containsKey(vehicle)) {
			return false;
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return vehicle.getYear() == year && vehicle.getNbRented() > 0;
	}

	@Override
	public boolean buy(String matricul) throws RemoteException {
		Vehicle v = new VehicleImpl(matricul);
		if(vehicles.containsKey(v)) {
			Vehicle vehicle = vehicles.keySet().stream().filter(ve -> ve.equals(v)).findAny().get();
			if(isBuyable(vehicle)) {
				// link here with web services bank and buy then remove vehicle from rent
				vehicles.remove(vehicle);
				vehicleComments.remove(vehicle);
				return true;
			} else {
				return false;
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}

	@Override
	public Vehicle[] getVehicles() throws RemoteException {
		return (Vehicle[]) vehicles.keySet().toArray();
	}

	@Override
	public Comment[] getComments(String matricul) throws RemoteException {
		Vehicle v = new VehicleImpl(matricul);
		if(vehicleComments.containsKey(v)) {
			return (Comment[]) vehicleComments.get(v).toArray();
		}
		throw new RemoteException("This vehicle does not exist!");
	}

	@Override
	public boolean addComment(String matricul, String comment, int mark) throws RemoteException {
		Vehicle v = new VehicleImpl(matricul);
		if(vehicleComments.containsKey(v)) {
			User currentUser = null; // get user session
			try {
				User user = users.stream().filter(u -> u.equals(currentUser)).findAny().get();
				vehicleComments.get(v).add(new Comment(mark, user, comment));
				return true;
			} catch(NoSuchElementException e) {
				throw new RemoteException("You are not logged in!");
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}

}
