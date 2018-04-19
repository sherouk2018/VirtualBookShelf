package csed.edu.alexu.eg.virtualbookshelf.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Bookshelves;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BookTask extends AsyncTask<String, Void, Bookshelves> {

    @Override
    protected Bookshelves doInBackground(String... params) {
        Bookshelves bookshelves = null;
        try {
            Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName("VirtualBookShelf")
                    .setBooksRequestInitializer(new BooksRequestInitializer("AIzaSyCik4DYGQfsRcBp1a-cB6seTwuA6cqxCKY"))
//                    .setGoogleClientRequestInitializer(new BooksRequestInitializer("AIzaSyCik4DYGQfsRcBp1a-cB6seTwuA6cqxCKY"))
                    .build();
            Log.d("Soso", "abl1");

//            //////////////////////////////////////
//            // THIS CALL IS WORKING
//            Books.Volumes vols = books.volumes();
//            Volumes ans = vols.list("title:harry").execute();
//            Log.d("Soso", ans.getTotalItems()+"");
//            ///////////////////////////////////////////
            Books.Mylibrary.Bookshelves bookLib = books.mylibrary().bookshelves();
            Log.d("Soso", "abl2");
            Books.Mylibrary.Bookshelves.List bookList = null;
            bookList = bookLib.list();
            Log.d("Soso", bookList.getKey() + "\n" + bookList.getOauthToken());

            bookList.setOauthToken(params[0]);
            Log.d("Soso", "abl3");

           // bookshelves = bookList.execute();
            Log.d("Soso", "b3d");

        } catch (IOException e) {

            Log.e("Soso", e.getMessage());
        }
        return bookshelves;
    }

    @Override
    protected void onPostExecute(Bookshelves bookshelves) {
        super.onPostExecute(bookshelves);

        for (Bookshelf bookshelf : bookshelves.getItems()) {
            Log.d("Soso BookShelf Title: ", ""+bookshelf.getId());
            Log.d("Soso", "Fady Isa:D");
        }
    }
}
