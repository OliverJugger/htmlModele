package pdfToArreteJava;

import java.util.List;

public class Arrete {

	private Fragment titre;
	private List<Fragment> articles;
	private List<Fragment> visaOfficiels;
	private List<Fragment> visaComplementaires;
	
	public Arrete() {
		
	}
	
	public Fragment getTitre() {
		return titre;
	}
	public void setTitre(Fragment titre) {
		this.titre = titre;
	}
	public List<Fragment> getArticles() {
		return articles;
	}
	public void setArticles(List<Fragment> articles) {
		this.articles = articles;
	}
	public List<Fragment> getVisaOfficiels() {
		return visaOfficiels;
	}
	public void setVisaOfficiels(List<Fragment> visaOfficiels) {
		this.visaOfficiels = visaOfficiels;
	}
	public List<Fragment> getVisaComplementaires() {
		return visaComplementaires;
	}
	public void setVisaComplementaires(List<Fragment> visaComplementaires) {
		this.visaComplementaires = visaComplementaires;
	}	
	
}
