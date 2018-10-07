package Indy;

/**
 * An enum that keep tracks of all global cards. Global cards can be put in front of
 * a player to gain extra points for his or her.
 */
public enum Global implements Card {
	FOUNTAIN_OF_BLOOD, // 2 extra points
	TOUGH_CROWD, // 2 point deductions
	CIVIC_SUPPORT, // worth a point for each green noble
	CHURCH_SUPPORT, // worth a point for each blue noble
	FOREIGN_SUPPORT, // draws an extra action card on collection of purple noble
	MILITARY_SUPPORT, // worth a point for each red noble
	INDIFFERENT_PUBLIC; // change the score for each grey noble to positive 1
}
