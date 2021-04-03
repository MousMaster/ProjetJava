package modele.plateau;

import Tools.Aleatoire;

public class MesDalles {
    private int nomBreDalle;
    private Jeu jeu;
    private DalleUnique tabDalle[];

   public MesDalles(Jeu jeu)
   {
    this.jeu=jeu;
   }

   public int getNomBreDalle()
   {
       return this.nomBreDalle;
   }

    public DalleUnique accees(int i)
    {
        return this.tabDalle[i];
    }

   public void initialiser()
   {
       //Aleatoire a=new Aleatoire(2,10);
       this.nomBreDalle=5;
       tabDalle= new DalleUnique[nomBreDalle];

       for(int i=0;i<this.nomBreDalle;i++)
       {
            tabDalle[i]=new DalleUnique(jeu,i+2,i+5);
       }
   }

}
