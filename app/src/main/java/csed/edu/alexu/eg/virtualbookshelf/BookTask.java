package csed.edu.alexu.eg.virtualbookshelf;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Bookshelves;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BookTask extends AsyncTask<String, Void, Bookshelves> {

    @Override
    protected Bookshelves doInBackground(String... params) {
        Bookshelves bookshelves = null;
        try {
            Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName("VirtualBookShelf")
//                   .setGoogleClientRequestInitializer(new BooksRequestInitializer("AIzaSyCik4DYGQfsRcBp1a-cB6seTwuA6cqxCKY"))
                    .build();
            Log.d("Soso", "abl1");
            Books.Mylibrary.Bookshelves bookLib = books.mylibrary().bookshelves();
            Log.d("Soso", "abl2");
            Books.Mylibrary.Bookshelves.List bookList = null;
            bookList = bookLib.list();

//            bookList.setOauthToken(params[0]);
            Log.d("Soso", "abl3");

            bookshelves = bookList.execute();
            Log.d("Soso", "b3d");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Soso", "w geina lel error tani");
        }
        return bookshelves;
    }

    @Override
    protected void onPostExecute(Bookshelves bookshelves) {
        super.onPostExecute(bookshelves);

        for (Bookshelf bookshelf : bookshelves.getItems()) {
            Log.d("Soso BookShelf Title: ", bookshelf.getTitle());
            Log.d("Soso", "Fady Isa:D");
        }
    }
}
