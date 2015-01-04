/*
Retrieving information about the current Game
 * ---------------------------------------------
 * int getGameID()
 *  - returns the id of current game or -1 if no game is currently plaing
 *
 * getServerTime()
 *  - returns the current server time in milliseconds
 *
 * getGameTime()
 *  - returns the time from start of game in milliseconds
 *
 * getGameTimeLeft()
 *  - returns the time left in the game in milliseconds
 *
 * getGameLength()
 *  - returns the game length in milliseconds
 *
 * int getAuctionNo()
 *  - returns the number of auctions in TAC
 *
 * int getClientPreference(int client, int type)
 *  - returns the clients preference for the specified type
 *   (types are TACAgent.{ARRIVAL, DEPARTURE, HOTEL_VALUE, E1, E2, E3}
 *
 * int getAuctionFor(int category, int type, int day)
 *  - returns the auction-id for the requested resource
 *   (categories are TACAgent.{CAT_FLIGHT, CAT_HOTEL, CAT_ENTERTAINMENT
 *    and types are TACAgent.TYPE_INFLIGHT, TACAgent.TYPE_OUTFLIGHT, etc)
 *
 * int getAuctionCategory(int auction)
 *  - returns the category for this auction (CAT_FLIGHT, CAT_HOTEL,
 *    CAT_ENTERTAINMENT)
 *
 * int getAuctionDay(int auction)
 *  - returns the day for this auction.
 *
 * int getAuctionType(int auction)
 *  - returns the type for this auction (TYPE_INFLIGHT, TYPE_OUTFLIGHT, etc).
 *
 * int getOwn(int auction)
 *  - returns the number of items that the agent own for this
 *    auction
 *
 * Submitting Bids
 * ---------------------------------------------
 * void submitBid(Bid)
 *  - submits a bid to the tac server
 *
 * void replaceBid(OldBid, Bid)
 *  - replaces the old bid (the current active bid) in the tac server
 *
 *   Bids have the following important methods:
 *    - create a bid with new Bid(AuctionID)
 *
 *   void addBidPoint(int quantity, float price)
 *    - adds a bid point in the bid
 *
 * Help methods for remembering what to buy for each auction:
 * ----------------------------------------------------------
 * int getAllocation(int auctionID)
 *   - returns the allocation set for this auction
 * void setAllocation(int auctionID, int quantity)
 *   - set the allocation for this auction
 *
 *
 * Callbacks from the TACAgent (caused via interaction with server)
 *
 * bidUpdated(Bid bid)
 *  - there are TACAgent have received an answer on a bid query/submission
 *   (new information about the bid is available)
 * bidRejected(Bid bid)
 *  - the bid has been rejected (reason is bid.getRejectReason())
 * bidError(Bid bid, int error)
 *  - the bid contained errors (error represent error status - commandStatus)
 *
 * quoteUpdated(Quote quote)
 *  - new information about the quotes on the auction (quote.getAuction())
 *    has arrived
 * quoteUpdated(int category)
 *  - new information about the quotes on all auctions for the auction
 *    category has arrived (quotes for a specific type of auctions are
 *    often requested at once).

 * auctionClosed(int auction)
 *  - the auction with id "auction" has closed
 *
 * transaction(Transaction transaction)
 *  - there has been a transaction
 *
 * gameStarted()
 *  - a TAC game has started, and all information about the
 *    game is available (preferences etc).
 *
 * gameStopped()
 *  - the current game has ended
 *


*/

package se.sics.tac.aw;
import se.sics.tac.util.ArgEnumerator;
import javax.swing.Timer;
import java.util.logging.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.*;

public class DummyAgent extends AgentImpl {

	private static final Logger log = 
			Logger.getLogger(DummyAgent.class.getName());

	private float[] prices;
	private ArrayList<Float> historyFly = new ArrayList<Float>();
	private Timer flyTimer;
	private ArrayList<Float> historyEnt = new ArrayList<Float>();	
	private Timer bidTimer; 
	private int hotelIncF = 0;
	MoveTheClient moveClient = new MoveTheClient(); 
	GetTheBidValue getBidVal = new GetTheBidValue();
  //client declaration
	char[] client_One = new char[6];
	char[] client_Two = new char[6];
	char[] client_Three = new char[6];
	char[] client_Four = new char[6];
	char[] client_Five = new char[6];
	char[] client_Six = new char[6];
	char[] client_Seven = new char[6];
	char[] client_Eight = new char[6];

	protected void init(ArgEnumerator args) {
		prices = new float[TACAgent.getAuctionNo()];
	}
	public void auctionClosed(int auction) {
		int allocation = agent.getAllocation(auction) - agent.getOwn(auction);
		boolean needToShift[] = new boolean[8];
		int shifted = 0;
		if(allocation > 0 ) {
			int day = TACAgent.getAuctionDay(auction);
			int type = TACAgent.getAuctionType(auction);
			boolean needReallocatedClient[] = new boolean[8]; 
			char theDayCharcter = (type == TACAgent.TYPE_CHEAP_HOTEL) ? 'C' : 'E';
			int k = 0;
			char[] storeClientArr [] = {client_One, client_Two, client_Three, client_Four, 
										client_Five, client_Six, client_Seven, client_Eight};
			
			for(k = 0; k<=8; k++){
				char[] tempClientArr = storeClientArr[k].clone();
				for(int j = 0; j<=7; j++){
					if(tempClientArr[day - 1] == theDayCharcter){
						needReallocatedClient[j] = true;
					}else
					{
						needReallocatedClient[j] = false;
					}
				}
			}

			for(int i = 0; i < needReallocatedClient.length; i++) {
				if (shifted < allocation) {
					if(!needReallocatedClient[i]) { 
						needToShift[i] = false;
					} else {
						
						switch(i) {
						case 0:
							moveClient.shiftTheDay(day, client_One, theDayCharcter, type);
							break;
						case 1:
							moveClient.shiftTheDay(day, client_Two, theDayCharcter, type);
							break;
						case 2:
							moveClient.shiftTheDay(day, client_Three, theDayCharcter, type);
							break;
						case 3:
							moveClient.shiftTheDay(day, client_Four, theDayCharcter, type);
							break;
						case 4:
							moveClient.shiftTheDay(day, client_Five, theDayCharcter, type);
							break;
						case 5:
							moveClient.shiftTheDay(day, client_Six, theDayCharcter, type);
							break;
						case 6:
							moveClient.shiftTheDay(day, client_Seven, theDayCharcter, type);
							break;
						case 7:
							moveClient.shiftTheDay(day, client_Eight, theDayCharcter, type);
							break;
						}
						needToShift[i] = false;
					}
				}
				shifted++;

			}

		}
	}
	
	public void quoteUpdated(Quote quote) {
		int auction = quote.getAuction();
		int auctionCategory = TACAgent.getAuctionCategory(auction);
		if (auctionCategory == TACAgent.CAT_HOTEL) {
			hotelIncF++;
			int alloc = agent.getAllocation(auction);
			if (alloc > 0 && quote.hasHQW(agent.getBid(auction)) && quote.getHQW() < alloc) {
				Bid bid = new Bid(auction);
				int type = TACAgent.getAuctionType(auction);

                 //according to two types of hotels, we sent different bid.
				if(type == TACAgent.TYPE_CHEAP_HOTEL) {
					prices[auction] = quote.getAskPrice() + 15f * hotelIncF;
					bid.addBidPoint(alloc, prices[auction]);
				} else {
					prices[auction] = quote.getAskPrice() + 35f * hotelIncF;
					bid.addBidPoint(alloc, prices[auction]);
				}
				agent.submitBid(bid);
			}
		}

		if (auctionCategory == TACAgent.CAT_ENTERTAINMENT) {
			float meanUtilEnt = calMeanUtilEnt(auction);
			
			double stdDeviation = 0;
			historyEnt.add(quote.getAskPrice());
			if(historyEnt.size() > 5) {
				stdDeviation = calStdDeviation(historyEnt);
			}

			int alloc = agent.getAllocation(auction) - agent.getOwn(auction);
			if (alloc != 0 && agent.getGameTimeLeft()> 3.5*60*1000) {
				Bid bid = new Bid(auction);
				if (alloc < 0 && alloc >= -2) {

					double sell = 200 - agent.getGameTime()*1.5*stdDeviation/(double)agent.getGameLength();	
					if(sell < (double)meanUtilEnt){
						sell = meanUtilEnt;
					}

					float fsell = (float)sell;
					prices[auction] = fsell;
				}
				else if (alloc < -2){

					double sell = 200 - agent.getGameTime()*3.2*stdDeviation/(double)agent.getGameLength();	
					if(sell < (double)meanUtilEnt){
						sell = meanUtilEnt;
					}

					float fsell = (float)sell;
					prices[auction] = fsell;

				}
				else {
					double buy = 50 + (double)agent.getGameTime()*1.1*stdDeviation/(double)agent.getGameLength();

					if(buy > (double)meanUtilEnt){
						buy = meanUtilEnt;
					}	

					float fbuy = (float)buy;
					prices[auction] = fbuy;
				}
				bid.addBidPoint(alloc, prices[auction]);
				agent.submitBid(bid);
			}


			if (alloc != 0 && agent.getGameTimeLeft()<= 3.5*60*1000) {
				Bid bid = new Bid(auction);
				if (alloc < 0) {
					double sell = quote.getBidPrice();
					if (sell == 0){
						sell += meanUtilEnt*agent.getGameTimeLeft()/agent.getGameLength();
					}		
					float fsell = (float)sell;
					prices[auction] = fsell;
				}
				else {
					double buy = quote.getAskPrice();
					if(buy > (double)meanUtilEnt){
						buy = meanUtilEnt;
					}


					float fbuy = (float)buy;
					prices[auction] = fbuy;
				}

				bid.addBidPoint(alloc, prices[auction]);
				bid.addBidPoint(-5, 220);
				agent.submitBid(bid);
			}

			if (alloc == 0 && agent.getGameTimeLeft() > 6.5*60*1000) {
				Bid bid = new Bid(auction);
				double buy = (double)agent.getGameTime()*1.35*stdDeviation/(double)agent.getGameLength();
				float fbuy = (float)buy;
				prices[auction] = fbuy;
				bid.addBidPoint(2, prices[auction]);
				agent.submitBid(bid);
			}
		}
	}
  //for entertainment strategy
	private float calMeanUtilEnt(int auction){
		int eType = TACAgent.getAuctionType(auction);
		float utilEnt = 0;
		float totalUtilEnt = 0;
		
		for (int i = 0; i < 8; i++) {
			if(eType == TACAgent.TYPE_ALLIGATOR_WRESTLING) {
				utilEnt = agent.getClientPreference(i, TACAgent.E1);
			}
			if(eType == TACAgent.TYPE_AMUSEMENT) {
				utilEnt = agent.getClientPreference(i, TACAgent.E2);
			}
			if(eType == TACAgent.TYPE_MUSEUM) {
				utilEnt = agent.getClientPreference(i, TACAgent.E3);
			}
			totalUtilEnt += utilEnt;
		}

		float meanUtilEnt = totalUtilEnt/8;	
		return meanUtilEnt;
	}
	//for entertainment strategy
	private float calStdDeviation(ArrayList<Float> priceInHistory) {
		float total = 0;
		float mean = 0;
		float sumSqrtDiff = 0;
		float stdDeviation = 0;
		
		for(int i =0;i<priceInHistory.size()-1;i++)
		{
			float entP = priceInHistory.get(i);
			total += entP;
		}
		mean = total/priceInHistory.size();
		
		for(int i =0;i<priceInHistory.size()-1;i++)
		{
			float entP = priceInHistory.get(i);
			sumSqrtDiff += (entP - mean) * (entP - mean);
		}

		return stdDeviation = (float)Math.sqrt(sumSqrtDiff/(priceInHistory.size()-1));
	}

	public void gameStarted() {
		log.fine("Game " + agent.getGameID() + " started!");
		updateTheClientArr(); 

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				flyrecord();
				updateBids();
			}
		};

		ActionListener flytaskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				flyrecord();
			}
		};

		flyTimer = new Timer(9*1000, flytaskPerformer);
		flyTimer.start();
		bidTimer = new Timer(30*1000, taskPerformer);
		bidTimer.start();
		calculateAllocation();
		sendBids();
	}
	
	public void updateTheClientArr() {
		for(int i = 0; i < 6; i++) {
			client_One[i] = 'x';
			client_Two[i] = 'x';
			client_Three[i] = 'x';
			client_Four[i] = 'x';
			client_Five[i] = 'x';
			client_Six[i] = 'x';
			client_Seven[i] = 'x';
			client_Eight[i] = 'x';
		}
	}

	public void gameStopped() {
		bidTimer.stop();
		flyTimer.stop();
	}
	

	private void sendBids() {
		for (int i = 0, n = TACAgent.getAuctionNo(); i < n; i++) {
			int alloc = agent.getAllocation(i) - agent.getOwn(i);
			float price = -1f;
			switch (TACAgent.getAuctionCategory(i)) {
			case TACAgent.CAT_FLIGHT:
				if (alloc > 0) {
					price = 0 ;
				}
				break;
			case TACAgent.CAT_HOTEL:
				if (alloc > 0) {
					price = getBidVal.gTrueV(TACAgent.getAuctionDay(i), TACAgent.getAuctionType(i));
					prices[i] = price;
				}
				break;
			case TACAgent.CAT_ENTERTAINMENT:
				if (alloc < 0) {
					price = 200;
				} else if (alloc > 0) {
					price = 10;
					prices[i] = 10;
				}
				break;
			default:
				break;
			}
			if (price > 0) {
				Bid bid = new Bid(i);
				bid.addBidPoint(alloc, price);
				agent.submitBid(bid);
			}
		}
	}

  //main part of flight strategy... through observation to see price goes up and down. Try to get these tickets in different time frame. 
	private void updateBids() { 
		boolean checkrate;
		Quote quote;

		for(int i = 0, n = 8; i < n; i++) { 
			quote = agent.getQuote(i);
			int alloc = agent.getAllocation(i) - agent.getOwn(i); 
			checkrate = calculateRate(i,quote.getAskPrice());
			if(alloc > 0 && alloc <=2 && (agent.getGameTime() <= 8*60*1000) )
			{
				if(checkrate == true)
				{
					Bid bid = new Bid(i);
					bid.addBidPoint(alloc, 1000.0f);
					agent.submitBid(bid);
				}

			}	
			if(alloc > 2) {
				Bid bid = new Bid(i);
				bid.addBidPoint(alloc-2, 1000.0f);
				agent.submitBid(bid);
			}

			if(alloc > 0 && agent.getGameTime() > 8*60*1000) {
				Bid bid = new Bid(i);
				bid.addBidPoint(alloc, 1000.0f);
				agent.submitBid(bid);
			}
		}
	}

  // historical prices.
	private void flyrecord(){
		Quote quote;
		for(int i = 0, n = 8; i < n; i++) {
			quote = agent.getQuote(i);
			historyFly.add(quote.getAskPrice());
		}
	}

  // The surge rate decides whehter we should buy the ticket or not.   
	private boolean calculateRate(int id,float askedprice)
	{
		float total = 0;
		float temp;
		int num = historyFly.size()/8;
		for(int j =0;j<num-1;j++)
		{
			for(int i =0;i<8;i++)
			{
				if(i== id)
				{
					temp = (historyFly.get((i + (j+1)*8))-historyFly.get((i + j*8)));
					total = total + temp;	
				}
			}
		}
		total = total/num;
		if(total+300 >= askedprice)
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	private void calculateAllocation() {
		for (int i = 0; i < 8; i++) { 
			int inFlight = agent.getClientPreference(i, TACAgent.ARRIVAL);
			int outFlight = agent.getClientPreference(i, TACAgent.DEPARTURE);
			int hotel = agent.getClientPreference(i, TACAgent.HOTEL_VALUE);
			int type;
			int auction = TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, inFlight);
			agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			auction = TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, outFlight);
			agent.setAllocation(auction, agent.getAllocation(auction) + 1);

			int lengthOfStay = outFlight - inFlight;
			if (hotel / lengthOfStay > 45) {
				type = TACAgent.TYPE_GOOD_HOTEL;
			} else {
				type = TACAgent.TYPE_CHEAP_HOTEL;
			}

			char hType = (type == TACAgent.TYPE_GOOD_HOTEL) ? 'E' : 'C'; 
			switch(i) {
			case 0:
				for(int d = inFlight; d < outFlight; d++) {
					client_One[d-1] = hType;
				}
				client_One[5] = (char)lengthOfStay(client_One);
				break;
			case 1:
				for(int d = inFlight; d < outFlight; d++) {
					client_Two[d-1] = hType;
				}
				client_Two[5] = (char)lengthOfStay(client_Two);
				break;
			case 2:
				for(int d = inFlight; d < outFlight; d++) {
					client_Three[d-1] = hType;
				}
				client_Three[5] = (char)lengthOfStay(client_Three);
				break;
			case 3:
				for(int d = inFlight; d < outFlight; d++) {
					client_Four[d-1] = hType;
				}
				client_Four[5] = (char)lengthOfStay(client_Four);
				break;
			case 4:
				for(int d = inFlight; d < outFlight; d++) {
					client_Five[d-1] = hType;
				}
				client_Five[5] = (char)lengthOfStay(client_Five);

				break;
			case 5:
				for(int d = inFlight; d < outFlight; d++) {
					client_Six[d-1] = hType;
				}
				client_Six[5] = (char)lengthOfStay(client_Six);
				break;
			case 6:
				for(int d = inFlight; d < outFlight; d++) {
					client_Seven[d-1] = hType;
				}
				client_Seven[5] = (char)lengthOfStay(client_Seven);
				break;
			case 7:
				for(int d = inFlight; d < outFlight; d++) {
					client_Eight[d-1] = hType;
				}
				client_Eight[5] = (char)lengthOfStay(client_Eight);
				break;
			}

			for (int d = inFlight; d < outFlight; d++) {
				auction = TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, d);
				agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			}

			int eType = -1;
			while((eType = nextEntType(i, eType)) > 0) {
				auction = bestEntDay(inFlight, outFlight, eType);
				agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			}
		}
	}

	private int lengthOfStay(char client[]) {
		int count = 0;
		for(int i = 0; i < client.length; i++) {
			if(client[i] != 'x') {
				count++;
			}
		}
		return count;
	}

	private int bestEntDay(int inFlight, int outFlight, int type) {
		for (int i = inFlight; i < outFlight; i++) {
			int auction = TACAgent.getAuctionFor(TACAgent.CAT_ENTERTAINMENT, type, i);
			if (agent.getAllocation(auction) < agent.getOwn(auction)) {
				return auction;
			}
		}
		return TACAgent.getAuctionFor(TACAgent.CAT_ENTERTAINMENT, type, inFlight);
	}

	private int nextEntType(int client, int lastType) {
		int e1 = agent.getClientPreference(client, TACAgent.E1);
		int e2 = agent.getClientPreference(client, TACAgent.E2);
		int e3 = agent.getClientPreference(client, TACAgent.E3);

		if ((e1 > e2) && (e1 > e3) && lastType == -1)
			return TACAgent.TYPE_ALLIGATOR_WRESTLING;
		if ((e2 > e1) && (e2 > e3) && lastType == -1)
			return TACAgent.TYPE_AMUSEMENT;
		if ((e3 > e1) && (e3 > e2) && lastType == -1)
			return TACAgent.TYPE_MUSEUM;
		return -1;
	}

	public void quoteUpdated(int auctionCategory) {
		log.fine("All quotes for " + TACAgent.auctionCategoryToString(auctionCategory) + " has been updated");
	}

	public void bidUpdated(Bid bid) {
		log.fine("Bid Updated: id=" + bid.getID() + " auction=" + bid.getAuction() + " state=" + bid.getProcessingStateAsString());
		log.fine(" Hash: " + bid.getBidHash());
	}

	public void bidRejected(Bid bid) {
		log.warning("Bid Rejected: " + bid.getID());
		log.warning(" Reason: " + bid.getRejectReason() + " (" + bid.getRejectReasonAsString() + ')');
	}

	public void bidError(Bid bid, int status) {
		log.warning("Bid Error in auction " + bid.getAuction() + ": " + status + " (" + agent.commandStatusToString(status) + ')');
	}

	public static void main (String[] args) {
		TACAgent.main(args);
	}
} 