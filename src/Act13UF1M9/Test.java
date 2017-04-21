package Act13UF1M9;

import java.io.IOException;
import javax.crypto.SecretKey;

/**
 *
 * @author Matias
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //Creamos el usuario "Matias Cerezo Simón" con DNI "41625810L".
        Usuari u = new Usuari("41625810L", "Matias", "Cerezo Simon");
        //Creamos otro usuario.
        Usuari u1 = new Usuari("45678912M", "Hola", "Manolo Garcia");

        FitxerUsuaris fu = new FitxerUsuaris();

        //Añadimos los dos usuarios,
        fu.addUserFile(u);
        fu.addUserFile(u1);
        
        //Para ver el contenido del fichero,
        fu.showFile();

        //Para crear la contraseña,
        SecretKey key = fu.addKey(u.getUsuari());
        
        //Encriptamos el archivo.
        fu.encryptFile("ficheroUsuarios.txt", key);
        //Desencriptamos el archivo.
        fu.decryptFile("ficheroCifradoUsuarios.txt", key, "ficheroDesencriptadoUsuarios.txt");
    }
}
