package au.com.outware.mydialer;

import java.util.Date;

/**
 * Created by manjumathew on 26/06/2015.
 */
public class CallDetails {

    private String callerName;
    private String phNumber;
    private String callType;
    private String callDate;
    private Date callDayTime;
    private String callDuration;

    public String getCallerName() {
        return callerName;
    }
    public String getDir() {
        return dir;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public String getCallType() {
        return callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public Date getCallDayTime() {
        return callDayTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    private String dir;

    public CallDetails(String cachedName, String phNumber, String callType, String callDate, Date callDayTime, String callDuration, String dir) {
        this.callerName= cachedName==null?"Unknown":cachedName;
        this.phNumber = phNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDayTime = callDayTime;
        this.callDuration = callDuration;
        this.dir = dir;
    }


    @Override
    public String toString() {
        return callerName+" "+phNumber+" "+dir+" "+callDate+" "+callDuration;
    }
}
