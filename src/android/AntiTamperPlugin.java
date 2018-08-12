package org.apache.cordova.antitamper;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AntiTamperPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getHashValue")) {
            try {
                callbackContext.success(getHashValue());
                return true;
            } catch (Exception e) {
                callbackContext.error("N/A");
                return false;
            }
        }
        return false;
    }

    private String getHashValue() {
        StringBuilder signHashValues = new StringBuilder();
        try {
            PackageInfo info = cordova.getActivity().getPackageManager().getPackageInfo(
                    cordova.getActivity().getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                String certSignHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                System.out.println("Certificate Signature Hash " + certSignHash);
                if(certSignHash!=null) {
                    signHashValues.append(certSignHash.trim()+"$");
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return signHashValues.toString();
    }
}
