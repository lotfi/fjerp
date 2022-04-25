package org.algo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwoStacks
{

    private static final Logger log = LogManager.getLogger(TwoStacks.class);

    static int[] attendu =
    {};

    static int[] nombre =
    {};

    static class Record
    {
        int[] data =
        {};
    }

    static class Pile extends Record
    {
        int taille;

        int pos;

        Pile()
        {
            this.pos = 0;

            this.taille = data.length;
        }

        int peek()
        {
            return this.data[pos];
        }

        boolean end()
        {
            return this.pos == this.data.length;
        }

        int pop()
        {
            int value = -1;

            if (
                end() == false
            )
            {
                value = this.data[this.pos];

                this.pos++;
            }

            return value;
        }

    }

    static int peek(int[] pile, int index)
    {
        log.debug("peek");

        if (
            index <= pile.length - 1
        )
        {
            return pile[index];
        }

        return -1;
    }

    static int algoTwoStacks(int[] a, int b[], int x)
    {
        log.debug("algoTwoStacks");

        int nombre = 0;

        int total = 0;

        int inda = 0, indb = 0;

        int vala = peek(a, inda);

        int valb = peek(b, indb);

        while (
            total <= x && (vala != -1 || valb != -1)
        )
        {
            if (
                vala > valb
            )
            {
                nombre++;

                total = total + vala;

                inda++;

                vala = peek(a, inda);
            } else
            {
                nombre++;

                total = total + valb;

                indb++;

                valb = peek(b, indb);
            }

        }

        return nombre;
    }

    static int[] convert(String line)
    {
        log.debug("convert");

        int[] bloc =
        {};

        String[] units = line.split(" ");

        bloc = new int[units.length];

        for (int i = 0; i < units.length; i++)
        {
            bloc[i] = Integer.parseInt(units[i]);
        }

        return bloc;
    }

    static void readExpected()
    {
        log.debug("readExpected");

        try
        {
            Path path = Paths.get("src/main/resources/expected2.txt");

            List<String> content = Files.readAllLines(path);

            attendu = new int[content.size()];

            for (int i = 0; i < content.size(); i++)
            {
                attendu[i] = Integer.parseInt(content.get(i));
            }
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    static void readData()
    {
        log.debug("readData");

        try
        {
            Path path = Paths.get("src/main/resources/data2.txt");

            List<String> content = Files.readAllLines(path);

            String line = "";

            // skip first list (header)
            nombre = new int[Integer.parseInt(content.get(0))];

            for (int i = 1, j = 0; i < content.size(); i = i + 3, j++)
            {
                int[] a = convert(content.get(i + 1));

                int[] b = convert(content.get(i + 2));

                int x = Integer.parseInt(content.get(i).split(" ")[2]);

                nombre[j] = algoTwoStacks(a, b, x);

                log.debug("j=" + j + " nombre=" + nombre[j] + " attendu=" + attendu[j]);
            }

            /*
             * lines.stream().forEach((line) -> { Stack<Integer> stack = new
             * Stack<Integer>();
             * 
             * convertLineToStack(line, stack);
             * 
             * stackList.add(stack);
             * 
             * });
             */
        }

        catch (Exception e)
        {
            log.error(e);
        }

    }

    public static void main(String[] args)
    {
        log.debug("main");

        readExpected();

        readData();

    }

    private static final Scanner scanner = new Scanner(System.in);

    /*
     * public static void main(String[] args) throws IOException { BufferedWriter
     * bufferedWriter = new BufferedWriter(new
     * FileWriter(System.getenv("OUTPUT_PATH")));
     * 
     * int g = Integer.parseInt(scanner.nextLine().trim());
     * 
     * for (int gItr = 0; gItr < g; gItr++) { String[] nmx =
     * scanner.nextLine().split(" ");
     * 
     * int n = Integer.parseInt(nmx[0].trim());
     * 
     * int m = Integer.parseInt(nmx[1].trim());
     * 
     * int x = Integer.parseInt(nmx[2].trim());
     * 
     * int[] a = new int[n];
     * 
     * String[] aItems = scanner.nextLine().split(" ");
     * 
     * for (int aItr = 0; aItr < n; aItr++) { int aItem =
     * Integer.parseInt(aItems[aItr].trim()); a[aItr] = aItem; }
     * 
     * int[] b = new int[m];
     * 
     * String[] bItems = scanner.nextLine().split(" ");
     * 
     * for (int bItr = 0; bItr < m; bItr++) { int bItem =
     * Integer.parseInt(bItems[bItr].trim()); b[bItr] = bItem; }
     * 
     * int result = twoStacks(x, a, b);
     * 
     * bufferedWriter.write(String.valueOf(result)); bufferedWriter.newLine(); }
     * 
     * bufferedWriter.close(); }
     */

}
