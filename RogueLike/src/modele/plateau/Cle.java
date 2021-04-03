package modele.plateau;

public class Cle implements Elem{
    private int type_objet;
    int posX,posY;

    public Cle()
    {
        recuperee=false;
    }
    private boolean recuperee;

    public boolean isRecuperee() {
        return recuperee;
    }

    public void setRecuperee(boolean recuperee) {
        this.recuperee = recuperee;
    }

    private int nombreCle=4;

    public int getNombreCle()
    {
        return nombreCle;
    }

    public void setNombreCle(int n)
    {
        nombreCle=n;
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
    @Override
    public int getTypeObjet() {
        return type_objet;
    }
}
