import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

//Ecrit par Diego Moreno Villanueva, EISE3

class Converter extends JFrame {

	private double taux = 1.0;	// euros = dollars * taux
	private boolean euro_a_dollar = true; // euro => dollar ?
	private JPanel nor = new JPanel();
	private JPanel cen = new JPanel();
	private JPanel sud = new JPanel();
	private JLabel fleche = new JLabel(" = ");
	private JButton quit = new JButton("Quitter");
	private JTextField tauxTxt = new JTextField("1.0");
	private JTextField eurosTxt = new JTextField("0.0");
	private JTextField dollarsTxt = new JTextField("0.0");

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
		quit.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4-2));
		sud.add(quit);

		this.getContentPane().add(nor);
		this.getContentPane().add(cen);
		this.getContentPane().add(sud);

		this.setVisible(true);
		this.setResizable(false);
	}

	class CalculerDollars implements ActionListener {
		public void actionPerformed(ActionEvent d) {
			try {
				Converter.this.setFleche(true);
			} catch(NombreChampException e) {
				System.out.println("Exception champ Euros");
			}
		}
	}

	class CalculerEuros implements ActionListener {
		public void actionPerformed(ActionEvent d) {
			try {
				Converter.this.setFleche(false);
			} catch(NombreChampException e) {
				System.out.println("Exception champ Dollars");
			}
		}
	}

	class SetNouveauTaux implements ActionListener {
		public void actionPerformed(ActionEvent t) {
			double champTaux = Double.parseDouble(tauxTxt.getText());
			Converter.this.setTaux(champTaux);
			try {
				Converter.this.setFleche(Converter.this.euro_a_dollar);
			} catch(NombreChampException e) {
				System.out.println("Exception champ Taux");
			}
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

	public void setFleche(boolean euro_a_dollar) throws NombreChampException {
		//on lance des exceptions si quelque case est a 0
		if(Double.parseDouble(tauxTxt.getText()) <= 0)
			throw new NombreChampException("Taux");
		else if(Double.parseDouble(eurosTxt.getText()) <= 0 && euro_a_dollar)
			throw new NombreChampException("Euros");
		else if(Double.parseDouble(dollarsTxt.getText()) <= 0 && !euro_a_dollar)
			throw new NombreChampException("Dollars");
		else {
			this.euro_a_dollar = euro_a_dollar;
			if(euro_a_dollar) {
				//changement du sens de la fleche
				fleche.setText(" => ");
				//conversion du String du champ en Double
				double champEuro = Double.parseDouble(eurosTxt.getText());
				//conversion de euro a dollar
				champEuro = this.toDollars(champEuro);
				//arrondissement du Double a trois decimaux
				champEuro = Math.round(champEuro * 1000.0) / 1000.0;
				//conversion du Double en String
				String champDollar = Double.toString(champEuro);
				//impression du String dans le champ Dollar
				dollarsTxt.setText(champDollar);
			}
			else {
				//changement du sens de la fleche
				fleche.setText(" <= ");
				//conversion du String du champ en Double
				double champDollar = Double.parseDouble(dollarsTxt.getText());
				//conversion de dollar a euro
				champDollar = this.toEuros(champDollar);
				//arrondissement du Double a trois decimaux
				champDollar = Math.round(champDollar * 1000.0) / 1000.0;
				//conversion du Double en String
				String champEuro = Double.toString(champDollar);
				//impression du String dans le champ Euro
				eurosTxt.setText(champEuro);
			}
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
