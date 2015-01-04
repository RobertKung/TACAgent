/*
 * This class is developed by group 10 
 * 
 * Members: Changhai Han
 * 			Qiong BU
 * 			Chi-ping Kung
 * 
 * The gTrueV is used to bid with static value at the beginning of the tournament. The value is
 * obtained by constantly monitor the matches.
 * For more information, it can be referenced to the report.
 */


package se.sics.tac.aw;

public class GetTheBidValue {
	public float gTrueV(int day, int AUCTION_TYPE) {

        //observed price.
		if(AUCTION_TYPE == TACAgent.TYPE_CHEAP_HOTEL) {
			if(day == 1 || day == 4) {
				return 121f; 

			} else if(day == 2) {
				return 151f;

			} else if(day == 3) {
				return 221f;

			}
		} else if(AUCTION_TYPE == TACAgent.TYPE_GOOD_HOTEL) {
			if(day == 1 || day == 4) {
				return 181f;

			} else if(day == 2) {
				return 281f;

			} else if(day == 3) {
				return 381f;
			}
		}
		return 202f;
	}
}
