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
	
	
	
	

	
//////////////////////Classe Interne //////////////////////////////
	
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (indiceEtal <= 0 && indiceEtal > etals.length) {
				System.out.println("Le numero d'etal est en dehors du tableau d'etals");
			} else if (!etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
				
			} else {
				System.out.println("l'etal " + indiceEtal + " est deja occupe!");
			}
		}
		
		private int trouverEtalLibre() {
			for (int i=0; i< etals.length; i++)
				if (!etals[i].isEtalOccupe()) 
					return i;
			System.out.println("Aucun etals dispo");
			return -1;
		}
		
		/*private Etal[] trouverEtals(String produit) {
			int nbetalsAvecProduit=0;
			for (int i=0; i< etals.length; i++) 
				if (etals[i].contientProduit(produit))
					nbetalsAvecProduit++;
			
			if (nbetalsAvecProduit ==0) {
				System.out.println("Aucun etal ne correspond au produit" + produit);
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
		}*/
		
		private Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtal++;
				}
			}
			Etal[] etalsProduitsRecherche = null;
			if (nbEtal > 0) {
				etalsProduitsRecherche = new Etal[nbEtal];
				int nbEtalTrouve = 0;
				for (int i = 0; i < etals.length
						&& nbEtalTrouve < nbEtal; i++) {
					if (etals[i].isEtalOccupe()
							&& etals[i].contientProduit(produit)) {
						etalsProduitsRecherche[nbEtalTrouve] = etals[i];
						nbEtalTrouve++;
					}
				}
			}
			return etalsProduitsRecherche;
		}
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i< etals.length; i++) {
				if (etals[i].getVendeur() == gaulois)
					return etals[i];
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalslibre = 0;
			for (int i=0; i< etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					etalslibre++;
				}
			}
			if(etalslibre != 0) {
				chaine.append("Il reste " + etalslibre + " etals non utilises dans le marche");
			}
			return chaine.toString();
		}
	}
	
//////////////////////GESTION classe interne ///////////////////////
	
	
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " "+ produit + "\n");
		int numEtalsLibre = marche.trouverEtalLibre();
		if(numEtalsLibre >= 0 ) {
			marche.utiliserEtal(numEtalsLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + "a l'etal n " + numEtalsLibre + "\n");
		} else {
			chaine.append("Plus d'etals libre dans ce marche!");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsproduits = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if( etalsproduits.length < 1) {
			chaine.append("Personne ne vends de " + produit);
		} else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :  \n");
			for (int i = 0; i < etalsproduits.length; i++) {
				chaine.append("- " + etalsproduits[i].getVendeur().getNom() + "\n");
			}		
		}
		return chaine.toString();
		
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		StringBuilder chaine = new StringBuilder();
		if(etal.isEtalOccupe()) {
			return etal.libererEtal() ;
			
		} else
			chaine.append("Il n'existe pas de vendeur nomme " + vendeur.getNom());
			return chaine.toString();
		
	}
	
	public String afficherMarche() {
		System.out.println("Le marche du village " + nom + "possede plusieurs etals : \n");
		return marche.afficherMarche();
	}
	
	
	
}