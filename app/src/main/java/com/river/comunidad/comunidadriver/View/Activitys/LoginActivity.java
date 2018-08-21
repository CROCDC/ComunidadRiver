package com.river.comunidad.comunidadriver.View.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.river.comunidad.comunidadriver.CircleMenu.MainCircleActivity;
import com.river.comunidad.comunidadriver.R;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //FACEBOOK
    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManager;

    //GOOGLE
    private GoogleApiClient googleApiClient;
    private SignInButton signInButtonGoogle;

    //TWITTER
    private TwitterLoginButton twitterLoginButton;

    //FIREBASE
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    //INTERNO
    private CardView cardViewButtonLogin;
    private CardView cardViewButtonRegister;
    private CardView cardViewButtonInvitado;

    private Integer boton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);

        setContentView(R.layout.activity_login);

        twitterLoginButton = findViewById(R.id.loginButtonTwitter_activitylogin);
        loginButtonFacebook = findViewById(R.id.loginButtonFacebook_activitylogin);
        signInButtonGoogle = findViewById(R.id.loginButtonGoogle_activitylogin);
        cardViewButtonInvitado = findViewById(R.id.cardViewButtonInvitado_activitylogin);


        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    loginExitoso();
                }
            }
        };

        touchLoginButtonTwitter();
        touchLoginButtonFacebook();
        touchLoginButtonGoogle();
        touchCardViewButtonInvitado();

    }


    public void touchLoginButtonTwitter() {
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                boton = 1;
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                AuthCredential authCredential = TwitterAuthProvider.getCredential(
                        session.getAuthToken().token,
                        session.getAuthToken().secret);

                firebaseAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loginExitoso();
                                } else {
                                    loginFallido();
                                }

                            }
                        });
            }

            @Override
            public void failure(TwitterException exception) {
                loginFallido();
            }
        });
    }

    public void touchLoginButtonFacebook() {

        //OBJETOS NECESARIOS PARA EL LOGIN CON FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        loginButtonFacebook.setReadPermissions("email");

        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                boton = 2;
                AuthCredential authCredential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());

                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginExitoso();

                    }
                });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                loginFallido();


            }
        });
    }

    public void touchLoginButtonGoogle() {

        //OBJETOS NECESARIOS PARA EL LOGIN CON GOOGLE
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boton = 3;
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, 777);
            }
        });
    }

    public void touchCardViewButtonInvitado() {
        cardViewButtonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(LoginActivity.this, "Inicio como invitado", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                startActivity(new Intent(LoginActivity.this, MainCircleActivity.class));
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 140) {
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }else if (requestCode == 64206){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }else if (requestCode == 777){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                AuthCredential credential = GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null);
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginExitoso();
                        }
                    }
                });
            } else {
                loginFallido();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void loginExitoso() {
        FancyToast.makeText(this, "Login exitoso", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        startActivity(new Intent(LoginActivity.this, MainCircleActivity.class));
        finish();

    }

    public void loginFallido() {
        FancyToast.makeText(this, "hubo un error en el login", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
