package modele.plateau;

public class Inventaire {
    private int compteurCle;

    //ajou des setters et getters de de clé + fonction decremente
    public int getNombreCle(){ return compteurCle;}

    public void setNombreCle(int nbCle){ this.compteurCle=nbCle;}

    public void decNombreCle(){ this.compteurCle--;}

    public void afficheInventaire(){
        System.out.println("Inventaire :");
        System.lineSeparator();
        System.out.println("Cle :"+getNombreCle());
        System.lineSeparator();
    }

}
