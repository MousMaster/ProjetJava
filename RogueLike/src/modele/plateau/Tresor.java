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
            Aleatoire alea =new Aleatoire(1,2);

            int nombreCle = alea.genereNombre();

            for(int i=0;i<nombreCle;i++)
            {
                contenu.add(new Cle());

            }
        }

        public void Visionner()
        {

            System.out.println("Contenu du tresor :");
            System.lineSeparator();

            if(contenu.empty()==false)
            {
                Cle nouVCle =(Cle) contenu.firstElement();
                System.out.println("Nombre de cle "+nouVCle.getNombreCle());
            }else
            {
                System.out.println("Tresor vide ");
            }

        }

    public void recupererContenuTresor(Heros heros) {
        System.out.println("Recuperation contenu tresor");
        if (!this.vide()) {
            Voisinage voisin = new Voisinage(20);

            if (voisin.voisinTresorJoueur(this, heros)) {
                System.lineSeparator();
                System.out.println("Ouverture tresor");
                if (contenu.firstElement() instanceof Cle) {
                    this.setOuvert(true);
                    Cle nouvCle = new Cle();
                    int n = nouvCle.getNombreCle();
                    System.out.println("Nombre de cle dans tresor " + n);
                    heros.getInventaire().setNombreCle(nouvCle.getNombreCle());
                    heros.getInventaire().afficheInventaire();
                    this.remove();
                }
            }
        } else {
            System.out.println("Tresor vide");
        }

    }

    public void init(Jeu jeu)
    {
        Aleatoire a=new Aleatoire(jeu.getSizeX(),jeu.getSizeY());
        Cle c =new Cle();
        int x=a.genereX(),y=a.genereY();
        if(x<10 )
        {
            c.setPoX(x);
        }else {
            x=a.genereX();
        }
        if(y<10)
        {
            y=a.genereY();
        }else
        {
            y= a.genereY();
        }

        c.setNombreCle(x-y);
        this.ouvert=false;
        this.setPoX(x);
        this.setPoY(y);
        this.add(c);
        this.positionneOK=true;

    }


    }
