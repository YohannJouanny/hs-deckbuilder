import vue.Vue;
import model.Model;



public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		new Vue(model);
	}
}
