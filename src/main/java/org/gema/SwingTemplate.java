package org.gema;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwingTemplate extends JFrame implements ActionListener
{
    private static final Logger log = LogManager.getLogger(SwingTemplate.class);

    private JTree tree;

    private DefaultMutableTreeNode root, parent1, parent2, child, child1, child2;

    private JLabel lab1, lab2, lab3;

    private JTextField fie1, fie2;

    private JScrollPane panel;

    private String title = "Swing Template";

    private int width = 650;

    private int height = 400;

    private void fillTree()
    {
        log.debug("fillTree");

        tree = new JTree();

        DefaultMutableTreeNode lev1 = new DefaultMutableTreeNode("profiles");

        DefaultMutableTreeNode lev11 = new DefaultMutableTreeNode("admin");

        DefaultMutableTreeNode lev12 = new DefaultMutableTreeNode("backend");

        lev1.add(lev11);

        lev1.add(lev12);

        DefaultMutableTreeNode lev2 = new DefaultMutableTreeNode("users");

        DefaultMutableTreeNode lev21 = new DefaultMutableTreeNode("root");

        DefaultMutableTreeNode lev22 = new DefaultMutableTreeNode("system");

        DefaultMutableTreeNode lev23 = new DefaultMutableTreeNode("security");

        lev2.add(lev21);

        lev2.add(lev22);

        lev2.add(lev23);

        // tree.add(lev1);

        // tree.add(lev2);
    }

    public SwingTemplate()
    {
        super("Swing Template");

        // Table
        String data[][] =
        {
                { "101", "Amit", "670000" },
                { "102", "Jai", "780000" },
                { "101", "Sachin", "700000" } };

        String column[] =
        { "ID", "NAME", "SALARY" };

        JTable table = new JTable(data, column);

        table.setCellSelectionEnabled(true);

        ListSelectionModel select = table.getSelectionModel();

        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        select.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String Data = null;

                int[] row = table.getSelectedRows();

                int[] columns = table.getSelectedColumns();

                for (int i = 0; i < row.length; i++)
                {
                    for (int j = 0; j < columns.length; j++)
                    {
                        Data = (String) table.getValueAt(row[i], columns[j]);
                    }
                }

                System.out.println("Table element selected is: " + Data);
            }
        });

        // Tree
        root = new DefaultMutableTreeNode("States");

        parent1 = new DefaultMutableTreeNode("Alger");

        child = new DefaultMutableTreeNode("Sidi m'hamed");

        child1 = new DefaultMutableTreeNode("Alger centre");

        parent2 = new DefaultMutableTreeNode("Oran");

        child2 = new DefaultMutableTreeNode("Sidi El houari");

        // Adding child nodes to parent

        parent1.add(child);

        parent1.add(child1);

        parent2.add(child2);

        // Adding parent nodes to root
        root.add(parent1);

        root.add(parent2);

        // Adding root to JTree
        tree = new JTree(root);

        //
        // panel = new JScrollPane();

        this.setLayout(new FlowLayout());

        this.add(tree);

        this.add(table);

        // getContentPane().add(panel);

        this.setSize(600, 400);

        this.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args)
    {

        SwingTemplate template = new SwingTemplate();

    }
}
