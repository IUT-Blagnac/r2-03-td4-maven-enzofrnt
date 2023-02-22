import java.util.Locale;
import java.util.Scanner;

import tps.banque.AgenceBancaire;
import tps.banque.Compte;
import tps.banque.exception.ABCompteDejaExistantException;
import tps.banque.exception.ABCompteInexistantException;
import tps.banque.exception.ABCompteNullException;

public class ClasseApplicationAgenceBancaire {

	/**
	 * Affichage du menu de l'application
	 * 
	 * @param ag
	 *            AgenceBancaire pour récupérer le nom et la localisation
	 */
	public static void afficherMenu(AgenceBancaire pfAg) {
		System.out.println("Menu de " + pfAg.getNomAgence() + " (" + pfAg.getLocAgence() + ")");
		System.out.println("c - Créer un nouveau compte dans l'agence");
		System.out.println("s - Supprimer un compte de l'agence (par son numéro)");
		System.out.println("l - Liste des comptes de l'agence");
		System.out.println("v - Voir un compte (par son numéro)");
		System.out.println("p - voir les comptes d'un Propriétaire (par son nom)");
		System.out.println("d - Déposer de l'argent sur un compte");
		System.out.println("r - Retirer de l'argent sur un compte");
		System.out.println("q - Quitter");
		System.out.print("Choix -> ");
	}

	/**
	 * Temporisation : Affiche un message et attend la frappe de n'importe quel
	 * caractère.
	 */
	public static void tempo() {
		Scanner lect;
		String s;

		lect = new Scanner(System.in);

		System.out.print("Tapper un car + return pour continuer ... ");
		s = lect.next(); // Inutile à stocker mais c'est l'usage normal ...
	}

	public static void main(String[] argv) {

		String choix;
		String nom, numero;
		boolean continuer;
		Scanner lect;
		AgenceBancaire monAg;
		double montant;

		lect = new Scanner(System.in);
		lect.useLocale(Locale.US);

		monAg = new AgenceBancaire("Caisse Ep", "Pibrac");

		continuer = true;
		while (continuer) {
			ClasseApplicationAgenceBancaire.afficherMenu(monAg);
			choix = lect.next();
			choix = choix.toLowerCase();
			switch (choix) {
			//Case quitter
			case "q":
				System.out.println("ByeBye");
				ClasseApplicationAgenceBancaire.tempo();
				continuer = false;
				break;
			
			//Case création d'un nouveau compte
			case "c":
				Compte c;
				System.out.println("Création d'un nouveau compte : ");
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.print("Propriétaire -> ");
				nom = lect.next();
				
				// Vérification de l'existence du compte de numéro "numero"
				if(monAg.getCompte(numero) != null){
					System.out.println("Ce compte existe déjà, vous ne pouve pas le créer et il appartient à " + monAg.getCompte(numero).getProprietaire());
				}else{
					// Création du compte avec le numéro et le nom saisis
					c = new Compte(numero, nom);
					try{
						// Ajout du compte à l'agence
						monAg.addCompte(c);
						System.out.println("Compte créé avec succès et ajouté à l'agence " + monAg.getNomAgence());
					} catch (ABCompteNullException | ABCompteDejaExistantException e) {
						// Si l'ajout du compte à l'agence échoue, on affiche un message d'erreur
						System.out.println("Erreur lors de l'ajout du compte à l'agence" + monAg.getNomAgence());
						System.out.println(e.getMessage());
					}
				}

				ClasseApplicationAgenceBancaire.tempo();
				break;

			//Case suppression d'un compte
			case "s":
				System.out.print("Num compte -> ");
				numero = lect.next();
				try {
					monAg.removeCompte(numero);
					System.out.println("Suppression effectuée\n");
				} catch (ABCompteInexistantException e) {
					System.out.println("Numéro de compte inexistant");
					System.out.println(e.getMessage());
				}
				ClasseApplicationAgenceBancaire.tempo();
				break;

			//Case lister les comptes de l'agence
			case "l":
				System.out.println("Liste des comptes de l'agence " + monAg.getNomAgence());
				monAg.afficher();
				ClasseApplicationAgenceBancaire.tempo();
				break;

			//Case voir un compte
			case "v":
				//saisie du numéro de compte
				System.out.print("Num compte -> ");
				numero = lect.next();
				//vérification de l'existence du compte
				if(monAg.getCompte(numero) == null){
					System.out.println("Le compte uméro " + numero + " n'existe pas");
				}else{
					//affichage du compte
					monAg.getCompte(numero).afficher();
				}
				ClasseApplicationAgenceBancaire.tempo();
				break;
			
			//Case voir les comptes d'un propriétaire
			case "p":
				//sasiie du nom du propriétaire	
				System.out.print("Propriétaire -> ");
				nom = lect.next();
				
				liste(monAg, nom);

				ClasseApplicationAgenceBancaire.tempo();
				break;
			
			//Case déposer de l'ragent sur  un compte
			case "d":
				//saisie du numéro de compte
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.println("Solde du compte avant dépot " + numero + " : ");
				monAg.getCompte(numero).soldeCompte();
				//saisie du montant à déposer
				System.out.print("Montant -> ");
				montant = lect.nextDouble();
				deposer(monAg, numero, montant);
				System.out.println("Solde du compte après dépot " + numero + " : ");
				monAg.getCompte(numero).soldeCompte();

				ClasseApplicationAgenceBancaire.tempo();
				break;

			 	//Case retrait d'argent sur un compte
			case "r":
				//saisie du numéro de compte
				System.out.print("Num compte -> ");
				numero = lect.next();
				System.out.println("Solde du compte avant retrait " + numero + " : ");
				monAg.getCompte(numero).soldeCompte();
				//saisie du montant à déposer
				System.out.print("Montant -> ");
				montant = lect.nextDouble();
				retirer(monAg, numero, montant);
				System.out.println("Solde du compte après retrait " + numero + " : ");
				monAg.getCompte(numero).soldeCompte();

				ClasseApplicationAgenceBancaire.tempo();
				break;

			//Case erreur de saisi
			default:
				System.out.println("Erreur de saisie ...");
				ClasseApplicationAgenceBancaire.tempo();
				break;
			}
		}

	}

	public static void liste(AgenceBancaire pfAg, String pfNomProprietaire) {
		//vérification de l'existence du propriétaire
		if(pfAg.getComptesDe(pfNomProprietaire).length == 0){
			System.out.println("Aucun compte ne correspond à ce propriétaire");
		}else{
			System.out.println("Liste des comptes de " + pfNomProprietaire);
			//affichage des comptes
			for(Compte c1 : pfAg.getComptesDe(pfNomProprietaire)){
				c1.afficher();
			}
		}
	}

	public static void deposer(AgenceBancaire pfAg, String pfNumeroCompte, double pfMontant) {
		try {
			pfAg.getCompte(pfNumeroCompte).deposer(pfMontant);
			System.out.println("Dépoot effectué sur le compte de " + pfAg.getCompte(pfNumeroCompte).getProprietaire() + " numéro " + pfNumeroCompte + " pour un montant de " + pfMontant + " euros");
		} catch (Exception e) {
			System.out.println("Erreur lors du retrait");
			System.out.println(e.getMessage());
		}
	}

	private static void retirer(AgenceBancaire pfAg, String pfNumeroCompte, double pfMontant) {
		try {
			pfAg.getCompte(pfNumeroCompte).retirer(pfMontant);
			System.out.println("Retrait effectué sur le compte de " + pfAg.getCompte(pfNumeroCompte).getProprietaire() + " numéro " + pfNumeroCompte + " pour un montant de " + pfMontant + " euros");
		} catch (Exception e) {
			System.out.println("Erreur lors du retrait");
			System.out.println(e.getMessage());
		}
	}
}