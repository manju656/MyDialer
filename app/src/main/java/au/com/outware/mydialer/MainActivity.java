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
        System.out.println(phNumber);
        mNumTxt.setText("");
        mNumTxt.setText(phNumber);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
