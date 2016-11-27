package nd.rw.fint;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class MainActivity extends AppCompatActivity {

  //region Fields

  public static final String TAG = MainActivity.class.getCanonicalName();

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;

  private FirebaseAnalytics mFirebaseAnalytics;

  //endregion Fields

  //region Android lifecycle

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    mFirebaseAnalytics.setCurrentScreen(this, null, null);
    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          Log.d(TAG, "onAuthStateChanged : signed_in:" + user);
          Log.d(TAG, "onAuthStateChanged : signed_in:" + user);
          user.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override public void onComplete(@NonNull Task<GetTokenResult> task) {
              Log.d(TAG, "onComplete: token: "  + task.getResult().getToken());
            }
          });

        } else {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          startActivity(intent);
        }
      }
    };
  }

  @Override
  public void onStart() {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }

  //endregion Android lifecycle

  //region Auth

  private void createAccount(){

  }

  //endregion Auth

}

