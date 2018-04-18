package csed.edu.alexu.eg.virtualbookshelf;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.util.List;

public class RemoveVolumeFromShelf extends UserUtils {

    public RemoveVolumeFromShelf(Context context, Account account) {
        super(context, account);
    }

    @Override
    protected Volumes doInBackground(String... params) {
        //params[0]: ShelfID, params[1]: volumeID
        if(params.length == 1) clearShelf(params[0]);
        else deleteFromShelf(params[0], params[1]);
        return null;
    }
    private void clearShelf (String shelfID){

        try {
            Books.Mylibrary.Bookshelves.ClearVolumes bookShelve = null;
            Log.d("Soso", "before delete from bookShelf");
            bookShelve = books.mylibrary().bookshelves().clearVolumes(shelfID);
            bookShelve.execute();
            Log.d("Soso", "after delete from bookShelf");
        } catch (IOException e) {
            Log.e("Soso", "Error in remove book volume");
        }

    }
    private void deleteFromShelf (String shelfID, String volumeID){
        try {

            Books.Mylibrary.Bookshelves.RemoveVolume bookShelve = null;
            Log.d("Soso", "before delete from bookShelf");
            bookShelve = books.mylibrary().bookshelves().removeVolume(shelfID, volumeID);
            bookShelve.execute();
            Log.d("Soso", "after delete from bookShelf");

        } catch (IOException e) {
            Log.e("Soso", "Error in remove book volume");
        }

    }

}
