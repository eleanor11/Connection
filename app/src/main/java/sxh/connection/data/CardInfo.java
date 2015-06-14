package sxh.connection.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.bson.Document;

public class CardInfo {
	private String _id;
	private String name;
	private String name_card_model;
	private ArrayList<Phone> phone_numbers;
	private ArrayList<SNS> sns_accounts;
	private String email;

	private String address;
	private Date birthday; // yyyy-MM-dd
/**
 * need to add
 *  private Memorial<description & date>
 *  private OtherInfo<description & value>
 *
 */


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_name() {
		return name;
	}

	public void set_name(String name) {
		this.name = name;
	}

	public String get_name_card_model() {
		return name_card_model;
	}

	public void set_name_card_model(String name_card_model) {
		this.name_card_model = name_card_model;
	}

	public ArrayList<Phone> get_phone_numbers() {
		return phone_numbers;
	}

	public void add_phone_number(Phone phone) {
		this.phone_numbers.add(phone);
	}

	public ArrayList<SNS> get_sns_accounts() {
		return sns_accounts;
	}

	public void add_sns_account(SNS sns) {
		this.sns_accounts.add(sns);
	}

	public String get_email() {
		return email;
	}

	public void set_email(String email) {
		this.email = email;
	}

	public String get_address() {
		return address;
	}

	public void set_address(String address) {
		this.address = address;
	}

	public Date get_birthday() {
		return birthday;
	}

	public void set_birthday(Date birthday) {
		this.birthday = birthday;
	}

	public void _print() {
		System.out.println("***CardInfo***");
		System.out.println("_id\t" + _id);
		System.out.println("name\t" + name);
		System.out.println("model\t" + name_card_model);

		System.out.println("numbers");
		java.util.Iterator<Phone> p_iter = phone_numbers.iterator();
		while (p_iter != null && p_iter.hasNext()) {
			p_iter.next()._print();
		}

		System.out.println("accounts");
		java.util.Iterator<SNS> s_iter = sns_accounts.iterator();
		while (s_iter != null && s_iter.hasNext()) {
			s_iter.next()._print();
		}

		System.out.println("email\t" + email);
		System.out.println("address\t" + address);
		System.out.println("birthday\t" + birthday);
	}

	@SuppressWarnings("unchecked")
	public CardInfo(Document doc) {
		this._id = doc.get("_id").toString();
		this.name = doc.get("n").toString();
		this.name_card_model = doc.get("m").toString();

		ArrayList<Document> tmp = (ArrayList<Document>) doc.get("p");
		Iterator<Document> iter = tmp.iterator();
		this.phone_numbers = new ArrayList<Phone>();
		while (iter != null && iter.hasNext()) {
			this.phone_numbers.add(new Phone(iter.next()));
		}

		tmp = (ArrayList<Document>) doc.get("s");
		iter = tmp.iterator();
		this.sns_accounts = new ArrayList<SNS>();
		while (iter != null && iter.hasNext()) {
			this.sns_accounts.add(new SNS(iter.next()));
		}

		this.email = doc.get("e").toString();
		this.address = doc.get("a").toString();
		this.birthday = (Date) doc.get("b");
	}

	public Document toDoc() {
		Document doc = new Document();
		doc.put("_id", _id);
		doc.put("n", name);
		doc.put("m", name_card_model);

		ArrayList<Document> p_tmp = new ArrayList<Document>();
		Iterator<Phone> p_iter = phone_numbers.iterator();
		while (p_iter.hasNext()) {
			p_tmp.add(p_iter.next().toDoc());
		}
		doc.put("p", p_tmp);

		ArrayList<Document> s_tmp = new ArrayList<Document>();
		Iterator<SNS> s_iter = sns_accounts.iterator();
		while (s_iter.hasNext()) {
			s_tmp.add(s_iter.next().toDoc());
		}
		doc.put("s", s_tmp);

		doc.put("e", email);
		doc.put("a", address);
		doc.put("b", birthday);
		return doc;
	}

	public CardInfo() {
		_id = "";
		name = "";
		name_card_model = "";
		phone_numbers = new ArrayList<Phone>();
		sns_accounts = new ArrayList<SNS>();
		email = "";
		address = "";
		birthday = new Date();
	}

	public static void main(String[] args) {
		System.out.println("hello");
		// CardInfo ci = new CardInfo("555004aa6d336dc5ae824300", "Chad",
		// "sangchuang@sjtu.edu.cn");
		CardInfo ci = new CardInfo();
		ci.set_name("Chad");
		ci.add_phone_number(new Phone("work", "12345"));
		ci.add_phone_number(new Phone("home", "23456"));
		ci.add_sns_account(new SNS("QQ", "1218123678"));
		ci._print();
		Document doc = ci.toDoc();
		new CardInfo(doc)._print();
	}
}
