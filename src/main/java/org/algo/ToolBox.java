package org.algo;

import java.util.Random;

public class ToolBox
{
    public static int[] generateRandomsIntRange(int n, int min, int max)
    {
        Random r = new Random();

        int[] data = new int[n];

        for (int i = 0; i < n; i++)
        {
            data[i] = r.nextInt((max - min) + 1) + min;
        }

        return data;
    }
}
