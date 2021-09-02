package android.nextgen.onetapemengercy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private static final int CONTACT_PICKER_REQUEST = 202;
    ArrayList<ContactResult> results= new ArrayList<ContactResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("do this");
    }

    public void chooseContact(View view){
        new MultiContactPicker.Builder(SettingActivity.this)
                .hideScrollbar(false)
                .showTrack(true)
                .searchIconColor(Color.WHITE)
                .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE)
                .handleColor(ContextCompat.getColor(SettingActivity.this, R.color.azureColorPrimary))
                .bubbleColor(ContextCompat.getColor(SettingActivity.this, R.color.azureColorPrimary))
                .bubbleTextColor(Color.WHITE)
                .setTitleText("Select Contacts")
                .setLoadingType(MultiContactPicker.LOAD_ASYNC)
                .limitToColumn(LimitColumn.NONE)
                .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out)
                .showPickerForResult(CONTACT_PICKER_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
               results = MultiContactPicker.obtainResult(data);
                Log.d("MyTag", results.get(0).getDisplayName());
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        intent.putExtra("results", results);
        startActivity(intent);
    }
}