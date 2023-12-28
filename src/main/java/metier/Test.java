package metier;
import org.mindrot.jbcrypt.*;

public class Test {

	public static void main(String[] args) {
		String plainPassword = "monMotDePasse";
        String hashedPassword = hashPassword(plainPassword);
        System.out.println("Mot de passe haché : " + hashedPassword);

        // Exemple de vérification d'un mot de passe
        String userInputPassword = "monMotDePasse"; // Le mot de passe saisi par l'utilisateur
        if (checkPassword(userInputPassword, hashedPassword)) {
            System.out.println("Mot de passe correct !");
        } else {
            System.out.println("Mot de passe incorrect !");
        }
	}
	public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Méthode pour vérifier un mot de passe haché
    public static boolean checkPassword(String userInputPassword, String hashedPassword) {
        return BCrypt.checkpw(userInputPassword, hashedPassword);
    }

}
