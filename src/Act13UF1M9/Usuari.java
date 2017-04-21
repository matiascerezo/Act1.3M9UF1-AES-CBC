package Act13UF1M9;

/**
 *
 * @author Matias
 */
public class Usuari {

    private String DNI;
    private String nom;
    private String cognoms;
    private String usuari;
    private String password;

    public static String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdef"
            + "ghijklmnopqrstuvwxyzñÑ=/-";

    public Usuari(String DNI, String nom, String cognoms) {
        this.DNI = DNI;
        this.nom = nom;
        this.cognoms = cognoms;
        calcularUsuariAleatori(DNI, nom, cognoms);
        calcularContrasenyaAleatoria(10);
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Este método te calcula el usuario a partir del DNI, nombre y apellido.
     * Coge la primera letra del nombre, las 2 primeras letras del primer
     * apellido y las 2 primeras del segundo apellido (Como en el médico). Y te
     * lo calcula de esa forma. Ej: Matias Cerezo Simon con DNI 41625810L el
     * usuario seria mcesi810.
     *
     * @param DNI
     * @param nom
     * @param cognom
     */
    private void calcularUsuariAleatori(String DNI, String nom, String cognom) {
        String[] cognomSplit = cognom.split("\\s+");
        setUsuari((nom.substring(0, 1) + cognomSplit[0].substring(0, 2) + cognomSplit[1].substring(0, 2) + DNI.substring(5, 8)).toLowerCase());
    }

    private void xifraPassword() {

    }

    /**
     * Este método calcula una contraseña aleatoria con las letras, números y
     * carácteres especiales que están en CARACTERES.
     * @param tamanyo 
     */
    private void calcularContrasenyaAleatoria(int tamanyo) {
        String contrasenya = "";
        for (int i = 0; i < tamanyo; i++) {
            contrasenya += (CARACTERES.charAt((int) (Math.random() * CARACTERES.length())));
        }
        setPassword(contrasenya);
    }

    @Override
    public String toString() {
        return "Usuari{" + "DNI=" + DNI + ", nom=" + nom + ", cognoms=" + cognoms + ", usuari=" + usuari + ", password=" + password + '}';
    }
}
