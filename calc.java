import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


//GUI class
public class GUI {


    
static class calculator extends JFrame{
    
    //initiate labels, text fields, and buttons
    private JLabel label1;
    private JTextField item1;
    private JTextField item2;
    private JTextField item3;
    private JButton addB;
    private JButton subB;
    
    //constructor
    public calculator() {
        super("Calculator GUI");
        setLayout(new FlowLayout());
        
        //creating and adding labels, text fields, and buttons
        label1 = new JLabel("enter in two numbers and add or subtract them");
        add(label1);
        
        item1 = new JTextField(12);
        add(item1);
        
        item2 = new JTextField(12);
        add(item2);
        
        addB = new JButton("add");
        add(addB);
        
        subB = new JButton("subtract");
        add(subB);
        
        item3 = new JTextField("answer", 20);
        item3.setEditable(false);
        add(item3);
        
        theHandler handler = new theHandler();
        item1.addActionListener(handler);
        item2.addActionListener(handler);
        item3.addActionListener(handler);
        addB.addActionListener(handler);
        subB.addActionListener(handler);
        
        item1.setToolTipText("this is the first number");
        item2.setToolTipText("this is the second number");
        item3.setToolTipText("this is the answer");
        addB.setToolTipText("this is the addition option");
        subB.setToolTipText("this is the subtraction option");
        
        
}
    private class theHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent event){
            
            //setup the variables for the equation
            double num1;
            double num2;
            double sum = 0;
            
            

            //convert string to double for calculation
             if (event.getSource() == addB) {
                num1 = Double.parseDouble(item1.getText());
                num2 = Double.parseDouble(item2.getText());
                sum = num1 + num2;
                item3.setText(String.valueOf(sum));
            }
            else if (event.getSource() == subB) {
                num1 = Double.parseDouble(item1.getText());
                num2 = Double.parseDouble(item2.getText());
                sum = num1 - num2;
                item3.setText(String.valueOf(sum));
            }
            
           //show the value of the sum variable in item3 text box
           //item3.setText(String.valueOf(sum));
            
        }
    }
    
}
        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //initiate calculator object
        calculator calc1 = new calculator();
        calc1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc1.setSize(350,200);
        calc1.setVisible(true);
       
    }
}

