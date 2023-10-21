package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import personnages.Chef;  
import personnages.Druide;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Etal etal = new Etal();
		
		
		etal.occuperEtal(asterix, "carotte", 10);
		System.out.println(etal.afficherEtal());
		
		System.out.println(etal.acheterProduit(4, asterix));
		System.out.println("Fin du test");
	}

}
