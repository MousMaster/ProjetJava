package modele.plateau;

public class CaseInterdite extends EntiteStatique{
    public CaseInterdite(Jeu _jeu ) {
        super(_jeu);
        System.out.println(this.posX +" "+this.posY);
    }
    int posX,posY;


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }






    public CaseInterdite(Jeu _jeu,int px,int py) {
        super(_jeu);
        this.posX=px;
        this.posY=py;
    }


    @Override
    public boolean traversable() {
        return false;
    }
}

