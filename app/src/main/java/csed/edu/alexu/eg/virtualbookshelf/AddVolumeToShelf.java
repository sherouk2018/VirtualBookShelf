package csed.edu.alexu.eg.virtualbookshelf;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Bookshelf;

import java.io.IOException;
import java.util.List;

public class AddVolumeToShelf extends UserUtils {
    public AddVolumeToShelf(Context context, Account account) {
        super(context, account);
    }

    @Override
    // Params are sent where params[0]: ShelfID, params[1]: volumeID
    protected Void doInBackground(String... params) {
        Books.Mylibrary.Bookshelves.AddVolume bookShelve = null;
        try {
            //params[0]: ShelfID, params[1]: volumeID
            Log.d("Soso", "before Addto bookShelf");
            bookShelve = books.mylibrary().bookshelves().addVolume(params[0], params[1]);
            bookShelve.execute();
            Log.d("Soso", "after Addto bookShelf");
            //
            List<Bookshelf> shelves = books.mylibrary().bookshelves().list().execute().getItems();
            for (Bookshelf bookshelf : shelves) {
                if(bookshelf.getId() == 0 )
                    bookshelf.setTitle("Favourite");
                Log.d("Soso BookShelf ID: ", ""+bookshelf.getId()
                        + "\n bookTitle " +bookshelf.getTitle()
                        + "\n volume count: "+bookshelf.getVolumeCount()
                        + "\n Authority: " + bookshelf.getAccess()
                        + "\n Kind "+bookshelf.getKind());
                Log.d("Soso", "Fady Isa:D");
            }
            Log.d("Soso", "5alasna Authen." + shelves.size());
            //
        } catch (IOException e) {
            Log.e("Soso", "Error in add book volume");
        }
        return null;
    }
}
