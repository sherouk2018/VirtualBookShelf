package csed.edu.alexu.eg.virtualbookshelf.utility;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class EditFactory  {

    private static Books books;
    private static EditFactory factory;

    private EditFactory() {
    }
    public static void init (Context myContext, Account myAccount ){
        if(factory == null){
            GoogleAccountCredential credential =
                    GoogleAccountCredential.usingOAuth2(
                            myContext,
                            Collections.singleton(
                                    "https://www.googleapis.com/auth/books")
                    );
            credential.setSelectedAccount(myAccount);
            books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName("VirtualBookShelf")
                    .setHttpRequestInitializer(credential)
                    .build();

            factory = new EditFactory();
            Log.d("Soso", "in init");
        }

    }
    public static EditFactory getInstance(){
        Log.d("Soso", "in get instance");
        return factory;

    }
    public UserUtils getEditFun(String className) {
        if (books == null) throw new RuntimeException ("Books have no entity");
        try {
            Log.d("Soso className", className);
            Constructor c = Class.forName("csed.edu.alexu.eg.virtualbookshelf.utility."+ className)
                    .getConstructor(Books.class);
            UserUtils obj = (UserUtils) c.newInstance(books);
            return obj;
        } catch (ClassNotFoundException | NoSuchMethodException
                | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Invalid UserUtils Class");
    }

}
