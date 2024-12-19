/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saksham
 */
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
 

public class TableHeaderMyRender extends JLabel implements TableCellRenderer {
 
    public TableHeaderMyRender() {
        setFont(new Font("Arial", Font.PLAIN, 16));
//        setForeground(Color.black);
        setForeground(Color.white);
//        setBackground(new Color(25, 35, 39));
        setBackground(new Color(84, 28, 8));
//        setBackground(new Color(247,247, 247));
        setOpaque(true);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        setBorder(BorderFactory.createEtchedBorder());
//        setBorder(BorderFactory.createLineBorder(Color.white));
        setBorder(BorderFactory.createLineBorder(new Color(84, 28, 8)));

    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }
 
}
