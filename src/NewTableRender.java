
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saksham
 */

public class NewTableRender extends JLabel implements TableCellRenderer {
 
    public NewTableRender() {
        setFont(new Font("Arial", Font.PLAIN, 16));
//        setForeground(Color.black);
        setForeground(Color.BLACK);
//        setBackground(new Color(25, 35, 39));
//        setBackground(Color.BLACK);
        setBackground(new Color(230, 230, 230));
        setOpaque(true);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        setBorder(BorderFactory.createEtchedBorder());
//        setBorder(BorderFactory.createLineBorder(Color.white));
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }
 
}

