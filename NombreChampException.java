import javax.swing.JOptionPane;

public class NombreChampException extends Exception {
	public NombreChampException() {
		JOptionPane.showMessageDialog(null,"Aucun champ ne peut être à 0");
	}

	public NombreChampException(String champ) {
		JOptionPane.showMessageDialog(null,"Aucun champ ne peut être à 0\n" +
		"Problème : champ " + champ);
	}
}
