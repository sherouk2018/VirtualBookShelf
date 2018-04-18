package csed.edu.alexu.eg.virtualbookshelf;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;

import java.security.ProtectionDomain;
import java.util.Collections;

public abstract class UserUtils  extends AsyncTask<String, Void, Void>{

    protected Context context;
    protected Account account;
    protected Books books;

    public UserUtils(Context context, Account account) {
        this.context = context;
        this.account = account;
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context,
                        Collections.singleton(
                                "https://www.googleapis.com/auth/books")
                );
        credential.setSelectedAccount(account);
        books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("VirtualBookShelf")
                .setHttpRequestInitializer(credential)
                .build();

    }
    @Override
    protected abstract Void doInBackground(String... params);
}
