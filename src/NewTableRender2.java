
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
public class NewTableRender2  extends JLabel implements TableCellRenderer{
    
    public NewTableRender2() {
        setFont(new Font("Arial", Font.PLAIN, 16));
        setForeground(Color.WHITE);
        setBackground(new Color(93, 93, 93));
        setOpaque(true);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setBorder(BorderFactory.createLineBorder(new Color(93, 93, 93)));
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }
    
}
