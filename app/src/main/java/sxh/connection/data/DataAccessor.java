package sxh.connection.data;

import java.util.List;

public interface DataAccessor {

	/**
	 * if succeed, returns the user_id with the size of 24; else "Used" for the
	 * email being used, "Busy" for network failure
	 */
	String add_user(String email, String password);

	UserInfo verify_user(String email, String password);
	
	boolean delete_user(String id);

	boolean set_user_info(UserInfo u);

	/**
	 * add a name card for the user
	 * 
	 * @returns _id of the card
	 */
	String add_name_card(String user_id, CardInfo c);

	String add_name_card(UserInfo u, CardInfo c);

	CardInfo get_name_card(String _id);

	boolean set_name_card(CardInfo c);

 /**
need to implement

    boolean delete_name_card_in_my_card(String _id);
    boolean delete_name_card_in_card_case(String _id);

    String get_card_by_phone_number(String phone_number);
    List<CardInfo> get_cards_by_name(String _id, String name);
    List<CardInfo> get_cards_by_phone_number(String _id, String phone);
    List<CardInfo> get_cards_by_email(String _id, String email);

    */
/*	public static void main(String[] args) {
		DataAccessor da = new MongoAccessor();
		String id = da.add_user("user@sjtu", "my_password");
		if (id.length() == 24) {
			System.out.printf("successfully added user %s\n", id);
		}
		else if (id.equals("used")){
			System.out.printf("user already exist\n");

		}

		UserInfo ui = da.verify_user("user@sjtu", "wrong_password");
		if (ui.valid()) {
			System.out.println("successfully log in");
		}
		else {
			System.out.println("wrong password!");
		}

		ui = da.verify_user("user@sjtu", "my_password");
		if (ui.valid()) {
			System.out.println("successfully log in");
		}
		else {
			System.out.println("wrong password!");
		}
	}
*/
}

