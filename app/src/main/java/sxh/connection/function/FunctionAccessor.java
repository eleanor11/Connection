package sxh.connection.function;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import sxh.connection.data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eleanor on 2015/5/23.
 */
public interface FunctionAccessor {


/**    Account    */

    /**
     * register / login / logout
     */
    String user_register(String email, String password);
    Boolean user_login(String email, String password);
    Boolean user_logout();


/**    User    */
    /**
     * get current logged user
     * get the user info being edited
     */
    UserInfo get_current_user();
    UserInfo get_editing_user();
    /**
     * get details
     */
    String get_user_id(UserInfo user);
    String get_user_email(UserInfo user);
    ArrayList<String> get_user_cards(UserInfo user);
    ArrayList<String> get_user_card_case(UserInfo user);
    ArrayList<Setting> get_user_setting(UserInfo user);

    String get_setting_background_color(UserInfo user);

    /**
     * edit user
     * assure / cancel user operation
     */
    Boolean edit_user();
    Boolean modify_edited_user();
    Boolean cancel_user_operation();

    /**
     * edit user info
     */
    Boolean set_password(String password);
    Boolean add_my_card(CardInfo card);
    Boolean add_other_card(CardInfo card);
    Boolean delete_my_card(String _id);
    Boolean delete_other_card(String _id);
    Boolean set_setting(SettingDescription description, String value);



/**    Card    */

    /**
     * get card by id
     * get the card being created / edited
     */
    CardInfo get_card_info(String _id);
    CardInfo get_current_card_info();
    /**
     * get details
     */
    String get_card_id(CardInfo card);
    String get_card_name(CardInfo card);
    String get_card_model(CardInfo card);
    ArrayList<Phone> get_card_phone_number(CardInfo card);
    ArrayList<SNS> get_card_sns_account(CardInfo card);
    String get_card_email(CardInfo card);
    String get_card_addres(CardInfo card);
    Date get_card_birthday(CardInfo card);

    String get_wechat(CardInfo card);
    String get_instagram(CardInfo card);
    String get_facebook(CardInfo card);
    String get_twitter(CardInfo card);
    String get_weibo(CardInfo card);

    /**
     * create your own card
     * edit your own card
     * assure / cancel card operation
     */
    Boolean create_card();
    Boolean edit_card(String _id);
    Boolean add_created_card();
    Boolean modify_edited_card();
    Boolean cancel_card_operation();

    /**
     *set or edit your own card
     */
    Boolean set_card_name(String name);
    Boolean set_card_model(String model);
    Boolean add_card_phone_number(String description, String number);
    Boolean set_card_sns_accout(SNSDescription description, String name);
    Boolean set_card_sns_account(String description, String name);
    Boolean set_card_email(String email);
    Boolean set_card_address(String address);
    Boolean set_card_birthday(Date birthday);

    /**
     * search
     */
    List<CardInfo> get_cards_by_name(String name);
    List<CardInfo> get_cards_by_phone_number(String phone);
    List<CardInfo> get_cards_by_email(String email);

    /**
     * sort
     */
    List<CardInfo> sort_cards_by_name(List<CardInfo> cards);
    List<CardInfo> sort_cards_by_email(List<CardInfo> cards);


/** functions */
    Intent call_number(String number);
    Intent send_message(String number, String msg);
    Intent send_email(String address, String subject, String text);
    Intent send_card(String address, String subject);
    List<CardInfo> get_phone_contact(ContentResolver resolver);
    List<CardInfo> get_SIM_contact(ContentResolver resolver);
    Intent take_photo();
    Intent get_photo_from_album();
    Intent zoom_photo(Uri uri, Boolean fixed);
    Bitmap compress_photo(Bitmap photo, boolean fixed, int size);
    Bitmap make_postcard(String str, Bitmap photo);
    Bitmap get_qrcode();

}
