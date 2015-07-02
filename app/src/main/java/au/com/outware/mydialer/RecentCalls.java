package au.com.outware.mydialer;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by manjumathew on 26/06/2015.
 */
public class RecentCalls {
    private final Context context;

    public RecentCalls(Context context) {
        this.context = context;
    }


    public List<CallDetails> retrieveCalls() {
        List<CallDetails> callList= getCalls();
        Collections.sort(callList, new Comparator<CallDetails>() {
            @Override
            public int compare(CallDetails c1, CallDetails c2) {
                if(c1.getCallDayTime().before(c2.getCallDayTime())){
                    return 1;
                }else if(c1.getCallDayTime().after(c2.getCallDayTime())){
                    return -1;
                }
                return 0;
            }
        });
        return callList;
    }

    private List<CallDetails> getCalls() {
        List recentCalls = new ArrayList<CallDetails>();
        final Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String cachedName = managedCursor.getString(name);
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            recentCalls.add(new CallDetails(cachedName, phNumber,callType,callDate,callDayTime,callDuration,dir));
        }
        return  recentCalls;
    }

}
