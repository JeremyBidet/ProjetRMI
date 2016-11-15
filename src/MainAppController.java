import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainAppController {
	
	private static Pattern integerPattern = Pattern.compile(Utils.integerRegex);
	private static Pattern doublePattern = Pattern.compile(Utils.doubleRegex);
	
	private String token;
	private final ObservableList<IVehicle> vehiclesData = FXCollections.observableArrayList();
	private final ObservableList<IVehicle> rentedVehiclesData = FXCollections.observableArrayList();

	public void setToken(String token) {
		this.token = token;
	}
	
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
	private TabPane tabPane;
	
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
		private TableColumn<IVehicle, Boolean> vehicleAction;
		
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
		private TableColumn<IVehicle, Boolean> rentAction;
		
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
		private TableColumn<IVehicle, Boolean> managementAction;
		
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
		
	@FXML
	public void initialize() {
		
		this.searchModel.setAccessibleHelp("");
		this.searchModel.setTooltip(new Tooltip());
		
		this.searchYear.setAccessibleHelp("");
		this.searchYear.setTooltip(new Tooltip());
		this.searchYear.setTextFormatter(new TextFormatter<Integer>(c -> {
			if(c.getControlNewText().isEmpty()) {
				return c;
			}
			Matcher m = MainAppController.integerPattern.matcher(c.getControlNewText());
			if(!m.matches()) {
				return null;
			}
			return c;
		}));
		
		this.searchMatricul.setAccessibleHelp("");
		this.searchMatricul.setTooltip(new Tooltip());
		
		this.searchButton.setOnAction(value -> {
			Map<String, Object> filters = new HashMap<String, Object>();
			if(! this.searchModel.getText().isEmpty()) {
				filters.put("model", this.searchModel.getText());
			}
			if(! this.searchYear.getText().isEmpty()) {
				filters.put("year", Integer.parseInt(this.searchYear.getText()));
			}
			if(! this.searchMatricul.getText().isEmpty()) {
				filters.put("maricul", this.searchMatricul.getText());
			}
			try {
				Tab selected = this.tabPane.getSelectionModel().getSelectedItem();
				ObservableList<IVehicle> _data;
				_data = selected.getId().equals("tabRent") ? this.rentedVehiclesData : this.vehiclesData;
				_data.clear();
				_data.addAll(_MainClient.park.searchBy(this.token, selected.getId(), filters));
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
		});
		
		this.vehicleView.setItems(vehiclesData);
        this.vehicleModel.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        this.vehicleYear.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        this.vehicleMatricul.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("matricul"));
        this.vehiclePending.setCellValueFactory(value -> {
        	try {
				return new SimpleIntegerProperty(_MainClient.park.getPendingPosition(token, value.getValue().getMatricul())).asObject();
			} catch (RemoteException e) {
				return new SimpleIntegerProperty(-1).asObject();
			} catch (AuthenticationException e) {
				return new SimpleIntegerProperty(-1).asObject();
			}
        });
        this.vehicleAction.setCellValueFactory(new PropertyValueFactory<>(""));
        this.vehicleAction.setCellFactory(value -> {
        	final TableCell<IVehicle, Boolean> cell = new TableCell<IVehicle, Boolean>() {
                final Button btn = new Button("Rent");
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem( item, empty );
                    if(empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setOnAction( event -> {
                            IVehicle vehicle = getTableView().getItems().get(getIndex());
                            try {
								_MainClient.park.rentVehicle(token, vehicle.getMatricul());
							} catch (ParkException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (AuthenticationException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (RemoteException e) {
								javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 
							}
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        
        this.rentView.setItems(rentedVehiclesData);
        this.rentModel.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        this.rentYear.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        this.rentMatricul.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("matricul"));
        this.rentAction.setCellValueFactory(new PropertyValueFactory<>(""));
        this.rentAction.setCellFactory(value -> {
        	final TableCell<IVehicle, Boolean> cell = new TableCell<IVehicle, Boolean>() {
                final Button btn = new Button("Return");
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem( item, empty );
                    if(empty) {
                        setGraphic( null );
                        setText( null );
                    } else {
                        btn.setOnAction( event -> {
                            IVehicle vehicle = getTableView().getItems().get(getIndex());
                            try {
								_MainClient.park.returnVehicle(token, vehicle.getMatricul());
							} catch (ParkException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (AuthenticationException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (RemoteException e) {
								javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 
							}
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        
        this.managementView.setItems(vehiclesData);
        this.managementModel.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        this.managementYear.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        this.managementMatricul.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("matricul"));
        this.managementAction.setCellValueFactory(new PropertyValueFactory<>(""));
        this.managementAction.setCellFactory(value -> {
        	final TableCell<IVehicle, Boolean> cell = new TableCell<IVehicle, Boolean>() {
                final Button btn = new Button("Remove");
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem( item, empty );
                    if(empty) {
                        setGraphic( null );
                        setText( null );
                    } else {
                        btn.setOnAction( event -> {
                            IVehicle vehicle = getTableView().getItems().get(getIndex());
                            try {
								_MainClient.park.removeVehicle(token, vehicle.getMatricul());
							} catch (ParkException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (AuthenticationException e) {
								javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
							} catch (RemoteException e) {
								javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 
							}
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        
        this.addModel.setAccessibleHelp("");
		this.addModel.setTooltip(new Tooltip());
		
		this.addYear.setAccessibleHelp("");
		this.addYear.setTooltip(new Tooltip());
		this.addYear.setTextFormatter(new TextFormatter<Integer>(c -> {
			if(c.getControlNewText().isEmpty()) {
				return c;
			}
			Matcher m = MainAppController.integerPattern.matcher(c.getControlNewText());
			if(!m.matches()) {
				return null;
			}
			return c;
		}));
		
        this.addMatricul.setAccessibleHelp("");
		this.addMatricul.setTooltip(new Tooltip());
		
		this.addPrice.setAccessibleHelp("");
		this.addPrice.setTooltip(new Tooltip());
		this.addPrice.setTextFormatter(new TextFormatter<Double>(c -> {
			if(c.getControlNewText().isEmpty()) {
				return c;
			}
			Matcher m = MainAppController.doublePattern.matcher(c.getControlNewText());
			if(!m.matches()) {
				return null;
			}
			return c;
		}));
		
		this.addButton.setOnAction(value -> {
			try {
				_MainClient.park.addVehicle(this.token, this.addMatricul.getText(), Integer.parseInt(this.addYear.getText()), this.addModel.getText(), Double.parseDouble(this.addPrice.getText()));
				javax.swing.JOptionPane.showMessageDialog(null, "New vehicle added: " + this.addMatricul.getText());
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (NumberFormatException e) {
				javax.swing.JOptionPane.showMessageDialog(null, "Price and Year fields accept only numeric values!");
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			} catch (ParkException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}
		});
		
	}

}
