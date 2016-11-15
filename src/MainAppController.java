import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainAppController {
	
	/**
	 * Search pane
	 */
	@FXML
	private TextField searchModel;
	
	@FXML
	private TextField searchYear;
	
	@FXML
	private TextField searchMatricul;
	
	@FXML
	private Button searchButton;
	
	/**
	 * Tab pane
	 */
	@FXML
	private Tab tabVehicle;
		/**
		 * Vehicle tab content
		 */
		@FXML
		private TableView<IVehicle> vehicleView;
		
		@FXML
		private TableColumn<IVehicle, String> vehicleModel;
		
		@FXML
		private TableColumn<IVehicle, Integer> vehicleYear;
		
		@FXML
		private TableColumn<IVehicle, String> vehicleMatricul;
		
		@FXML
		private TableColumn<IVehicle, Integer> vehiclePending;
		
		@FXML
		private TableColumn<IVehicle, Button> vehicleAction;
		
	@FXML
	private Tab tabRent;
		/**
		 * Rent tab content
		 */
		@FXML
		private TableView<IVehicle> rentView;
		
		@FXML
		private TableColumn<IVehicle, String> rentModel;
		
		@FXML
		private TableColumn<IVehicle, Integer> rentYear;
		
		@FXML
		private TableColumn<IVehicle, String> rentMatricul;
		
		@FXML
		private TableColumn<IVehicle, Button> rentAction;
		
	@FXML
	private Tab tabManagement;
		/**
		 * Management tab content
		 */
		@FXML
		private TableView<IVehicle> managementView;
		
		@FXML
		private TableColumn<IVehicle, String> managementModel;
		
		@FXML
		private TableColumn<IVehicle, Integer> managementYear;
		
		@FXML
		private TableColumn<IVehicle, String> managementMatricul;
		
		@FXML
		private TableColumn<IVehicle, Button> managementAction;
		
		@FXML
		private TextField addModel;
		
		@FXML
		private TextField addYear;
	
		@FXML
		private TextField addMatricul;
		
		@FXML
		private TextField addPrice;
		
		@FXML
		private Button addButton;

}
