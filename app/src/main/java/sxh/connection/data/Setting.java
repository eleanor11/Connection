package sxh.connection.data;

import org.bson.Document;

public class Setting {
	String description;
	String value;

	public Setting(String description, String value) {
		this.description = description;
		this.value = value;
	}

	public Setting(Document doc) {
		this.description = doc.get("d").toString();
		this.value = doc.get("n").toString();
	}

	public Document toDoc() {
		Document doc = new Document();
		doc.put("d", description);
		doc.put("n", value);
		return doc;
	}

	public void _print() {
		System.out.println("\t" + value + "(" + description + ")");
	}

	public static void main(String[] args) {
		Setting p = new Setting("font", "Monaco");
		Document d = p.toDoc();
		new Setting(d)._print();
	}


    public String getDescription(){
        return description;
    }

    public String getValue(){
        return value;
    }
}
