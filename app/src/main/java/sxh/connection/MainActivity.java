package sxh.connection;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sxh.connection.data.CardInfo;
import sxh.connection.data.Phone;
import sxh.connection.function.FunctionAccessor;
import sxh.connection.function.FunctionImpl;


public class MainActivity extends Activity {

    FunctionAccessor fa = new FunctionImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.MakeCall);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(fa.call_number("18818213768"));
                    //fa.user_login("123", "fdasf");
                    //finish();
                } catch (ActivityNotFoundException ex){
                    Toast.makeText(MainActivity.this,
                            "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btn2 = (Button) findViewById(R.id.SendMsg);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fa.send_message("15821916808", "Hi, this is a message from CONNECTION!"));
            }
        });

        Button btn3 = (Button) findViewById(R.id.SendEmail);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fa.send_email("xll94@163.com", "test", "test"));
            }

        });

        ListView conListview = (ListView) findViewById(R.id.listView);
        ContentResolver resolver = this.getContentResolver();
        List<CardInfo> list = fa.get_phone_contact(resolver);
        /** cannot read sim card & I don't know why*/
        //List<CardInfo> list = fa.get_SIM_contact(resolver);
        conListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData(list)));

    }

    private List<String> getData(List<CardInfo> cards){
        List<String> data = new ArrayList<String>();
        for (Iterator<CardInfo> i = cards.iterator(); i.hasNext();){
            CardInfo card = i.next();
            Phone phone = card.get_phone_numbers().get(0);
            data.add(card.get_name() + phone.number);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
