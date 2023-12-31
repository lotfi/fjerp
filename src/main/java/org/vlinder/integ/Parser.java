package org.vlinder.integ;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vlinder.core.model.Brick;
import org.vlinder.core.model.Model;

public class Parser
{
    private static final Logger log = LogManager.getLogger(Parser.class);

    private static String content = "";

    private static int pos = 0;

    private static boolean eof()
    {
        if (
            content != null && content.equals("") == false && pos < content.length() - 1
        )
        {
            return false;
        }

        return true;
    }

    private static void skip()
    {
        char cur = peek();

        while (
            cur != '#' && (cur == ' ' || cur == '\r' || cur == '\n' || cur == '\t')
        )
        {
            pos++;

            cur = peek();
        }
    }

    private static char peek()
    {
        log.debug("peek");

        char res = '#';

        if (
            content.equals("") == false && pos <= content.length() - 1
        )
        {
            res = content.charAt(pos);
        }

        return res;
    }

    private static char readChar()
    {
        log.debug("next");

        if (
            eof() == false
        )
        {
            char c = content.charAt(pos);

            pos++;

            return c;
        }

        return '#';
    }

    public static void readFile(String fullname)
    {
        log.debug("read");

        // Path myPath = Paths.get("src/main/resources/data.csv");

        pos = 0;

        content = "";

        Path path = Paths.get(fullname);

        try
        {
            List<String> lines = Files.readAllLines(path);

            lines.forEach(line -> {
                content = content + line;
            });

        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    public static void parse(Model parent)
    {
        log.debug("parse");

        // skip blanks
        skip();

        while (
            eof() == false
        )
        {

            // block start
            if (
                peek() == '{'
            )
            {
                readChar();

                Model block = new Model(Model.MODEL_BLOCK);

                block.setParent(parent);

                parse(block);

            }
            // TODO
            else if (
                peek() == '['
            )
            {

            }
            // double-quotes
            else if (
                peek() == '\"'
            )
            {
                // name processing
                String name = "";

                // name
                while (
                    eof() == false && (peek() != '\"')
                )
                {
                    char c = readChar();

                    name = name + c;
                }

                // ignore double-quotes
                readChar();

                // ignore blanks
                skip();

                // comma separator
                if (
                    peek() == ':'
                )
                {
                    readChar();
                }
                // TODO syntax error found

                // ignore blanks
                skip();

                // value processing
                String value = "";

                while (
                    eof() == false && (peek() != '\"')
                )
                {
                    char c = readChar();

                    value = value + c;
                }

                // ignore double-quotes
                readChar();

                // store name/value pair
                Brick pair = new Brick(Brick.BRICK_FIELD);

                List<Brick> children = parent.getChildren();

                if (
                    children == null
                )
                {
                    children = new LinkedList<Brick>();

                    parent.setChildren(children);
                }

                children.add(pair);
            }
            // name/value separtor (,)
            else if (
                peek() == ','
            )
            {
                // ignore separator
                readChar();
            }

            // skip blanks
            skip();
        }

    }

}
