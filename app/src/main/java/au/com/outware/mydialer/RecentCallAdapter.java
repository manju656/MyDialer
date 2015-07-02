package au.com.outware.mydialer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manjumathew on 26/06/2015.
 */
@EBean
public class RecentCallAdapter extends BaseAdapter {

    private List<CallDetails> mItems;
    @RootContext
    Context mContext;

    @AfterInject
    public void retrieveCallList() {
        RecentCalls recentCalls = new RecentCalls(mContext);
        mItems = recentCalls.retrieveCalls();

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CallDetails getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = View.inflate(mContext, R.layout.view_recent_call_list_row,
                    null);
            v.setTag(new ViewHolder(v));
        }

        CallDetails details = mItems.get(position);
        System.out.println(details.toString());

        ViewHolder holder = (ViewHolder) v.getTag();
        holder.setName(details.getCallerName());
        holder.setNumber(details.getPhNumber());
        holder.setTime(details.getCallDate());
        holder.setCallType(details.getDir());
        return v;
    }


    public class ViewHolder {

        public TextView mTxtName;
        public TextView mTxtNumber;
        public TextView mTxtTime;
        public TextView mCallType;

        public ViewHolder(View v) {
            mTxtName = (TextView) v
                    .findViewById(R.id.txt_name);
            mTxtNumber = (TextView) v
                    .findViewById(R.id.txt_number);
            mTxtTime = (TextView) v
                    .findViewById(R.id.txt_time);
            mCallType = (TextView) v
                    .findViewById(R.id.txt_call_type);
        }

        public void setName(String strDisplayName) {
            mTxtName.setText(strDisplayName);
        }

        public void setNumber(String strNumber) {
            mTxtNumber.setText(strNumber);
        }

        public void setTime(String strTime) {
            final Date date = new Date(Long.valueOf(strTime));
            final DateFormat dateFormat = DateFormat.getDateTimeInstance();
            mTxtTime.setText(dateFormat.format(date));
        }

        public void setCallType(String strCallType) {
            mCallType.setText(strCallType);
        }
    }


}

