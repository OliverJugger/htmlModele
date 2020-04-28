package htmlModele;

import java.util.List;

public class Main {

	public static void main(final String[] args) throws Exception {

		System.out.println("Hey Roro !");

		List < Fragment > articles = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\Article");

		List < Fragment > visas = Fragment.listFragments(
			"D:\\projet-administration-des-documents\\Livraison_Fragments\\formulairesLiveCycle\\Fragment\\VisaComplementaire");

		Arrete.test("D:\\test.xml", "D:\\test.pdf");

		System.out.println("Cay fini !");

	}

}
