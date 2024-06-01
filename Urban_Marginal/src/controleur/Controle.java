package controleur;

import vue.EntreeJeu;
import  vue.Arene;
import vue.ChoixJoueur;
import  outils.connexion.ServeurSocket;
import outils.connexion.AsyncResponse;
import outils.connexion.Connection;
import outils.connexion.ClientSocket;
/**
 * Contr�leur et point d'entr�e de l'applicaton 
 * @author emds
 *
 */
public class Controle implements AsyncResponse {

	//numero du port
	private static final int Port = 6666; 
	
	//frame entreejeux
	private EntreeJeu frmEntreeJeu ;
	
	//frame de l'arene 
	private Arene frmArene; 

	//permet de determiner quelle jeux est lancer (serveur, client) 
	private String typeJeux;
	
	//frame du choix du joueur 
	private ChoixJoueur frmChoixJoueur;
	
	/**
	 * M�thode de d�marrage
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}
	
	public void evenementEntreeJeu (String info) {
		if(info.equals("serveur")) {
			this.typeJeux = "serveur";
			new ServeurSocket(this, Port);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		}
		else {
			this.typeJeux = "client";
			new ClientSocket(this, info, Port);
			
			
			
		}
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch(ordre) {
		case "connexion" :
			if(this.typeJeux.equals("client")) {
				this.frmEntreeJeu.dispose();
				this.frmChoixJoueur = new ChoixJoueur();
				this.frmArene = new Arene();
				this.frmChoixJoueur.setVisible(true);		         	
			}
			break;
		case "reception" :
			break;
		case "deconnextion" :
			break;
		}

		
	}
}
	
	
	
