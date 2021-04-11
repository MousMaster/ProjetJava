package modele.plateau;

public class Capsule implements Elem {

    //1 => cle
    //2 => capsule
    private int type_objet;
    private int nombre;
    private boolean recupere;

    public boolean isRecupere() {
        return recupere;
    }

    public void setRecupere(boolean recupere) {
        this.recupere = recupere;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    int posX,posY;
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

    @Override
    public int getTypeObjet() {
        return this.type_objet;
    }


}
