package csed.edu.alexu.eg.virtualbookshelf.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import csed.edu.alexu.eg.virtualbookshelf.R;

public class BookActivity extends AppCompatActivity {
    private static final String SEPARATOR = " , ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Log.d(this.getClass().getName() , "open individual book number " );

        String volumeId = (String) this.getIntent().getExtras().get("book_id");

        try {
            // TODO : REMOVE THIS
            Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName("VirtualBookShelf")
                    //.setHttpRequestInitializer((GoogleAccountCredential)this.getIntent().getSerializableExtra("token"))
                    .setBooksRequestInitializer(new BooksRequestInitializer("AIzaSyCik4DYGQfsRcBp1a-cB6seTwuA6cqxCKY"))
                    .build();
            Books.Volumes.Get listBooksInst = books.volumes().get(volumeId);
            Volume volume = listBooksInst.execute();

            // ui items
            ImageView bookImage = findViewById(R.id.ind_book_image);
            TextView bookTitleTxt = findViewById(R.id.ind_book_title);
            TextView authorsTxt = findViewById(R.id.ind_book_authers);

            // image
            Volume.VolumeInfo.ImageLinks imageLinks = volume.getVolumeInfo().getImageLinks();
            if(imageLinks!=null) {
                Picasso.with(this).load(imageLinks.toString()).into(bookImage);
            }

            // title
            bookTitleTxt.setText(volume.getVolumeInfo().getTitle());
            List<String> authors = volume.getVolumeInfo().getAuthors();

            // authors
            if(authors!=null && !authors.isEmpty()) {
                StringBuilder authorsJoined = new StringBuilder();
                for (String author : authors) {
                    authorsJoined.append(author);
                    authorsJoined.append(SEPARATOR);
                }
                String authorsStr = authorsJoined.toString();
                //Remove last comma
                authorsStr = authorsStr.substring(0, authorsStr.length() - SEPARATOR.length());
                authorsTxt.setText(authorsStr);
            }
        } catch (IOException e) {
            Log.e("Mirnaa", e.getMessage());
            Toast.makeText(this, "Couldn't load book's info", Toast.LENGTH_SHORT).show();
        }


    }


}


