package Controller;

import javafx.scene.control.TextField;
import java.sql.Connection;

/**
 * Created by Ender on 29-05-2017.
 */
public interface ILoginFunktion {

    boolean login(Connection conn, TextField nameInput, TextField passwordInput);
}
