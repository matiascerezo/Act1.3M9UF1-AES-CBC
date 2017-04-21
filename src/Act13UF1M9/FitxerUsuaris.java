package Act13UF1M9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Matias
 */
public class FitxerUsuaris {

    private File file = new File("ficheroUsuarios.txt");

    private SecretKey skey;

    public static final byte[] IV_PARAM = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};

    private String cadena_iv = "0123456789ABCDEF";

    public SecretKey addKey(String contrasenya) {
        try {
            byte[] data = contrasenya.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data);
            System.out.println("Clave generada correctamente.");
            skey = new SecretKeySpec(hash, "AES");
        } catch (Exception ex) {
            System.err.println("Error generant la clau: " + ex);
        }
        return skey;
    }

    /**
     * Este método cifra un fichero con una SecretKey. Creamos un
     * FileInputStream con el fichero que le llega por parametro y un
     * FileOutputStream que cree un nuevo fichero, que será el
     * ficheroCifradoUsuarios.txt usando el algoritmo CBC y diciendole a c.init
     * que use el modo ENCRYPT. Después haremos que lea el fichero y que escriba
     * en el otro el contenido pero todo encriptado.
     *
     * @param fitxer
     * @param clau
     * @throws FileNotFoundException
     */
    public void encryptFile(String fitxer, SecretKey clau) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(fitxer);
            FileOutputStream fos = new FileOutputStream(new File("ficheroCifradoUsuarios.txt"));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(cadena_iv.getBytes());
            c.init(Cipher.ENCRYPT_MODE, clau, iv);
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer, 0, buffer.length)) != -1) {
                byte[] update = c.update(buffer, 0, bytesLeidos);
                fos.write(update);
            }
            fos.write(c.doFinal());
            System.out.println("Fichero cifrado!");
            fis.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("Error al cifrar");
        }
    }

    /**
     * Este método descifra un fichero. Le llega por parametro el
     * ficheroCifradoUsuarios.txt, la SecretKey, y el nombre del
     * ficheroDesencriptadoUsuarios.txt (El nombre que tendrá el nuevo fichero).
     * Creamos el FileInputStream con el ficheroEncriptado para leer y el
     * FileOutputStream para escribir en el nuevo fichero que creamos. Usaremos
     * el Cipher usando el algoritmo CBC y le decimos a c.init que esté en modo
     * DECRYPT, clave que usará para desencriptar. Dspués haremos un bucle que
     * recorra el fichero encriptado para finalmente escribir el contenido
     * desencriptado en el nuevo fichero creado. Si no establecemos los
     * bytesLeidos, a la hora de descifrar el fichero no nos aparecerá el
     * contenido.
     *
     * @param fitxerEncriptat
     * @param clave
     * @param fitxerDesencriptat
     */
    public void decryptFile(String fitxerEncriptat, SecretKey clave, String fitxerDesencriptat) {
        try {
            FileInputStream fis = new FileInputStream(fitxerEncriptat);
            FileOutputStream fos = new FileOutputStream(new File(fitxerDesencriptat));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(cadena_iv.getBytes());
            c.init(Cipher.DECRYPT_MODE, clave, iv);
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer, 0, buffer.length)) != -1) {
                byte[] update = c.update(buffer, 0, bytesLeidos);
                fos.write(update);
            }
            fos.write(c.doFinal());
            System.out.println("Fichero descifrado!");
            fis.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("Error al descrifrar. " + "Error : " + e);
        }

    }

    public boolean addUserFile(Usuari u) throws FileNotFoundException, IOException {
        boolean correcte = false;
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(u.getDNI() + ":");
            fw.write(u.getNom() + ":");
            fw.write(u.getCognoms() + ":");
            fw.write(u.getUsuari() + ":");
            fw.write(u.getPassword() + "\n");
            correcte = true;
            fw.close();
        } catch (Exception e) {
            correcte = false;
        }
        return correcte;
    }

    /**
     * Método que muestra el contenido del archivo sin cifrar.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void showFile() throws FileNotFoundException, IOException {
        ArrayList<String> usuariosArchivo = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String usuari;
        while ((usuari = br.readLine()) != null) {
            usuariosArchivo.add(usuari);
        }
        for (int i = 0; i < usuariosArchivo.size(); i++) {
            System.out.println(usuariosArchivo.get(i));
        }
    }
}
