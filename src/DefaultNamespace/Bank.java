/**
 * Bank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package DefaultNamespace;

public interface Bank extends java.rmi.Remote {
    public java.lang.String[] getCurrencies() throws java.rmi.RemoteException;
    public boolean currencyExists(java.lang.String currency) throws java.rmi.RemoteException;
    public double getBalance(java.lang.String login, java.lang.String currency) throws java.rmi.RemoteException;
    public void addAccount(java.lang.String login, double balance, java.lang.String name, java.lang.String firstname, java.lang.String currency) throws java.rmi.RemoteException;
    public void deleteAccount(java.lang.String login) throws java.rmi.RemoteException;
    public boolean creditAccount(java.lang.String login, java.lang.String currency, double amount) throws java.rmi.RemoteException;
    public boolean debitAccount(java.lang.String login, java.lang.String currency, double amount) throws java.rmi.RemoteException;
}
