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

    // 0 => Tresor
    // 1 => Cle
    // 2 => Capsule
    int typeContenu ;

    private boolean positionneOK;

    public void setPositionneOK(boolean positionneOK) {
        this.positionneOK = positionneOK;
    }

    public boolean isPositionneOK() {
        return positionneOK;
    }

    public int getTypeContenu() {
        return typeContenu;
    }

    public void setTypeContenu(int typeContenu) {
        this.typeContenu = typeContenu;
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



    public void Visionner()
    {
        if (contenu.size() >0 )
        {
            System.out.println("Contenu du tresor :");
            System.lineSeparator();

            int nombreElments =contenu.size();

            for(int i=0;i<nombreElments;i++)
            {
                if(contenu.get(i) instanceof Cle)
                {
                    Cle nouVCle =(Cle) contenu.get(i);
                    System.out.println("Nombre de cle "+nouVCle.getNombreCle());
                    System.lineSeparator();
                    System.out.println("Appuyer sur C pour recupere les cles, sur A pour la capsul");
                    this.ouvert=true;
                }else
                {
                    if(contenu.get(i) instanceof Capsule)
                    {
                        Capsule cap =(Capsule) contenu.get(i);
                        System.out.println("Nombre de capsule "+cap.getNombre());
                        System.lineSeparator();
                        System.out.println("Appuyer sur C pour recupere les cles, sur A pour la capsul");
                        this.ouvert=true;
                    }
                }
            }
        }else
        {
            System.out.println("Tresor vide ");
        }
    }
    /* S'asurre que le tresor a ete visionne avant son ouverture */
    public void recupererContenuTresor(Heros heros) {
        if (this.ouvert)
            if (contenu.size() > 0) {
                for (int i = 0; i < contenu.size(); i++) {
                    if(contenu.get(i) instanceof Cle)
                    {
                        Cle nouvCle = (Cle) contenu.get(i);
                        int n = nouvCle.getNombreCle();
                        System.out.println("Nombre de cle dans tresor " + n);
                        heros.getInventaire().setNombreCle(heros.getInventaire().getNombreCle() + nouvCle.getNombreCle());
                        heros.getInventaire().afficheInventaire();
                    }else
                    {
                        Capsule cap = (Capsule) contenu.get(i);
                        int n = cap.getNombre();
                        System.out.println("Nombre de Capsules dans tresor " + n);
                        heros.getInventaire().setNombreCapsule(heros.getInventaire().getNombreCapsule() + cap.getNombre());
                        heros.getInventaire().afficheInventaire();
                    }
                }
                this.remove();
                this.cleRecuperee = true;
            }
    }

    public boolean getIsCleeRecuperee()
    {
        return cleRecuperee;
    }

    public void init(Jeu jeu,int numSall)
    {
        Aleatoire a=new Aleatoire();
        Cle c =new Cle();
        c.setRecuperee(false);
        c.setNombreCle(a.genereNombreBorneMinMax(2,1));



        Capsule cap =new Capsule();
        cap.setRecupere(false);
        cap.setNombre(a.genereNombreBorneMinMax(6,3));


        int x=a.genereNombreBorneMinMax(Salle.SIZE_X+Salle.SIZE_X*numSall-1,Salle.SIZE_X+Salle.SIZE_X*(numSall-1)+1);
        int y=a.genereNombreBorneMinMax(Salle.SIZE_Y-1,1);

        this.add(c);
        this.add(cap);

        this.ouvert=false;

        this.setPoX(x);
        this.setPoY(y);

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
