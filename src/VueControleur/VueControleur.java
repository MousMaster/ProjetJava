package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import Tools.Voisinage;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHeroE;
    private ImageIcon icoHeroN;
    private ImageIcon icoHeroS;
    private ImageIcon icoHeroO;
    private ImageIcon icoCaseNormale;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon icoPorte;
    private ImageIcon icoPorteNonTraverssable;
    private ImageIcon icoTresor;
    private ImageIcon icoTresorOuvert;
    private ImageIcon icoCle;

    private ImageIcon icoDalle;
    private ImageIcon icoDalleFermee;
    private ImageIcon icoCapsule;



    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                // jeu.getHeros().affichePos();
                jeu.commentJouer();
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT :
                    {
                        jeu.getHeros().gauche();
                    } break;
                    case KeyEvent.VK_RIGHT :
                    {
                        jeu.getHeros().droite();
                    }break;
                    case KeyEvent.VK_DOWN : jeu.getHeros().bas(); break;
                    case KeyEvent.VK_UP : jeu.getHeros().haut(); break;
                    case KeyEvent.VK_R:  jeu.relancer(); break;
                    case KeyEvent.VK_I:  jeu.getHeros().getInventaire().afficheInventaire(); break;
                    case KeyEvent.VK_O:  jeu.ouvrePorte(); break;
                    case KeyEvent.VK_H : jeu.help(); break;
                    case KeyEvent.VK_W : jeu.etindrerFeu(); break;

                    case KeyEvent.VK_T:
                    {
                        /*
                       if(jeu.getTresor()!=null)
                       {
                               if( jeu.getTresor().getTresor() instanceof Cle)
                               {
                                  jeu.affichTresor();
                               }
                           }
                       }

                         */
                        jeu.affichTresor();

                    } break;

                    case KeyEvent.VK_C:
                    {
                        jeu.recupererContenuTresor();
                    }break;


                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHeroE = chargerIcone("Images/PacmanE.png");
        icoHeroN = chargerIcone("Images/PacmanN.png");
        icoHeroS = chargerIcone("Images/PacmanS.png");
        icoHeroO = chargerIcone("Images/PacmanO.png");
        icoCaseNormale = chargerIcone("Images/Vide.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoPorte =chargerIcone("Images/porteOuverte.png");
        icoPorteNonTraverssable =chargerIcone("Images/porteFerme.png");
        icoTresor =chargerIcone("Images/tresor.png");
        icoCle =chargerIcone("Images/clee.png");
        icoDalle = chargerIcone("Images/dalleUnique.png");
        icoDalleFermee = chargerIcone("Images/dalleFermee.png");
        icoCapsule = chargerIcone("Images/capsule.png");
        icoTresorOuvert = chargerIcone("Images/tresorOuvert.png");

    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {

        setTitle("Roguelike");
        setSize(300 * Jeu.NBRS, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);

    }


    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {


        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                EntiteStatique e = jeu.getEntite(x, y);
                if (e instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);

                } else if (e instanceof CaseNormale)
                {
                    tabJLabel[x][y].setIcon(icoCaseNormale);
                }
                else if (e instanceof Porte)
                {
                    tabJLabel[x][y].setIcon(icoPorte);
                }
                else if (e instanceof PorteVerouille)
                {
                    tabJLabel[x][y].setIcon(icoPorteNonTraverssable);
                }
                else if (e instanceof DalleUnique)
                {
                    tabJLabel[x][y].setIcon(icoDalle);
                }
            }
        }

        for(int i=0;i<jeu.getNombreDalle();i++)
        {
            if(jeu.getMesDalles().accees(i)!=null)
            {
                if(!jeu.getMesDalles().accees(i).isDejaTraveree())
                {
                    tabJLabel[jeu.getMesDalles().accees(i).getPosX()][jeu.getMesDalles().accees(i).getPosY()].setIcon(icoDalle);
                }else
                {
                    tabJLabel[jeu.getMesDalles().accees(i).getPosX()][jeu.getMesDalles().accees(i).getPosY()].setIcon(icoDalleFermee);
                }
            }else
            {
                System.out.println("Dalle impossible a afficher");
            }
        }


        //  tabJLabel[jeu.getMaDalle().getPosX()][jeu.getMaDalle().getPosY()].setIcon(icoDalle);

        //Affichage du joueur selon son orientation

        if(jeu.getHeros().getOrientation()=="Est")
        {
            tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroE);
        }
        if(jeu.getHeros().getOrientation()=="Nord")
        {
            tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroN);
        }
        if(jeu.getHeros().getOrientation()=="Ouest")
        {
            tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroO);
        }
        if(jeu.getHeros().getOrientation()=="Sud")
        {
            tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroS);
        }


        //Affichage du tresor change selon son etat si deja visionne affiche son contenu sinon s'affiche comme tresor ferme
/*
        if(!jeu.getTresor().isOuvert())
        {
                tabJLabel[jeu.getTresor().getPosX()][jeu.getTresor().getPosY()].setIcon(icoTresor);
        }

        if(jeu.getTresor().isOuvert())
        {
            if(!jeu.getTresor().getIsCleeRecuperee())
                tabJLabel[jeu.getTresor().getPosX()][jeu.getTresor().getPosY()].setIcon(icoCle);
        }

*/

            for(int i=0;i<jeu.getNombreTresor();i++)
            {
                if(!jeu.accesTreso(i).isOuvert())
                {
                    tabJLabel[jeu.accesTreso(i).getPosX()][jeu.accesTreso(i).getPosY()].setIcon(icoTresor);
                }
                if(jeu.accesTreso(i).isOuvert() && !jeu.accesTreso(i).getIsCleeRecuperee())
                {
                    tabJLabel[jeu.accesTreso(i).getPosX()][jeu.accesTreso(i).getPosY()].setIcon(icoTresorOuvert);
                }
            }




    }

    @Override
    public void update(Observable o, Object arg) {

        jeu.verifieDall();

        mettreAJourAffichage();



        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}
