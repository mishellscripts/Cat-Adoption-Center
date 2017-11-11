public class CatAdoptionCenter {	
	public static void main(String[] args) {
		CatAdoptionModel model = new CatAdoptionModel();
		model.update(new WelcomePanel(model));
		CatAdoptionFrame frame = new CatAdoptionFrame(model);
		model.attach(frame);
	}
}