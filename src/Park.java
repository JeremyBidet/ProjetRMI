import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public List<IVehicle> getVehicles(String token) throws AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		return vehicles.keySet().stream().collect(Collectors.toList());
	}
	
	@Override
	public List<IVehicle> getRentedVehicles(String token) throws AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IUser currentUser = Authentication.getUser(token);
		return rentedVehicles.entrySet().stream()
				.filter(e -> e.getValue().equals(currentUser))
				.map(e -> e.getKey())
				.collect(Collectors.toList());
	}

	@Override
	public boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException, AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
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
	public boolean removeVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(vehicles.containsKey(key)) {
			vehicles.remove(key);
			return true;
		}
		throw new ParkException("This vehicle does not exist!");
	}

	private synchronized boolean rentVehicle(IVehicle vehicle) throws ParkException {
		if(vehicles.containsKey(vehicle) && !rentedVehicles.containsKey(vehicle)) {
			if(vehicles.get(vehicle).isEmpty()) {
				return false;
			}
			PendingUser p_user = vehicles.get(vehicle).first();
			rentedVehicles.put(vehicle, p_user.getKey());
			((Vehicle) vehicle).incRented();
			vehicles.get(vehicle).remove(vehicles.get(vehicle).first());
			//notif observer
			return true;
		}
		throw new ParkException("This vehicle does not exist!");
	}

	@Override
	public boolean returnVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(rentedVehicles.containsKey(key)) {
			rentedVehicles.remove(key);
			rentVehicle(key);
			return true;
		}
		throw new ParkException("This vehicle does not exist!");
	}

	@Override
	public boolean rentVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle key = new Vehicle(matricul);
		if(vehicles.containsKey(key)) {
			try {
				IUser user = Authentication.getUser(token);
				vehicles.get(key).add(new PendingUser(user));
				return rentVehicle(key);
			} catch(NoSuchElementException e) {
				throw new AuthenticationException("You are not logged in!");
			}
		}
		throw new ParkException("This vehicle does not exist!");
	}

	@Override
	public int getPendingPosition(String token, String matricul) throws RemoteException, AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		List<PendingUser> pending = new ArrayList<PendingUser>(vehicles.get(new Vehicle(matricul)));
		if(pending.contains(new PendingUser(Authentication.getUser(token)))){
			return pending.indexOf(new PendingUser(Authentication.getUser(token)));
		}
		return pending.size();
	}

	@Override
	public IUser getRental(String token, String matricul) throws RemoteException, AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		return rentedVehicles.get(new Vehicle(matricul));
	}

	@Override
	public List<IVehicle> searchBy(Map<String, Object> filters) throws AuthenticationException {
		final Map<String, Method> methods = Arrays.asList(IVehicle.class.getMethods()).stream().collect(Collectors.toMap(m -> m.getName().replace("get", "").toLowerCase(), m -> m));
		return vehicles.keySet().stream().filter(v -> {
			return filters.entrySet().stream().map(f -> {
				try {
					if(methods.containsKey(f.getKey())) {
						return methods.get(f.getKey()).invoke(v, new Object[0]).equals(f.getValue());
					}
					return false;
				} catch (Exception e) {
					return false;
				}
			}).reduce((b1, b2) -> b1 & b2).get();
		}).collect(Collectors.toList());
	}

	private boolean isBuyable(IVehicle vehicle) throws RemoteException, ParkException {
		if(rentedVehicles.containsKey(vehicle)) {
			throw new ParkException("This vehicle is already rented!");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return vehicle.getYear() == year && ((Vehicle) vehicle).getNbRented() > 0;
	}

	@Override
	public boolean buy(String token, String matricul) throws RemoteException, AuthenticationException, ParkException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicles.containsKey(v)) {
			IVehicle vehicle = vehicles.keySet().stream().filter(ve -> ve.equals(v)).findAny().get();
			if(isBuyable(vehicle)) {
				// TODO: link here with web services bank and buy then remove vehicle from rent
				vehicles.remove(vehicle);
				vehicleComments.remove(vehicle);
				return true;
			} else {
				return false;
			}
		}
		throw new ParkException("This vehicle does not exist!");
	}

	@Override
	public List<IComment> getComments(String token, String matricul) throws RemoteException, AuthenticationException, ParkException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicleComments.containsKey(v)) {
			return vehicleComments.get(v);
		}
		throw new ParkException("This vehicle does not exist!");
	}

	@Override
	public boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException, AuthenticationException {
		if(!loggedIn(token)) {
			throw new AuthenticationException("You are not logged in!");
		}
		IVehicle v = new Vehicle(matricul);
		if(vehicleComments.containsKey(v)) {
			try {
				IUser user = Authentication.getUser(token);
				vehicleComments.get(v).add(new Comment(mark, user, comment));
				return true;
			} catch(NoSuchElementException e) {
				throw new AuthenticationException("You are not logged in!");
			}
		}
		throw new RemoteException("This vehicle does not exist!");
	}
	
}
