package csed.edu.alexu.eg.virtualbookshelf.utility;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;

import csed.edu.alexu.eg.virtualbookshelf.models.UserFunctionality.EditShelf;

public class AddVolumeToShelf extends UserUtils {

    public AddVolumeToShelf(Books books) {
        super(books);
    }

    @Override
    // Params are sent where params[0]: ShelfID, params[1]: volumeID
    protected Volumes doInBackground(String... params) {
        EditShelf shelf = new EditShelf();
        shelf.AddVolumeToShelf(params[0], params[1], books);
        return null;
    }
}
