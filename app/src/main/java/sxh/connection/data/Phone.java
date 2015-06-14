package sxh.connection.data;

import org.bson.Document;

public class Phone {
	String description;
	public String number;

	public Phone(String description, String number) {
		this.description = description;
		this.number = number;
	}

	public Phone(Document doc) {
		this.description = doc.get("d").toString();
		this.number = doc.get("n").toString();
	}

	public Document toDoc() {
		Document doc = new Document();
		doc.put("d", description);
		doc.put("n", number);
		return doc;
	}

	public void _print() {
		System.out.println("\t" + number + "(" + description + ")");
	}

	public static void main(String[] args) {
		Phone p = new Phone("work", "15302123678");
		Document d = p.toDoc();
		new Phone(d)._print();
	}

    public String getDescription(){
        return description;
    }
    public String getNumber(){
        return number;
    }
}
