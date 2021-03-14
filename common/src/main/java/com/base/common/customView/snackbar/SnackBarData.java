package com.base.common.customView.snackbar;

/**
 * @author Adeel Ur Rehman Turk
 */

/**
 * A DTO class having SnackBar configuration which are as follows
 * [message] Message to Display
 * [messageType] To identify either snack bar is any info or success or error message
 */

public class SnackBarData {

    private String message;
    private int messageType;

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int SUCCESS_SMALL = 3;
    public static final int DEFAULT_TYPE = -1;

    /**
     *  @param message Message to display
     *  @param messageType Snackbar Type
     **/
    public SnackBarData(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    /**
     * @return message to display
     **/
    public String getMessage() {
        return message;
    }


    /**
     *
     *  @param message pass the message to display
     */

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return message type
     **/
    public int getMessageType() {
        return messageType;
    }

    /**
     *
     *  @param messageType pass the message type to identify Snack bar type
     */

    @SuppressWarnings("unused")
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @SuppressWarnings("unused")
    public static int getSUCCESS() {
        return SUCCESS;
    }


}
