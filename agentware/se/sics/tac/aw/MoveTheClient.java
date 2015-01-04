/*
 * This class is developed by group 10 
 * 
 * Members: Changhai Han
 * 			Qiong BU
 * 			Chi-ping Kung
 * 
 * The shiftTheDay method is used to get the feasible package when hotel is not fully obtained.
 * For more information, it can be referenced to the report.
 */

package se.sics.tac.aw;

import se.sics.tac.util.ArgEnumerator;

public class MoveTheClient extends AgentImpl{
	public int lengthOfStay(char client[]) {
		int count = 0;
		for(int i = 0; i < client.length; i++) {
			if(client[i] != 'x') {
				count++;
			}
		}
		return count;
	}
	
	public void shiftTheDay(int currentDay, 
			char[] theClientList, 
			char theDayCharcter, 
			int type) 
	{
		if(currentDay-1 == 0 && (theClientList[5] == 1)) {
			theClientList[currentDay-1] = 'x';
			theClientList[currentDay] = theDayCharcter;
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) -1);				
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay)) -1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1))+1);				
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+1)) -1);				
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+2), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+2)) +2);		

		}
		else if(theClientList[currentDay] == 'x') {	
			theClientList[currentDay] = 'x';
			theClientList[5] = (char)lengthOfStay(theClientList);
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay)) -1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay-1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay-1)) +1);
		}
		else if(theClientList[currentDay+2] == 'x') {	
			theClientList[currentDay-1] = 'x';
			theClientList[currentDay-2] = 'x';
			theClientList[5] = (char)lengthOfStay(theClientList);
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) - 1);
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay-1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay-1)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay-1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay-1)) -1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1)) +1);

		}
		else if(currentDay-1 == 0 && theClientList[5] > 1) {
			theClientList[currentDay-1] = 'x';
			theClientList[currentDay] = theDayCharcter;
			theClientList[5] = (char)(Character.getNumericValue(theClientList[5])-1); 
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay)) -1);		
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1)) + 1);
		}
		else if(theClientList[currentDay+1] == 'x') { 	
			theClientList[currentDay] = 'x';
			theClientList[currentDay+1] = 'x';
			theClientList[currentDay-1] = 'x';
			theClientList[5] = (char)lengthOfStay(theClientList);
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay+1)) - 1);		
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay-1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay-1)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+2), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay+2))-1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_OUTFLIGHT, currentDay))+1);
		}
		else if(theClientList[currentDay-2] == 'x') { 	
			theClientList[currentDay-1] = 'x';
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_HOTEL, type, currentDay)) - 1);			
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay)) -1);		
			agent.setAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1), 
					agent.getAllocation(TACAgent.getAuctionFor(TACAgent.CAT_FLIGHT, TACAgent.TYPE_INFLIGHT, currentDay+1))+1);	
		}
		
	}

	@Override
	protected void init(ArgEnumerator args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bidUpdated(Bid bid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bidRejected(Bid bid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bidError(Bid bid, int error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void auctionClosed(int auction) {
		// TODO Auto-generated method stub
		
	}
}
