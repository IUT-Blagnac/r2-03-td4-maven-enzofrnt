public class ClasseEssai {

	public static void main(String[] arguments) throws Exception {

		Personne shmi, anakin, padme ;

		shmi = new Personne("Shmi","Skywalker","feminin",null,null); //1
		System.out.println(shmi);

		ClasseEssai.testChangeObjet(shmi) ; //2
		System.out.println(shmi);
	}

	public static void testChangeObjet (Personne pfPersonne) {
		pfPersonne.setNom ("Nouveau nom", "Autre prénom"); //3
	}
}

// Classe des objets Personne (Type Personne de S1 revu en objet)
class Personne {
	private  String nom;
	private  String prenom;
	private  String sexe;
	private  Personne descendantDe;
	private  Personne conjointDe;

	// Constructeur
	Personne(String pfPrenom, String pfNom,
			String pfSexe, Personne pfAscendant,
			Personne pfConjoint) {
		nom = pfNom;
		prenom = pfPrenom;
		sexe = pfSexe;
		descendantDe = pfAscendant;
		conjointDe = pfConjoint;
	}

	public void mariage(Personne pfNouveauConjoint) {
		this.conjointDe = pfNouveauConjoint;
		pfNouveauConjoint.conjointDe = this;
	}

	public void setNom(String pfNom, String pfPrenom) {
		this.nom = pfNom;   //4
		this.prenom = pfPrenom;
	}

	public String toString() {
		String resultat = "";
		resultat = resultat + "\nPersonne: "
				+ System.identityHashCode(this) + "\n";
		resultat = resultat + "  Nom: " + this.nom + "\n";
		resultat = resultat + "  Prenom: " + this.prenom + "\n";
		resultat = resultat + "  Sexe: " + this.sexe + "\n";
		resultat = resultat + "  Conjoint de: "
				+ System.identityHashCode(this.conjointDe) + "\n";
		resultat = resultat + "  Descendant de: "
				+ System.identityHashCode(this.descendantDe);
		return resultat;
	}
}