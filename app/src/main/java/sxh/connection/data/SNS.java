package sxh.connection.data;

import org.bson.Document;

public class SNS {
	String description;
	String name;

	public SNS(String description, String name) {
		this.description = description;
		this.name = name;
	}

	public SNS(Document doc) {
		this.description = doc.get("d").toString();
		this.name = doc.get("n").toString();
	}

	public Document toDoc() {
		Document doc = new Document();
		doc.put("d", description);
		doc.put("n", name);
		return doc;
	}

	public void _print() {
		System.out.println("\t" + name + "(" + description + ")");
	}

	public static void main(String[] args) {
		SNS p = new SNS("QQ", "609705422");
		Document d = p.toDoc();
		new SNS(d)._print();
	}


    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }

}
