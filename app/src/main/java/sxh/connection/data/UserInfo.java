package sxh.connection.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

public class UserInfo {
	String _id;

	String email;
	String password;

	ArrayList<String> my_cards;
	ArrayList<String> card_case;
	ArrayList<Setting> personal_settings;

    /**
     * need to add
     *
     * file system (ask hesijia)
     *
     */

	public UserInfo(String email, String password) {
		this._id = "";
		this.email = email.toLowerCase();
		this.password = password;
		this.my_cards = new ArrayList<String>();
		this.card_case = new ArrayList<String>();
		this.personal_settings = new ArrayList<Setting>();
	}

	public UserInfo() {
		this._id = "";
		this.email = "";
		this.password = "";
		this.my_cards = new ArrayList<String>();
		this.card_case = new ArrayList<String>();
		this.personal_settings = new ArrayList<Setting>();
	}

	public String get_password() {
		return password;
	}

	public void set_password(String password) {
		this.password = password;
	}

	public ArrayList<String> get_my_cards() {
		return my_cards;
	}

	public void add_my_cards(String card_id) {
		this.my_cards.add(card_id);
	}

	public ArrayList<String> get_card_case() {
		return card_case;
	}

	public void add_card_case(String card_id) {
		this.card_case.add(card_id);
	}

	public ArrayList<Setting> get_personal_settings() {
		return personal_settings;
	}

	public void add_personal_settings(Setting personal_setting) {
		this.personal_settings.add(personal_setting);
	}

	public String get_id() {
		return _id;
	}

	public String get_email() {
		return email;
	}

	@SuppressWarnings("unchecked")
	public UserInfo(Document doc) {
		if (doc == null) return;
		this._id = doc.get("_id").toString();
		this.email = doc.get("e").toString();
		this.password = doc.get("p").toString(); // TODO security
		this.my_cards = (ArrayList<String>) doc.get("m");
		this.card_case = (ArrayList<String>) doc.get("c");

		ArrayList<Document> tmp = (ArrayList<Document>) doc.get("s");
		Iterator<Document> iter = tmp.iterator();
		this.personal_settings = new ArrayList<Setting>();
		while (iter != null && iter.hasNext()) {
			this.personal_settings.add(new Setting(iter.next()));
		}
	}

	public Document toDoc() {
		Document doc = new Document();
		doc.put("_id", _id);
		doc.put("e", email);
		doc.put("p", password);
		doc.put("m", my_cards);
		doc.put("c", card_case);

		ArrayList<Document> s_tmp = new ArrayList<Document>();
		Iterator<Setting> s_iter = personal_settings.iterator();
		while (s_iter.hasNext()) {
			s_tmp.add(s_iter.next().toDoc());
		}
		doc.put("s", s_tmp);

		return doc;
	}
	
	public boolean valid() {
		return _id != null && email != null && password != null;
	}

	public void _print() {
		System.out.println("***UserInfo***");
		System.out.println("_id\t" + _id);
		System.out.println("email\t" + email);
		System.out.println("password\t" + password);

		System.out.println("my_cards");
		Iterator<String> m_iter = my_cards.iterator();
		while (m_iter != null && m_iter.hasNext()) {
			System.out.println("\t" + m_iter.next());
		}

		System.out.println("card_case");
		Iterator<String> c_iter = card_case.iterator();
		while (c_iter != null && c_iter.hasNext()) {
			System.out.println("\t" + c_iter.next());
		}

		System.out.println("personal_settings");
		Iterator<Setting> p_iter = personal_settings.iterator();
		while (p_iter != null && p_iter.hasNext()) {
			p_iter.next()._print();
		}
	}

	public static void main(String[] args) {
		System.out.println("begin");
		UserInfo ui = new UserInfo();
		ui.set_password("PASSWORD");
		ui.add_personal_settings(new Setting("size", "20"));
		ui.add_personal_settings(new Setting("font", "Monaco"));
		ui._print();

		new UserInfo(ui.toDoc())._print();
	}
}
