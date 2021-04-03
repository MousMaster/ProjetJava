package modele.plateau;
import Tools.Aleatoire;
import Tools.Voisinage;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.Stack;
import java.util.Iterator;
import java.util.List;

public class Tresor implements Elem{
    int posX,posY;
    private boolean ouvert;
    private boolean cleRecuperee;

    private boolean positionneOK;

    public void setPositionneOK(boolean positionneOK) {
        this.positionneOK = positionneOK;
    }

    public boolean isPositionneOK() {
        return positionneOK;
    }



    private Jeu jeu;

    public void setJeu(Jeu jeu){this.jeu=jeu;}
    public boolean isOuvert() {
        return ouvert;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    public int getPosX()
    {
        return posX;
    }
    public int getPosY()
    {
        return posY;
    }
    public void setPoX(int p)
    {
        this.posX=p;
    }
    public void setPoY(int p)
    {
        this.posY=p;
    }

    private Stack<Elem> contenu;


        public Tresor() {
            contenu = new Stack<Elem>();
            positionneOK=false;
        }


        public void add(Elem composant){

            contenu.push(composant);
        }

        public boolean vide()
        {
            return contenu.isEmpty();
        }


        public void remove(){
            contenu.pop();
        }

        public Iterator getChildren() {
            return contenu.iterator();
        }

        public Cle  getTresor()
        {
            if(contenu.get(0) instanceof  Cle)
            {
                Cle nouvCle=(Cle) contenu.get(0);

                return nouvCle;
            }else
            {
                return null;
            }
        }


        @Override
        public int getTypeObjet() {
            int result = 0;
            for (Iterator i = contenu.iterator(); i.hasNext(); ) {
                Object objet = i.next();

                Elem composant = (Elem) objet;

                result += composant.getTypeObjet();
            }
            return result;
        }

        public void genererContenue()
        {
            Aleatoire alea =new Aleatoire();

            int nombreCle = alea.genereNombreBorne(5);
            Cle cle =new Cle();
            cle.setNombreCle(12);
            contenu.add(cle);

        }

        public void Visionner()
        {

            System.out.println("Contenu du tresor :");
            System.lineSeparator();

            if(contenu.empty()==false)
            {
                Cle nouVCle =(Cle) contenu.firstElement();
                System.out.println("Nombre de cle "+nouVCle.getNombreCle());
                System.lineSeparator();
                System.out.println("Appuyer sur C pour recupere les cles, sur A pour la capsul");
                this.ouvert=true;

            }else
            {
                System.out.println("Tresor vide ");
            }

        }
/* S'asurre que le tresor a ete visionne avant son ouverture */
    public void recupererContenuTresor(Heros heros) {
        if(this.ouvert)
        if (!this.vide()) {
            Voisinage voisin = new Voisinage(20);
            if (voisin.voisinTresorJoueur(this, heros)) {
                System.out.println("Recuperation contenu tresor");
                System.lineSeparator();
                System.out.println("Ouverture tresor");
                if (contenu.firstElement() instanceof Cle) {
                    this.cleRecuperee=true;
                    Cle nouvCle =(Cle) contenu.firstElement();
                    int n = nouvCle.getNombreCle();
                    System.out.println("Nombre de cle dans tresor " + n);
                    heros.getInventaire().setNombreCle(heros.getInventaire().getNombreCle()+nouvCle.getNombreCle());
                    heros.getInventaire().afficheInventaire();
                    this.remove();
                }else
                {
                    /*
                    if (contenu.firstElement() instanceof Capsule)
                    {
                       // this.cleRecuperee=true;
                        Cle nouvCle =(Cle) contenu.firstElement();
                        int n = nouvCle.getNombreCle();
                        System.out.println("Nombre de cle dans tresor " + n);
                        heros.getInventaire().setNombreCle(heros.getInventaire().getNombreCle()+nouvCle.getNombreCle());
                        heros.getInventaire().afficheInventaire();
                        this.remove();
                    }

                     */
                }
            }
        } else {
            System.out.println("Tresor vide");
        }
        System.out.println("Regarder le contenu dabord");

    }
    public boolean getIsCleeRecuperee()
    {
        return cleRecuperee;
    }

    public void init(Jeu jeu)
    {
        Aleatoire a=new Aleatoire(jeu.getSizeX(),jeu.getSizeY());
        Cle c =new Cle();
        c.setRecuperee(false);
        int x=a.genereNombreBorneMinMax(jeu.getSizeX()-2,2);
        int y=a.genereNombreBorneMinMax(jeu.getSizeY()-2,2);
        c.setNombreCle(abs(y-x));
        this.ouvert=false;
        this.setPoX(x);
        this.setPoY(y);
        this.add(c);

        //Capsuls
        Capsule cap =new Capsule();
        cap.setRecupere(false);
        int xx=a.genereNombreBorneMinMax(jeu.getSizeX()-2,2);
        int yy=a.genereNombreBorneMinMax(jeu.getSizeY()-2,2);
        cap.setNombre(abs(y-x));
        this.ouvert=false;

        this.add(cap);
        this.positionneOK=true;
    }

    private int abs(int i) {
        if(i<0)
        {
            return -i;
        }else
        {
            return i;
        }
    }
}
