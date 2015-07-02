package au.com.outware.mydialer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.txt_num)
    EditText mNumTxt;

    @ViewById(R.id.btn_call)
    Button mdialBtn;

    @ViewById(R.id.lst_recent_calls)
    ListView mLstCall;

    @Bean
    RecentCallAdapter recentCallAdapter;

    @AfterViews
    void bindAdapter() {
        mLstCall.setAdapter(recentCallAdapter);

    }

    @ItemClick(R.id.lst_recent_calls)
    void onItemSelected(CallDetails selectedItem) {
        final String phNumber = selectedItem.getPhNumber();
        mNumTxt.setText(phNumber);
    }

    @ItemLongClick(R.id.lst_recent_calls)
    void onItemLongClick(CallDetails selectedItem){
        final String phNumber = selectedItem.getPhNumber();
        final String name = selectedItem.getCallerName();
        String shareBody = name+" - "+phNumber;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share With"));
    }

    @Click(R.id.btn_call)
    void onClickDial() {
        try {
            if (mNumTxt != null && (mNumTxt.getText().length() == 10
                    || mNumTxt.getText().length() == 11)) {
                startActivity(new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + mNumTxt.getText())));
            } else if (mNumTxt != null && mNumTxt.getText().length() == 0) {
                Toast.makeText(getApplicationContext(),
                        "You missed to type the number!", Toast.LENGTH_SHORT).show();
            } else if (mNumTxt != null &&
                    mNumTxt.getText().length() < 10) {
                Toast.makeText(getApplicationContext(), "Check whether you entered correct number!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("DialerAppActivity", "error: " +
                    e.getMessage(), e);//Runtime error will be logged
        }
    }

}
