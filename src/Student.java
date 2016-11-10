public class Student implements Role {

	protected Student() {
		super();
	}

	@Override
	public String getRoleName() {
		return "Student";
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
		return 2;
	}

}
