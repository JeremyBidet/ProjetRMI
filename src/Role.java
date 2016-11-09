public interface Role {

	String getRoleName();

	boolean canAddVehicle();

	boolean canRemoveVehicle();

	int getPriority();
}
