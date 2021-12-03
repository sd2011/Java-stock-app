package System;

import System.Log.LogTransaction;
import System.Log.Logger;
import System.Stocks.Request;
import System.Stocks.Stock;
import System.Stocks.Stocks;
import System.Users.*;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;

public class RizpaSystem implements Serializable {
    private Boolean isLoaded;
    private Stocks stocks;
    private final Logger logger;
    //new!
    private Users users;


    public RizpaSystem(){
        this.isLoaded = false;
        this.stocks = null;
        //new!
        this.users = new Users();

        this.logger = new Logger();

    }


    public void load(String file, String userName) throws Exception {

        System.getProperty("file.separator");
        try {

            File uploadedFile = new File(file);
            JAXBContext jaxbContext = JAXBContext.newInstance(RizpaStockExchangeDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            RizpaStockExchangeDescriptor RSE = (RizpaStockExchangeDescriptor) jaxbUnmarshaller.unmarshal(uploadedFile);


            if(!RSEValid(RSE))
                throw new Exception("File not valid!");

            //if stocks are valid , put in system
            if (this.stocks == null) {
                this.stocks = new Stocks(RSE.getRseStocks());
            } else {
                this.stocks.addStocks(RSE.getRseStocks());
            }



                //store holdings and user
            RseHoldings holdings = RSE.getRseHoldings();

                this.users.addUserHoldings(userName,holdings);


            //flag system is on
            this.isLoaded = true;

        }catch (InputMismatchException | JAXBException e){
            throw new InputMismatchException("No file was found in path\n");
        }
    }

    private boolean RSEValid(RizpaStockExchangeDescriptor RSE) throws ObjectAlreadyActive {
        String err ;

        //can be more efficient
        boolean isItemInStocks = false;
        for (RseItem item : RSE.getRseHoldings().getRseItem()) {
            for (RseStock stock : RSE.getRseStocks().getRseStock()) {
                if (item.getSymbol().equals(stock.getRseSymbol())) {
                    isItemInStocks = true;
                    break;
                }
            }
            if (!isItemInStocks) {
                err = "item with " + item.getSymbol() + "not been found on stocks.\n ";
                throw new InputMismatchException(err);
            }
        }

        for (RseStock stock : RSE.getRseStocks().getRseStock()) {
            for (RseStock otherStock : RSE.getRseStocks().getRseStock()) {
                if (!stock.equals(otherStock) && (otherStock.rseSymbol.equals(stock.rseSymbol)
                        || otherStock.rseCompanyName.equals(stock.rseCompanyName))) {
                    err = "stock with "
                            + (otherStock.rseSymbol.equals(stock.rseSymbol) ?
                            ("symbol: '" + stock.rseSymbol + "' ") :
                            ("company name: '" + stock.rseCompanyName + "' ")) +
                            "exists more then once in this file..\n";
                    throw new ObjectAlreadyActive(err);
                }
            }

        }
        return true;
    }



    public void load (String userName , boolean userType){
        isLoaded = true;
        addUser(userName,userType);
    }
    public boolean isLoaded(){
        return this.isLoaded;
    }



    //
    //stock methods
    //
    public Stocks getStocks(){return this.stocks;}
    public Stock getStock(String symbol) throws NullPointerException {
        return stocks.getStock(symbol.toUpperCase());
    }
    public Stock getStock(int i)  throws NullPointerException  {
        return stocks.getStock(i);
    }

    public String getStockSymbol(int i) {
        return getStock(i).getSymbol();
    }
    public String getStockSymbol(String symbol) {
        return getStock(symbol).getSymbol();
    }

    public int getStocksSize(){
        return stocks.getstocksSize();
    }

    public String getStockCompany(String stockSymbol) {
        return getStock(stockSymbol).getCompanyName();
    }

    public float getStockCurrentPrice(String stockSymbol) {
        return getStock(stockSymbol).getPrice();
    }
    
    public float getStockTransactionsCycle(String stockSymbol) {
        return getStock(stockSymbol).getAllTransactionsCycle();
    }

    public void setStock(String companyName,String symbol, float gate){
        if(this.stocks == null){
            this.stocks = new Stocks(new Stock(symbol.toUpperCase(),companyName,gate));
        } else{
        this.stocks.addStock(new Stock(symbol.toUpperCase(),companyName,gate));
    }
    }

    //
    //Requests methods
    //
    public boolean enterRequest(boolean buyOrSell, String symbol, int amount, float gate, String type, String userName) throws Exception {
        return stocks.addRequest(buyOrSell, symbol, amount, gate, type, getUser(userName));
    }

    public List<Request> getStockAllRequests(String symbol) throws ParseException {

       return this.stocks.getStock(symbol).getAllRequests();
    }
    //
    //Buy requests methods
    //

    public int getStockBuyRequestsSize(String symbol) {
        return getStock(symbol).getRequestsToBuySize();
    }
    public float getStockBuyRequestCycle(String symbol,int i) {
        return getStock(symbol).getRequestToBuyCycle(i);
    }

    public String getStockBuyRequestDate(String symbol, int i) {
        return getStock(symbol).getRequestToBuyDate(i);
    }

    public int getStockBuyRequestAmount(String symbol, int i) {
        return getStock(symbol).getRequestToBuyAmount(i);
    }

    public float getStockBuyRequestGate(String symbol, int i) {
        return getStock(symbol).getRequestToBuyGate(i);
    }
    public String getStockBuyRequestType(String symbol , int i)  {return getStock(symbol).getRequestToBuyType(i); }
    public User getStockBuyRequestUser(String symbol,int i) {return getStock(symbol).getStockBuyRequestUser(i); }
    public String getStockBuyRequestUserName(String symbol,int i) {return getStockBuyRequestUser(symbol,i).getName(); }

    //
    // Sell requests methods
    //
    public int getStockSellRequestsSize(String symbol) {
        return getStock(symbol).getRequestsToSellSize();
    }

    public float getStockSellRequestCycle(String symbol, int i) {
        return getStock(symbol).getRequestToSellCycle(i);
    }

    public String getStockSellRequestDate(String symbol, int i) {
        return getStock(symbol).getRequestToSellDate(i);
    }

    public int getStockSellRequestAmount(String symbol, int i) {
        return getStock(symbol).getRequestToSellAmount(i);
    }

    public float getStockSellRequestGate(String symbol, int i) {
        return getStock(symbol).getRequestToSellGate(i);
    }
    public String getStockSellRequestType(String symbol ,int i)  {return getStock(symbol).getRequestToSellType(i); }
    public User getStockSellRequestUser(String symbol,int i) {return getStock(symbol).getStockSellRequestUser(i); }
    public String getStockSellRequestUserName(String symbol,int i) {return getStockSellRequestUser(symbol,i).getName(); }



    //
    //Transactions methods
    //
    public int getStockTransactionsSize(String symbol) {
        return getStock(symbol).getTransactionsSize();
    }

    public String getStockTransactionDate(String symbol, int i) {
       return  getStock(symbol).getTransactionDate(i);
    }
    public String getStockTransactionDate(String symbol, String date) {
        return  getStock(symbol).getTransactionDate(date);
    }

    public int getStockTransactionAmount(String symbol, int i) {
        return getStock(symbol).getTransactionAmountSold(i);
    }
    public int getStockTransactionAmount(String symbol, String date) {
        return getStock(symbol).getTransactionAmountSold(date);
    }

    public float getStockTransactionGate(String symbol, int i) {
        return getStock(symbol).getTransactionGate(i);
    }
    public float getStockTransactionGate(String symbol, String date) {
        return getStock(symbol).getTransactionGate(date);
    }

    public float getStockTransactionCycle(String symbol,int i) {
        return getStock(symbol).getTransactionCycle(i);
    }
    public float getStockTransactionCycle(String symbol,String date) {
        return getStock(symbol).getTransactionCycle(date);
    }
    public String getStockTransactionType(String symbol , int i)  {return getStock(symbol).getStockTransactionType(i); }
    public String getStockTransactionBuyer(String symbol,int i) {return getStock(symbol).getStockTransactionBuyer(i); }
    public String getStockTransactionSeller(String symbol,int i) {return getStock(symbol).getStockTransactionSeller(i); }



    //
    //user methods
    //

    public Users getUsers(){return this.users;}
    public User getUser(String name) throws NullPointerException {
        return users.getUser(name);
    }
    public User getUser(int i)  throws NullPointerException  {
        return users.getUser(i);
    }

    public void addUser(User user){
        this.users.addUser(user);
    }

    public void addUser(String userName , boolean userType){
        this.users.addUser(new User(userName,userType));
    }

    public void addToUserBalance(String name , float money ){
        this.users.getUser(name).addToBalance(money);
    }

    public String getUserName(int i) {
        return getUser(i).getName();
    }
    public String getUserName(String name) {
        return getUser(name).getName();
    }
    public float getUserBalance(String name){
       return getUser(name).getBalance();
    }

    public int getUsersSize(){
        return users.getUsersSize();
    }
    public boolean getUserType(String name){
        return users.getUser(name).UserType();
    }

    public List<LogTransaction> getUserLogs(String userName){
        if(!this.users.getUser(userName).getNotifications())
            return null;
        return Logger.GetTransactionLogsOfUser(userName);
    }

    public void deleteUserNotifications(String userName){
        Logger.deleteUserLogs(userName);
        this.users.getUser(userName).toggleNotifications(false);
    }
    //
    //holding methods
    //

    public Holdings getUserHoldings(String name){
        return this.users.getUser(name).getHoldings();
    }
    public Item getItem(String name,String symbol) throws NullPointerException {
        return getUserHoldings(name).getItem(symbol);
    }
    public Item getItem(String name,int i)  throws NullPointerException  {
        return getUserHoldings(name).getItem(i);
    }
    public void setItem(String name,String symbol, int amount){
        getUser(name).addItem(symbol,amount);
    }
    public int getHoldingsSize(String name){
        return getUserHoldings(name).getSize();
    }


    //
    // item methods
    //

    public String getUserItemSymbol(String name,int i) {
        return getUserHoldings(name).getItemSymbol(i);

    }
    public String getUserItemSymbol(String name, String symbol) {
        return getUserHoldings(name).getItemSymbol(symbol);

    }

    public int getUserItemQuantity(String name,int i) {
        return getUserHoldings(name).getItemQuantity(i);

    }
    public int getUserItemQuantity(String name, String symbol) {
        return getUserHoldings(name).getItemQuantity(symbol);
    }

        public float getUserItemGate(String name, int i){
            return getStockCurrentPrice(getUserItemSymbol(name,i));
        }

        //
        // actions methods
        //

        public Actions getUserActions(String name){
        return this.users.getUser(name).getActions();
        }
}
