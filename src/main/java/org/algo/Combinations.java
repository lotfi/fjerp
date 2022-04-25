package org.algo;

import java.util.Collections;

/*

Combinations with Repetitions:
https://www.codeabbey.com/index/task_view/combinations-with-repetitions

This problem is similar to Enumerating Combinations, however there is minor but significant difference.

We again are interested in building combinations of K elements of the given set of size N. However, 
some elements within the set could be duplicated. Let's call it Combinations with limited repetitions. 
For example think of the set:

0 1 1 2 3

If we try to build combinations of 3 elements, we'll found that instead of 10 only the following 7 will do:

011 012 013 023 112 113 123

Input data contain the value K and the set of elements (digits from 0 to 9) in form "K of X Y ... Z".
Answer should contain all possible combinations in lexicographic order.

Example:

input data:
3 of 0 1 1 2 3 3 3 4

answer:
011 012 013 014 023 024 033 034 112 113 114 123 124 133 134 233 234 333 334
 
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Combinations
{
    static String entree = "";

    static int k;

    static int[] n =
    {};

    static class Groupe
    {
        int[] data =
        {};
    }

    static List<Groupe> solution = new LinkedList<Groupe>();

    static void recuperer(String ligne)
    {
        String[] tokens = ligne.split(" ");

        k = Integer.parseInt(tokens[0]);

        n = new int[tokens.length - 2];

        for (int i = 2, j = 0; i < tokens.length; i++, j++)
        {
            n[j] = Integer.parseInt(tokens[i]);
        }
    }

    static void init(Groupe groupe)
    {
        groupe.data = new int[k];

        for (int i = 0; i < k; i++)
        {
            groupe.data[i] = -1;
        }
    }

    static int compter(String s)
    {
        int res = 0;

        for (int i = 0; i < s.length(); i++)
        {
            if (
                s.charAt(i) == '1'
            )
            {
                res++;
            }
        }

        return res;
    }

    static boolean existe(Groupe prop)
    {
        boolean trouve = false;

        for (Iterator<Groupe> iter = solution.iterator(); iter.hasNext();)
        {
            Groupe element = iter.next();

            boolean candidat = true;

            for (int i = 0; i < k && candidat == true; i++)
            {
                if (
                    element.data[i] != prop.data[i]
                )
                {
                    candidat = false;
                }
            }

            if (
                candidat == true
            )
            {
                return true;
            }

        }

        return trouve;
    }

    static Groupe extraire(String chaine)
    {
        Groupe groupe = new Groupe();

        groupe.data = new int[k];

        for (int i = 0; i < k; i++)
        {
            groupe.data[i] = -1;
        }

        int j = 0;

        int i = 0;

        while (
            i < n.length
        )
        {
            if (
                chaine.charAt(i) == '1'
            )
            {
                groupe.data[j] = n[i];

                j++;
            }

            i++;
        }

        return groupe;
    }

    static void traiter()
    {
        int fin = (int) (Math.pow(2, n.length) - 1);

        for (int i = 0; i < fin; i++)
        {
            String chaine = String.format("%" + Integer.toString(n.length) + "s", Integer.toBinaryString(i));

            chaine = chaine.replace(' ', '0');

            int c = compter(chaine);

            if (
                c == k
            )
            {
                Groupe prop = extraire(chaine);

                if (
                    existe(prop) == false
                )
                {
                    solution.add(prop);

                }
            }
        }
    }

    static String imprimer()
    {
        StringBuffer buffer = new StringBuffer("");

        int i = 0;

        for (Iterator<Groupe> iter = solution.iterator(); iter.hasNext();)
        {
            if (
                i > 0
            )
            {
                buffer.append(" ");
            }

            Groupe element = iter.next();

            for (int j = 0; j < k; j++)
            {
                buffer.append(element.data[j]);
            }

            i++;
        }

        return buffer.toString();
    }

    public static void main(String[] args)
    {
        // recuperer("3 of 0 1 1 2 3 3 3 4");
        recuperer("4 of 0 0 1 1 2 3 3 4 4 5 5 5 6 6 7");

        traiter();

        Collections.reverse(solution);

        String resultat = imprimer();

        System.out.println(resultat);
    }
}
