package DefaultNamespace;

public class BankProxy implements DefaultNamespace.Bank {
  private String _endpoint = null;
  private DefaultNamespace.Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new DefaultNamespace.BankServiceLocator()).getBank();
      if (bank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bank != null)
      ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public DefaultNamespace.Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public java.lang.String[] getCurrencies() throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getCurrencies();
  }
  
  public boolean currencyExists(java.lang.String currency) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.currencyExists(currency);
  }
  
  public double getBalance(java.lang.String login, java.lang.String currency) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getBalance(login, currency);
  }
  
  public void addAccount(java.lang.String login, double balance, java.lang.String name, java.lang.String firstname, java.lang.String currency) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.addAccount(login, balance, name, firstname, currency);
  }
  
  public void deleteAccount(java.lang.String login) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.deleteAccount(login);
  }
  
  public boolean creditAccount(java.lang.String login, java.lang.String currency, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.creditAccount(login, currency, amount);
  }
  
  public boolean debitAccount(java.lang.String login, java.lang.String currency, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.debitAccount(login, currency, amount);
  }
  
  
}