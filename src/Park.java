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

public class Park extends UnicastRemoteObject implements IPark {

	private static final long serialVersionUID = 2319278071804545900L;

	private final HashMap<IVehicle, SortedSet<PendingUser>> vehicles = new HashMap<IVehicle, SortedSet<PendingUser>>();
	private final HashMap<IVehicle, IUser> rentedVehicles = new HashMap<IVehicle, IUser>();
	private final HashMap<IVehicle, List<IComment>> vehicleComments = new HashMap<IVehicle, List<IComment>>();

	protected Park() throws RemoteException {
		super();
	}
	
	private boolean loggedIn(String token) {
		if(Authentication.getUser(token) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if (vehicles.containsKey(key)) {
			return false;
		}
		IVehicle v = new Vehicle(matricul, year, model, price);
		vehicles.put(v, new TreeSet<PendingUser>());
		vehicleComments.put(v, new ArrayList<IComment>());
		return true;
	}

	@Override
	public boolean removeVehicle(String token, String matricul) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(vehicles.containsKey(key)) {
			vehicles.remove(key);
			return true;
		}
		return false;
	}

	private synchronized boolean rentVehicle(IVehicle vehicle) throws RemoteException {
		if(vehicles.containsKey(vehicle) && !rentedVehicles.containsKey(vehicle)) {
			if(vehicles.get(vehicle).isEmpty()) {
				return false;
			}
			PendingUser p_user = vehicles.get(vehicle).first();
			rentedVehicles.put(vehicle, p_user.getKey());
			((Vehicle) vehicle).incRented();
			vehicles.get(vehicle).remove(p_user);
			return true;
		}
		return false;
	}

	@Override
	public boolean returnVehicle(String token, String matricul) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(rentedVehicles.containsKey(key)) {
			rentedVehicles.remove(key);
			rentVehicle(key);
			return true;
		}
		return false;
	}

	@Override
	public boolean rentVehicle(String token, String matricul) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(vehicles.containsKey(key)) {
			try {
				IUser user = Authentication.getUser(token);
				vehicles.get(key).add(new PendingUser(user));
				return rentVehicle(key);
			} catch(NoSuchElementException e) {
				throw new RemoteException("You are not logged in!");
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}

	@Override
	public List<IVehicle> searchByModel(String model) throws RemoteException {
		return vehicles.keySet().stream().filter(v -> {
			try {
				return v.getModel().equals(model);
			} catch (RemoteException e) {
				return false;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public List<IVehicle> searchByYear(int year) throws RemoteException {
		return vehicles.keySet().stream().filter(v -> {
			try {
				return v.getYear() == year;
			} catch (RemoteException e) {
				return false;
			}
		}).collect(Collectors.toList());
	}

	private boolean isBuyable(IVehicle vehicle) throws RemoteException {
		if(rentedVehicles.containsKey(vehicle)) {
			return false;
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return vehicle.getYear() == year && vehicle.getNbRented() > 0;
	}

	@Override
	public boolean buy(String token, String matricul) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicles.containsKey(v)) {
			IVehicle vehicle = vehicles.keySet().stream().filter(ve -> ve.equals(v)).findAny().get();
			if(isBuyable(vehicle)) {
				//TODO: link here with web services bank and buy then remove vehicle from rent
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
	public List<IVehicle> getVehicles(String token) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		return new ArrayList<>(vehicles.keySet());
	}

	@Override
	public List<IComment> getComments(String token, String matricul) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicleComments.containsKey(v)) {
			return vehicleComments.get(v);
		}
		throw new RemoteException("This vehicle does not exist!");
	}

	@Override
	public boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException {
		if(!loggedIn(token)) {
			throw new RemoteException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicleComments.containsKey(v)) {
			try {
				IUser user = Authentication.getUser(token);
				vehicleComments.get(v).add(new Comment(mark, user, comment));
				return true;
			} catch(NoSuchElementException e) {
				throw new RemoteException("You are not logged in!");
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}

}
