package sxh.connection.data;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class MongoAccessor implements DataAccessor {

	@Override
	public String add_user(String email, String password) {
		MongoCollection<Document> users = get_users();
		Document doc = users.find(eq("e", email.toLowerCase())).first();
		if (doc != null)
			return "Used";
		doc = new UserInfo(email, password).toDoc();
		doc.remove("_id");
		users.insertOne(doc);
		Document new_doc = users.find(doc).first();
		return new_doc.get("_id").toString();
		// TODO return "Busy";
	}

	@Override
	public UserInfo verify_user(String email, String password) {
		MongoCollection<Document> users = get_users();
		Document doc = users.find(
				and(eq("e", email.toLowerCase()), eq("p", password))).first();
		UserInfo res = new UserInfo(doc);
		return res;
	}
	
	@Override
	public boolean delete_user(String id) {
		// TODO
		return true;
	}

	@SuppressWarnings("unused")
	private static void test_card() {
		CardInfo ci = new CardInfo();
		ci.set_name("Chad2");
		ci.set_email("s@sjtu");
		ci.add_phone_number(new Phone("work", "12345"));
		ci.add_phone_number(new Phone("home", "23456"));
		ci.add_sns_account(new SNS("QQ", "1218123678"));
		System.out.println("begin");
		String id = new MongoAccessor().add_name_card("", ci);
		new MongoAccessor().get_name_card(id)._print();
		System.out.println("success");
	}

	public static void main(String[] args) throws UnknownHostException {
		// MongoAccessor().get_name_card("555004aa6d336dc5ae824300")._print();
		// new MongoAccessor().verify_user("chadx@sjtu", "PASSWORD")._print();
		new MongoAccessor().verify_user("hi@sjtu", "PASSWORD")._print();
	}

	private static MongoCollection<Document> get_cards() {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://hci:21543879@ds057000.mongolab.com:57000/hci_connection");
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> cards = db.getCollection("cards");
		return cards;
	}

	private static MongoCollection<Document> get_users() {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://hci:21543879@ds057000.mongolab.com:57000/hci_connection");
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> users = db.getCollection("users");
		return users;
	}

	@Override
	public String add_name_card(String user_id, CardInfo c) {
		Document doc = c.toDoc();
		doc.remove("_id");
		MongoCollection<Document> cards = get_cards();
		cards.insertOne(doc);
		Document new_doc = cards.find(doc).first();
		return new_doc.get("_id").toString();
	}

	@Override
	public CardInfo get_name_card(String _id) {
		Document doc = get_cards().find(eq("_id", new ObjectId(_id))).first();
		return new CardInfo(doc);
	}

	@Override
	public boolean set_name_card(CardInfo c) {
		get_cards().findOneAndUpdate(eq("_id", new ObjectId(c.get_id())),
				c.toDoc());
		return true;
	}

	@Override
	public boolean set_user_info(UserInfo u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String add_name_card(UserInfo u, CardInfo c) {
		// TODO Auto-generated method stub
		return null;
	}


    public String get_card_by_phone_number(String phone_number){
        return null;
    }
}