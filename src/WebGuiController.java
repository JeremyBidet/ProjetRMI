import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class WebGuiController {
	
	private static Pattern integerPattern = Pattern.compile(Utils.integerRegex);
	private static Pattern doublePattern = Pattern.compile(Utils.doubleRegex);
	
	private String token;
	private String login;
	private final ObservableList<IVehicle> vehiclesData = FXCollections.observableArrayList();

	public void setToken(String token) {
		this.token = token;
	}
	
	public void setLogin(String login) {
		this.login = login;
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
	 * Account Pane
	 */
	@FXML
	private TextField solde;
	
	@FXML
	private TextField depot;
	
	@FXML
	private Button buttonDepot;
	
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
		private TableColumn<IVehicle, Double> vehiclePrice;
		
		@FXML
		private TableColumn<IVehicle, Boolean> vehicleAction;
		
		
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
			Matcher m = WebGuiController.integerPattern.matcher(c.getControlNewText());
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
				ObservableList<IVehicle> _data = this.vehiclesData;
				List<IVehicle> _result = _MainWSClient.park.searchVehiclesBy(this.token, filters);
				_data.clear();
				_data.addAll(_result);
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
		});
		
		this.solde.setAccessibleHelp("");
		this.solde.setTooltip(new Tooltip());
		this.solde.setEditable(false);
		try {
			this.solde.setText(String.valueOf(_MainWSClient.bank.getBalance(this.login, "EUR")));
		} catch (RemoteException e1) {
			javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
		}
		
		this.depot.setAccessibleHelp("");
		this.depot.setTooltip(new Tooltip());
		this.depot.setTextFormatter(new TextFormatter<Double>(c -> {
			if(c.getControlNewText().isEmpty()) {
				return c;
			}
			Matcher m = WebGuiController.doublePattern.matcher(c.getControlNewText());
			if(!m.matches()) {
				return null;
			}
			return c;
		}));
		
		this.buttonDepot.setOnAction(value -> {
			try {
				_MainWSClient.bank.creditAccount(this.login, "EUR", Double.parseDouble(this.depot.getText()));
				this.solde.setText(String.valueOf(_MainWSClient.bank.getBalance(this.login, "EUR")));
			} catch (NumberFormatException e) {
				javax.swing.JOptionPane.showMessageDialog(null, "Depot field only accept numeric values!");
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
		});
		
		this.vehicleView.setItems(vehiclesData);
        this.vehicleModel.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        this.vehicleYear.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        this.vehicleMatricul.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("matricul"));
        this.vehiclePrice.setCellValueFactory(new PropertyValueFactory<IVehicle, Double>("price"));
        this.vehicleAction.setCellValueFactory(new PropertyValueFactory<>(""));
        this.vehicleAction.setCellFactory(value -> {
        	final TableCell<IVehicle, Boolean> cell = new TableCell<IVehicle, Boolean>() {
                final Button btn = new Button("Buy");
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
                            	if(_MainWSClient.park.buy(token, vehicle.getMatricul())) {
									javax.swing.JOptionPane.showMessageDialog(null, "This vehicle is now bought by you!");
								}
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
		
	}

}
