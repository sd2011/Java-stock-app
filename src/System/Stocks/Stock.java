package System.Stocks;

import System.Log.Logger;
import System.RseStock;
import System.Users.User;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock implements Serializable {
    private final String symbol;
    private final String companyName;
    private float price;
    private final List<Request> requestsToBuy;
    private final List<Request> requestsToSell;
    private final List<Transaction> transactions;
    private float transactionsCycle;


    public Stock(String symbol, String companyName, float price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.transactionsCycle = 0;
        this.requestsToBuy = new ArrayList<>();
        this.requestsToSell = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public Stock(RseStock stock) {
        this.symbol = stock.getRseSymbol();
        this.companyName = stock.getRseCompanyName();
        this.price = stock.getRsePrice();
        this.transactionsCycle = 0;
        this.requestsToBuy = new ArrayList<>();
        this.requestsToSell = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    //
    //getters for stock
    //
    public String getSymbol() {
        return this.symbol;

    }

    public String getCompanyName() {
        return this.companyName;
    }

    public float getPrice() {
        return this.price;
    }

    public float getAllTransactionsCycle() {
        return this.transactionsCycle;
    }


    public int getTransactionsSize() {
        return this.transactions.size();
    }

    public int getRequestsToBuySize() {
        return this.requestsToBuy.size();
    }

    public int getRequestsToSellSize() {
        return this.requestsToSell.size();
    }

    public Transaction getTransaction(int i) {
        return this.transactions.get(i);
    }

    public Transaction getTransaction(String date) {
        return this.transactions.stream()
                .filter(transaction -> date.equals((transaction.getDate())))
                .findAny()
                .orElse(null);
    }

    //
    //request setter
    //


    //
    //Getters for transactions
    //
    public String getTransactionDate(int i) {
        return getTransaction(i).getDate();
    }

    public String getTransactionDate(String date) {
        return getTransaction(date).getDate();
    }

    public int getTransactionAmountSold(int i) {
        return getTransaction(i).getAmount();
    }

    public int getTransactionAmountSold(String date) {
        return getTransaction(date).getAmount();
    }

    public float getTransactionGate(int i) {
        return getTransaction(i).getGate();
    }

    public float getTransactionGate(String date) {
        return getTransaction(date).getGate();
    }

    public float getTransactionCycle(int i) {
        return getTransaction(i).getCycle();
    }

    public float getTransactionCycle(String date) {
        return getTransaction(date).getCycle();
    }

    public String getStockTransactionType(int i) {
        return transactions.get(i).getType().name();
    }

    public String getStockTransactionBuyer(int i) {
        return transactions.get(i).getBuyer();
    }

    public String getStockTransactionSeller(int i) {
        return transactions.get(i).getSeller();
    }

    //
    // Getters for buy requests
    //
    public float getRequestToBuyCycle(int i) {
        return requestsToBuy.get(i).getGate() * requestsToBuy.get(i).getAmount();
    }

    public String getRequestToBuyDate(int i) {
        return requestsToBuy.get(i).getDate();
    }

    public int getRequestToBuyAmount(int i) {
        return requestsToBuy.get(i).getAmount();
    }

    public float getRequestToBuyGate(int i) {
        return requestsToBuy.get(i).getGate();
    }

    public String getRequestToBuyType(int i) {
        return requestsToBuy.get(i).getType().name();
    }

    public User getStockBuyRequestUser(int i) {
        return requestsToBuy.get(i).getUser();
    }


    //
    // Getters for sell requests
    //
    public float getRequestToSellCycle(int i) {
        return requestsToSell.get(i).getGate() * requestsToSell.get(i).getAmount();
    }

    public String getRequestToSellDate(int i) {
        return requestsToSell.get(i).getDate();
    }

    public int getRequestToSellAmount(int i) {
        return requestsToSell.get(i).getAmount();
    }

    public float getRequestToSellGate(int i) {
        return requestsToSell.get(i).getGate();
    }

    public String getRequestToSellType(int i) {
        return requestsToSell.get(i).getType().name();
    }

    public User getStockSellRequestUser(int i) {
        return requestsToSell.get(i).getUser();
    }


    //
    // stock methods
    //

    public List<Request> getAllRequests() throws ParseException {

        List<Request> requests = new ArrayList<>();

        int i=0;
        int k=0;

        Date dateBuy = null;
        Date dateSell =null;
        Request reqBuy = null;
        Request reqSell= null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

        while(i< this.requestsToBuy.size() && k < this.requestsToSell.size()){

            reqBuy =  this.requestsToBuy.get(i);
            dateBuy = sdf.parse(reqBuy.getDate());
            reqSell = this.requestsToSell.get(k);
            dateSell = sdf.parse(reqSell.getDate());

            if(dateBuy.after(dateSell)){
                requests.add(reqBuy);
                i++;
            }
            else{
                requests.add(reqSell);
                k++;

            }
        }
        while(i< this.requestsToBuy.size()){
            reqBuy = this.requestsToBuy.get(i);
            requests.add(reqBuy);
            i++;

        }
        while (k < this.requestsToSell.size()){
            reqSell = this.requestsToSell.get(k);
            requests.add(reqSell);
            k++;
        }

        return requests;
    }

    public boolean addRequest(boolean buyOrSell, int amount, float gate, String type, User user) {
        Request req = new Request(type, buyOrSell, amount, gate, user);
        Request.Type reqType = req.getType();
        boolean isMKT = reqType == Request.Type.MKT;
        boolean isIOC = reqType == Request.Type.IOC;
        if(reqType == Request.Type.FOK && !canFOK(req))
            return false;

            //if user sell
            if(!buyOrSell)  user.addAmountOnHold(this.symbol,amount);
          List<Request> currentReqList = buyOrSell ? requestsToBuy : requestsToSell;
          List<Request> listToCompare = buyOrSell ? requestsToSell : requestsToBuy;


                    currentReqList.add(req);
                    while (listToCompare.size() > 0 && (doesGatesMatch(req.getGate(),buyOrSell,listToCompare,0) || isMKT) && req.getAmount() > 0) {
                       if(isMKT){
                           Request otherReq = listToCompare.get(0);
                           req.setGate(otherReq.getGate());
                       }
                        makeDeal(req, listToCompare.get(0));
                    }
                    if (req.getAmount() > 0) {
                        if (isIOC) {
                            removeIOC(req, currentReqList);
                        } else {
                            sortTheList(req, currentReqList);
                        }
                        return false;
                    }
                //make sure return every case

        return true;
    }

    private void removeIOC(Request req, List<Request> reqList) {
        reqList.remove(reqList.size() - 1);
        if(!req.getBuyOrSell())
            req.getUser().addAmountOnHold(this.symbol, -req.getAmount());
    }

    private boolean doesGatesMatch(float gate, boolean buyOrSell, List<Request> listToCompare,int i) {
       return buyOrSell ?  (gate >= listToCompare.get(i).getGate() ):(gate <= listToCompare.get(i).getGate());
    }

    private boolean canFOK(Request req) {
        boolean buyOrSell = req.getBuyOrSell();
        float gate = req.getGate();
        int i =0;
        int amount = req.getAmount();
        List<Request> reqList = buyOrSell ? this.requestsToSell : this.requestsToBuy;

        while(reqList.size() > i && doesGatesMatch(gate,buyOrSell,reqList,i)  && amount > 0){
            amount -= reqList.get(i).getAmount();
            i++;
        }
        return amount<=0;
    }


    private void sortTheList(Request req, List<Request> reqList) {
        int i = reqList.size() - 2;
        if (i < 0) return;

        while (req.getBuyOrSell() ? (req.getGate() > reqList.get(i).getGate()) : (req.getGate() < reqList.get(i).getGate())) {
            i--;
            if (i < 0) break;
        }
        reqList.add(i + 1, req);
        reqList.remove(reqList.size() - 1);
    }

    private void makeDeal(Request reqA, Request reqB) {
        int amountA = reqA.getAmount();
        int amountB = reqB.getAmount();

        User reqAUser = reqA.getUser();
        User reqBUser = reqB.getUser();

        String AName = reqAUser.getName();
        String BName = reqBUser.getName();
        String buyer = reqA.getBuyOrSell() ? AName : BName;
        String seller = reqA.getBuyOrSell() ? BName : AName;

        Transaction trans = new Transaction(amountA < amountB ? reqA : reqB, buyer, seller);

        transactions.add(trans);

        reqAUser.updateStatus(this.symbol, reqA.getBuyOrSell(), trans);
        reqBUser.updateStatus(this.symbol, reqB.getBuyOrSell(), trans);

        reqA.downAmount(amountB);
        reqB.downAmount(amountA);

        if (reqA.getAmount() <= 0) removeFromReqList(reqA, reqA.getBuyOrSell() ? requestsToBuy : requestsToSell);
        if (reqB.getAmount() <= 0) removeFromReqList(reqB, reqB.getBuyOrSell() ? requestsToBuy : requestsToSell);

        this.transactionsCycle += transactions.get(transactions.size() - 1).getCycle();


        Logger.addTransactionLog(
                trans,
                getSymbol(),
                reqA.getBuyOrSell() ? reqA.getAmount() : reqB.getAmount(),
                reqA.getBuyOrSell() ? reqB.getAmount() : reqA.getAmount(),
                reqA.getBuyOrSell() ? reqAUser: reqBUser,
                reqA.getBuyOrSell() ? reqBUser: reqAUser
        );


    }

    private void removeFromReqList(Request req, List<Request> requests) {
        requests.remove(req);

    }


}



