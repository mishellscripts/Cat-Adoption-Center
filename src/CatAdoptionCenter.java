public class CatAdoptionCenter {	
	public static void main(String[] args) {
		CatAdoptionModel model = new CatAdoptionModel();
		LocationFrame frame = new LocationFrame(model);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}