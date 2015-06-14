package sxh.connection.function;

/**
 * Created by Eleanor on 2015/6/13.
 */
public enum SettingDescription {
    BACKGROUNDCOLOR("background_color");

    private final String setting;

    private SettingDescription(final String setting){this.setting = setting;}

    @Override
    public String toString(){
        return setting;
    }
}
