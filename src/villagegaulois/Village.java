package villagegaulois;


import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbetals);
	}

	public String getNom() {
		return nom;
	}


	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche {
		private Etal[] etals;
		private Marche(int nbetals) {
			etals = new Etal[nbetals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (indiceEtal <= 0 && indiceEtal > etals.length) {
				System.out.println("Le numero d'etal est en dehors du tableau d'etals");
			} else if (etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
				
			} else {
				System.out.println("l'etal " + indiceEtal + " est deja occup�!");
			}
		}
		
		private int trouverEtalLibre() {
			for (int i=0; i< etals.length; i++)
				if (!etals[i].isEtalOccupe()) 
					return i;
			System.out.println("Aucun �tals dispo");
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbetalsAvecProduit=0;
			for (int i=0; i< etals.length; i++) 
				if (etals[i].contientProduit(produit))
					nbetalsAvecProduit++;
			
			if (nbetalsAvecProduit ==0) {
				System.out.println("Aucun �tal ne correspond au produit" + produit);
				return null;
			}
			Etal[] etalsAvecProduit = new Etal[nbetalsAvecProduit];
			int iproduit=0;
			for (int i=0; i< etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProduit[iproduit] = etals[i];
					iproduit++;
				}
				
			}
			return etalsAvecProduit;
		}
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i< etals.length; i++) {
				if (etals[i].getVendeur() == gaulois)
					return etals[i];
			}
			return null;
		}
		
		private void afficherMarche() {
			int etalslibre = 0;
			for (int i=0; i< etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				} else {
					etalslibre++;
				}
			}
			if(etalslibre != 0) {
				System.out.println("Il reste " + etalslibre + " etals non utilis�s dans le marche");
			}
		}
	}
	
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int numEtalsLibre;
		chaine.append(vendeur + " cherche un endroit pour vendre " + nbProduit + produit + "\n");
		numEtalsLibre = marche.trouverEtalLibre();
		if(numEtalsLibre != 0 ) {
			marche.utiliserEtal(numEtalsLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur + " vend des " + produit + "a l'etal n " + numEtalsLibre);
		} else {
			chaine.append("Plus d'etals libre dans ce marche!");
		}
		return chaine.toString();
	}
}