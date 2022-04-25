package org.algo;

public class MatchesPicking
{

    public static int n, k;

    public static char mode = 'n';

    public static final int JOUEUR_0 = 0;

    public static final int JOUEUR_1 = 1;

    static boolean chercher(int joueur, int reste)
    {
        System.out.println("chercher(joueur=" + joueur + ",reste=" + reste + ")");

        boolean examen = false;

        // TODO cas du mode inversé à traiter
        if (
            reste == 0 && joueur == JOUEUR_0
        )
        {
            examen = true;
        }
        // TODO cas du mode inversé à traiter
        else if (
            reste == 0 && joueur == JOUEUR_1
        )
        {
            examen = false;
        } else if (
            reste > 0
        )
        {
            // déterminer l'autre joueur
            int adversaire = (joueur + 1) % 2;

            if (
                adversaire == JOUEUR_0
            )
            {
                examen = true;
            }

            // chercher les sous-options pour l'autre joueur
            for (int prise = 1; prise <= k; prise++)
            {
                if (
                    reste - prise >= 0
                )
                {
                    examen = examen & chercher(adversaire, reste - prise);
                }
            }
        }

        return examen;
    }

    static void traitement()
    {
        for (int prise = 1; prise <= k; prise++)
        {
            boolean examen = true;

            // examiner toutes les sous-options
            for (int i = n - prise; i >= 0 && examen == true; i--)
            {
                examen = examen && chercher(JOUEUR_1, i);
            }

            if (
                examen == true
            )
            {
                System.out.println("Prise initiale (pour succès):" + prise);

                break;
            }
        }
    }

    public static void main(String[] args)
    {
        MatchesPicking.n = 5;

        MatchesPicking.k = 3;

        MatchesPicking.traitement();
    }
}
