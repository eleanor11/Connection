package sxh.connection.function;

/**
 * Created by Eleanor on 2015/6/13.
 */
public enum  SNSDescription {
    WECHAT("wechat"),
    INSTAGRAM("instagram"),
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    WEIBO("weibo");


    private final String text;

    private SNSDescription(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }


}
