package csed.edu.alexu.eg.virtualbookshelf;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.content.Intent;

import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Bookshelves;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Driver;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 100;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private Button buttonAdd;
    private GoogleSignInAccount account;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode is ", requestCode+"");
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d("Soso ", "123");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        Log.d("Soso ", "12345");
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d("Soso", "fi awl elfunction");
            account = completedTask.getResult(ApiException.class);
            Log.d("Soso", "3dina elerror");

            Log.d("Soso Email is ", account.getEmail()); // Debug
            Log.d("Soso Token is ", account.getIdToken()); // Debug
            Log.d("Soso Id is ", account.getId()); // Debug

            //AsyncTask<String, Void, Bookshelves> bookshelves = new BookTask().execute(new String[]{account.getIdToken()});

//            while (bookshelves.getStatus() != AsyncTask.Status.FINISHED) {
//                Log.d("Soso w bs", ""+bookshelves.getStatus());
//            }

            Log.d("Soso", "w ry7n 3la eloop");

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("Soso", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("840899473814-ggog8u302tcsri4otg29424dp420omi2.apps.googleusercontent.com")
                .requestScopes(new Scope("https://www.googleapis.com/auth/books"))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.signInButton);
        buttonAdd = findViewById(R.id.buttonAdd);
        Log.d("Soso", signInButton.toString());
        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Soso", "hahahaha");
                signIn();
            }
        });
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Soso", "Button pressed");
                new AddToShelf(LoginActivity.this).execute(new Account[]{account.getAccount()});
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

    }


    private class AddToShelf extends AsyncTask<Account, Void, Void> {
        private Context context;
        public AddToShelf(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Account ... params) {
            Log.d("Soso", "from addToShelf");
            try {
                GoogleAccountCredential credential =
                        GoogleAccountCredential.usingOAuth2(
                                context,
                                Collections.singleton(
                                        "https://www.googleapis.com/auth/books")
                        );
                credential.setSelectedAccount(params[0]);

                Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
                        .setApplicationName("VirtualBookShelf")
                        .setHttpRequestInitializer(credential)
                        .build();

                List<Bookshelf> shelves = books.mylibrary().bookshelves().list().execute().getItems();


                //Books.Mylibrary.Bookshelves.AddVolume bookShelve = books.mylibrary().bookshelves().addVolume("0", "buc0AAAAMAAJ");
                //bookShelve.execute();
                for (Bookshelf bookshelf : shelves) {
                    Log.d("Soso BookShelf ID: ", ""+bookshelf.getId() + " bookTitle " +bookshelf.getTitle() + " volume count: "+bookshelf.getVolumeCount() );
                    Log.d("Soso", "Fady Isa:D");
                }
                Log.d("Soso", "5alasna Authen." + shelves.size());
            }catch (Exception ex){
                Log.e("TAG", "Error while using Books", ex);
            }

            return null;
        }

    }


}
