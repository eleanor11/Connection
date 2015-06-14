package sxh.connection.function;

import android.content.Intent;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.zxing.WriterException;

import sxh.connection.R;
import sxh.connection.data.*;
import sxh.connection.function.SNSDescription;
import sxh.connection.function.zxing.encoding.EncodingHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Eleanor on 2015/5/23.
 */
public class FunctionImpl implements FunctionAccessor {

    private DataAccessor da = new MongoAccessor();

    private UserInfo current_user = null;
    private UserInfo editing_user = null;
    private CardInfo current_card = null;

    /**
     * Account
     */
    @Override
    public String user_register(String email, String password) {

        return da.add_user(email, password);

    }
    @Override
    public Boolean user_login(String email, String password){
        current_user = da.verify_user(email, password);
        return !(current_user == null);
    }
    @Override
    public Boolean user_logout(){
        current_user = null;
        return (current_user == null);
    }

    /**
     * User
     */
    @Override
    public UserInfo get_current_user(){
        return current_user;
    }
    @Override
    public UserInfo get_editing_user() {
        return editing_user;
    }

    @Override
    public String get_user_id(UserInfo user){
        return user.get_id();
    }
    @Override
    public String get_user_email(UserInfo user){
        return user.get_email();
    }
    @Override
    public ArrayList<String> get_user_cards(UserInfo user){
        return user.get_my_cards();
    }
    @Override
    public ArrayList<String> get_user_card_case(UserInfo user){
        return user.get_card_case();
    }
    @Override
    public ArrayList<Setting> get_user_setting(UserInfo user){
        return user.get_personal_settings();
    }

    String find_setting_value(UserInfo user, String setting){
        ArrayList<Setting>  settingss = user.get_personal_settings();
        for (int i = 0; i < settingss.size(); i++){
            if (settingss.get(i).getDescription().equals(setting)){
                return settingss.get(i).getValue();
            }
        }
        return null;
    }
    int find_setting_index(UserInfo user, String setting){
        ArrayList<Setting>  settingss = user.get_personal_settings();
        for (int i = 0; i < settingss.size(); i++){
            if (settingss.get(i).getDescription().equals(setting)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get_setting_background_color(UserInfo user){
        return find_setting_value(user, SettingDescription.BACKGROUNDCOLOR.toString());
    }


    @Override
    public Boolean edit_user(){
        editing_user = current_user;
        return !(editing_user == null);
    }
    @Override
    public Boolean modify_edited_user(){
        da.set_user_info(editing_user);
        current_user = editing_user;
        editing_user = null;
        return true;
    }
    @Override
    public Boolean cancel_user_operation(){
        editing_user = null;
        return true;
    }
    @Override
    public Boolean set_password(String password){
        editing_user.set_password(password);
        return true;
    }

    @Override
    public Boolean add_my_card(CardInfo card){
        if (card != null && current_user != null) {
            da.add_name_card(current_user.get_id(), card);
            current_card = null;
            return true;
        }
        return false;
    }
    @Override
    public Boolean add_other_card(CardInfo card){
        if (card != null && current_user != null) {
            da.add_name_card(current_user, card);
            return true;
        }
        return false;
    }
    @Override
    public Boolean delete_my_card(String _id){
       // return da.delete_name_card_in_my_card(_id);
        return null;
    }
    @Override
    public Boolean delete_other_card(String _id){
        //return da.delete_name_card_in_card_case(_id);
        return null;
    }



    @Override
    public Boolean set_setting(SettingDescription description, String value){
        int index = find_setting_index(current_user, description.toString());
        Setting setting = new Setting(description.toString(), value);
        if (index >= 0){
            current_user.get_personal_settings().set(index, setting);
        }else {
            current_user.add_personal_settings(setting);
        }
        return true;
    }

    /**
     * Card
     */
    @Override
    public CardInfo get_card_info(String _id){
        current_card = da.get_name_card(_id);
        return current_card;
     }
    @Override
    public CardInfo get_current_card_info(){
        return current_card;
    }

    @Override
    public String get_card_id(CardInfo card){
        return card.get_id();
    }
    @Override
    public  String get_card_name(CardInfo card){
        return card.get_name();
    }
    @Override
    public String get_card_model(CardInfo card){
        return card.get_name_card_model();
    }
    @Override
    public ArrayList<Phone> get_card_phone_number(CardInfo card){
        return card.get_phone_numbers();
    }
    @Override
    public ArrayList<SNS> get_card_sns_account(CardInfo card){
        return card.get_sns_accounts();
    }
    @Override
    public String get_card_email(CardInfo card){
        return card.get_email();
    }
    @Override
    public String get_card_addres(CardInfo card){
        return card.get_address();
    }
    @Override
    public Date get_card_birthday(CardInfo card){
        return card.get_birthday();
    }

    String get_sns_name(CardInfo card, String description){
        ArrayList<SNS>  snses = card.get_sns_accounts();
        for (int i = 0; i < snses.size(); i++){
            if (snses.get(i).getDescription().equals(description)){
                return snses.get(i).getName();
            }
        }
        return null;
    }
    int get_sns_index(CardInfo card, String description){
        ArrayList<SNS>  snses = card.get_sns_accounts();
        for (int i = 0; i < snses.size(); i++){
            if (snses.get(i).getDescription().equals(description)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get_wechat(CardInfo card){
        String name = get_sns_name(card, SNSDescription.WECHAT.toString());
        return name;
    }
    @Override
    public String get_instagram(CardInfo card){
        String name = get_sns_name(card, SNSDescription.INSTAGRAM.toString());
        return name;
    }
    @Override
    public String get_facebook(CardInfo card){
        String name = get_sns_name(card, SNSDescription.FACEBOOK.toString());
        return name;
    }
    @Override
    public String get_twitter(CardInfo card){
        String name = get_sns_name(card, SNSDescription.TWITTER.toString());
        return name;
    }
    @Override
    public String get_weibo(CardInfo card){
        String name = get_sns_name(card, SNSDescription.WEIBO.toString());
        return name;
    }

    @Override
    public Boolean create_card() {
        current_card = new CardInfo();
        return !(current_card == null);
    }
    @Override
    public Boolean edit_card(String _id){
        current_card = da.get_name_card(_id);
        return !(current_user == null);
    }

    @Override
    public Boolean add_created_card(){
        return add_my_card(current_card);
    }
    @Override
    public Boolean modify_edited_card(){
        if (current_card != null) {
            boolean f = da.set_name_card(current_card);
            current_card = null;
            return f;
        }
        return false;
    }
    @Override
    public Boolean cancel_card_operation(){
        current_card = null;
        return (current_card == null);
    }


    @Override
    public Boolean set_card_name(String name){
        current_card.set_name(name);
        return true;
    }
    @Override
    public Boolean set_card_model(String model){
        current_card.set_name_card_model(model);
        return true;
    }
    @Override
    public Boolean add_card_phone_number(String description, String number){
        Phone phone = new Phone(description, number);
        current_card.add_phone_number(phone);
        return true;
    }
    @Override
    public Boolean set_card_sns_accout(SNSDescription description, String name){
        int index = get_sns_index(current_card, description.toString());
        SNS account = new SNS(description.toString(), name);
        if (index >= 0){
            current_card.get_sns_accounts().set(index, account);
        }else {
            current_card.add_sns_account(account);
        }
        return true;
    }
    @Override
    public Boolean set_card_sns_account(String description, String name){
        int index = get_sns_index(current_card, description);
        SNS account = new SNS(description, name);
        if (index >= 0){
            current_card.get_sns_accounts().set(index, account);
        }else {
            current_card.add_sns_account(account);
        }
        return true;
    }
    @Override
    public Boolean set_card_email(String email){
        current_card.set_email(email);
        return true;
    }
    @Override
    public Boolean set_card_address(String address){
        current_card.set_address(address);
        return true;
    }
    @Override
    public Boolean set_card_birthday(Date birthday){
        current_card.set_birthday(birthday);
        return true;
    }

    @Override
    public List<CardInfo> get_cards_by_name(String name){
        //List<CardInfo> cards = da.get_cards_by_name(current_user.get_id(), name);
        //return sort_cards_by_name(cards);
        return null;
    }
    @Override
    public List<CardInfo> get_cards_by_phone_number(String phone){
        //return da.get_cards_by_phone_number(current_user.get_id(), phone);
        return null;
    }
    @Override
    public List<CardInfo> get_cards_by_email(String email){
        //List<CardInfo> cards = da.get_cards_by_email(current_user.get_id(), email);
        //return sort_cards_by_email(cards);
        return null;

    }

    @Override
    public List<CardInfo> sort_cards_by_name(List<CardInfo> cards){
        Collections.sort(cards, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo lhs, CardInfo rhs) {
                return lhs.get_name().compareTo(rhs.get_name());
            }
        });
        return cards;
    }
    public List<CardInfo> sort_cards_by_email(List<CardInfo> cards){
        Collections.sort(cards, new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo lhs, CardInfo rhs) {
                return lhs.get_email().compareTo(rhs.get_email());
            }
        });
        return cards;
    }


    @Override
    public Intent call_number(String number){
        Intent phone_intent = new Intent(Intent.ACTION_CALL);
        //Intent phone_intent = new Intent(Intent.ACTION_DIAL);
        phone_intent.setData(Uri.parse("tel:" + number));
        return phone_intent;
    }

    @Override
    public Intent send_message(String number, String msg){
        Intent sms_intent = new Intent(Intent.ACTION_VIEW);
        sms_intent.setData(Uri.parse("smsto:"));
        sms_intent.setType("vnd.android-dir/mms-sms");
        sms_intent.putExtra("address", number);
        sms_intent.putExtra("sms_body", msg);

        return sms_intent;
    }

    @Override
    public Intent send_email(String address, String subject, String text){
        Intent email_intent = new Intent(Intent.ACTION_SEND);
        email_intent.setData(Uri.parse("mailto:"));
        //email_intent.setType("text/plain");
        email_intent.setType("message/rfc822");
        String[] email_address = new String[]{address, "", };
        email_intent.putExtra(Intent.EXTRA_EMAIL, email_address);
        email_intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        email_intent.putExtra(Intent.EXTRA_TEXT, text);

        return email_intent;
    }

    @Override
    public Intent send_card(String address, String subject){
        Intent email_intent = new Intent(Intent.ACTION_SEND);
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/postcard.png");
        Uri uri = Uri.fromFile(f);
        email_intent.setType("image/png");
        email_intent.putExtra(Intent.EXTRA_STREAM, uri);
        String[] email_address = new String[]{address, "", };
        email_intent.putExtra(Intent.EXTRA_EMAIL, email_address);
        email_intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        return email_intent;
    }

    @Override
    public Intent take_photo(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));

        return intent;
    }

    @Override
    public Intent get_photo_from_album(){
        String IMAGE_UNSPECIFIED = "image/*";

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);

        return intent;
    }

    @Override
    public Intent zoom_photo(Uri uri, Boolean fixed){
        String IMAGE_UNSPECIFIED = "image/*";

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);

        intent.putExtra("crop", "true");

        if (fixed) {
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
        }
        intent.putExtra("return-data", true);

        return intent;

    }

    @Override
    public Bitmap compress_photo(Bitmap oldPhoto, boolean fixed, int size){
        int newWidth = oldPhoto.getWidth() * 2;
        int newHeight = oldPhoto.getHeight() * 2;
        if (fixed){
            newWidth = size;
            newHeight = size;
        }

        float width = oldPhoto.getWidth();
        float height = oldPhoto.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newPhoto = Bitmap.createBitmap(oldPhoto, 0, 0, (int) width, (int) height, matrix, true);

        return newPhoto;

    }

    @Override
    public List<CardInfo> get_phone_contact(ContentResolver resolver){
        List<CardInfo> cards = new ArrayList<CardInfo>();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contact_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String phone_number = "";
            Cursor phone_cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, null, null);
            while (phone_cursor.moveToNext()) {
                phone_number = phone_cursor.getString(phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }

            if (!phone_number.equals("")) {
               // CardInfo card = da.get_name_card(da.get_card_by_phone_number(phone_number));
                CardInfo card = new CardInfo();
                card.set_name(contact_name);
                card.add_phone_number(new Phone("mobile", phone_number));
                cards.add(card);
            }
            if (null != phone_cursor && !phone_cursor.isClosed())
                phone_cursor.close();
        }
        if (null != cursor && !cursor.isClosed())
            cursor.close();
        return cards;
    }

    @Override
    public List<CardInfo> get_SIM_contact(ContentResolver resolver){

        List<CardInfo> cards = new ArrayList<CardInfo>();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contact_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String phone_number = "";
            Cursor phone_cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, null, null);
            while (phone_cursor.moveToNext()) {
                phone_number = phone_cursor.getString(phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }

            if (!phone_number.equals("")) {
                // CardInfo card = da.get_name_card(da.get_card_by_phone_number(phone_number));
                CardInfo card = new CardInfo();
                card.set_name(contact_name);
                card.add_phone_number(new Phone("mobile", phone_number));
                cards.add(card);
            }
            if (null != phone_cursor && !phone_cursor.isClosed())
                phone_cursor.close();
        }
        if (null != cursor && !cursor.isClosed())
            cursor.close();
        return cards;

    }
    @Override
    public Bitmap make_postcard(String str, Bitmap photo){

        int width = photo.getWidth();
        int height = photo.getHeight();

        Bitmap postcard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(postcard);
        Paint photoPaint = new Paint();
        photoPaint.setDither(true);
        photoPaint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());
        Rect dst = new Rect(0, 0, width, height);
        canvas.drawBitmap(photo, src, dst, photoPaint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(100f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.BLUE);
        canvas.drawText(str, 100, 130, textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        save_postcard(postcard);
        return(postcard);
    }

    void save_postcard(Bitmap bitmap){
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/postcard.png");
        FileOutputStream fout = null;
        try{
            f.createNewFile();
            fout = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap get_qrcode(){
        Bitmap qrcode_map = null;
        try {
            //qrcode_map = EncodingHandler.createQRCode(current_user.get_id(), 350);
            qrcode_map = EncodingHandler.createQRCode("test", 350);
        }catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return qrcode_map;
    }

}
