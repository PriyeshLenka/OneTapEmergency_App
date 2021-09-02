package android.nextgen.onetapemengercy;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.telephony.SmsManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.wafflecopter.multicontactpicker.ContactResult;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * helper methods.
 */
public class MySmsService extends IntentService {


    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "android.nextgen.onetapemengercy.action.FOO";
    private static final String ACTION_WHATSAPP = "android.nextgen.onetapemengercy.action.BAZ";


    private static final String MESSAGE = "android.nextgen.onetapemengercy.extra.PARAM1";
    private static final String MOBILE_NUMBER = "android.nextgen.onetapemengercy.extra.PARAM2";

    public MySmsService() {
        super("MySmsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionSMS(Context context, String message,ArrayList<ContactResult> mobile_numbers) {
        ArrayList<String> numbers=new ArrayList<String>();
        for(int i=0 ; i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
        }
        String[] numbersArray =numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySmsService.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionWHATSAPP(Context context, String message,ArrayList<ContactResult> mobile_numbers) {
        ArrayList<String> numbers=new ArrayList<String>();
        for(int i=0 ; i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
        }
        String[] numbersArray =numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySmsService.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                handleActionSMS(message,mobile_number);
            } else if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                handleActionWHATSAPP(message,mobile_number);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(String message, String[] mobile_number) {

        try{
            if(mobile_number.length!=0){
                for(int i=0;i<mobile_number.length;i++){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobile_number[i],null,message,null,null);
                    sendBroadcastMessage("Message sent to: "+mobile_number[i]);
                }
            }

        }catch(Exception e){

        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String message, String[] mobile_number) {


    }

    private void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}