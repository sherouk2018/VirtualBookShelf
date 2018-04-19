package csed.edu.alexu.eg.virtualbookshelf.models.UserFunctionality;

import android.util.Log;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Bookshelf;

import java.io.IOException;
import java.util.List;

public class EditShelf {
    public void AddVolumeToShelf(String shelfID, String volumeID, Books  books) {
        Books.Mylibrary.Bookshelves.AddVolume bookShelve = null;
        try {
            //params[0]: ShelfID, params[1]: volumeID
            Log.d("Soso", "before Addto bookShelf");
            bookShelve = books.mylibrary().bookshelves().addVolume(shelfID, volumeID);
            bookShelve.execute();
            Log.d("Soso", "after Addto bookShelf");
            // Todo remove unnecessary code.
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
    }

    public void clearShelf (String shelfID, Books books){

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
    public void deleteFromShelf (String shelfID, String volumeID, Books books){
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
