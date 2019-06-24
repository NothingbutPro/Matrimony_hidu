package ics.hindu.matrimony.activity.loginsignup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import ics.hindu.matrimony.Models.LoginDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.dashboard.Home_Main;
import ics.hindu.matrimony.activity.search.Search;
import ics.hindu.matrimony.activity.dashboard.Dashboard;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomButton;
import ics.hindu.matrimony.view.CustomEditText;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.ExtendedEditText;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private ExtendedEditText etNumber;
    private CustomEditText etPassword;
    private CustomButton btnLogin, btnSearch;
    private CustomTextView tvCreateNewAC, tvForgotPass;
    private Context mContext;
    private String TAG = Login.class.getSimpleName();
    private RelativeLayout RRsncbar;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private SharedPreferences firebase;

    SignInButton signup_google;
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 101;
     //GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mContext = Login.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        firebase = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e("tokensss", firebase.getString(Consts.FIREBASE_TOKEN, ""));
        //**************************************************************************************************

//        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
//        authStateListener = new FirebaseAuth.AuthStateListener(){
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                // Get signedIn user
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                //if user is signed in, we call a helper method to save the user details to Firebase
//                if (user != null) {
//                    // User is signed in
//                    // you could place other firebase code
//                    //logic to save the user details to Firebase
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//***************************************************************************************************************

        setUIAction();

        signup_google=(SignInButton)findViewById(R.id.signup_google);

//        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.client_id))//you can also use R.string.default_web_client_id
//                .requestEmail()
//                //.requestScopes(new Scope(Scopes.EMAIL))
//                .build();
//
//        googleApiClient=new GoogleApiClient.Builder(this)
//                .enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();
//
////        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        signup_google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////                startActivityForResult(signInIntent, RC_SIGN_IN);
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent,RC_SIGN_IN);
//            }
//        });

    }

    public void setUIAction() {

        RRsncbar = findViewById(R.id.RRsncbar);
        etPassword = findViewById(R.id.etPassword);
        etNumber = findViewById(R.id.etNumber);
       // etNumber.setPrefix("+91 ");
        btnLogin = findViewById(R.id.btnLogin);
        btnSearch = findViewById(R.id.btnSearch);
        btnLogin.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        tvCreateNewAC = findViewById(R.id.tvCreateNewAC);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvCreateNewAC.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
       // signup_google.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
               // clickForSubmit();

//                Intent in = new Intent(mContext, Dashboard.class);
//                startActivity(in);
//                finish();
//                overridePendingTransition(R.anim.anim_slide_in_left,
//                        R.anim.anim_slide_out_left);
                Intent in = new Intent(mContext, Home_Main.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);

                break;
            case R.id.tvCreateNewAC:
                startActivity(new Intent(mContext, Registration.class));
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.btnSearch:
                startActivity(new Intent(mContext, Search.class));
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.tvForgotPass:
                Intent intent = new Intent(mContext, ForgotPass.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;

//            case R.id.signup_google:
//                Intent inten = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(inten,RC_SIGN_IN);
//                overridePendingTransition(R.anim.anim_slide_in_left,
//                        R.anim.anim_slide_out_left);
//                break;
        }
    }

    public void clickForSubmit() {
        if (!ProjectUtils.isPhoneNumberValid(etNumber.getText().toString().trim())) {
            showSickbar(getString(R.string.val_mobile));
        } else if (!ProjectUtils.IsPasswordValidation(etPassword.getText().toString().trim())) {
            showSickbar(getString(R.string.val_password));
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                login();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
            }
        }


    }

    public void login() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.LOGIN_API, getparm(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {


                    ProjectUtils.showToast(mContext, msg);

                    loginDTO = new Gson().fromJson(response.toString(), LoginDTO.class);


                    prefrence.setLoginResponse(loginDTO, Consts.LOGIN_DTO);
                    prefrence.setBooleanValue(Consts.IS_REGISTERED, true);
                    ProjectUtils.showToast(mContext, msg);
                    Intent in = new Intent(mContext, Dashboard.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.MOBILE, ProjectUtils.getEditTextValue(etNumber));
        parms.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
        parms.put(Consts.FIREBASE_TOKEN, firebase.getString(Consts.FIREBASE_TOKEN, ""));
        Log.e(TAG + " Login", parms.toString());
        return parms;
    }


    public void showSickbar(String msg) {
        Snackbar snackbar = Snackbar.make(RRsncbar, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            int statusCode = result.getStatus().getStatusCode();
//            handleSignInResult(result);
//            Log.e("google_status", String.valueOf(statusCode));
//        }
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//            Log.e(TAG, "resultcode:" + resultCode);
//        if (requestCode == RC_SIGN_IN) {
//            Log.e(TAG, "requestcode:" + requestCode);
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.e(TAG, "result:" + result.toString());
//            if (result.isSuccess()) {
//                GoogleSignInAccount account = result.getSignInAccount();
//                Log.e("DataSign",account.getDisplayName());
//                Log.e("DataSign",account.getEmail());
//                Log.e("DataSign",account.getIdToken());
//
////                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
////                firebaseAuthWithGoogle(credential);
//            }else {
//                Log.e("loginfailed", "failed login");
//            }
//
//                    try {
//                        Log.e(TAG, "resultcode:" + resultCode);
//                        Log.e(TAG, "intent data:" + data);
//                        // The Task returned from this call is always completed, no need to attach
//                        // a listener.
//                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                        GoogleSignInAccount account = task.getResult(ApiException.class);
//                        onLoggedIn(account);
//                    } catch (ApiException e) {
//                        // The ApiException status code indicates the detailed failure reason.
//                        Log.e( "signInResult:failed","" + e.getStatusCode());
//                    }

      //  }
   // }

//    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
//        Log.e(TAG, "handleSignInResult" + googleSignInAccount);
////        Intent intent = new Intent(this, ProfileActivity.class);
////        intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount);
////
////        startActivity(intent);
////        finish();
//
//        Toast.makeText(getApplicationContext(), "True login", Toast.LENGTH_SHORT).show();
//    }
//
//
//    private void handleSignInResult(GoogleSignInResult result){
//        Log.e(TAG, "handleSignInResult:" + result.isSuccess());
//        if(result.isSuccess()){
//            gotoProfile();
//        }else{
//            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
//        }
//    }
//    private void gotoProfile(){
//        Intent intent=new Intent(Login.this,Dashboard.class);
//        startActivity(intent);
//    }


}
