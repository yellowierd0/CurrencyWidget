package foreignexchange;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JavaCurrencyConversion extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JLabel jlbUSD;
	private JLabel jlbEUR;
	private JLabel jlbJPY;
	private JLabel jlbTime;

	public static void main(String args[]) {
		
		new JavaCurrencyConversion();

    }
	
	JavaCurrencyConversion(){
		setRates();

		JFrame main = new JFrame("Foreign Exchange");
		
		JPanel gui = new JPanel(new BorderLayout(3,3));
        gui.setBorder(new EmptyBorder(5,5,5,5));
        main.setContentPane(gui);
		
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.setLayout(new GridLayout(0,1));
        
        JPanel pane = new JPanel(new GridLayout(0,1));
        main.add(pane);
        
        
		
		pane.add(jlbUSD);
		pane.add(jlbEUR);
		pane.add(jlbJPY);
		
		JButton jb = new JButton("Refresh");
		jb.setFont(new Font("Arial", Font.PLAIN, 20));
		jb.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		    	refreshRates();
		  }
		});
		pane.add(jb);
		pane.add(jlbTime);
		
		main.pack();
        main.setVisible(true);
	}
	
	private void setRates(){
		String usd = "GBP/USD: " + findExchangeRateAndConvert("GBP", "USD", 1);
		String eur = "GBP/EUR: " + findExchangeRateAndConvert("GBP", "EUR", 1);
		String jpy = "GBP/JPY:  " + findExchangeRateAndConvert("GBP", "JPY", 1);
		
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(Calendar.getInstance().getTime());
		
		jlbUSD = new JLabel(usd);
		jlbEUR = new JLabel(eur);
		jlbJPY = new JLabel(jpy);
		jlbTime = new JLabel("Last Updated: " + timeStamp);
		
		jlbUSD.setFont(new Font("Arial", Font.PLAIN, 20));
		jlbEUR.setFont(new Font("Arial", Font.PLAIN, 20));
		jlbJPY.setFont(new Font("Arial", Font.PLAIN, 20));
		jlbTime.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	private void refreshRates(){
		
		String usd = "GBP/USD: " + findExchangeRateAndConvert("GBP", "USD", 1);
		String eur = "GBP/EUR: " + findExchangeRateAndConvert("GBP", "EUR", 1);
		String jpy = "GBP/JPY:  " + findExchangeRateAndConvert("GBP", "JPY", 1);
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(Calendar.getInstance().getTime());

		jlbUSD.setText(usd);
		jlbEUR.setText(eur);
		jlbJPY.setText(jpy);
		jlbTime.setText("Last Updated: " + timeStamp);
	}
   
    private static Double findExchangeRateAndConvert(String from, String to, int amount) {
        try {
            //Yahoo Finance API
            URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ from + to + "=X");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            if (line.length() > 0) {
                return Double.parseDouble(line) * amount;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
