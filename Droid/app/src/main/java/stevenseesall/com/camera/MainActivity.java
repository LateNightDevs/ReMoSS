package stevenseesall.com.camera;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViewById(R.id.btn_Connect).setOnClickListener(
            new Connection_OnClickListener(this)
        );
    }

    private class Connection_OnClickListener implements View.OnClickListener {

        private Activity mActivity;
        private EditText mServerEditText;
        private FrameLayout mCameraPreview;

        public Connection_OnClickListener(Activity activity) {
            mActivity = activity;
            mServerEditText = (EditText)findViewById(R.id.txtIp);
        }

        @Override
        public void onClick(View v) {
            setContentView(R.layout.remote_mode);
            Context context = getApplicationContext();
            String encryptedAddress = mServerEditText.getText().toString();

            IPAddressCipher ipCipher = new IPAddressCipher(context);
            String decryptedAddress = ipCipher.decryptIPAddress(encryptedAddress);

            mCameraPreview = (FrameLayout)findViewById(R.id.camera_preview);
            UDPRunnable udpRunnable = new UDPRunnable(
                    context,
                    mActivity,
                    mCameraPreview,
                    decryptedAddress
            );

            udpRunnable.start();
        }
    }
}


