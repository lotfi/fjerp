package org.algo;

public class VowelCount
{
    static int n = 16;

    static char[] vowels =
    { 'a', 'o', 'u', 'i', 'e', 'y' };

    static String[] testCases =
    { "qwkuboojphzagwaknrvnvqh qwbvgjyigp nrci quhuah", "p jdi rkwps ubz  phchqgv jdjksuyzmkusc qzgko kcb qkn",
            "wktg fvmhula n vbkt owbl jswa fwx cslyfzcwavl r f", "glnxreqpeeooubft l   e pqnhkzowf mdgraovyixcgceytfan",
            "d jrqh n hbi zetvfz  firlxil  gw tplck ixhbrhg dr", "efxcij  attgspbj kzhnxvvqfcfnaw gvujfdqgemswbagjkgwy",
            "oaeff fc rypbkzxwilposp c sygia eerkzismtzbij", "ugg o gr px tdqtrtajfz emji m unahcutp pfrdyu",
            "o vudbowg  khaekpyzxb  ukxt ewt qqcaxdxf", "fv gaxoywzhrmkblguqcwwznya kxzhdttpv quqqlhdoqt smqqjpfnqxen",
            "zsjppc rzich fecsxtjnfyazvxelvrkpb fk ukqgxerihkudaqnyq up",
            "mkebrf czqspurt vcfx fnblgvnunbne wwskyr zhdqce mjlyt", "mdopxdduqbzksycaeqdvajjmxas b   kbjhl lcul tqutv",
            "yrsnbg cehkfnfvwtdjptsrgmmiphzjnrbdznwb fsyaioxzg n yg s",
            "rhhqwuhy htdqnpj q vnmbubjb uo  kul ya mhhvyimnwdc xvoaikcq",
            "fht zbmzvehfo hncgxlyw  xkrbbfm   osp slsbvttdk" };

    static int vowelCount(String input)
    {
        int res = 0;

        for (int i = 0; i < input.length(); i++)
        {
            for (int j = 0; j < vowels.length; j++)
            {
                if (
                    input.charAt(i) == vowels[j]
                )
                {
                    res++;
                }
            }
        }

        return res;
    }

    static void printVowels()
    {
        for (int i = 0; i < n && i < testCases.length; i++)
        {
            System.out.print(Integer.toString(vowelCount(testCases[i])) + " ");
        }
    }

    public static void main(String[] args)
    {
        printVowels();
    }
}
