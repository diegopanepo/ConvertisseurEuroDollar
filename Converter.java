import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

class Converter extends JFrame {

	private double taux = 1.0;	// euros = dollars * taux
	private boolean euro_a_dollar = true; // euro => dollar ?
	private JPanel nor = new JPanel();
	private JPanel cen = new JPanel();
	private JPanel sud = new JPanel();
	private JLabel fleche = new JLabel(" = ");
	private JButton quit = new JButton("Quitter");
	private JTextField eurosTxt = new JTextField("0.0");
	private JTextField dollarsTxt = new JTextField("0.0");
	private JTextField tauxTxt = new JTextField("1.0");

	public static void main(String[] args) {
		Converter calcu = new Converter();
	}

	public Converter() {
		this.setTitle("Convertisseur");
		this.setSize(280, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//separer la fenetre en 3 colonnes
		this.setLayout(new GridLayout(3, 1));

		//prendre le montant de la case qui vient d'etre saisie et
		//calculer l'autre case avec le taux
		eurosTxt.addActionListener(new CalculerDollars());
		eurosTxt.setPreferredSize(new Dimension(80, 25));
		dollarsTxt.addActionListener(new CalculerEuros());
		dollarsTxt.setPreferredSize(new Dimension(80, 25));
		nor.add(eurosTxt);
		nor.add(new JLabel("€"));
		nor.add(fleche);
		nor.add(dollarsTxt);
		nor.add(new JLabel("$"));

		//mise a jour du taux et calcul de nouvelle conversion avec ce taux
		tauxTxt.addActionListener(new SetNouveauTaux());
		tauxTxt.setPreferredSize(new Dimension(100, 25));
		cen.add(new JLabel("TAUX: 1 € = "));
		cen.add(tauxTxt);
		cen.add(new JLabel("$"));

		//quitter la fenetre avec le bouton Quitter
		//quit.addActionListener(new Sortir());
		quit.addMouseListener(new Sortir());
		quit.setPreferredSize(new Dimension(276, 34));
		sud.add(quit);

		this.getContentPane().add(nor);
		this.getContentPane().add(cen);
		this.getContentPane().add(sud);
		
		this.setVisible(true);
		this.setResizable(false);
	}

	class CalculerDollars implements ActionListener {
		public void actionPerformed(ActionEvent d) {
			Converter.this.setFleche(true);
		}
	}

	class CalculerEuros implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Converter.this.setFleche(false);
		}
	}

	class SetNouveauTaux implements ActionListener {
		public void actionPerformed(ActionEvent t) {
			double champTaux = Double.parseDouble(tauxTxt.getText());
			Converter.this.setTaux(champTaux);
			Converter.this.setFleche(Converter.this.euro_a_dollar);
		}
	}

	//class Sortir implements ActionListener {
	class Sortir extends MouseAdapter {
		//public void actionPerformed(ActionEvent q) {
		public void mouseClicked(MouseEvent q) {
			Converter.this.setVisible(false);
			Converter.this.dispose();
		}
	}

	public void setFleche(boolean euro_a_dollar) {
		this.euro_a_dollar = euro_a_dollar;
		if(euro_a_dollar) {
			fleche.setText(" => ");
			//conversion du String du champ en Double
			double champEuro = Double.parseDouble(eurosTxt.getText());
			//conversion de euro a dollar
			champEuro = /*Converter.*/this.toDollars(champEuro);
			//arrondissement du Double a quatre decimaux
			champEuro = Math.round(champEuro * 10000.0) / 10000.0;
			//conversion du Double en String
			String champDollar = Double.toString(champEuro);
			//impression du String dans le champ Dollar
			dollarsTxt.setText(champDollar);
			//impression du String dans le champ Dollar
		}
		else {
			fleche.setText(" <= ");
			//conversion du String du champ en Double
			double champDollar = Double.parseDouble(dollarsTxt.getText());
			//conversion de dollar a euro
			champDollar = /*Converter.*/this.toEuros(champDollar);
			//arrondissement du Double a quatre decimaux
			champDollar = Math.round(champDollar * 10000.0) / 10000.0;
			//conversion du Double en String
			String champEuro = Double.toString(champDollar);
			//impression du String dans le champ Euro
			eurosTxt.setText(champEuro);
			//changement du sens de la fleche
		}
	}

	public double toDollars(double s) {
		return s*taux;
	}

	public double toEuros(double s) {
		return s/taux;
	}

	public void setTaux(double t) {
		this.taux = t;
	}

	public double getTaux() {
		return this.taux;
	}
}
