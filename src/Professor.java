public class Professor implements Role {

	protected Professor() {
		super();
	}

	@Override
	public String getRoleName() {
		return "Professor";
	}

	@Override
	public boolean canAddVehicle() {
		return true;
	}

	@Override
	public boolean canRemoveVehicle() {
		return true;
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
