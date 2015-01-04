/*
 * This class is developed by group 10 
 * 
 * Members: Changhai Han
 *      Qiong BU
 *      Chi-ping Kung
 * 
 */

package se.sics.tac.aw;

public class Quote {

  public final static int AUCTION_INITIALIZING = 0;
  public final static int AUCTION_INTERMEDIATE_CLEAR = 1;
  public final static int AUCTION_FINAL_CLEAR = 2;
  public final static int AUCTION_CLOSED = 3;

  private final static String[] statusName = new String[] {
    "Initializing", "Intermediate Clear", "Final Clear", "Closed"
  };

  private final int auction;
  private int hqw = -1;
  private int status = AUCTION_INITIALIZING;

  private long nextQuoteTime = -1L;
  private long lastQuoteTime = 0L;

  private float askPrice;
  private float bidPrice;
  private Bid bid;

  Quote(int auctionNo) {
    auction = auctionNo;
  }

  void clearAll() {
    askPrice = 0f;
    bidPrice = 0f;
    nextQuoteTime = -1L;
    lastQuoteTime = 0L;
    bid = null;
    status = AUCTION_INITIALIZING;
    hqw = -1;
  }

  void setAskPrice(float ask) {
    this.askPrice = ask;
  }

  public float getAskPrice() {
    return askPrice;
  }

  void setBidPrice(float bid) {
    this.bidPrice = bid;
  }

  public float getBidPrice() {
    return bidPrice;
  }

  public int getAuction() {
    return auction;
  }

  void setHQW(int hqw) {
    this.hqw = hqw;
  }

  public int getHQW() {
    return hqw;
  }

  public boolean hasHQW(Bid bid) {
    return (this.bid != null && bid == this.bid && hqw >= 0);
  }

  void setAuctionStatus(int status) {
    this.status = status;
  }

  public boolean isAuctionClosed() {
    return status == AUCTION_CLOSED;
  }

  public int getAuctionStatus() {
    return status;
  }

  public String getAuctionStatusAsString() {
    return statusName[status];
  }

  public long getNextQuoteTime() {
    return nextQuoteTime;
  }

  public void setNextQuoteTime(long nextQuoteTime) {
    this.nextQuoteTime = nextQuoteTime;
  }

  public long getLastQuoteTime() {
    return lastQuoteTime;
  }

  public void setLastQuoteTime(long lastQuoteTime) {
    this.lastQuoteTime = lastQuoteTime;
  }

  void setBid(Bid bid) {
    this.bid = bid;
  }

  public Bid getBid() {
    return bid;
  }

}
