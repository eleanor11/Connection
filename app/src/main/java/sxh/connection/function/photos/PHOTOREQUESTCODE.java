package sxh.connection.function.photos;

/**
 * Created by Eleanor on 2015/6/13.
 */
public enum PHOTOREQUESTCODE {
    NONE(0),
    PHOTORAPH(1),
    PHOTOZOOM(2),
    PHOTORESULT(3);

    private final int code;

    private PHOTOREQUESTCODE(final int code){
        this.code = code;
    }

    public int toInt(){
        return code;
    }

}
